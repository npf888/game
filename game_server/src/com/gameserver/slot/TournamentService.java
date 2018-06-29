package com.gameserver.slot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.core.template.TemplateService;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.slot.data.HumanSngInfo;
import com.gameserver.slot.data.SlotRankData;
import com.gameserver.slot.data.SngTournamentData;
import com.gameserver.slot.msg.GCSlotGetRank;
import com.gameserver.slot.msg.GCSlotGetReward;
import com.gameserver.slot.msg.GCSlotRankList;
import com.gameserver.slot.msg.GCSlotTournamentNotOpen;
import com.gameserver.slot.msg.GCSlotTournamentStartData;
import com.gameserver.slot.msg.GCSngRankInfo;
import com.gameserver.slot.msg.GCTournamentGetList;
import com.gameserver.slot.pojo.HumanRank;
import com.gameserver.slot.pojo.Rank1Rank2VO;
import com.gameserver.slot.pojo.SngInfo;
import com.gameserver.slot.template.TournamentRankListTemplate;
import com.gameserver.slot.template.TournamentTemplate;
/**
 * 老虎机竞赛
 * @author 郭君伟
 *
 */
public class TournamentService implements InitializeRequired {
	
	/**没有打开 **/
	public static final int NO_OPEN = 0;
	/**打开 **/
	public static final int OPEN = 1;
	
	/**个人记录最大值 **/
	public static final int INFO_SIZE = 20;
	

    /**竞速类型 **/
	private Map<Integer,TournamentTemplate> tournamentData = new HashMap<Integer,TournamentTemplate>();
	
	/*** 奖励   tournamentId 对应  list***/
	private Map<Integer,List<TournamentRankListTemplate>> tournamentRankListMap = new HashMap<Integer,List<TournamentRankListTemplate>>();
	
	private Map<Integer,Integer> maxLanMap = new HashMap<Integer,Integer>();
	/** tournamentId 对应**/
	private Map<Integer,List<Rank1Rank2VO>> rank1Rank2Map = new HashMap<Integer,List<Rank1Rank2VO>>();
	
	/**老虎机类型  竞赛类型 **/
	private Map<Integer,Integer> slotTypeSngType = new HashMap<Integer,Integer>();
	
	/*** 竞赛的 类型  ，竞赛的唯一ID(自增长)***/
	private Map<Integer,Long> sngTypeSngIDMap = new HashMap<Integer,Long>();
	//========================================================================
	
	/**竞赛类型  角色ID 是否打开面板（0 没有打开默认没有打开     1 打开）**/
	private Map<Integer,Map<Long,Integer>> humanIsOpen = new ConcurrentHashMap<Integer,Map<Long,Integer>>(); 
	
	/**竞赛类型 进入 退出调用平凡 **/
	private Map<Integer,List<HumanRank>> humanRankMap = new ConcurrentHashMap<Integer,List<HumanRank>>();
	
	
	/**竞赛类型  竞赛奖池 结算的时候清空 **/
	private Map<Integer,Long> mapSngValue = new ConcurrentHashMap<Integer,Long>();
	
	/**竞赛类型  结束时间点 **/
	private Map<Integer,Long> sngEndTime = new HashMap<Integer,Long>();
	
	/**竞赛类型 是否开始结束   true：表示在竞赛中**/
	private Map<Integer,AtomicBoolean> flyMap = new HashMap<Integer,AtomicBoolean>();
	/**所有 开着 竞赛list的那个界面，就是所有 的竞赛的那个界面，不是面板 的 所有人的 userId**/
	private Set<Long> allOpenTournamentList = new HashSet<Long>();
	@Override
	public void init() {
		 TemplateService templateService = Globals.getTemplateService();
			
		 Map<Integer,TournamentTemplate> tournamen =  templateService.getAll(TournamentTemplate.class);
		 
		 for(TournamentTemplate tt : tournamen.values()){
			 tournamentData.put(tt.getTournamentType(), tt);
			 
			 int sngType = tt.getTournamentType();
			 
			 Set<Integer> set =  tt.getSlotSet();
			 
			 for(int slotType : set){
				 slotTypeSngType.put(slotType, sngType);
			 }
			 initData(sngType);
		 }
		/**
		  * 加载奖励
		  */
		 Map<Integer,TournamentRankListTemplate> tournamenRankMap =  templateService.getAll(TournamentRankListTemplate.class);
			 
		 int existTournamentId = -1;
		 for(TournamentRankListTemplate tr: tournamenRankMap.values()){
			int tournamentId = tr.getTournamentId();
			if(existTournamentId != tournamentId){
				existTournamentId = tournamentId;
				List<TournamentRankListTemplate> tournamentRankListTemplateList = new ArrayList<TournamentRankListTemplate>();
				tournamentRankListTemplateList.add(tr);
				tournamentRankListMap.put(tournamentId, tournamentRankListTemplateList);
			}else{
				List<TournamentRankListTemplate> tournamentRankListTemplateList = tournamentRankListMap.get(existTournamentId);
				tournamentRankListTemplateList.add(tr);
			}
		 }
			 
		/**
		 * 现在每个竞赛有自己的  排行榜最大值
		 */
		 for(TournamentTemplate tournamentTemplate:tournamentData.values()){
			 int tournamentType = tournamentTemplate.getTournamentType();
			 List<TournamentRankListTemplate> tournamentRankListTemplateList = tournamentRankListMap.get(tournamentType);
			 int rank2 = tournamentRankListTemplateList.get(tournamentRankListTemplateList.size()-1).getRank2();
			 maxLanMap.put(tournamentType, rank2);
			 
			 for(TournamentRankListTemplate trt:tournamentRankListTemplateList){
				 int rank11 = trt.getRank1();
				 int rank22  = trt.getRank2();
				 Rank1Rank2VO rank1Rank2VO = new Rank1Rank2VO();
				 rank1Rank2VO.setRank1(rank11);
				 rank1Rank2VO.setRank2(rank22);
				 List<Rank1Rank2VO> existRank1Rank2VOList = rank1Rank2Map.get(tournamentType);
				 if(existRank1Rank2VOList == null){
					 List<Rank1Rank2VO> rank1Rank2VOList = new ArrayList<Rank1Rank2VO>();
					 rank1Rank2VOList.add(rank1Rank2VO);
					 rank1Rank2Map.put(tournamentType, rank1Rank2VOList);
				 }else{
					 existRank1Rank2VOList.add(rank1Rank2VO);
				 }
			 }
		 }
			 
//		 start();
	}
	
	
	private void initData(int sngType){
		 flyMap.put(sngType, new AtomicBoolean(false));
		 sngEndTime.put(sngType, 0l);
		 mapSngValue.put(sngType, 0l);
		 humanRankMap.put(sngType, new ArrayList<HumanRank>());
		 humanIsOpen.put(sngType, new HashMap<Long,Integer>());
	}
	
	public void start(){
		//竞赛开始 循环处理
		for(TournamentTemplate tt : tournamentData.values()){
			//竞赛总时间
			int totalTime = tt.getTotalTime();
			Globals.getScheduleService().scheduleWithFixedDelay(new TournamentStart(tt), LLScheduleEnum.SLOT_SNG_RANK, 0, totalTime*1000);
		}
		//每隔  多长时间  更新一次前端 打开面板 的用户的数据
		Globals.getScheduleService().scheduleWithFixedDelay(new TournamentRefresh(this), LLScheduleEnum.SLOT_SNG_RANK, 0, 18*1000);
	}
	
	/**
	 * 角色下线调用
	 * @param passportId
	 * @param slotType
	 */
	public void loginOut(long passportId,int slotType){
		int slotTypeSngType = getTournament(slotType);
		
		Map<Long,Integer> map = humanIsOpen.get(slotTypeSngType);
		if(map == null){
			return;
		}
		if(map.containsKey(passportId)){
			map.remove(passportId);
			List<HumanRank> list = humanRankMap.get(slotTypeSngType);
			for(int i = 0;i < list.size();i++){
				HumanRank humanRank = list.get(i);
				if(humanRank.getPassportId() == passportId){
//					list.remove(i); 这边老虎机用户下线 也不 退出排行榜
					break;
				}
			}
			broadcast2(slotTypeSngType);
		}
		
	}
	
	/**
	 * 开启竞赛
	 * @param template 模板数据
	 * @param endTime  结束时间点
	 */
	public void openSng(TournamentTemplate template,long endTime){
		
		int tournamentType = template.getTournamentType();
		
		AtomicBoolean ab = flyMap.get(tournamentType);
		
		ab.compareAndSet(false, true);
		
		sngEndTime.put(tournamentType, endTime);
		/**
		 * 把最大的ID 取出来 然后 +1
		 */
		long ID=1;
		for(long id:sngTypeSngIDMap.values()){
			if(id > ID){
				ID=id;
			}
		}
		ID++;
		sngTypeSngIDMap.put(tournamentType, ID);
		/**
		 * 日志
		 */
		Globals.getLogService().sendTournamentLog(null, LogReasons.TournamentLogReason.TOURNAMENT, "",ID, tournamentType, 0, 0, 0, 0, 0, 0);
		
		//给所有打开面板的玩家推送竞赛开启消息  
		broadcast1(tournamentType);
		broadcast2(tournamentType);
		//每次开启竞赛 都要给 面板打开的人 发送 开启竞赛的消息
		broadcastStart3(tournamentType);
	}
	
	


	private void broadcastStart3(int tournamentType) {
		  GCMessage message = Globals.getTournamentService().getGCSlotTournamentStartData(tournamentType);
		  AtomicBoolean ab = flyMap.get(tournamentType);
			if(!ab.get()){
				return;
			}
			/**
			 * 找到 打开 竞赛list的那个界面 的所有人 ，当竞赛开始的时候 给他们推送消息
			 */
			for(Long userId :allOpenTournamentList){
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(userId);
				if(player == null){
					continue;
				}
				player.sendMessage(message);
			}
	}


	/**
	 * 竞赛结束
	 * @param template  模板数据
	 */
	public void closeSng(TournamentTemplate template){
		
	    int tournamentType = template.getTournamentType();
	    
	    AtomicBoolean ab = flyMap.get(tournamentType);
		ab.compareAndSet(true, false);
	    
	    
	    endSngReward(template);
	    //完事 清空奖池 开始下一轮
	    mapSngValue.put(tournamentType, 0l);
	    List<HumanRank> rankList = humanRankMap.get(tournamentType);
		rankList.clear();
	    
		broadcast3(tournamentType);
		//竞赛结束给所有人 发送 正在进行的竞赛的列表
		broadcast4(tournamentType);
	    
	}
	
	/**
	 * 奖励前三名
	 * @param template
	 */
	private void endSngReward(TournamentTemplate template){
		try{
		
			 List<HumanRank> rankList = humanRankMap.get(template.getTournamentType());
			 if(rankList.isEmpty()){
				 return;
			 }
			 
			    List<Integer> rewardList = getRewardList(template.getTournamentType());
			    for(int i = 0;i <rankList.size() && i < maxLanMap.get(template.getTournamentType());i++){
			    	HumanRank hr = rankList.get(i);
			    	if(hr != null){
			    		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(hr.getPassportId());
				    	if(player != null && hr.getValue() != 0){//奖励前3名玩家钱
				    		Human human = player.getHuman();
				    		/**
				    		 * 看看用户 在哪个获奖的段位 ,就给那个段位的 奖励
				    		 */
				    		List<Rank1Rank2VO> rrList = rank1Rank2Map.get(template.getTournamentType());
				    		int position = getPosition(rrList,i);
				    		int reward = rewardList.get(position);
				    		
				    		/**奖池中的总金额**/
				    		Long rewards = mapSngValue.get(template.getTournamentType());
				    		if(rewards == null){
				    			rewards = 0l;
				    		}
				    		Long obtainedReward = (rewards*reward)/10000;
				    	    human.giveMoney(obtainedReward, Currency.GOLD, true, LogReasons.GoldLogReason.SLOTSNGBONUS, LogReasons.GoldLogReason.SLOTSNGBONUS.getReasonText(), -1, 1);
				    	    /**
				    		 * 日志
				    		 */
				    	    long id =sngTypeSngIDMap.get(template.getTournamentType());
				    		Globals.getLogService().sendTournamentLog(human, LogReasons.TournamentLogReason.TOURNAMENT, "",
				    				id,template.getTournamentType(), 0, 0, human.getPassportId(), rewards, reward, obtainedReward);
				    	    GCSlotGetReward gCSlotGetReward = new GCSlotGetReward();
				    	    gCSlotGetReward.setRank(i+1);
				    	    gCSlotGetReward.setRewardNum(obtainedReward);
				    	    human.getPlayer().sendMessage(gCSlotGetReward);
				    	    saveInfo(human,obtainedReward,i+1,template.getTournamentType());
				    	   }
			    	}
			    }
		}catch(Exception e){
			Loggers.slotLogger.error("", e);
			return;
		}
	}
	
	//获取当前的用户 在哪个获奖的段位
	private int getPosition(List<Rank1Rank2VO> rrList,int i){
		
		for(int x=0;x<rrList.size();x++){
			Rank1Rank2VO rr = rrList.get(x);
			int rank1 = rr.getRank1();
			int rank2 = rr.getRank2();
			if((i+1)>=rank1 && (i+1)<= rank2){
				return x;
			}
		}
		return 0;
	}
	
	private List<Integer> getRewardList(int tournamentId){
		 List<TournamentRankListTemplate> theRankList  =  tournamentRankListMap.get(tournamentId);
		 List<Integer> rewardList = new ArrayList<Integer>();
		 for(int i=0;i<theRankList.size();i++){
			 rewardList.add(theRankList.get(i).getReward());
		 }
		 return rewardList;
	}
	/**
	 * 记录领奖信息
	 * @param human
	 * @param rewardNum
	 * @param rank
	 * @param tournamentType
	 */
	private void saveInfo(Human human,long rewardNum,int rank,int tournamentType){
		List<SngInfo> list = human.getSnginfos();
		Collections.sort(list);
		if(list.size() > INFO_SIZE){
			list.remove(0);
		}
		SngInfo sngInfo = new SngInfo();
		sngInfo.setRewardNum(rewardNum);
		sngInfo.setRank(rank);
		sngInfo.setRewardTime(Globals.getTimeService().now());
		sngInfo.setTournamentType(tournamentType);
		list.add(sngInfo);
		human.setModified();
	}
	
	/**
	 * 进入老虎机
	 * @param player
	 * @param slotType
	 * @param passportId
	 * @param img
	 */
	public  void enterSlot(Player player,int slotType,long passportId,String img){
		
		int slotTypeSngType = getTournament(slotType);
		
		Map<Long,Integer> isOpenmap = humanIsOpen.get(slotTypeSngType);
		if(isOpenmap == null){
			return;
		}
		isOpenmap.put(passportId, NO_OPEN);
		
	}
	
	private HumanRank getHumanRank(long passportId,String img,String name,long value){
		HumanRank hr = new HumanRank();
		hr.setPassportId(passportId);
		hr.setImg(img);
		hr.setValue(value);
		hr.setName(name);
		return hr;
	}
	
	/**
	 * 退出老虎机
	 * @param slotType
	 * @param passportId
	 */
	public void outSlot(int slotType,int oldSlotType,long passportId){
		
		int oldslotTypeSngType = getTournament(oldSlotType);
		int slotTypeSngType = getTournament(slotType);
		
		if(oldslotTypeSngType == slotTypeSngType){
			return;
		}
		
	   Map<Long,Integer> isOpenmap = humanIsOpen.get(oldslotTypeSngType);
	   if(isOpenmap != null){
		   isOpenmap.remove(passportId);
	   }
		
       List<HumanRank> rankList = humanRankMap.get(oldslotTypeSngType);
       int index = -1;
       if(rankList != null){
    	   for(int i = 0;i < rankList.size();i++){
    		   HumanRank hr = rankList.get(i);
    		   if(hr.getPassportId() == passportId){
//			   rankList.remove(i);
    			   index = i;
    			   break;
    		   }
    	   }
    	   
       }
	   
	   if(index != -1 && flyMap.get(oldslotTypeSngType).get()){
		   broadcast2(oldslotTypeSngType);
	   }
		
	}
	
	
	
	
	/**
	 * 添加奖金池
	 * @param slotType
	 * @param tempAllBets
	 */
	public void putBonuts(int slotType,long tempAllBets,Human human){
		
		int slotTypeSngType = getTournament(slotType);
		
		TournamentTemplate template = tournamentData.get(slotTypeSngType);
		
		AtomicBoolean ab = flyMap.get(slotTypeSngType);
		
		Long totalValue = mapSngValue.get(slotTypeSngType);
		int raceReward = 0;
		if(ab == null){
			return;
		}
		if(!ab.get()){
			return;
		}
		if(ab.get()){
			raceReward = template.getRaceReward();
		}else{
			raceReward = template.getIntervalRaceReward();
		}
		long bonus = (long)Math.floor(tempAllBets*(raceReward/10000f));
		
		mapSngValue.put(slotTypeSngType, totalValue+bonus);
		
		/**
		 * 日志
		 */
		long id =sngTypeSngIDMap.get(template.getTournamentType());
		Globals.getLogService().sendTournamentLog(human, LogReasons.TournamentLogReason.TOURNAMENT, "",
				id,slotTypeSngType, slotType, 0, human.getPassportId(), 0, 0, 0);
		if(ab.get()){//正在比赛才可以广播
			broadcast1(slotTypeSngType);
		}
	}
	
	/**
	 * 每转动老虎机调用
	 * 在每个竞赛的Map中存入数据
	 * @param player
	 * @param slotType
	 * @param passportId
	 * @param tempReward
	 */
	public void putData(Player player,int slotType,long passportId, long tempReward){
		try{
			int slotTypeSngType = getTournament(slotType);
			
			AtomicBoolean ab = flyMap.get(slotTypeSngType);
			if(ab == null){
				return;
			}
			if(!ab.get()){
				return;
			}
			
			List<HumanRank> list = humanRankMap.get(slotTypeSngType);
			
			boolean fly = true;
			for(HumanRank hr : list){
				if(hr.getPassportId() == passportId){
					hr.setValue(hr.getValue()+tempReward);
					fly = false;
					break;
				}
			}
			if(fly){
				list.add(getHumanRank(passportId,player.getImg(),player.getHuman().getName(),tempReward));
			}
			
			Collections.sort(list);
			
			broadcast2(slotTypeSngType);
		}catch(Exception e){
			Loggers.slotLogger.error("", e);
		}
	}
	
	/**
	 * 关闭面板
	 * @param tournamentType
	 */
	public void closePanel(int tournamentType,long passportId){
		Map<Long,Integer> map = humanIsOpen.get(tournamentType);
		map.put(passportId, NO_OPEN);
	}
	
	/**
	 * 打开面板
	 * @param tournamentType
	 * @param passportId
	 */
	public void openPanel(int tournamentType,long passportId){
		Map<Long,Integer> map = humanIsOpen.get(tournamentType);
		map.put(passportId, OPEN);
	}
	

	/**
	 * 更具老虎机类型获取竞赛类型
	 * @param slotType
	 * @return -1 错误
	 */
	public int getTournament(int slotType){
		if(slotTypeSngType.containsKey(slotType)){
			return slotTypeSngType.get(slotType);
		}
		return -1;
	}
	
	public boolean isOPen(int tournamentType){
		 AtomicBoolean ab = flyMap.get(tournamentType);
		 return ab.get();
	}
	
	
	/**
	 * 奖池变化 广播给打开面板的人
	 * @param tournamentType
	 */
	public void broadcast1(int tournamentType){
		
		Map<Long,Integer> map = humanIsOpen.get(tournamentType);
		
		
		for(Entry<Long,Integer> en : map.entrySet()){
			
			    long passportId = en.getKey();
			    int fly = en.getValue();
		
			    if(fly == OPEN){
			    	Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
			    	if(player != null){
			    		GCMessage message = getGCSlotTournamentStartDataNew(tournamentType,player.getHuman());
			    		player.sendMessage(message);
			    	}
			    	
			    }
		}
	}
	
	/**
	 * 排行榜变化 广播给打开面板的人
	 * @param tournamentType
	 */
	public void broadcast2(int tournamentType){
        Map<Long,Integer> map = humanIsOpen.get(tournamentType);

		for(Entry<Long,Integer> en : map.entrySet()){
			
			    long passportId = en.getKey();
			    int fly = en.getValue();
			    
			    if(fly == OPEN){
			    	Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
			    	if(player != null){
		    			List<SlotRankData> list = Globals.getTournamentService().getRoleRankData(player.getHuman(),player.getImg(), tournamentType, passportId,player.getHuman().getName());
		    			GCMessage message = getGCSlotRankList(list);
		    			player.sendMessage(message);
			    	}
			    	
			    }
		}
	}
	
	/**
	 * 竞赛结束 广播给打开面板的人
	 * @param tournamentType
	 */
	public void broadcast3(int tournamentType){
		 Map<Long,Integer> map = humanIsOpen.get(tournamentType);

			for(Entry<Long,Integer> en : map.entrySet()){
				
				    long passportId = en.getKey();
				    int fly = en.getValue();
				    
				    if(fly == OPEN){
				    	Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
				    	if(player != null){
				    		 player.sendMessage(new GCSlotTournamentNotOpen());
				    	}
				    	
				    }
			}
	}
	public void broadcast4(int tournamentType){
		Map<Long,Integer> map = humanIsOpen.get(tournamentType);
		for(Entry<Long,Integer> en : map.entrySet()){
			
			long passportId = en.getKey();
			int fly = en.getValue();
			
			if(fly == OPEN){
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
				if(player != null){
					GCMessage message = getGCSlotTournamentStartDataNew(tournamentType,player.getHuman());
					player.sendMessage(message);
				}
				
			}
		}
	}
	
	
	/**
	 * 获取自己排行榜排名
	 * 
	 * 用户是 第一名 显示 1 2 3 名
	 * 用户是 第二名  显示 1 2 3 名
	 * 用户是 第三名 显示  1 2 3 名
	 * 前三名特殊点  只要在前三名 就显示前三名
	 * 用户 是 第四名 显示 3 4 5名
	 * 用户 是 第五名 显示  4 5 6名
	 * 以此类推
	 * 
	 * 
	 * @param img
	 * @param tournamentType
	 * @param passportId
	 * @return
	 */
	public List<SlotRankData> getRoleRankData(Human human,String img,int tournamentType,long passportId,String name){
		List<SlotRankData> list = new ArrayList<SlotRankData>();
		try{
		
		
		List<HumanRank> listRank = humanRankMap.get(tournamentType);
		
		int fly = 0;//默认没有上榜
		int indexNum = 0;
		for(int i = 0; i < listRank.size();i++){
			HumanRank hr = listRank.get(i);
			if(hr.getPassportId() == passportId && i < 3){
				fly = 1;
			}else if(hr.getPassportId() == passportId && i >= 3){
				fly = 2;
				indexNum = i;
			}
		}
		/**
		 * 如果用户  没有在榜单上  就是用户 压根都没有转过 老虎机 
		 * 那么 用户排在最后一名，取出倒数第一  和 倒数第二 放在他前边
		 */
		if(fly == 0){
			/**
			 * 如果没有上榜 就把自己放在最后
			 * 那么 用户排在最后一名，取出倒数第一  和 倒数第二 放在他前边
			 * 
			 * 
			 */
			//取出 榜单上最后两个
			if(listRank.size() < 3){
				for(int i = 0; i < listRank.size();i++){
					HumanRank humanRank = listRank.get(i);
					list.add(getRankData(humanRank.getPassportId(),tournamentType,humanRank.getValue(),humanRank.getImg(),i+1,humanRank.getName(),human.getHumanVipNewManager().getVipLv()));
				}
			}else{
				for(int i = listRank.size()-3; i > listRank.size()-1;i--){
					HumanRank humanRank = listRank.get(i);
					list.add(getRankData(humanRank.getPassportId(),tournamentType,humanRank.getValue(),humanRank.getImg(),i+1,humanRank.getName(),human.getHumanVipNewManager().getVipLv()));
				}
			}
			/**
			 * 自己放最后
			 */
		    list.add(getRankData(passportId,tournamentType,0,img,-1,name,human.getHumanVipNewManager().getVipLv()));
		    
		    
		  /**
		   * 用户在榜单里  并且 在前三名 里
		   */
		}else if(fly == 1){
			for(int i = 0; i < 3;i++){
				if(i >= listRank.size()){
					break;
				}
				HumanRank humanRank = listRank.get(i);
				if(humanRank == null){
					break;
				}
				list.add(getRankData(humanRank.getPassportId(),tournamentType,humanRank.getValue(),humanRank.getImg(),i+1,humanRank.getName(),human.getHumanVipNewManager().getVipLv()));
			}
			
		 /**
		   * 用户在榜单里  但是没有在 前三名
		   */
		}else if(fly == 2){
			if(indexNum == (listRank.size() -1)){//最后一名
				for(int i = indexNum-2;i < listRank.size();i++){
					HumanRank humanRank = listRank.get(i);
					list.add(getRankData(humanRank.getPassportId(),tournamentType,humanRank.getValue(),humanRank.getImg(),i+1,humanRank.getName(),human.getHumanVipNewManager().getVipLv()));
				}
			}else{
				for(int i = indexNum-1;i < indexNum+2;i++){
					HumanRank humanRank = listRank.get(i);
					list.add(getRankData(humanRank.getPassportId(),tournamentType,humanRank.getValue(),humanRank.getImg(),i+1,humanRank.getName(),human.getHumanVipNewManager().getVipLv()));
				}
			}
		}
		
		
		}catch(Exception e){
			Loggers.slotLogger.error("", e);
		}
		return list;
	}
	
	/**
	 * SlotRankData
	 * @param passportId
	 * @param tournamentType
	 * @param bonus
	 * @param img
	 * @param rank
	 * @return
	 */
	private SlotRankData getRankData(long passportId,int tournamentType,long bonus,String img,int rank,String name,int vipLevel){
		SlotRankData data = new SlotRankData();
		data.setBonus(bonus);
		data.setImg(img);
		data.setPassportId(passportId);
		data.setTournamentType(tournamentType);
		data.setRank(rank);
		data.setName(name);
		data.setVipLevel(vipLevel);
		return data;
	}
	/**
	 * 获取 竞赛的前三名
	 * @param tournamentType
	 * @return
	 */
	public List<SlotRankData> getSlotRankData(int tournamentType,Human human){
		List<SlotRankData> list = new ArrayList<SlotRankData>();
		 AtomicBoolean ab = flyMap.get(tournamentType);
		 if(ab.get()){
			 List<HumanRank> listRank = humanRankMap.get(tournamentType);
			 for(int i = 0; i < listRank.size() && i < maxLanMap.get(tournamentType);i++){
				 HumanRank humanRank = listRank.get(i);
				 list.add(getRankData(humanRank.getPassportId(),tournamentType,humanRank.getValue(),humanRank.getImg(),i+1,humanRank.getName(),human.getHumanVipNewManager().getVipLv()));
			 }
		 }
		return list;
	}
	
	public List<SngTournamentData> getSngBriefData(){
		List<SngTournamentData> list = new ArrayList<SngTournamentData>();
		long now = Globals.getTimeService().now();
		 for(Entry<Integer,AtomicBoolean> en : flyMap.entrySet()){
			 int key = en.getKey();
			 AtomicBoolean ab = en.getValue();
			 if(ab.get()){
				 SngTournamentData data = new SngTournamentData();
				 data.setTournamentType(key);
				 long entTime = sngEndTime.get(key);
				 data.setEndTimeBlock(entTime - now);
				 long sngJackpot = mapSngValue.get(key);
				 data.setSngJackpot(sngJackpot);
				 int totalHuman = humanIsOpen.get(key).size();
				 data.setTotalHuman(totalHuman);
				 list.add(data);
			 }
		 }
		return list;
	}
	
	
	/**
	 * 打开左侧竞赛面板 竞赛正在进行中
	 * @param player
	 * @param tournamentType
	 */
	public void enterTheContest(Player player,int tournamentType){
		  long passportId = player.getPassportId();
		 
		  List<SlotRankData> list = Globals.getTournamentService().getRoleRankData(player.getHuman(),player.getImg(), tournamentType, passportId,player.getHuman().getName());
		  GCMessage message2 = Globals.getTournamentService().getGCSlotTournamentStartData(tournamentType);
		  GCMessage message1 = Globals.getTournamentService().getGCSlotRankList(list);
		  player.sendMessage(message2);
		  player.sendMessage(message1);
	}
	
	/**
	 * 竞赛数据1
	 * @param tournamentType
	 * @param startTime
	 * @param allBonus
	 * @return
	 */
	public GCMessage getGCSlotTournamentStartData(int tournamentType){
		
		long now = Globals.getTimeService().now();
		
		long endTime = sngEndTime.get(tournamentType);
		long allBonus = mapSngValue.get(tournamentType);
		int totalHuman = humanIsOpen.get(tournamentType).size();
		
		GCSlotTournamentStartData message = new GCSlotTournamentStartData();
		message.setTournamentType(tournamentType);
		message.setStartTime(endTime - now);
		message.setAllBonus(allBonus);
		message.setTotalNum(totalHuman);
		return message;
	}
	
	/**
	 * 竞赛数据1 new
	 * @param tournamentType
	 * @param startTime
	 * @param allBonus
	 * @return
	 */
	public GCMessage getGCSlotTournamentStartDataNew(int tournamentType1,Human human){
		GCTournamentGetList gCTournamentList = new GCTournamentGetList();
		List<SngTournamentData> sngTournamentDataList = new ArrayList<SngTournamentData>();
		long now = Globals.getTimeService().now();
		for(Entry<Integer, AtomicBoolean> entry:flyMap.entrySet()){
			AtomicBoolean  isRuning = entry.getValue();
			if(isRuning.get()){
				Integer tournamentType = entry.getKey();
				SngTournamentData data = new SngTournamentData();
				data.setTournamentType(tournamentType);
				long entTime = sngEndTime.get(tournamentType);
				data.setEndTimeBlock(entTime - now);
				long sngJackpot = mapSngValue.get(tournamentType);
				data.setSngJackpot(sngJackpot);
				int totalHuman = humanIsOpen.get(tournamentType).size();
				data.setTotalHuman(totalHuman);
				data.setVipLevel(human.getHumanVipNewManager().getVipLv());
				sngTournamentDataList.add(data);
			}
		}
		SngTournamentData[] sngTournamentData = new SngTournamentData[sngTournamentDataList.size()];
		for(int i=0;i<sngTournamentDataList.size();i++){
			sngTournamentData[i]=sngTournamentDataList.get(i);
		}
		gCTournamentList.setSngTournamentData(sngTournamentData);
		return gCTournamentList;
	}
	
	/**
	 * 竞赛数据2
	 * @param list
	 * @return
	 */
	public GCMessage getGCSlotRankList(List<SlotRankData> list){
		GCSlotRankList message = new GCSlotRankList();
		message.setSlotRankData(list.toArray(new SlotRankData[list.size()]));
		return message;
	}
	
	/**
	 * 竞赛前3名
	 * @param list
	 * @return
	 */
	public GCMessage getGCSlotGetRank(List<SlotRankData> list){
		GCSlotGetRank message = new GCSlotGetRank();
		message.setSlotRankData(list.toArray(new SlotRankData[list.size()]));
		return message;
	}
	
	/**
	 * 打开面板老虎机竞赛没有开启推崇
	 * @return
	 */
	public GCMessage getGCSlotTournamentNotOpen(){
		GCSlotTournamentNotOpen message = new GCSlotTournamentNotOpen();
		return message;
	}
	
	/**
	 * 打开竞赛页面获取信息
	 * @param list
	 * @return
	 */
	public GCMessage getGCSngEnter(List<SngTournamentData> list){
		
		GCTournamentGetList message = new GCTournamentGetList();
		message.setSngTournamentData(list.toArray(new SngTournamentData[list.size()]));
		return message;
	}
	
	/**
	 * 角色自己的获奖记录
	 * @param list
	 * @return
	 */
	public GCMessage getGCSngRankInfo(List<HumanSngInfo> list){
		GCSngRankInfo message = new GCSngRankInfo();
		message.setHumanSngInfos(list.toArray(new HumanSngInfo[list.size()]));
		return message;
	}


	public void refreshTournament() {
		//遍历 每一种类型的 竞赛
		for(TournamentTemplate tournamentTemplate:tournamentData.values()){
			AtomicBoolean ab = flyMap.get(tournamentTemplate.getTournamentType());
			if(!ab.get()){
				return;
			}
			//找到 这个类型的竞赛的 所有 人
			Map<Long,Integer> humanIsOpenMap= humanIsOpen.get(tournamentTemplate.getTournamentType());
			for(Entry<Long,Integer> entry:humanIsOpenMap.entrySet()){
				long userId = entry.getKey();
				int openOrNot = entry.getValue();
				//找到 这个类型的竞赛的 所有 人 ，如果用户的面板开着 就给他 发送消息
				if(openOrNot == 1){
					Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(userId);
					if(player == null){
						continue;
					}
					//1
					player.sendMessage(getGCSlotTournamentStartData(tournamentTemplate.getTournamentType()));
					//2
					List<SlotRankData> list = Globals.getTournamentService().getRoleRankData(player.getHuman(),player.getImg(), tournamentTemplate.getTournamentType(), player.getPassportId(),player.getHuman().getName());
	    			GCMessage message = getGCSlotRankList(list);
	    			player.sendMessage(message);
				}
			}
		}
		
	}

	public void setUserIdWhenGetTourmentList(long userId){
		allOpenTournamentList.add(userId);
	}
}
