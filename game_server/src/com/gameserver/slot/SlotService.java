package com.gameserver.slot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.core.util.MathUtils;
import com.core.uuid.UUIDType;
import com.db.model.SlotEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.player.Player;
import com.gameserver.redis.enums.RedisKey;
import com.gameserver.redis.enums.RedisSlotKey;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.pojo.DemoSlot;
import com.gameserver.slot.pojo.PlayerSlotSenceBet;
import com.gameserver.slot.template.BoxTemplate;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ReelTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotJackpotNewTemplate;
import com.gameserver.slot.template.SlotJackpotTemplate;
import com.gameserver.slot.template.SlotsListTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.slot.template.WinMultipleTemplate;


/**
 * slot service
 * @author wayne
 *
 */
public class SlotService implements InitializeRequired,AfterInitializeRequired{

	public static int MAX_BOX_NUM=4;
	/** 5个JackPot 获取彩金 **/
	public static int JackPort = 5;
	
	private Logger logger = Loggers.slotLogger;
	
	/**模板服务*/
	private TemplateService templateService;

	// slotsList.xls 数据组合==============================================
	
	/**机器模板列表     初始老虎机列表*/
	private List<SlotsListTemplate> slotsListTemplateList = new ArrayList<SlotsListTemplate>();
	
	/**key:ID value: SlotsList **/
	private Map<Integer,SlotsListTemplate>  slotsListTemplateMap = new HashMap<Integer,SlotsListTemplate>();
	
	/**key:ID value:(押注筹码  所需要等级) **/
	private Map<Integer,Map<Integer,Integer>> slotsListBetLvMap = new HashMap<Integer,Map<Integer,Integer>>();
	
	/**老虎机类型  老虎机 **/
	private Map<Integer,List<SlotsListTemplate>> slotMapList = new HashMap<Integer,List<SlotsListTemplate>>();
	
	private Map<Integer,Integer> slotTypeOPen = new HashMap<Integer,Integer>();
	
	//payLines.xls ====================================================
	
	/**连线模板列表  */
	private List<PaylinesTemplate> paylinesTemplateList = new ArrayList<PaylinesTemplate>();
	
	// 需要修改 加 连线类型
    /** key: 连线类型 1 3*5老虎机  2 4*5 老虎机 value: 连线信息 **/
	private Map<Integer,List<PaylinesTemplate>> paylinesTemplateMap = new HashMap<Integer,List<PaylinesTemplate>>();
	
	
	// 老虎机实例=================================
	
	/**老虎机ID 老虎机 **/
	private Map<Integer,Slot> slotMap = new HashMap<Integer,Slot>();
	
	//========================================
	
	
	/** 卷轴   key:老虎机类型  value:(位置(元素)) */
	private Map<Integer,List<List<SlotsTemplate>>> scrollListListMap = new HashMap<Integer,List<List<SlotsTemplate>>>(); 
	
	//需要修改 加等级区间
	/**key:老虎机类型  value:(key:等级区间 value:(List(图标元素))) **/
	private Map<Integer,Map<String,List<List<SlotsTemplate>>>> scrollListListMapNew = new HashMap<Integer,Map<String,List<List<SlotsTemplate>>>>(); 
	
	
	/**赔率列表*/
	private Map<Integer,List<PayTemplate>> payTemplateListMap = new HashMap<Integer,List<PayTemplate>>();
	
	/**元素列表*/
	private Map<Integer,List<SlotsTemplate>> slotsTemplateListMap = new HashMap<Integer,List<SlotsTemplate>>();
	/**老虎机类型  元素 类型*/
	private Map<Integer,Map<Integer,Integer>> slotsTurnTemplateListMap = new HashMap<Integer,Map<Integer,Integer>>();
	
	/**scatter列表*/
	private List<ScatterTemplate> scatterTemplateList = new ArrayList<ScatterTemplate>();
	//需要修改 加老虎机类型
	/**key:老虎机类型    value:老虎机ScatterTemplate **/
	private Map<Integer,List<ScatterTemplate>> scatterTemplateMap = new HashMap<Integer,List<ScatterTemplate>>();
	
	/**scatter 箱子权重列表*/
	private List<BoxTemplate> boxTemplateList = new ArrayList<BoxTemplate>();
	//需要修改 加老虎机类型 等级区间
	/**key:老虎机类型    value:（key:等级区间 value:(BoxTemplate)）**/
    private Map<Integer,Map<String,List<BoxTemplate>>> boxTemplateMap = new HashMap<Integer,Map<String,List<BoxTemplate>>>();
	
	
	private int[] boxWeightArr;
	
	//需要修改 老虎机类型 等级区间 权重公式
	 private Map<Integer,Map<String,int[]>> boxWeightArrMap = new HashMap<Integer,Map<String,int[]>>();
	
	
	//老虎机彩金信息=================================
	
	/** key:ID value:模板数据**/
	private Map<Integer,SlotJackpotTemplate> slotJackpotTemplateMap = new HashMap<Integer,SlotJackpotTemplate>();
	
	/** key:slotType value:模板数据**/
	private Map<Integer,List<SlotJackpotNewTemplate>> slotJackpotNewTemplateMap = new HashMap<Integer,List<SlotJackpotNewTemplate>>();
	
	private Map<Integer,Map<Integer,List<SlotJackpotTemplate>>> slotJackpotMapList = new HashMap<Integer,Map<Integer,List<SlotJackpotTemplate>>>();
	
	//老虎机 竞技内存 数据结构==================================
	
	/**老虎机ID 所在玩家角色ID角色ID 广播的时候用 进入出来的时候删除数据  **//*
	private Map<Integer,List<Long>> slotHumanIds = new ConcurrentHashMap<Integer,List<Long>>();
	
	*//**老虎机ID 进入 退出调用平凡 **//*
	private Map<Integer,List<HumanRank>> humanRankMap = new ConcurrentHashMap<Integer,List<HumanRank>>();
	
	
	*//**老虎机ID 竞赛奖池 结算的时候清空 **//*
	private Map<Integer,Long> mapSngValue = new ConcurrentHashMap<Integer,Long>();
	
	*//**竞赛开启时间 **//*
	private volatile long sngStartTime = Globals.getTimeService().now();
	
	private AtomicBoolean fly = new AtomicBoolean(false);*/
	
	//老虎机竞赛方法==================================
	
	//马来网红数据=============================================
	
	private List<WinMultipleTemplate> listWinMultipleTemplate = new ArrayList<WinMultipleTemplate>();
	private int[] weightWinMultiple;
	
	
	
	
	/**
	 * 用户在 哪个老虎机 的哪个 场景 的 哪个 倍数 下
	 */
	
	private List<PlayerSlotSenceBet> playerSlotSenceBetList = new ArrayList<PlayerSlotSenceBet>();
	
	
	/**
	 * 每摇一次老虎机调用这个方法 
	 * @param slotId
	 * @param passportId
	 * @param tempReward
	 */
	/*public synchronized void putData(Player player,int slotId,long passportId, long tempReward){
		
		if(!fly.get()){
			return;
		}
		
		if(humanRankMap.containsKey(slotId)){
			List<HumanRank> list = humanRankMap.get(slotId);
			boolean fly = false;
			for(HumanRank hr : list){
				if(hr.getPassportId() == passportId){
					hr.setValue(hr.getValue()+tempReward);
					fly = true;
					break;
				}
			}
			if(!fly){
				 HumanRank hr = new HumanRank();
				 hr.setPassportId(passportId);
				 hr.setImg(player.getImg());
				 hr.setValue(tempReward);
				 list.add(hr);
			}
			
			Collections.sort(list);
			for(int i = 0;i < list.size();i++){
				HumanRank humanRank = list.get(i);
				if(humanRank.getPassportId() == passportId && i <= 3){
					//广播给其他玩家 前三名变化 广播给其他玩家
					sendRank(slotId);
					break;
				}
				if(i > 3){//后面的不广播
					break;
				}
			}
		}else{
			List<HumanRank> list = new ArrayList<HumanRank>();
			 HumanRank hr = new HumanRank();
			 hr.setPassportId(passportId);
			 hr.setImg(player.getImg());
			 hr.setValue(tempReward);
			 list.add(hr);
			 humanRankMap.put(slotId, list);
			 
			//广播给其他玩家
		    sendRank(slotId);
			
		}

	}*/
	
	/**
	 * 添加竞赛奖金池
	 * 在redis广播接收那里也设置一下
	 * @param slotId
	 * @param bonuts
	 */
	/*public synchronized void putBonuts(int slotId,long bonuts){
		if(mapSngValue.containsKey(slotId)){
			mapSngValue.put(slotId, mapSngValue.get(slotId)+bonuts);
		}else{
			mapSngValue.put(slotId, bonuts);
		}
		
		if(fly.get()){//比赛开始才可以广播
			//广播给其他玩家
			sendBonusData(slotId);
		}
		
	}*/
	
	/**
	 * 进入老虎机
	 * @param slotId
	 * @param passportId
	 */
	/*public synchronized void enterSlot(Player player,int slotId,long passportId,String img){
		 if(slotHumanIds.containsKey(slotId)){
			 slotHumanIds.get(slotId).add(passportId);
		 }else{
			 List<Long> list = new CopyOnWriteArrayList<Long>();
			 list.add(passportId);
			 slotHumanIds.put(slotId, list);
		 }
		 
		 
		 if(humanRankMap.containsKey(slotId)){
			 List<HumanRank> list = humanRankMap.get(slotId);
			 boolean fly = false;
			 for(HumanRank h : list){
				 if(h.getPassportId() == passportId){
					 fly = true;
					 break;
				 }
			 }
			 if(!fly){
				 HumanRank hr = new HumanRank();
				 hr.setPassportId(passportId);
				 hr.setImg(img);
				 hr.setValue(0);
				 list.add(hr);
			 }
		 }else{
			 List<HumanRank> list = new ArrayList<HumanRank>();
			 HumanRank hr = new HumanRank();
			 hr.setPassportId(passportId);
			 hr.setImg(img);
			 hr.setValue(0);
			 list.add(hr);
			 humanRankMap.put(slotId, list);
		 }
        
		 
		 HumanRank rank = new HumanRank();
		 rank.setImg(img);
		 rank.setPassportId(passportId);
		 redisRankSlotType(slotId,0,rank);
		 
		 sendBonusDataOneself(player,slotId);
		 sendRankOneself(player,slotId);
		 
	}*/
	
	/**
	 * 有序集合
	 * @param slotId
	 * @param value
	 * @param humanRank
	 */
	/*public void redisRankSlotType(int slotId,long value,HumanRank humanRank){
		
		JedisPool pool = Globals.getRedisService().getJedisPool();
		
		Jedis jedis = pool.getResource();
		try{
			String key = RedisSlotKey.slotIdRank.getKey()+slotId;
			jedis.zadd(key, value, JSON.toJSONString(humanRank));
		}finally{
			pool.returnResourceObject(jedis);
		}
	}*/
	
	/**
	 * 退出老虎机
	 * @param slotId
	 * @param passportId
	 */
	/*public synchronized void outSlot(int slotId,long passportId){
		if(slotHumanIds.containsKey(slotId)){
			List<Long> list = slotHumanIds.get(slotId);
			for(long id : list){
				 if(id == passportId){
					 list.remove(id);
					 break;
				 }
			}
		}
	
	}*/
	
	/**
	 * 清空ID
	 * @param passportId
	 */
	/*public synchronized void loginOutClear(long passportId){
		
		for(List<Long> list : slotHumanIds.values()){
			  for(long id : list){
				   if(id == passportId){
					   list.remove(id);
					   break;
				   }
			  }
		}
		
	}*/
	
	
	
	/**
	 * 比赛结束 结算数据
	 */
	/*public synchronized void clear(){
		//修改内存
		fly.compareAndSet(true, false);
		
		
		//key:老虎机类型  value:需要广播的消息
		Map<Integer,SlotSngView> viewMap = new HashMap<Integer,SlotSngView>();
		
		for(Entry<Integer,List<HumanRank>> en : humanRankMap.entrySet()){
			
			       int slotId = en.getKey();
		
			       //前三名玩家
			       List<HumanRank> list = en.getValue();
			        
			       if(mapSngValue.containsKey(slotId) && slotsListTemplateMap.containsKey(slotId)){
			    	   //这个老虎机的奖金池
			    	   long allBonus = mapSngValue.get(slotId);
			    	   
			    	   //老虎机模板
			    	   SlotsListTemplate template = slotsListTemplateMap.get(slotId);
			    	   
			    	  float firstReward = template.getFirstReward()/10000f;
			    	  float secondReward = template.getSecondReward()/10000f;
			    	  float thirdReward = template.getThirdReward()/10000f;
			    	  
			    	  for(int i = 0;i < list.size();i++){
			    		  HumanRank hr = list.get(i);
			    		  long valueData = hr.getValue();
			    		  if(valueData > 0){
			    			  long getbonus = 0;
				    		  switch (i) {
							case 0://第一名大厅展示处理
								getbonus = (long)Math.floor(firstReward*allBonus);
								
								int slotType = template.getType();
								
								if(viewMap.containsKey(slotType)){
									SlotSngView view = viewMap.get(slotType);
									if(view.getBonus() < getbonus){
										view.setBonus(getbonus);
										view.setImg(hr.getImg());
									}
								}else{
									SlotSngView view = new SlotSngView();
									view.setSlotType(slotType);
									view.setImg(hr.getImg());
									view.setBonus(getbonus);
									viewMap.put(slotType, view);
								}
						   Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(hr.getPassportId());
						      if(player != null){//成就系统
						    	  player.getHuman().getHumanAchievementManager().updateSlotRanking(template.getType());
						      }
								break;
							case 1:
								getbonus = (long)Math.floor(secondReward*allBonus);
								break;
							case 2:
								getbonus = (long)Math.floor(thirdReward*allBonus);
								break;
							default:
								break;
							}
				    	Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(hr.getPassportId());
				    	if(player != null && getbonus != 0){//奖励前3名玩家钱
				    		Human human = player.getHuman();
				    	    human.giveMoney(getbonus, Currency.GOLD, true, LogReasons.GoldLogReason.SLOTSNGBONUS, LogReasons.GoldLogReason.SLOTSNGBONUS.getReasonText(), -1, 1);	
				    	   }
				    	 hr.setValue(0);//清空数据
			    		}
			    		 
			     }
			    	  
			  }
			 // sendRank(slotId);
			  mapSngValue.put(slotId, 0l);
			 // sendBonusData(slotId);
		}
		
		GCSlotSngView messsage = new GCSlotSngView();
		List<SlotSngView> list = new ArrayList<SlotSngView>();
		for(SlotSngView view : viewMap.values()){
			list.add(view);
		}
		messsage.setSlotSngView(list.toArray(new SlotSngView[list.size()]));
		sendSngView(messsage);
		
		humanRankMap.clear();

	}*/
	
	/**比赛开始 **/
	/*public synchronized void startSng(){
		fly.compareAndSet(false, true);
		sngStartTime = Globals.getTimeService().now();
		
		for(int slotId : slotHumanIds.keySet()){
			sendBonusData(slotId);
		}
	}
	*/

	
	/**发送老虎机竞赛排行榜信息 **/
	/*public void sendRank(int slotId){
		
		if(humanRankMap.containsKey(slotId) && slotHumanIds.containsKey(slotId)){
			List<HumanRank> list = humanRankMap.get(slotId);
			
			GCSlotGetRank message = new GCSlotGetRank();
			
			List<SlotRankData> dataList = new ArrayList<SlotRankData>();
			
			for(int i = 0 ; i < list.size();i++){
				if(i < 3){
					HumanRank hr = list.get(i);
					if(hr.getValue() > 0){
						SlotRankData data = new SlotRankData();
						data.setTournamentType(slotId);
						data.setRank(i+1);
						data.setImg(hr.getImg());
						data.setBonus(hr.getValue());
						data.setPassportId(hr.getPassportId());
						dataList.add(data);
					}
				}else{
					break;
				}
			}
			message.setSlotRankData(dataList.toArray(new SlotRankData[dataList.size()]));
			
			List<Long> listId = slotHumanIds.get(slotId);
			for(long passportId : listId){
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
				if(player != null){
					player.sendMessage(message);
				}
			}

		}

	}*/
	
	/**
	 * 广播比赛奖池数据
	 * @param slotId
	 */
	/*public void sendBonusData(int slotId){
		if(mapSngValue.containsKey(slotId) && slotHumanIds.containsKey(slotId)){
			long allBonus = mapSngValue.get(slotId);
			GCSlotGetSngbonus data = new GCSlotGetSngbonus();
			data.setAllBonus(allBonus);
			data.setSlotId(slotId);
			data.setStartTime(sngStartTime);
			List<Long> listId = slotHumanIds.get(slotId);
			for(long passportId : listId){
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
				if(player != null){
					player.sendMessage(data);
				}
			}
		}
	}*/
	
	/**进入老虎机的时候拉去排行榜数据 **/
	/*public void sendRankOneself(Player player,int slotId){
		if(humanRankMap.containsKey(slotId) && slotHumanIds.containsKey(slotId)){
			List<HumanRank> list = humanRankMap.get(slotId);
			
			GCSlotGetRank message = new GCSlotGetRank();
			
			List<SlotRankData> dataList = new ArrayList<SlotRankData>();
			
			for(int i = 0 ; i < list.size();i++){
				if(i < 3){
					HumanRank hr = list.get(i);
					if(hr.getValue() > 0){
						SlotRankData data = new SlotRankData();
						data.setTournamentType(slotId);
						data.setRank(i+1);
						data.setImg(hr.getImg());
						data.setBonus(hr.getValue());
						data.setPassportId(hr.getPassportId());
						dataList.add(data);
					}
				}else{
					break;
				}
			}
			message.setSlotRankData(dataList.toArray(new SlotRankData[dataList.size()]));
			
			player.sendMessage(message);

		}

	}*/
	
	
	
	/**进入老虎机给自己发送 **/
	/*public void sendBonusDataOneself(Player player,int slotId){
		
		if(!mapSngValue.containsKey(slotId)){
			mapSngValue.put(slotId, 0l);
		}
		
		if(slotHumanIds.containsKey(slotId)){
			long allBonus = mapSngValue.get(slotId);
			
			GCSlotGetSngbonus data = new GCSlotGetSngbonus();
			data.setAllBonus(allBonus);
			data.setSlotId(slotId);
			data.setStartTime(sngStartTime);
			
			player.sendMessage(data);
		}
	}*/
	
	
	
	/**
	 * 结算广播
	 * @param message
	 */
	/*public void sendSngView(GCSlotSngView message){
		for(Player player : Globals.getOnlinePlayerService().getOnlinePlayersMap().values()){
			player.sendMessage(message);
		}
	}
	*/
 //===============================================
	
	public Map<Integer,Slot> getSlotList(){
		return slotMap;
	}
	
	/**
	 * 获取老虎机彩金
	 * @param slotId
	 * @return
	 */
	public long getSlotJack(int slotId){
		if(slotMap.containsKey(slotId)){
			return slotMap.get(slotId).getJackpot();
		}
		return 0l;
	}
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		logger.info("slot service init");
	}
	
	/**
	 * 服务器启动调用
	 */
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		logger.info("slot service  after init");
		this.templateService = Globals.getTemplateService();
		//init slot template list
		this.slotsListTemplateList.addAll(this.templateService.getAll(SlotsListTemplate.class).values());
		
		for(SlotsListTemplate slotsList : slotsListTemplateList){
			slotsListTemplateMap.put(slotsList.getId(),slotsList);
			Map<Integer,Integer> map = new HashMap<Integer,Integer>();
			map.put(slotsList.getBet1(), slotsList.getBet1Lv());
			map.put(slotsList.getBet2(), slotsList.getBet2Lv());
			map.put(slotsList.getBet3(), slotsList.getBet3Lv());
			map.put(slotsList.getBet4(), slotsList.getBet4Lv());
			map.put(slotsList.getBet5(), slotsList.getBet5Lv());	
			slotsListBetLvMap.put(slotsList.getId(), map);
			
			if(slotMapList.containsKey(slotsList.getType())){
				slotMapList.get(slotsList.getType()).add(slotsList);
			}else{
				List<SlotsListTemplate> list = new ArrayList<SlotsListTemplate>();
				list.add(slotsList);
				slotMapList.put(slotsList.getType(), list);
			}
			
			int openLv = slotsList.getOpenLv();
			int type = slotsList.getType();
			if(slotTypeOPen.containsKey(type)){
				int op = slotTypeOPen.get(type);
				if(op > openLv){
					slotTypeOPen.put(type, openLv);
				}
			}else{
				slotTypeOPen.put(type, openLv);	
			}
			
		}
		
		this.paylinesTemplateList.addAll(this.templateService.getAll(PaylinesTemplate.class).values());
		
		//排序paylines升序
		Collections.sort(this.paylinesTemplateList, new Comparator<PaylinesTemplate>(){

			@Override
			public int compare(PaylinesTemplate arg0, PaylinesTemplate arg1) {
				// TODO Auto-generated method stub
				if(arg0.getId()==arg1.getId())
					return 0;
				if(arg0.getId()>arg1.getId())
					return 1;
				else{
					return -1;
				}
			
			}
			
		});
		
		for(PaylinesTemplate temPlate : paylinesTemplateList){
			int lineType = temPlate.getLineType();
			List<PaylinesTemplate> list = paylinesTemplateMap.get(lineType);
			if(list == null){
				list = new ArrayList<PaylinesTemplate>();
				paylinesTemplateMap.put(lineType, list);
			}
			list.add(temPlate);
		}
		
		
		
		//排序reel升序
		List<ReelTemplate> tempReelTemplateList = new ArrayList<ReelTemplate>();
		
		tempReelTemplateList.addAll(this.templateService.getAll(ReelTemplate.class).values());
		
		Collections.sort(tempReelTemplateList, new Comparator<ReelTemplate>(){
			@Override
			public int compare(ReelTemplate arg0, ReelTemplate arg1) {
				// TODO Auto-generated method stub
				if(arg0.getTurn()==arg1.getTurn())
					return 0;
				if(arg0.getTurn()>arg1.getTurn())
					return 1;
				else{
					return -1;
				}
			
			}
			
		});
	
		
		
		//init reel template list map 
		for(ReelTemplate reelTemplate:tempReelTemplateList){
			
			//老虎机类型
			int slotType = reelTemplate.getSlotsNum();
			
			int levelDown = reelTemplate.getLevelDown();
			int levelUp = reelTemplate.getLevelUp();
			
			String key = levelDown+"_"+levelUp;
			
			Map<String,List<List<SlotsTemplate>>> keymap = scrollListListMapNew.get(slotType);
			
			if(keymap == null){
				keymap = new HashMap<String,List<List<SlotsTemplate>>>();
				scrollListListMapNew.put(slotType, keymap);
			}
			
			//老虎机类型SlotsNum
			List<List<SlotsTemplate>> tempScrollListList = keymap.get(key);
			if(tempScrollListList == null){
				tempScrollListList = new ArrayList<List<SlotsTemplate>>();
				for(int i=0;i<reelTemplate.getSlotsListTemplate().getColumns();i++){
					tempScrollListList.add(new ArrayList<SlotsTemplate>());
				}
				keymap.put(key, tempScrollListList);
			}
			
             for(int i=0;i<reelTemplate.getSlotsListTemplate().getColumns();i++){
				
				List<SlotsTemplate> tempScrollList= tempScrollListList.get(i);
				
				tempScrollList.add(reelTemplate.getSlotsTemplateList().get(i));
				
			}
			
			
			
	        //老虎机类型SlotsNum
			/*List<List<SlotsTemplate>> tempScrollListList = this.scrollListListMap.get(reelTemplate.getSlotsNum());
			
			if(tempScrollListList == null){
				tempScrollListList = new ArrayList<List<SlotsTemplate>>();
				for(int i=0;i<reelTemplate.getSlotsListTemplate().getColumns();i++){
					tempScrollListList.add(new ArrayList<SlotsTemplate>());
				}
				this.scrollListListMap.put(reelTemplate.getSlotsNum(), tempScrollListList);
			}
			
			for(int i=0;i<reelTemplate.getSlotsListTemplate().getColumns();i++){
				
				List<SlotsTemplate> tempScrollList= tempScrollListList.get(i);
				
				tempScrollList.add(reelTemplate.getSlotsTemplateList().get(i));
			}*/
			
			
		}
		
		/*Loggers.slotLogger.info(JSON.toJSONString(scrollListListMapNew.get(3).get("1_10")));
		Loggers.slotLogger.info(scrollListListMapNew.get(3).get("1_10").size());
		Loggers.slotLogger.info(JSON.toJSONString(scrollListListMapNew.get(3).get("1_10").get(1)));
		Loggers.slotLogger.info(scrollListListMapNew.get(3).get("1_10").get(1).size());*/
		
		//init pay template list map
		for(PayTemplate payTemplate :this.templateService.getAll(PayTemplate.class).values()){
			List<PayTemplate> tempList = this.getPayTemplateListBySlotType(payTemplate.getSlotsNum());
			if(tempList == null){
				tempList = new ArrayList<PayTemplate>();
				this.payTemplateListMap.put(payTemplate.getSlotsNum(), tempList);
			}
			tempList.add(payTemplate);
		}
		
		//排序pay 降序
		for(List<PayTemplate> tempPayList:this.payTemplateListMap.values()){
			
			Collections.sort(tempPayList, new Comparator<PayTemplate>(){

				@Override
				public int compare(PayTemplate arg0, PayTemplate arg1) {
					// TODO Auto-generated method stub
					if(arg0.getPay()==arg1.getPay())
						return 0;
					if(arg0.getPay()>arg1.getPay())
						return -1;
					else{
						return 1;
					}
				}
			});
			
		}
		
		//init slot elements list map
		for(SlotsTemplate slotsTemplate:this.templateService.getAll(SlotsTemplate.class).values()){
			 int slotType = slotsTemplate.getSlotsNum();
			 int turn = slotsTemplate.getTurn();
			 int type = slotsTemplate.getType();
			List<SlotsTemplate> tempList = this.getSlotsTemplateListBySlotType(slotType);
			if(tempList == null){
				tempList = new ArrayList<SlotsTemplate>();
				this.slotsTemplateListMap.put(slotsTemplate.getSlotsNum(), tempList);
			}
			tempList.add(slotsTemplate);
			
			Map<Integer,Integer> map = slotsTurnTemplateListMap.get(slotType);
			
			if(map == null){
				map = new HashMap<Integer,Integer>();
				slotsTurnTemplateListMap.put(slotType, map);
			}
			map.put(turn, type);
		}
		
		//init scatter list
		this.scatterTemplateList.addAll(this.templateService.getAll(ScatterTemplate.class).values());
		//scatter 降序
		Collections.sort(this.scatterTemplateList, new Comparator<ScatterTemplate>(){

			@Override
			public int compare(ScatterTemplate arg0, ScatterTemplate arg1) {
				// TODO Auto-generated method stub
				if(arg0.getPay()==arg1.getPay())
					return 0;
				if(arg0.getPay()>arg1.getPay())
					return -1;
				else{
					return 1;
				}
			}
		});
		
		for(ScatterTemplate template : scatterTemplateList){
			int slotType = template.getSlotsNum();
			List<ScatterTemplate> list = scatterTemplateMap.get(slotType);
			if(list == null){
				list = new ArrayList<ScatterTemplate>();
				scatterTemplateMap.put(slotType, list);
			}
			list.add(template);
		}
		
		//init scatter box list
		this.boxTemplateList.addAll(this.templateService.getAll(BoxTemplate.class).values());
		//scatter box 升序
		Collections.sort(this.boxTemplateList, new Comparator<BoxTemplate>(){

			@Override
			public int compare(BoxTemplate arg0, BoxTemplate arg1) {
				// TODO Auto-generated method stub
				if(arg0.getId()==arg1.getId())
					return 0;
				if(arg0.getId()>arg1.getId())
					return 1;
				else{
					return -1;
				}
			}
		});
		this.boxWeightArr = new int[this.boxTemplateList.size()];
		for(int i=0;i<this.boxTemplateList.size() ;i++){
			this.boxWeightArr[i] = this.boxTemplateList.get(i).getValue();
		}
		
		for(BoxTemplate template : boxTemplateList){
			 int slotType = template.getSlotsNum();
			 
			 int levelDown = template.getLevelDown();
			 int levelUp = template.getLevelUp();
			 String key = levelDown+"_"+levelUp;
			 
			 Map<String,List<BoxTemplate>> map = boxTemplateMap.get(slotType);
			 if(map == null){
				 map = new HashMap<String,List<BoxTemplate>>();
				 boxTemplateMap.put(slotType, map);
			 }
			 List<BoxTemplate> list = map.get(key);
			 if(list ==null){
				 list = new ArrayList<BoxTemplate>();
				 map.put(key, list);
			 }
			 list.add(template);
		}
		
		for(Entry<Integer,Map<String,List<BoxTemplate>>> en : boxTemplateMap.entrySet()){
			      int slotType = en.getKey();
			      Map<String,List<BoxTemplate>> map = en.getValue();
			      for(Entry<String,List<BoxTemplate>> enn : map.entrySet()){
			    	  String key = enn.getKey();
			    	  List<BoxTemplate> list = enn.getValue();
			    	  int[] qz = new int[list.size()];
			    	  for(int i=0;i<list.size() ;i++){
			    		  qz[i] = list.get(i).getValue();
			  		}
			    	  
			    	Map<String,int[]> mapValue = boxWeightArrMap.get(slotType);
					if(mapValue == null){
						 mapValue = new HashMap<String,int[]>();
					      boxWeightArrMap.put(slotType, mapValue);
					   }
					mapValue.put(key, qz);
			      }
		}
		
		/**
		 * old jackpot
		 */
		
		slotJackpotTemplateMap = templateService.getAll(SlotJackpotTemplate.class);
		
		for(SlotJackpotTemplate sjt : slotJackpotTemplateMap.values()){
			
			int slotId = sjt.getSlotId();
			
			int bet = getBetByType(slotId,sjt.getBet());
			
			//int reelId = sjt.getReel();
			
			if(slotJackpotMapList.containsKey(slotId)){
				Map<Integer,List<SlotJackpotTemplate>> map = slotJackpotMapList.get(slotId);
				if(map.containsKey(bet)){
					map.get(bet).add(sjt);
				}else{
					List<SlotJackpotTemplate> list = new ArrayList<SlotJackpotTemplate>();
					list.add(sjt);
					map.put(bet, list);
				}
			}else{
				List<SlotJackpotTemplate> list = new ArrayList<SlotJackpotTemplate>();
				list.add(sjt);
				Map<Integer,List<SlotJackpotTemplate>> map = new HashMap<Integer,List<SlotJackpotTemplate>>();
				map.put(bet, list);
				slotJackpotMapList.put(slotId, map);
			}
			
			
		}
		
		
		/**
		 * new jackpot
		 */
		
		Map<Integer, SlotJackpotNewTemplate>  rSlotJackpotNewTemplateMap = templateService.getAll(SlotJackpotNewTemplate.class);
		int startNum = -1;
		for(SlotJackpotNewTemplate slotJackpotNewTemplate:rSlotJackpotNewTemplateMap.values()){
			int type = slotJackpotNewTemplate.getSlotNum();
			if(startNum != type){
				startNum = type;
				List<SlotJackpotNewTemplate> SlotJackpotNewTemplateList = new ArrayList<SlotJackpotNewTemplate>();
				SlotJackpotNewTemplateList.add(slotJackpotNewTemplate);
				slotJackpotNewTemplateMap.put(type, SlotJackpotNewTemplateList);
			}else{
				List<SlotJackpotNewTemplate> SlotJackpotNewTemplateList = slotJackpotNewTemplateMap.get(type);
				SlotJackpotNewTemplateList.add(slotJackpotNewTemplate);
			}
		}
		
		
		//init slots 
		initSlots();
	
	    listWinMultipleTemplate.addAll(this.templateService.getAll(WinMultipleTemplate.class).values());
	    
	    weightWinMultiple = new int[listWinMultipleTemplate.size()];
	    for(int i = 0;i < listWinMultipleTemplate.size();i++){
	    	WinMultipleTemplate wt = listWinMultipleTemplate.get(i);
	    	int weight = wt.getWeight();
	    	weightWinMultiple[i] = weight;
	    }
	   
	    
	}
	
	private int getBetByType(int slotsId,int betType){
		if(slotsListTemplateMap.containsKey(slotsId)){
			SlotsListTemplate template = slotsListTemplateMap.get(slotsId);
			switch (betType) {
			case 1:
				return template.getBet1();
			case 2:
				return template.getBet2();
			case 3:
				return template.getBet3();
			case 4:
				return template.getBet4();
			case 5:
				return template.getBet5();
			default:
				break;
			}
			return 0;
		}
		return 0;
	}
	
	/**
	 * 根据ID 获取老虎机类型
	 * @param id
	 * @return
	 */
	public int getTypeById(int id){
		if(slotsListTemplateMap.containsKey(id)){
			return slotsListTemplateMap.get(id).getType();
		}
		return 0;
	}
	
	/**
	 * 玩家等级判断
	 * @param id
	 * @param rolelevel
	 * @return  false 判断失败
	 */
	public boolean isLevelById(int id,long rolelevel){
		if(slotsListTemplateMap.containsKey(id)){
			return slotsListTemplateMap.get(id).getOpenLv() <=  rolelevel;
		}
		return false;
	}
	
	/**
	 * 根据ID获取老虎机数据
	 * @param id
	 * @return
	 */
	public SlotsListTemplate getslotTemplateMap(int id){
		return slotsListTemplateMap.get(id);
	}
	
	/**
	 * 最小下注额判断
	 * @param id
	 * @param roleGold
	 * @return  false 判断失败
	 */
	public boolean isGoldById(int id,long roleGold){
		if(slotsListTemplateMap.containsKey(id)){
			return slotsListTemplateMap.get(id).getBet1() <=  roleGold;
		}
		return false;
	}
	
	/**
	 *  判断玩家是否可以押注
	 * @param id
	 * @param bet 押注筹码
	 * @param rolelv 玩家等级
	 * @return  false: 判断失败
	 */
	public boolean isBetLv(int id,int bet,long rolelv){
		if(slotsListBetLvMap.containsKey(id)){
			Map<Integer,Integer> betLv = slotsListBetLvMap.get(id);
			if(betLv.containsKey(bet)){
			    return 	betLv.get(bet) <= rolelv;
			}
		}
		return false;
	}
	
	/**
	 * 获取指定类型的老虎机
	 * @param type
	 * @return
	 */
	public List<SlotsListTemplate> getListByType(int type){
		if(slotMapList.containsKey(type)){
			return slotMapList.get(type);
		}
		return new ArrayList<SlotsListTemplate>();
	}
	
	/**
	 * 彩金参数
	 * @param slotID
	 * @return
	 */
	public double getJackpotPoolPerById(int slotID){
		if(slotsListTemplateMap.containsKey(slotID)){
			return (double)slotsListTemplateMap.get(slotID).getJackpotPoolPer()/10000l;
		}
		return 0l;
	}
	
	/**
	 * 彩金累计参数
	 * @param slotID
	 * @return
	 */
	public double getJackpotAddPoolPerById(int slotID){
		if(slotsListTemplateMap.containsKey(slotID)){
			return (double)slotsListTemplateMap.get(slotID).getJackpotAddPoolPer()/10000l;
		}
		return 0l;
	}
	
	/**
	 * 获取彩金初始值
	 * @param slotID
	 * @return
	 */
	public long getJackpotOriValue(int slotID){
		if(slotsListTemplateMap.containsKey(slotID)){
			return slotsListTemplateMap.get(slotID).getJackpotOriValue();
		}
		return 0l;
	}
	
	
	
	/**
	 * 初始化老虎机
	 */
	private void initSlots(){
		
      List<SlotEntity> slotEntityList = Globals.getDaoService().getSlotDao().getAllSlots();
		
		for(SlotEntity slotEntity :slotEntityList){
			Slot slot = new Slot();
			slot.fromEntity(slotEntity);			
			if(slotEntity.getJackpot1() <=0){
				long jackpot1= getslotTemplateMap(slot.getTempleId()).getJackpotOriValue1();
				slot.setJackpot1(jackpot1);
				
			}
            if(slotEntity.getJackpot2() <= 0){
				long jackpot2= getslotTemplateMap(slot.getTempleId()).getJackpotOriValue2();
				slot.setJackpot2(jackpot2);
				
			}
            if(slotEntity.getJackpot3() <= 0){
				long jackpot3= getslotTemplateMap(slot.getTempleId()).getJackpotOriValue3();
				slot.setJackpot3(jackpot3);
				
			}
            if(slotEntity.getJackpot4() <= 0){
				long jackpot4= getslotTemplateMap(slot.getTempleId()).getJackpotOriValue4();
				slot.setJackpot4(jackpot4);
				
			}
             if(slotEntity.getJackpot5() <= 0){
				long jackpot5= getslotTemplateMap(slot.getTempleId()).getJackpotOriValue5();
				slot.setJackpot5(jackpot5);
				
			}
			
			
			
			slot.setType(this.getTypeById(slotEntity.getSlotType()));//设置老虎机的类型		
			slotMap.put(slot.getTempleId(), slot);
			slot.setModified();
		}
		for(SlotsListTemplate slotsListTemplate:slotsListTemplateList){
			Slot slot = getSlotByTemplateId(slotsListTemplate.getId());
			if(slot !=null){
				continue;
			}
			//创建新的老虎机
			slot = new Slot();
		
			long now = Globals.getTimeService().now();
			slot.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.SLOT));
			slot.setTempleId(slotsListTemplate.getId());
			slot.setJackpot(slotsListTemplate.getJackpotOriValue());//初始化彩金池
			slot.setCreateTime(Globals.getTimeService().now());
			slot.setInDb(false);
			slot.active();
			slot.setModified();
			slot.setType(slotsListTemplate.getType());//设置老虎机的类型		
			slotMap.put(slot.getTempleId(), slot);
		}
		
		if(Globals.getServerComm().isAuthority()){
			saveRedis();
		}
	}
	
	/**
	 * 放数据到redis
	 */
	private void saveRedis(){
        JedisPool pool = Globals.getRedisService().getJedisPool();
		Jedis jedis = pool.getResource();
		try{
			for(Slot slot : slotMap.values()){
				int id = slot.getTempleId();
				String key = RedisKey.SLOT.getKey()+id;
				jedis.hset(key, RedisSlotKey.jackpot.getKey(),String.valueOf(slot.getJackpot()));   
				jedis.hset(key, RedisSlotKey.cumuJackpot.getKey(),String.valueOf(slot.getCumuJackpot()));   
				jedis.hset(key, RedisSlotKey.stock.getKey(),String.valueOf(slot.getStock()));   
				jedis.hset(key, RedisSlotKey.spinTimes.getKey(),String.valueOf(slot.getSpinTimes())); 
			}
		}finally{
			jedis.close();
//			pool.returnResourceObject(jedis);
		}
	}
	
	
	/**
	 * 获取老虎机
	 * @param slotId  数据库里面的UUID
	 * @return
	 */
	public Slot getSlotById(long slotId){
		for(Entry<Integer,Slot> en : slotMap.entrySet()){
			Slot slot = en.getValue();
			if(slot.getDbId() == slotId)
				return slot;
		}
		return null;
	}
	

	
	/**
	 * 获取老虎机
	 * @param slotId
	 * @return
	 */
	public Slot getSlotByTemplateId(int slotId){
		return slotMap.get(slotId);
	}
	
	/**
	 * 获取赔率
	 */
	public List<PayTemplate> getPayTemplateListBySlotType(int slotType){
		return this.payTemplateListMap.get(slotType);
	}
	
	/**获取元素表
	 * 
	 */
	private List<SlotsTemplate> getSlotsTemplateListBySlotType(int slotType){
		return this.slotsTemplateListMap.get(slotType);
	}
	/**
	 * 根据元素类型获取 turn
	 * 获取元素表
	 * 
	 */
	public  int getTurnBySlotType(int slotType,int elementType){
		List<SlotsTemplate> turns = getSlotsTemplateListBySlotType(slotType);
		for(SlotsTemplate st :turns){
			if(elementType == st.getType()){
				return st.getTurn();
			}
		}
		return -1;
	}
	
	/**
	 * 获取老虎机元素类型
	 * @param slotType
	 * @param turn
	 * @return
	 */
   public int getTurnType(int slotType,int turn){
	   return slotsTurnTemplateListMap.get(slotType).get(turn);
   }
   
   public Map<Integer,Integer> getSlotElements(int slotType){
	   return slotsTurnTemplateListMap.get(slotType);
   }
	
	/**
	 * 进入老虎机
	 * @param player
	 * @param slot
	 */
	public void playerEnterSlot(Player player ,Slot slot){
		
		int oldSlotId = player.getHuman().getHumanSlotManager().getSlotId();
		if(oldSlotId != 0){
			//this.outSlot(oldSlotId, player.getPassportId());
			Slot oldSlot = Globals.getSlotService().getSlotByTemplateId(oldSlotId);
			Globals.getTournamentService().outSlot(slot.getType(),oldSlot.getType(),player.getPassportId());
		}
		player.getHuman().getHumanSlotManager().reset();//重置
		player.getHuman().getHumanSlotManager().setCurrentSlotId(slot.getDbId());//设置当前玩家所在老虎机
		player.getHuman().getHumanSlotManager().setSlotId(slot.getTempleId());
		SlotsListTemplate tempSlotsListTemplate= this.templateService.get(slot.getTempleId(), SlotsListTemplate.class);
		for(int i=0;i<tempSlotsListTemplate.getColumns();i++){
			player.getHuman().getHumanSlotManager().getCurrentSlotPosList().add(0);
		}
		
		
	    //this.enterSlot(player,slot.getTempleId(), player.getPassportId(), player.getImg());
	    Globals.getTournamentService().enterSlot(player, slot.getType(), player.getPassportId(), player.getImg());
	    /**
	     * 斯巴达 专用
	     */
	    Globals.getSpartaService().getBullet(slot.getType(), player.getHuman());
	}
	
	
	/**
	 * 退出老虎机
	 */
	public void playerExitSlot(Player player ,Slot slot){
		player.getHuman().getHumanSlotManager().setCurrentSlotId(0);
		player.getHuman().getHumanSlotManager().getCurrentSlotPosList().clear();
	}
	
	/**
	 * 获得出现元素
	 */
	public List<Integer> getSlotElementsBySlotPos(Human human){
		
		List<Integer> results = new ArrayList<Integer>();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
	
		Slot slot =  this.getSlotById(tempHumanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		
		//卷轴
		List<List<SlotsTemplate>> tempScrollListList = getSlotsTemplate(slot.getType(),human.getLevel());
		
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		
		for(int i=0;i<humanSlotManager.getCurrentSlotPosList().size();i++){
			
			//ith reel
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			
			int tempTurnBegin = humanSlotManager.getCurrentSlotPosList().get(i);
			//ith reel from j to j+row
			for(int j=0;j<tempSlotsListTemplate.getRows();j++){
				int tempTurn = tempTurnBegin+j;
				//翻页了
				tempTurn = tempTurn%tempScrollList.size();  
				
				results.add(tempScrollList.get(tempTurn).getTurn());
			}
		}
		
		return results;
	}
	
	/**
	 * 获得列表
	 */
	public List<SlotConnectInfo> getSlotConnectInfoListByHuman(Human human){
		
		List<SlotConnectInfo> tempSlotConnectInfoDataList= new ArrayList<SlotConnectInfo>();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
		
		Slot slot =  this.getSlotById(tempHumanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		//列
		int columns = tempSlotsListTemplate.getColumns();
		
		//获取连线类型
		int lineType = tempSlotsListTemplate.getLineType();
		
		List<PaylinesTemplate> listLineType = paylinesTemplateMap.get(lineType);
		
		//当前单线押注额
		int currBet = (int)tempHumanSlotManager.getCurrentBet()/tempSlotsListTemplate.getPayLinesNum();
				
		//获得连线的种类 20条线
		for(int i=0;i<tempSlotsListTemplate.getPayLinesNum();i++){
			
			//连线表顺序取
			PaylinesTemplate tempPaylinesTemplate = listLineType.get(i);
			
			//获得连线的元素列表（摇取得5个元素）
			List<SlotsTemplate> tempCombinationList =this.getCombinationListByHumanAndPayline(human,tempPaylinesTemplate,columns);
			
			//获得连线的奖励信息
			SlotConnectInfo tempSlotConnectInfo = this.getSlotConnectInfo(slot,tempCombinationList,currBet,human);
			
			if(tempSlotConnectInfo!=null){
				
				tempSlotConnectInfo.setPaylinesTemplate(tempPaylinesTemplate);
				
				tempSlotConnectInfoDataList.add(tempSlotConnectInfo);
			}
		}
		return tempSlotConnectInfoDataList;
	}
	
	public List<PaylinesTemplate> getPaylinesTemplate(int lineType){
		List<PaylinesTemplate> listLineType = paylinesTemplateMap.get(lineType);
		return listLineType;
	}
	
	/**
	 * 获得5个位置摇取元素 卷轴停的位置
	 * @param human
	 * @param paylinesTemplate 连线
	 * @return
	 */
	public List<SlotsTemplate> getCombinationListByHumanAndPayline(Human human,PaylinesTemplate paylinesTemplate,int columns){
		List<SlotsTemplate> tempCombinationList = new ArrayList<SlotsTemplate>();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
		
		Slot slot =  this.getSlotById(tempHumanSlotManager.getCurrentSlotId());
	
		//获取这一类型老虎机的5种图形组合 (位置 图标列表)
		List<List<SlotsTemplate>> tempScrollListList = getSlotsTemplate(slot.getType(),human.getLevel());
	    //paylinesTemplate.getPositionList().size()
		for(int i=0;i<columns;i++){//5个位置
			//获取这个位置的所有元素集合
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);//
			//ith reel current pos 当前随机的步数
			int tempIthReelPos = tempHumanSlotManager.getCurrentSlotPosList().get(i);
			
			int tempIthPayPos = tempIthReelPos+paylinesTemplate.getPositionList().get(i)-1;
			
			//获得选中位置
			tempIthPayPos = tempIthPayPos%tempScrollList.size();
	
			tempCombinationList.add(tempScrollList.get(tempIthPayPos));
		}
		return tempCombinationList;
	}
	
	public List<List<SlotsTemplate>> getSlotsTemplate(int slotType,long level){
		List<List<SlotsTemplate>> list = new ArrayList<List<SlotsTemplate>>();
		Map<String,List<List<SlotsTemplate>>> map = scrollListListMapNew.get(slotType);
		if(map != null){
			for(Entry<String,List<List<SlotsTemplate>>> en : map.entrySet()){
				String[] strs = en.getKey().split("_");
				if(Long.valueOf(strs[0]) <= level && level <= Long.valueOf(strs[1])){
					return en.getValue();
				}
			}
		}
		
		return list;
	}
	
	
	
	public List<SlotJackpotNewTemplate> getJackpotNewList(int type){
		List<SlotJackpotNewTemplate> slotJackpotNewList = slotJackpotNewTemplateMap.get(type);
		return slotJackpotNewList;
	}
	/**
	 * 获取赔率
	 * @param slot
	 * @param tempCombinationList 得到的5个元素
	 * @param currBet 当前单线押注
	 * @param human
	 * @return
	 */
	public SlotConnectInfo getSlotConnectInfo(Slot slot,List<SlotsTemplate> tempCombinationList ,int currBet,Human human){
		List<SlotJackpotNewTemplate> slotJackpotNewList = slotJackpotNewTemplateMap.get(slot.getType());
		//获取当前老虎机赔率数据
		List<PayTemplate> tempPayTemplateList = this.getPayTemplateListBySlotType(slot.getType());
		
		SlotConnectInfo tempSlotConnectInfo = new SlotConnectInfo();
		
		
		
		for(PayTemplate tempPayTemplate : tempPayTemplateList){//循环逐个比较
			
			boolean match = true;
		
			tempSlotConnectInfo.getPosList().clear();
			
			int multiple = 0;
			int jackport = 0;
			//5个位置逐个比较
			for(int i=0;i<tempPayTemplate.getCombinationList().size();i++){
				//图标ID
				int tempSlotTurn = tempPayTemplate.getCombinationList().get(i);
				if(tempSlotTurn ==0){
					continue;
				}
				//图标
				SlotsTemplate tempSlotsTemplate = tempCombinationList.get(i);
				
				//判断是不是wild
				SlotElementType elementType =  tempSlotsTemplate.getSlotElementType();
				if(
						elementType != SlotElementType.WILD1 &&
						elementType != SlotElementType.WILD2 &&
						elementType != SlotElementType.WILD3 &&
						elementType != SlotElementType.WILD4 &&
						elementType != SlotElementType.bigWILD &&
						elementType != SlotElementType.SLOTTYPE15 &&
						elementType != SlotElementType.SLOTTYPE20WILD &&
						elementType != SlotElementType.SLOTWILD26 &&
						elementType != SlotElementType.WILD27 &&
						elementType != SlotElementType.SLOTWILD31 &&
						elementType != SlotElementType.WILD && tempSlotsTemplate.getTurn() != tempSlotTurn){
					match=false;//这条Pay线不匹配
					break;
				}
				
				multiple = multipleDouble(multiple,elementType);
				
			
			/*	
			 * 
			 * 原先的关于jackpot的
			 * 
			 * //用于计算jackpot
				if(elementType == SlotElementType.JACKPOT){
					jackport++;
				}
				tempSlotConnectInfo.getPosList().add(i);//位置
			}
			
			if(match){//这个图标List 完全匹配
				if(multiple == 0){
					multiple = 1;
				}
				tempSlotConnectInfo.setPay(tempPayTemplate.getPay()*multiple);
				tempSlotConnectInfo.setPayId(tempPayTemplate.getId());
				//获取当前老虎机ID 获取当前押注的金额   获取当前赔率ID   currBet
				int slotsId = slot.getTempleId();
				SlotJackpotTemplate sjt = getSlotJackpotTemplate(slotsId,currBet,tempPayTemplate.getId());
				if(sjt != null){
					tempSlotConnectInfo.setSjt(sjt);
					if(jackport == JackPort){
						tempSlotConnectInfo.setJackPort(true);
					}
				}
				return tempSlotConnectInfo;
			}*/
				
				
				
				
				tempSlotConnectInfo.getPosList().add(i);//位置
			}
			
			/**
			 * 现在的逻辑：如果 当前这条线中奖，而且这条线 对应的jackpoti 是否大于零，就中了jackpot
			 * 然后 就用jackpotid 去slotjackpotnewtemplate里去找相应的奖励
			 */
			if(match){//这个图标List 完全匹配
				if(multiple == 0){
					multiple = 1;
				}
				tempSlotConnectInfo.setPay(tempPayTemplate.getPay()*multiple);
				tempSlotConnectInfo.setPayId(tempPayTemplate.getId());
				
				int jackpot = tempPayTemplate.getJackpotid();
				tempSlotConnectInfo.setJackpotid(jackpot);
				if(jackpot >0 ){
					for(SlotJackpotNewTemplate slotJackpotNewTemplate:slotJackpotNewList){
						if(slotJackpotNewTemplate.getJackpotid() == jackpot){
							tempSlotConnectInfo.setSjtNEW(slotJackpotNewTemplate);
							tempSlotConnectInfo.setJackPort(true);
						}
					}
				}else{
					SlotJackpotNewTemplate slotJackpotNewTemplate = new SlotJackpotNewTemplate();
					tempSlotConnectInfo.setSjtNEW(slotJackpotNewTemplate);
					tempSlotConnectInfo.setJackPort(false);
				}
				return tempSlotConnectInfo;
			}
		}
	
		return null;
	}
	
	/**
	 * 赔率翻倍
	 * @param multiple
	 * @param elementType
	 * @return
	 */
	private int multipleDouble(int multiple,SlotElementType elementType){
		int multipleDouble = multiple;
		switch (elementType) {
		case WILD1:
			multipleDouble = multiple+1;
			break;
		case WILD2:
			multipleDouble = multiple+2;
			break;
		case WILD3:
			multipleDouble = multiple+3;
			break;
		case WILD4:
			multipleDouble = multiple+4;
			break;
		default:
			break;
		}
		return multipleDouble;
	}
	
	/**
	 * 是否获得彩金
	 * @param slotsId
	 * @param currBet
	 * @param payId
	 * @return
	 */
	public SlotJackpotTemplate getSlotJackpotTemplate(int slotsId,int currBet,int payId){
		if(slotJackpotMapList.containsKey(slotsId)){
			Map<Integer,List<SlotJackpotTemplate>> map = slotJackpotMapList.get(slotsId);
			if(map.containsKey(currBet)){
				List<SlotJackpotTemplate> list = map.get(currBet);
				for(SlotJackpotTemplate sjt : list){
					if(sjt.getPayId() == payId){
						return sjt;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取scatter
	 * @param slot
	 * @param tempCombinationList
	 * @return
	 */
/*	public ScatterInfo getScatterInfo(Human human,int linebet){
		ScatterInfo tempScatterInfo =new ScatterInfo();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
		
		Slot slot =  this.getSlotById(tempHumanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getSlotType(), SlotsListTemplate.class);
		
		List<List<SlotsTemplate>> tempScrollListList = getSlotsTemplate(slot.getType(),human.getLevel());
		
		int tempFoundNum = 0;
		//当前老虎机卷轴位置列表 5
		int col = tempHumanSlotManager.getCurrentSlotPosList().size();
		//行数
		int row = tempSlotsListTemplate.getRows();
		int bounsGameGold = 0;
		for(int i=0;i< col;i++){
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			//随机的步
			int tempIthReelPos = tempHumanSlotManager.getCurrentSlotPosList().get(i);
			
			//ith reel from j to j+row
			for(int j=0;j<row;j++){
				int tempTurn =tempIthReelPos +j;
				//翻页了
				tempTurn = tempTurn%tempScrollList.size();
				SlotsTemplate tempSlotsTemplate = tempScrollList.get(tempTurn);
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SCATTER){
					tempScatterInfo.getPosList().add(i*row+j);
					++tempFoundNum;
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS_GAME){
					BounsGameTemplate bg = Globals.getSlotWheelService().getBouns();
					tempScatterInfo.getBounsList().add(bg.getId());
					tempScatterInfo.getBounsListPosition().add(i*row+j);
					if(bg.getType() == 1){
						bounsGameGold = bounsGameGold + linebet*bg.getTimes();
					}else if(bg.getType() == 2){
						bounsGameGold = bounsGameGold + linebet*tempSlotsListTemplate.getPayLinesNum()*bg.getTimes();
					}
				}
			}
		}
		
		List<ScatterTemplate> list = scatterTemplateMap.get(slot.getType());
		
		for(ScatterTemplate tempScatterTemplate :list){
			int scatterNum = tempScatterTemplate.getScatterNum();
			if(tempFoundNum>=scatterNum){
				tempScatterInfo.setScatterTemplate(tempScatterTemplate);
				return tempScatterInfo;
			}
			
		}
		
		if(bounsGameGold > 0){
			//增加钱
			String detailReason = LogReasons.GoldLogReason.ROTARY_TABLE.getReasonText();
			human.giveMoney(bounsGameGold, Currency.GOLD, false, LogReasons.GoldLogReason.ROTARY_TABLE, detailReason, -1, 1);
			human.setModified();
		}
		
		return tempScatterInfo;
	}*/
	
	/*
	public ScatterRotaryInfo getScatterInfoBigwheel(Human human,int linebet){
		
		ScatterRotaryInfo tempScatterInfo =new ScatterRotaryInfo();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
		
		Slot slot =  this.getSlotById(tempHumanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getSlotType(), SlotsListTemplate.class);
		
		List<List<SlotsTemplate>> tempScrollListList = getSlotsTemplate(slot.getType(),human.getLevel());
		
		int tempFoundNum = 0;
		//列数
		int col = tempHumanSlotManager.getCurrentSlotPosList().size();
		//行数
		int row = tempSlotsListTemplate.getRows();
		
		int bounsGameGold = 0;
		
		for(int i=0;i< col;i++){
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			//随机的步
			int tempIthReelPos = tempHumanSlotManager.getCurrentSlotPosList().get(i);
			
			//ith reel from j to j+row
			for(int j=0;j<row;j++){
				int tempTurn =tempIthReelPos +j;
				//翻页了
				tempTurn = tempTurn%tempScrollList.size();
				
				SlotsTemplate tempSlotsTemplate = tempScrollList.get(tempTurn);
				
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BIGWHEEL){
					tempScatterInfo.getPosList().add(i*row+j);
					++tempFoundNum;
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS_GAME){
					BounsGameTemplate bg = Globals.getSlotWheelService().getBouns();
					tempScatterInfo.getBounsList().add(bg.getId());
					tempScatterInfo.getBounsListPosition().add(i*row+j);
					if(bg.getType() == 1){
						bounsGameGold = bounsGameGold + linebet*bg.getTimes();
					}else if(bg.getType() == 2){
						bounsGameGold = bounsGameGold + linebet*tempSlotsListTemplate.getPayLinesNum()*bg.getTimes();
					}
				}
			}
		}
		
		int num = Globals.getSlotWheelService().getSlotIconNum(slot.getType());
		
	    int money2 = 0;
		
		if(num <= tempFoundNum){//触发转盘游戏
			
			//随机 [1 3)
			int type = RandomUtil.nextInt(1, 3);
			tempScatterInfo.setType(type);
			//随机2盘
			GoldBonusTemplate gb2 = Globals.getSlotWheelService().getTwoPlates(type, 2);
			tempScatterInfo.setSecondIndex(gb2.getId());
			int rewardType2 = gb2.getRewardType();
			//随机3盘
			GoldBonusTemplate gb3 = Globals.getSlotWheelService().getTwoPlates(type, 3);
			tempScatterInfo.setThirdIndex(gb3.getId());
			int rewardType3 = gb3.getRewardType();
			
			BigWheelTemplate bw4 = null;
			
			if(rewardType3 == 4){
				//随机大盘
				bw4 = Globals.getSlotWheelService().getBigwheel();
				tempScatterInfo.setBigWheelIndex(bw4.getId());
				
		
			}
			
			if(type == 1){//增加钱
				money2 = gb2.getRewardNum()*linebet;
				if(rewardType3 == 3){
					money2 = money2+gb3.getRewardNum()*linebet;
				}else if(rewardType3 == 2){
					money2 = money2*gb3.getRewardNum();
				}else if(rewardType3 == 4){
					money2 = money2+bw4.getTimes()*linebet;
				}
				
			}else{//增加自由旋转次数
				int freeNum1 = gb2.getRewardNum();
				if(rewardType3 == 3){
					freeNum1 = freeNum1+gb3.getRewardNum();
				}else if(rewardType3 == 2){
					freeNum1 = freeNum1*gb3.getRewardNum();
				}else if(rewardType3 == 4){
					money2 = money2+bw4.getTimes()*linebet;
				}
				tempHumanSlotManager.addFreeSlot(freeNum1);
			}
			
		}
		int total = bounsGameGold+money2;
		
		if(total > 0){
			//增加钱
			String detailReason = LogReasons.GoldLogReason.ROTARY_TABLE.getReasonText();
			human.giveMoney(total, Currency.GOLD, false, LogReasons.GoldLogReason.ROTARY_TABLE, detailReason, -1, 1);
			human.setModified();
		}
		return tempScatterInfo;
	}*/
	
	public List<ScatterTemplate> getScatterTemplate(int type){
		return scatterTemplateMap.get(type);
	}
	
	
	/**
	 * 根据老虎机最高彩金
	 * @return
	 */
	public Map<Integer,Long> getLevelJackpot(){
		Map<Integer,Long> map = new HashMap<Integer,Long>();
		for(Entry<Integer,List<SlotsListTemplate>> en : slotMapList.entrySet()){
			    int type = en.getKey();
			    long jackPot = 0;
			   
			    for(Entry<Integer,Slot> enn : slotMap.entrySet()){
			    	
			    	Slot slot = enn.getValue();
			    	
			    	SlotsListTemplate template = slotsListTemplateMap.get(slot.getTempleId());
			    	
			    	if(template == null){
			    		continue;
			    	}
			    	 if(template.getType() == type){
			    		 long jack = slot.getJackpot();
			    		 if(jackPot < jack){
			    			 jackPot = jack;
			    			 map.put(type,jackPot);
			    		 }
			    	 }
			    }
		}
		return map;
	}
	
	
	public List<BoxTemplate> getRandomBoxList(){
		return ArrayUtils.randomFromArray(this.boxTemplateList, this.boxWeightArr, SlotService.MAX_BOX_NUM, false);
	}
	public List<BoxTemplate> getRandomBoxListByTypeLevel(int slotType,long level){
		if(boxTemplateMap.containsKey(slotType) && boxWeightArrMap.containsKey(slotType)){
			Map<String,List<BoxTemplate>> mm = boxTemplateMap.get(slotType);
			Map<String,int[]> mm1 = boxWeightArrMap.get(slotType);
			
			List<BoxTemplate> listmm = new ArrayList<BoxTemplate>();
			
			int[] num = new int[0];
			
			for(Entry<String,List<BoxTemplate>> en : mm.entrySet()){
				   String[] strs = en.getKey().split("_");
				   if(Long.valueOf(strs[0]) <= level && level <= Long.valueOf(strs[1])){
					   listmm = en.getValue();
					   break;
				   }
				
			}
			for(Entry<String,int[]> en : mm1.entrySet()){
				String[] strs = en.getKey().split("_");
				if(Long.valueOf(strs[0]) <= level && level <= Long.valueOf(strs[1])){
					num = en.getValue();
					break;
				}
				
			}
			return ArrayUtils.randomFromArray(listmm, num, listmm.size(), false);
			
		}
		
		return new ArrayList<BoxTemplate>();
	}
	
	public float getScatter(int id){
		for(BoxTemplate bt : boxTemplateList){
			 if(bt.getId() == id){
				 return bt.getRate();
			 }
		}
		return 1;
	}
	
	/**
	 * 马来网红
	 * @return
	 */
	public WinMultipleTemplate getWinMultip(){
		List<WinMultipleTemplate> list = ArrayUtils.randomFromArray(listWinMultipleTemplate, weightWinMultiple, 1, false);
		if(list== null || list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 获取老虎机对应的最低的等级
	 * @param type
	 * @return
	 */
	public int getOPenLevel(int type){
		try{
			return slotTypeOPen.get(type);
		}catch(Exception e){
			Loggers.slotLogger.error("", e);
		}
		return 0;
	}
	
	private List<Integer> getPostList(int columns,int reelNum){
		List<Integer> location = new ArrayList<Integer>();
		for(int i=0;i<columns;i++){
			//每个轴旋走的步数
			int temp = MathUtils.random(0,reelNum);
			location.add(temp);
		}
		return location;
	}
	
	/**
	 * 随机出现指定个数元素
	 * 
	 * @param slotType
	 * @param level
	 * @param et
	 * @return
	 */
	public List<Integer> randomElements(int slotType, int level, SlotElementType et,int num) {

		List<List<SlotsTemplate>> list = getSlotsTemplate(slotType, level);

		SlotsListTemplate template = slotMapList.get(slotType).get(0);

		List<Integer> location = new ArrayList<Integer>();

		// 列
		int columns = template.getColumns();
		// 行
		int rows = template.getRows();

		location = getPostList(columns, template.getReel1Num());
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for (int ii = 0; ii < list.size(); ii++) {

			List<SlotsTemplate> stList = list.get(ii);

            int size = stList.size();
			
            // 每一次取值的最大匹配值
            int numberMax = 0;
         			
			for(int n = 0;n < size;n++){
				int number = 0;
				for (int i = n; i < n+rows; i++) {
					SlotsTemplate slotsTemplate = stList.get(i%size);
					if (slotsTemplate.getType() == et.getIndex()) {
						number++;
					}
				}
				if (number > numberMax) {
					location.set(ii, n);
					numberMax = number;
					map.put(ii, numberMax);
				}
				int nnn = 0;
				for(int nn : map.values()){
					nnn = nnn+nn;
				}
				if(nnn >= num){
					return location;
				}
			}
		}
		return location;
	}
	
	/**
	 * 随机指定元素指定列数
	 * @param slotType
	 * @param level
	 * @param et
	 * @param num 指定列数
	 * @return
	 */
	public List<Integer> randomElementsColumns(int slotType, int level, SlotElementType et) {
		List<List<SlotsTemplate>> list = getSlotsTemplate(slotType, level);

		SlotsListTemplate template = slotMapList.get(slotType).get(0);

		List<Integer> location = new ArrayList<Integer>();

		// 列
		int columns = template.getColumns();
		// 行
		int rows = template.getRows();

		location = getPostList(columns, template.getReel1Num());

		for (int ii = 0; ii < list.size(); ii++) {

			List<SlotsTemplate> stList = list.get(ii);

			int size = stList.size();
			
			for(int n = 0;n < size;n++){
				int number = 0;
				for (int i = n; i < n+rows; i++) {
					SlotsTemplate slotsTemplate = stList.get(i%size);
					if (slotsTemplate.getType() == et.getIndex()) {
						number++;
					}
				}
				if (number == rows) {
					location.set(ii, n);
					break;
				}
			}
			
		}
		
		return location;
	}
	
	/**
	 * 第一行出现指定元素
	 * @param slotType
	 * @param level
	 * @param et
	 * @param num
	 * @return
	 */
	public List<Integer> randomElementsRows(int slotType, int level, SlotElementType et) {
		List<List<SlotsTemplate>> list = getSlotsTemplate(slotType, level);

		SlotsListTemplate template = slotMapList.get(slotType).get(0);

		List<Integer> location = new ArrayList<Integer>();

		// 列
		int columns = template.getColumns();
		
		location = getPostList(columns, template.getReel1Num());

		for (int ii = 0; ii < list.size(); ii++) {

			List<SlotsTemplate> stList = list.get(ii);
			
			int size = stList.size();
			
			for(int i = 0;i <= size;i++){
				
				SlotsTemplate slotsTemplate = stList.get(i);
				if (slotsTemplate.getType() == et.getIndex()) {
					location.set(ii, i);
					break;
				}
			}
		}

		return location;
	}
	
	/**
	 * 第一列出现任意相同元素
	 * @param slotType
	 * @param level
	 * @param et
	 * @param num
	 * @return
	 */
	public List<Integer> randomElementsRowsAny(int slotType, int level) {
		List<List<SlotsTemplate>> list = getSlotsTemplate(slotType, level);
		
		SlotsListTemplate template = slotMapList.get(slotType).get(0);
		
		List<Integer> location = new ArrayList<Integer>();
		
		// 列
		int columns = template.getColumns();
		
		location = getPostList(columns, template.getReel1Num());
		
		List<SlotsTemplate> stList = list.get(0);
		
		for(int i = 0;i < stList.size();i++){
			
			SlotsTemplate st = stList.get(i);
			
			Set<Integer> setType = new HashSet<Integer>();
			
			setType.add(st.getType());
			
			location.set(0, i);
			
			int index = 1;
			boolean fly1 = true;
			while(index < columns ){
				
				List<SlotsTemplate> stList1 = list.get(index);
				
				boolean fly = true;
				for(int ii = 0;ii < stList1.size();ii++){
					SlotsTemplate ss = stList.get(ii);
					if(!setType.add(ss.getType())){//出现相同元素
						location.set(index, ii);
						fly = false;
						break;
					}
				}
				if(fly){
					fly1 = false;
					break;
				}
				index++;
			}
			
			if(fly1){
				break;//已经找到匹配元素 退出
			}
			
		}

		return location;
	}
	
	/**
	 * 1:3个bonus 2 :4个bonus 3 :5个bonus 
	 * 
	 * 4:3个scatter 5:4个scatter 6:5个scatter 
	 * 
	 * 7:一列大号wild 8:2列大号wild 
	 * 
	 * 9:一列小号wild 10:2列小号wild 
	 * 
	 * 11:第一行随机出现相同元素 
	 * 
	 * 12:第一行出现3个jackpot 13:随机出现4个jackpot 14:随机出现5个jackpot 15: 随机出现6个jackpot 16: 随机出现7个jackpot 
	 * @param slotType
	 * @param type
	 * @return
	 */
	public int slotElemByType(int slotType,int type){
		
		//获取老虎机对应的元素类型集合
		Map<Integer,Integer> map = getSlotElements(slotType);
		
		if(type == 1 || type == 2 || type == 3){
			return getTurnType(map,DemoSlot.demoType1);
		}else if(type == 4 || type == 5 || type == 6){
			return getTurnType(map,DemoSlot.demoType2);
		}else if(type == 7 || type == 8){
			return getTurnType(map,DemoSlot.demoType3);
		}else if(type == 9 || type == 10){
			return getTurnType(map,DemoSlot.demoType4);
		}else{
			return getTurnType(map,DemoSlot.demoType5);
		}
	}
	
	private int getTurnType(Map<Integer,Integer> map,List<Integer> lsit){
		for(Entry<Integer,Integer> en : map.entrySet()){
			int turnType = en.getValue();
			if(lsit.contains(turnType)){
				return turnType;
			}
		}
		return 0;
	}
	
	
	/**
	 * 1:3个bonus 2 :4个bonus 3 :5个bonus 
	 * 
	 * 4:3个scatter 5:4个scatter 6:5个scatter 
	 * 
	 * 7:一列大号wild 8:2列大号wild 
	 * 
	 * 9:一列小号wild 10:2列小号wild 
	 * 
	 * 11:第一行随机出现相同元素 
	 * 
	 * 12:第一行出现3个jackpot 13:随机出现4个jackpot 14:随机出现5个jackpot 15: 随机出现6个jackpot 16: 随机出现7个jackpot 
	 * @param slotType
	 * @param type
	 * @return
	 */
	public List<Integer> getListPost(int slotType, int level, SlotElementType et,int type){
		
		if(type == 1 || type == 4){
			//随机出现指定个数元素
			return randomElements(slotType,level,et,3);
		}else if(type == 2 || type == 5){
			return randomElements(slotType,level,et,4);
		}else if(type == 3 || type == 6){
			return randomElements(slotType,level,et,5);
		}else if(type == 13){
			return randomElements(slotType,level,et,4);
		}else if(type == 15){
			return randomElements(slotType,level,et,6);
		}else if(type == 16){
			return randomElements(slotType,level,et,7);
		}else if( type == 7 || type == 8 ||  type == 9 || type == 10){
			return randomElementsColumns(slotType,level,et);
		}else if(type == 12 || type == 14){
			return randomElementsRows(slotType,level,et);
		}
		return new ArrayList<Integer>();
	}

	
	public List<SlotsListTemplate> getSlotsListTemplateList(int slotType){
		List<SlotsListTemplate> sList = new ArrayList<SlotsListTemplate>();
		for(SlotsListTemplate SlotsListTemplate:slotsListTemplateMap.values()){
			if(SlotsListTemplate.getType() == slotType){
				sList.add(SlotsListTemplate);
			}
		}
		return sList;
	}
	
	
	/**
	 * 用户在 哪个老虎机 的哪个 场景 的 哪个 倍数 下
	 */
	
	public void enterSlot(Slot slot,Player player,int bet){
		boolean exist =false;
		for(PlayerSlotSenceBet playerSlotSenceBet:playerSlotSenceBetList){
			if(playerSlotSenceBet.getPlayerId() == player.getPassportId()){
				exist=true;
				playerSlotSenceBet.setBet(bet);
			}
		}
		if(!exist){
			PlayerSlotSenceBet playerSlotSenceBet = new PlayerSlotSenceBet();
			playerSlotSenceBet.setBet(bet);
			playerSlotSenceBet.setPlayerId(player.getPassportId());
			playerSlotSenceBet.setSlotId(slot.getTempleId());
			playerSlotSenceBetList.add(playerSlotSenceBet);
		}
	}
	public void outSlot(Player player){
		List<PlayerSlotSenceBet> PlayerSlotSenceBetList = new ArrayList<PlayerSlotSenceBet>();
		for(PlayerSlotSenceBet playerSlotSenceBet:playerSlotSenceBetList){
			if(playerSlotSenceBet.getPlayerId() == player.getPassportId()){
				PlayerSlotSenceBetList.add(playerSlotSenceBet);
			}
		}
		playerSlotSenceBetList.removeAll(PlayerSlotSenceBetList);
	}

	public List<PlayerSlotSenceBet> getPlayerSlotSenceBetList() {
		return playerSlotSenceBetList;
	}

	public void setPlayerSlotSenceBetList(
			List<PlayerSlotSenceBet> playerSlotSenceBetList) {
		this.playerSlotSenceBetList = playerSlotSenceBetList;
	}
	
	
}
