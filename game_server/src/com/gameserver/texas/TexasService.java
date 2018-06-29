package com.gameserver.texas;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.common.model.Card;
import com.core.template.TemplateService;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.TextasEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanTexasManager;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerExitReason;
import com.gameserver.player.enums.PlayerRoleEnum;
import com.gameserver.rank.RankLogic;
import com.gameserver.rank.enums.RankTypeEnum;
import com.gameserver.scene.Scene;
import com.gameserver.scene.SceneService;
import com.gameserver.scene.manager.SceneRoomManager;
import com.gameserver.texas.data.TexasPoolSettleInfoData;
import com.gameserver.texas.data.TexasRoomPlayerInfoData;
import com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData;
import com.gameserver.texas.data.TexasRoomTypeInfoData;
import com.gameserver.texas.data.sng.TexasSngInfoData;
import com.gameserver.texas.data.vip.TexasVipInfoData;
import com.gameserver.texas.enums.TexasRoomEnum;
import com.gameserver.texas.interfaces.ITexasListener;
import com.gameserver.texas.msg.GCJoinTexas;
import com.gameserver.texas.msg.GCLeaveTexas;
import com.gameserver.texas.msg.GCSyncJoinTexas;
import com.gameserver.texas.msg.GCTexasAddBet;
import com.gameserver.texas.msg.GCTexasAllIn;
import com.gameserver.texas.msg.GCTexasBankerPos;
import com.gameserver.texas.msg.GCTexasBigBlind;
import com.gameserver.texas.msg.GCTexasButtomDeal;
import com.gameserver.texas.msg.GCTexasClearTable;
import com.gameserver.texas.msg.GCTexasCoinsSync;
import com.gameserver.texas.msg.GCTexasExit;
import com.gameserver.texas.msg.GCTexasFlop;
import com.gameserver.texas.msg.GCTexasFollow;
import com.gameserver.texas.msg.GCTexasGiveUp;
import com.gameserver.texas.msg.GCTexasHandCard;
import com.gameserver.texas.msg.GCTexasLook;
import com.gameserver.texas.msg.GCTexasPlayerTurn;
import com.gameserver.texas.msg.GCTexasPrepareRestart;
import com.gameserver.texas.msg.GCTexasRiver;
import com.gameserver.texas.msg.GCTexasRoomInfo;
import com.gameserver.texas.msg.GCTexasSeat;
import com.gameserver.texas.msg.GCTexasSettle;
import com.gameserver.texas.msg.GCTexasSettleGiveup;
import com.gameserver.texas.msg.GCTexasSidePool;
import com.gameserver.texas.msg.GCTexasSmallBlind;
import com.gameserver.texas.msg.GCTexasTurn;
import com.gameserver.texas.msg.GCTexasVipList;
import com.gameserver.texas.template.SngMatchConfigTemplate;
import com.gameserver.texas.template.SngMatchTemplate;
import com.gameserver.texas.template.TexasJackpotTemplate;
import com.gameserver.texas.template.TexasRoomConfigTemplate;
import com.gameserver.texas.template.TexasRoomTemplate;


/**
 * 德州扑克服务
 * @author wayne
 *
 */
public class TexasService implements InitializeRequired,AfterInitializeRequired{
	
	/** 日志 */
	private  Logger logger = Loggers.texasRoomLogger;
	
	/**德州信息key*/
	private final String TEXAS_PREFIX = "texas.";
	/**德州sng信息key*/
	private final String TEXAS_SNG_PREFIX = "texas.sng.";
	/**德州房间vip 最大id key*/
	private final String TEXAS_VIP_MAX_ID_PREFIX = "texas.vip.max.id";
	
	/**玩家等待时间 */
	private final long MAX_WAIT_TIME = 2*TimeUtils.SECOND;
	
	/**请求机器人间隔 */
	private final long ROBOT_REQUEST_INTERVAL = 10*TimeUtils.SECOND;
	
	/**模板服务*/
	private TemplateService templateService;
	/**场景服务*/
	private SceneService sceneService;
	/**redis服务*/
	private JedisPool jedisPool;
	
	/**德州普通房间模板*/
	private List<TexasRoomTemplate> texasRoomTemplateList = new ArrayList<TexasRoomTemplate>(); 
	/**德州普通房间id列表*/
	private List<Integer> roomIdList = new ArrayList<Integer>();
	/**德州普通房间类型数据列表*/
	private List<TexasRoomTypeInfoData> texasRoomTypeInfoDataList = new ArrayList<TexasRoomTypeInfoData>();
	/**德州房间配置模板*/
	private TexasRoomConfigTemplate texasRoomConfigTemplate;
	
	/**德州sng比赛模板列表*/
	private List<SngMatchTemplate> sngMathTemplateList = new ArrayList<SngMatchTemplate>(); 
	/**德州sng比赛数据列表*/
	private List<TexasSngInfoData> texasSngInfoDataList = new ArrayList<TexasSngInfoData>();
	/**德州sng比赛配置列表map*/
	private Map<Integer,List<SngMatchConfigTemplate>> sngMatchConfigTemplateMap= new HashMap<Integer,List<SngMatchConfigTemplate>>();
	
	/**德州监听器*/
	private List<ITexasListener> texasListenerList = new ArrayList<ITexasListener>();
	
	/**机器人请求队列*/
	private Map<TexasRoom,Long> requestRobotMap = new HashMap<TexasRoom,Long>();
	
	
	// 彩金池===================================================================
	private Map<Integer,Textas> textasMap = new HashMap<Integer,Textas>();
	
	private Map<Integer,TexasJackpotTemplate> teaxsCarMap = new HashMap<Integer,TexasJackpotTemplate>();
	//==================================================================
	
	public TexasService(){
		
	}
	
	public List<TexasSngInfoData > getTexasSngInfoDataList(){
		return texasSngInfoDataList;
	}
	
	public List<SngMatchTemplate> getSngMathTemplateList(){
		return sngMathTemplateList;
	}
	
	public List<SngMatchConfigTemplate> getSngMatchConfigTemplateList(int sngId){
		return sngMatchConfigTemplateMap.get(sngId);
	}
	
	/**
	 * 添加监听器
	 * @param texasLisner
	 */
	public void addListener(ITexasListener texasLisner){
		texasListenerList.add(texasLisner);
	}
	
	/**
	 * 移除监听器
	 * @param texasLisner
	 */
	public void removeListener(ITexasListener texasLisner){
		texasListenerList.remove(texasLisner);
	}

	
	@Override
	public void init() {
		logger.info("init texas service");
		// TODO Auto-generated method stub
		templateService = Globals.getTemplateService();
		sceneService = Globals.getSceneService();
		jedisPool= Globals.getRedisService().getJedisPool();
		
		initNormalRoom();
		initSNGRoom();
	}
	
	/**
	 * 初始化普通房间配置
	 */
	private void initNormalRoom(){
		logger.info("init texas service normal room");
		Map<Integer,TexasRoomTemplate> tempMap = templateService.getAll(TexasRoomTemplate.class);
		
		for(TexasRoomTemplate temp : tempMap.values())
		{
			texasRoomTemplateList.add(temp);
			roomIdList.add(temp.getId());
			TexasRoomTypeInfoData texasRoomTypeInfoData = TexasRoomTypeInfoData.convertFromTexasRoomTemplate(temp);
			texasRoomTypeInfoDataList.add(texasRoomTypeInfoData); 
		}	
		
		Collections.sort(texasRoomTemplateList, new Comparator<TexasRoomTemplate>(){

			@Override
			public int compare(TexasRoomTemplate o1, TexasRoomTemplate o2) {
				// TODO Auto-generated method stub
				if(o1.getMaxCarry()>o2.getMaxCarry()){
					return 1;
				}
				else if(o1.getMaxCarry()==o2.getMaxCarry()){
					return 0;
				}
				else{
					return -1;
				}
			}
			
		});
		
		texasRoomConfigTemplate = templateService.get(1, TexasRoomConfigTemplate.class);
		
		Map<Integer,TexasJackpotTemplate> tempJacMap = templateService.getAll(TexasJackpotTemplate.class);
		
		for(TexasJackpotTemplate tjt : tempJacMap.values()){
			teaxsCarMap.put(tjt.getCardType(), tjt);
		}
	}
	
	/**
	 * 初始化sng房间配置
	 */
	private void initSNGRoom(){
		logger.info("init texas service sng room");
		Map<Integer,SngMatchTemplate> tempMap = templateService.getAll(SngMatchTemplate.class);
		
		for(SngMatchTemplate temp : tempMap.values())
		{
			sngMathTemplateList.add(temp);
			TexasSngInfoData texasSngInfoData = TexasSngInfoData.convertFromSngMatchTemplate(temp);
			texasSngInfoDataList.add(texasSngInfoData);
		}
		
		Collections.sort(texasSngInfoDataList);
		
		for(SngMatchConfigTemplate temp:templateService.getAll(SngMatchConfigTemplate.class).values()){
			List<SngMatchConfigTemplate> tempSngMatchConfigTemplateList= sngMatchConfigTemplateMap.get(temp.getFieldId());
			if(tempSngMatchConfigTemplateList == null){
				tempSngMatchConfigTemplateList = new ArrayList<SngMatchConfigTemplate>();
				sngMatchConfigTemplateMap.put(temp.getFieldId(), tempSngMatchConfigTemplateList);
			}
			tempSngMatchConfigTemplateList.add(temp);
		}
	}
	
	/**
	 * 德州房间类型列表
	 * @return
	 */
	public List<TexasRoomTemplate> getTexasRoomTemplateList()
	{
		return texasRoomTemplateList;
	}
	
	public TexasRoomConfigTemplate getTexasRoomConfigTemplate() {
		return texasRoomConfigTemplate;
	}



	/**
	 * 德州房间类型信息列表
	 */
	public List<TexasRoomTypeInfoData> getTexasRoomTypeInfoDataList()
	{
		return texasRoomTypeInfoDataList;
	}
	
	/**
	 * 查找房间
	 * @param player
	 * @param roomId
	 * @return
	 */
	public TexasRoom normalRoomForRoomId(Player player,long roomId){
		int sceneId = player.getSceneId();
		Scene scene  = sceneService.getScene(sceneId);
		SceneRoomManager roomManager = scene.getRoomManager();

		TexasRoom room =  roomManager.searchRoomByRoomId(roomId);
		return room;
	}

	/**
	 * 牌型获取参数
	 * @param type
	 * @return
	 */
	public double getCarType(int type){
		if(teaxsCarMap.containsKey(type)){
			return teaxsCarMap.get(type).getJackpotPer()/10000d;
		}
		return 0l;
	}
	
	/**
	 * 是否中彩金
	 * @param type
	 * @return
	 */
	public boolean isCar(int type){
		return teaxsCarMap.containsKey(type);
	}
	
	/**
	 * 获取彩金池初始值
	 * @param id
	 * @return
	 */
	public long getJackpotinit(int id){
		for(TexasRoomTemplate tr : texasRoomTemplateList){
			if(tr.getId() == id){
				return tr.getJackpotOriValue();
			}
		}
		return 0l;
	}
	/**
	 * 彩金池参数
	 * @param id
	 * @return
	 */
	public long getJackpotPoolPer(int id){
		for(TexasRoomTemplate tr : texasRoomTemplateList){
			if(tr.getId() == id){
				return tr.getJackpotPoolPer();
			}
		}
		return 0l;
	}
	
	/**
	 * 累计彩金参数
	 * @param id
	 * @return
	 */
	public long getJackpotAddPoolPer(int id){
		for(TexasRoomTemplate tr : texasRoomTemplateList){
			if(tr.getId() == id){
				return tr.getJackpotAddPoolPer();
			}
		}
		return 0l;
	}
	
	
	/**
	 * 最快开始
	 */
	public TexasRoom roomForQuickStart(Player player){
		
		List<TexasRoomTemplate> list = getAppropriateLevel((int)player.getHuman().getLevel());
	
		for (int i= list.size()-1;i>=0;i--){
			TexasRoomTemplate texasRoomTemplate = list.get(i);
			//未开启
			if(texasRoomTemplate.getOpenUp()==0)
				continue;
			//最大携带 大于自身筹码的一半
			if(texasRoomTemplate.getMaxCarry()>player.getHuman().getGold()/2)
				continue;
			TexasRoom room = roomForJoin(player,texasRoomTemplate.getId());
			
			//没有合适的房间
			if(room ==null)
				continue;
			return room;
		}
		
		//第一个房间
		for (int i= 0;i<list.size();i++){
			TexasRoomTemplate texasRoomTemplate = list.get(i);
			//未开启
			if(texasRoomTemplate.getOpenUp()==0)
				continue;
			//最小携带 大于自身筹码的一半
			if(texasRoomTemplate.getMinCarry()>player.getHuman().getGold())
				continue;
			TexasRoom room = roomForJoin(player,texasRoomTemplate.getId());
			
			//没有合适的房间
			if(room ==null)
				continue;
			return room;
			
		}
		
		return null;
	}
	
	public List<TexasRoomTemplate> getAppropriateLevel(int humanLevel){
		
		List<TexasRoomTemplate> list = new ArrayList<TexasRoomTemplate>();
		for(TexasRoomTemplate template : texasRoomTemplateList){
			if(template.getOpenLv() <= humanLevel){
				list.add(template);
			}
		}
		return list;
		
	}
	
	
	
	/**
	 * 判断是否有空位
	 * -1 代表没有
	 */
	public TexasRoom roomForJoin(Player player,int roomType)
	{
		int sceneId = player.getSceneId();
		Scene scene  = sceneService.getScene(sceneId);
		SceneRoomManager roomManager = scene.getRoomManager();
		
		TexasRoom room =  roomManager.searchRoom(roomType);
		
		//不存在
		if(room == null)
		{
			int curRoomCount = roomManager.getCurrentRoomCount(roomType);
			int maxRoomCount = roomManager.getMaxRoomCount(roomType);
			//超过最大房间数
			if(curRoomCount >= maxRoomCount)
			{
				return null;
			}
			
			room = createTexasRoom(TexasRoomEnum.NORMAL,roomType);
			
            roomManager.addRoom(room);
			return room;
		}
		return room;
	}
	
	/**
	 * sng房间
	 * @param player
	 * @param sngId
	 * @return
	 */
	public TexasRoom roomForJoinSNG(Player player,int sngId) {
		// TODO Auto-generated method stub
		int sceneId = player.getSceneId();
		Scene scene  = sceneService.getScene(sceneId);
		SceneRoomManager roomManager = scene.getRoomManager();
		
		TexasRoom room =  roomManager.searchSNGRoom(sngId);
		
		//不存在
		if(room == null)
		{
			int curRoomCount = roomManager.getCurrentSNGRoomCount(sngId);
			int maxRoomCount = roomManager.getMaxSNGRoomCount(sngId);
			//超过最大房间数
			if(curRoomCount >= maxRoomCount)
			{
				logger.warn("SNG德州房间已满，玩家[" + player.getPassportId() + "]加入失败");
				return null;
			}
			
			//创建新房间
			SngMatchTemplate sngMatchTemplate = templateService.get(sngId, SngMatchTemplate.class);
			if(sngMatchTemplate == null){
				logger.warn("SNG比赛模板错误，玩家[" + player.getPassportId() + "]加入失败");
				return null;
			}
				
			room = createTexasRoom(TexasRoomEnum.SNG,sngId);
	
            roomManager.addSNGRoom(sngId, room);
			return room;
		}
		return room;
	}
	
	
	
	
	/**
	 * vip创建房间
	 * @param player
	 * @param vipId
	 * @param password
	 * @return
	 */
	public TexasRoom roomForVipCreate(Player player,int vipId,String password){
		// TODO Auto-generated method stub
		int sceneId = player.getSceneId();
		Scene scene  = sceneService.getScene(sceneId);
		SceneRoomManager roomManager = scene.getRoomManager();
		
		TexasRoom room =  createTexasRoom(TexasRoomEnum.VIP,vipId);
		
		room.setPassword(password);
		room.setName(player.getPassportName());
        roomManager.addVipRoom(room);
        
		return room;
	}
	
	/**
	 * 创建房间
	 * @param texasRoomEnum
	 * @param tId
	 * @return
	 */
	private TexasRoom createTexasRoom(TexasRoomEnum texasRoomEnum,int tId){
		long now = Globals.getTimeService().now();
		
		
		long rid = Globals.getUUIDService().getNextUUID(now, UUIDType.HUMANTEXASROOMID);
		TexasRoom room = new TexasRoom(texasRoomEnum,tId,rid);
		room.setRid(rid);
		//发送房间创建日志
		Globals.getLogService().sendTexasRoomLog(null,LogReasons.TexasRoomLogReason.CREATE, LogReasons.TexasRoomLogReason.CREATE.getReasonText(),rid,texasRoomEnum.getIndex(), tId);
		logger.info("创建房间模式["+texasRoomEnum.getIndex()+"],类型id["+tId+"],房间号["+rid+"]");
		return room;
	}
	
	/**
	 * 查找vip房间
	 */
	public TexasRoom vipRoomByRid(Player player,long roomId){
		int sceneId = player.getSceneId();
		Scene scene  = sceneService.getScene(sceneId);
		SceneRoomManager roomManager = scene.getRoomManager();
		TexasRoom texasRoom = roomManager.searchVipRoom(roomId);
		return texasRoom;
	}
	
	/**
	 * vip房间
	 * @return
	 */
	public GCTexasVipList buildTexasVipList(Player player){
		int sceneId = player.getSceneId();
		Scene scene  = sceneService.getScene(sceneId);
		SceneRoomManager roomManager = scene.getRoomManager();
		
		List<TexasRoom> roomList =  roomManager.getVipRoomList();
		int len = roomList.size();
		GCTexasVipList gcTexasVipList = new GCTexasVipList();
		TexasVipInfoData[] texasVipInfoList = new TexasVipInfoData[len];
		
		for(int i=0;i<len;i++){
			texasVipInfoList[i] = TexasVipInfoData.convertFromTexasRoom(roomList.get(i));
		}
		gcTexasVipList.setVipInfoDataList(texasVipInfoList);
		return gcTexasVipList;
	}
	
	
	/**
	 * 加入房间
	 */
	public void joinRoom(Player player,TexasRoom room)
	{
	
		Assert.notNull(room,"房间不存在");
		
		//满座
		if(room.isFull())
		{
			logger.error("德州房间已满，玩家[" + player.getPassportId() + "]加入失败");
			return;
		}
		//有座位
		if(room.hasSeat())
		{
			room.playerJoin(player,false);
			return;
		}
		//进入等待列表
		room.playerWaiting(player);


	}
	
	/**
	 * 加入房间
	 */
	public void joinSNGRoom(Player player,TexasRoom room,boolean ticket)
	{
		room.playerJoin(player,ticket);

		return;
	}
	
	/**
	 * 加入房间反馈
	 */
	public void onPlayerJoinRoom(Player player,TexasRoom room){
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		
		logger.debug("玩家["+player.getPassportId()+"],加入房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],位置["+humanTexasManager.getPos()+"]");
		
		
		TexasRoomPlayerInfoData selfTexasRoomPlayerInfoData = humanTexasManager
				.buildRoomPlayerData();

		List<TexasRoomPlayerInfoData> texasRoomPlayerInfoDataList = new ArrayList<TexasRoomPlayerInfoData>();
		for (Player tempRoomPlayer : room.getPlayerManager().getRoomPlayerList()) {
			HumanTexasManager tempHumanTexasManager = tempRoomPlayer.getHuman()
					.getHumanTexasManager();
			texasRoomPlayerInfoDataList.add(tempHumanTexasManager
					.buildRoomPlayerData());
		}
		GCJoinTexas gcJoinTexas = new GCJoinTexas();
		gcJoinTexas.setRoomType(room.getId());
		gcJoinTexas
				.setPlayerList(texasRoomPlayerInfoDataList
						.toArray(new TexasRoomPlayerInfoData[texasRoomPlayerInfoDataList
								.size()]));
		gcJoinTexas.setTexasRoomEnum(room.getTexasRoomEnum().getIndex());
		player.sendMessage(gcJoinTexas);

		if (room.isGaming()) {
			GCTexasRoomInfo gcTexasRoomInfo = room.buildTexasRoomInfoData();
			player.sendMessage(gcTexasRoomInfo);
		}
		// 同步消息
		GCSyncJoinTexas gcSyncJoinTexas = new GCSyncJoinTexas();
		gcSyncJoinTexas.setPlayerJoin(selfTexasRoomPlayerInfoData);
		syncMsg(room,player, gcSyncJoinTexas);
		
		//添加人数
		if(room.isSNG())
		{
			Jedis jedis = jedisPool.getResource();
			long num = jedis.incr(redisKeyForTexasSNGRoom(room.getId()));
			logger.debug("当前sng["+room.getId()+"],玩家人数["+num+"]");
			jedis.close();
		}else if(room.isNormal()){
			Jedis jedis = jedisPool.getResource();
			long num =  jedis.incr(redisKeyForTexasRoom(room.getId()));
			logger.debug("当前普通["+room.getId()+"],玩家人数["+num+"]");
			jedis.close();
		}
	}
	
	/**
	 * 等待房间反馈
	 */
	public void onPlayerWaitingRoom(Player player,TexasRoom room){
		logger.debug("玩家["+player.getPassportId()+"],等待房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"]");
		
		GCJoinTexas gcJoinTexas = new GCJoinTexas();

		List<TexasRoomPlayerInfoData> texasRoomPlayerInfoDataList = new ArrayList<TexasRoomPlayerInfoData>();
		for (Player tempRoomPlayer : room.getPlayerManager().getRoomPlayerList()) {
			HumanTexasManager tempHumanTexasManager = tempRoomPlayer.getHuman()
					.getHumanTexasManager();
			texasRoomPlayerInfoDataList.add(tempHumanTexasManager
					.buildRoomPlayerData());
		}

		gcJoinTexas
				.setPlayerList(texasRoomPlayerInfoDataList
						.toArray(new TexasRoomPlayerInfoData[texasRoomPlayerInfoDataList
								.size()]));

		gcJoinTexas.setRoomType(room.getId());
		gcJoinTexas.setTexasRoomEnum(room.getTexasRoomEnum().getIndex());
		player.sendMessage(gcJoinTexas);
		
		//游戏中
		if (room.isGaming()) {
			GCTexasRoomInfo gcTexasRoomInfo = room.buildTexasRoomInfoData();
			player.sendMessage(gcTexasRoomInfo);
		}
	}
	
	/**
	 * 玩家坐下反馈
	 */
	public void onPlayerSeat(Player player,TexasRoom room){

		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		logger.debug("玩家["+player.getPassportId()+"],坐下房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],位置["+humanTexasManager.getPos()+"]");
		
		TexasRoomPlayerInfoData selfTexasRoomPlayerInfoData = humanTexasManager
				.buildRoomPlayerData();
		GCTexasSeat gcTexasSeat = new GCTexasSeat();
		gcTexasSeat.setPlayerJoin(selfTexasRoomPlayerInfoData);
		player.sendMessage(gcTexasSeat);
		// 同步消息
		GCSyncJoinTexas gcSyncJoinTexas = new GCSyncJoinTexas();
		gcSyncJoinTexas.setPlayerJoin(selfTexasRoomPlayerInfoData);
		syncMsg(room,player, gcSyncJoinTexas);
		
		//添加人数
		if(room.isSNG())
		{
			Jedis jedis = jedisPool.getResource();
			jedis.incr(redisKeyForTexasSNGRoom(room.getId()));
			jedis.close();
		}else if(room.isNormal()){
			Jedis jedis = jedisPool.getResource();
			jedis.incr(redisKeyForTexasRoom(room.getId()));
			jedis.close();
		}
	}
	
	/**
	 * 玩家看牌
	 */
	public void onPlayerCheck(Player player,TexasRoom room){		
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		
		logger.debug("玩家["+player.getPassportId()+"],在房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],位置["+humanTexasManager.getPos()+"] check");
		
		// 广播信息
		GCTexasLook gcTexasLook = new GCTexasLook();
		gcTexasLook.setPos(humanTexasManager.getPos());
		broadcastMsg(room,gcTexasLook);
	}

	/**
	 * 玩家跟注
	 */
	public void onPlayerCall(Player player,TexasRoom room){
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		logger.debug("玩家["+player.getPassportId()+"],在房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],位置["+humanTexasManager.getPos()+"] call");
		
		// 广播信息
		GCTexasFollow gcTexasFollow = new GCTexasFollow();
		gcTexasFollow.setPos(humanTexasManager.getPos());
		broadcastMsg(room,gcTexasFollow);
	}
	
	/**
	 * 玩家加注
	 */
	public void onPlayerRaise(Player player,TexasRoom room,long addBet){
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		logger.debug("玩家["+player.getPassportId()+"],在房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],位置["+humanTexasManager.getPos()+"] raise ["+addBet+"]");
		
		// 广播信息
		GCTexasAddBet gcTexasAddBet = new GCTexasAddBet();
		gcTexasAddBet.setPos(humanTexasManager.getPos());
		gcTexasAddBet.setAddBet(addBet);
		broadcastMsg(room,gcTexasAddBet);
	}
	
	/**
	 * 玩家弃牌
	 * @param player
	 * @param room
	 */
	public void onPlayerGiveup(Player player,TexasRoom room){
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		logger.debug("玩家["+player.getPassportId()+"],在房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],位置["+humanTexasManager.getPos()+"] give up");
		
		// 广播信息
		GCTexasGiveUp gcTexasGiveUp = new GCTexasGiveUp();
		gcTexasGiveUp.setPos(humanTexasManager.getPos());
		broadcastMsg(room,gcTexasGiveUp);
	}
	
	/**
	 * 玩家allin
	 * @param player
	 * @param room
	 */
	public void onPlayerAllIn(Player player,TexasRoom room,long bet){
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		logger.debug("玩家["+player.getPassportId()+"],在房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],位置["+humanTexasManager.getPos()+"] all in ["+bet+"]");
		
		// 广播信息
		GCTexasAllIn gcTexasAllIn = new GCTexasAllIn();
		gcTexasAllIn.setPos(humanTexasManager.getPos());
		gcTexasAllIn.setAllInBet( bet);
		broadcastMsg(room,gcTexasAllIn);
	}
	

	/**
	 * 离开房间
	 */
	public void leaveRoom(Player player)
	{
		TexasRoom room = player.getHuman().getHumanTexasManager().getTexasRoom();
		
		Assert.notNull(room,"房间不存在");
		room.playerLeave(player,false);
		if(room.isSNG()){
			if(room.getCurrentGames()>0)
				room.sngSetRank(player);
		}
		room.playerSettle(player);
	}
	
	/**
	 * 玩家离开
	 * @param player
	 * @param room
	 * @param kick
	 */
	public void onPlayerLeave(Player player,TexasRoom room,boolean kick){

		GCLeaveTexas gcLeaveTexas = new GCLeaveTexas();
		gcLeaveTexas.setPlayerId(player.getPassportId());
		if (kick)
			gcLeaveTexas.setIfNoGold(1);
		else
			gcLeaveTexas.setIfNoGold(0);
		broadcastMsg(room,gcLeaveTexas);
		
		logger.debug("玩家["+player.getPassportId()+"]离开房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"]");
		
		if(room.isSNG()){
			Jedis jedis = jedisPool.getResource();
			long num =jedis.decr(redisKeyForTexasSNGRoom(room.getId()));
			logger.debug("当前sng["+room.getId()+"],玩家人数["+num+"]");
			jedis.close();
		}else if(room.isNormal()){
			Jedis jedis = jedisPool.getResource();
			long num = jedis.decr(redisKeyForTexasRoom(room.getId()));
			logger.debug("当前普通["+room.getId()+"],玩家人数["+num+"]");
			jedis.close();
		}
	}
	
	/**
	 * 玩家结算
	 */
	public void onPlayerSettle(Player player,TexasRoom room){

		logger.debug("玩家["+player.getPassportId()+"]结算房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"]");
		
		HumanTexasManager humanTexasManager =  player.getHuman().getHumanTexasManager();
		humanTexasManager.settleRoom();
		RankLogic.getInstance().updateRank(RankTypeEnum.TEXAS_SNG_WEEK, player.getPassportId(), humanTexasManager.getHumanTexasSNG().getWeekScore());

	}

	
	/**
	 * 玩家退出
	 */
	public void onPlayerExit(Player player,TexasRoom room){
		logger.debug("玩家["+player.getPassportId()+"]退出房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"]");
		
		GCTexasExit gcTexasExit = new GCTexasExit();
		player.sendMessage(gcTexasExit);
		player.getHuman().getHumanTexasManager().exitRoom();

	}
	
	/**
	 * 退出房间
	 */
	public void exitRoom(Player player){
		TexasRoom room = player.getHuman().getHumanTexasManager().getTexasRoom();
		Assert.notNull(room,"房间不存在");
		room.playerExit(player);
		
	}
	
	/**
	 * 轮到玩家
	 */
	public void onPlayerTurn(Player player,TexasRoom room){
		logger.debug("轮到玩家["+player.getPassportId()+"]在房间,模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"]");
		
		GCTexasPlayerTurn gcTexasPlayerTurn = new GCTexasPlayerTurn();
		gcTexasPlayerTurn.setPosition(player.getHuman()
				.getHumanTexasManager().getPos());
		broadcastMsg(room,(GCMessage) gcTexasPlayerTurn);
	}
	
	
	/**
	 * 开始
	 * @param room
	 */
	public void onStart(TexasRoom room,long nextBlindTime){
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],开始局号["+room.getGid()+"],剩余升盲时间["+nextBlindTime+"]");
		
		GCTexasPrepareRestart gcTexasPrepareStart = new GCTexasPrepareRestart();
		gcTexasPrepareStart.setSmallBlind(room.getSmallBlind());
		gcTexasPrepareStart.setDuration(nextBlindTime);
		broadcastMsg(room,gcTexasPrepareStart);
		
		//sng判断是否是首局
		if(room.isSNG()){
	
			for(Player player :room.getPlayerManager().getRoomPlayerList()){
				if(room.getCurrentGames()==0){
					logger.info("玩家["+player.getPassportId()+"]参加sng，比赛开始");
					player.getHuman().getHumanTexasManager().onSngStart();
				}
			}
		}
		
		//发送开始日志
		String detailReason = MessageFormat.format( LogReasons.TexasRoomLogReason.BEGIN.getReasonText(), room.getGid(),room.playersLog());
		Globals.getLogService().sendTexasRoomLog(null, LogReasons.TexasRoomLogReason.BEGIN, detailReason, room.getRid(), room.getTexasRoomEnum().getIndex(), room.getId());
	}
	
	/**
	 * 清理桌面
	 * @param room
	 */
	public void onClear(TexasRoom room){
		
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],清理桌面");
		
		GCTexasClearTable gcTexasClearTable = new GCTexasClearTable();
		broadcastMsg(room,gcTexasClearTable);
		
		for(Player player :room.getPlayerManager().getRoomPlayerList()){
			this.onSyncGold(player, room);
		}
		
		String detailReason = MessageFormat.format( LogReasons.TexasRoomLogReason.OVER.getReasonText(), room.getGid(),room.playersLog());
		Globals.getLogService().sendTexasRoomLog(null, LogReasons.TexasRoomLogReason.OVER, detailReason, room.getRid(), room.getTexasRoomEnum().getIndex(), room.getId());
		
		if(Globals.getServerConfig().isRobot()){
			checkRoomRobots(room);
		}
	}
	
	/**
	 * 检查机器人
	 * @param room
	 */
	private void checkRoomRobots(TexasRoom room){
		if(!room.isNormal())
			return;
		
		//发送请求加入机器人
		TexasRoomTemplate tempTexasRoomTemplate = Globals.getTemplateService().get(room.getId(), TexasRoomTemplate.class);
		if(tempTexasRoomTemplate.getOperRb()!=1)
			return;
		
		//判断是否都是机器人
		int robotNum = robotNumForRoom(room);
		if(robotNum == room.getPlayerManager().getRoomPlayerList().size()){
			List<Player> listCope = new ArrayList<Player>();
			listCope.addAll(room.getPlayerManager().getRoomPlayerList());
			for(Player player : listCope){
				if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
					//踢掉机器人
					player.disconnectNew();
					Globals.getOnlinePlayerService().offlinePlayerNew(player, PlayerExitReason.LASTNETTIMEOUT);
				
				}
			}
			listCope.clear();
			return;
		}
		
		//位置没满
		if(room.hasSeat()){
			//检查人数
			if(room.ifBestSuit()){
				return;
			}
			
			//发送机器人请求
			long now = Globals.getTimeService().now();
			requestRobotMap.put(room, now);
			Globals.getRobotService().requestRobotJoin(room.getRid());
			
			return;
		}

		if(robotNum>=1){
			for(Player player : room.getPlayerManager().getRoomPlayerList()){
				if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
					//踢掉机器人
					player.disconnectNew();
					Globals.getOnlinePlayerService().offlinePlayerNew(player, PlayerExitReason.LASTNETTIMEOUT);
					break;
				}
			}
		}
	}
	
	
	/**
	 * banker pos
	 */
	public void onBankerPos(TexasRoom room,int bankerPos){
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],庄家位置["+bankerPos+"]");
		
		// 发送庄家位置
		GCTexasBankerPos gcTexasBankerPos = new GCTexasBankerPos();
		gcTexasBankerPos.setPos(room.getBankerPos());
		broadcastMsg(room,gcTexasBankerPos);
	}
	
	/**
	 * 小盲注
	 */
	public void onSmallBlind(TexasRoom room,int pos,long smallBlind){
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],开始小盲注["+smallBlind+"],位置["+pos+"]");
		
		GCTexasSmallBlind gcTexasSmallBlind = new GCTexasSmallBlind();
		gcTexasSmallBlind.setPos(pos);;
		gcTexasSmallBlind.setSmallBlind(smallBlind);
		broadcastMsg(room,gcTexasSmallBlind);
	}
	
	/**
	 * 大盲注
	 */
	public void onBigBlind(TexasRoom room,int pos,long bidBlind){
		
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],开始大盲注["+bidBlind+"],位置["+pos+"]");
		
		GCTexasBigBlind gcTexasBigBlind = new GCTexasBigBlind();
		gcTexasBigBlind.setPos(pos);;
		gcTexasBigBlind.setBigBlind(bidBlind);
		broadcastMsg(room,gcTexasBigBlind);
	}
	
	/**
	 * preflop
	 * @param room
	 */
	public void onPreflop(TexasRoom room){
		
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],开始发送玩家底牌");
		
		// 发送底牌信息给玩家
		for (Player roomPlayer : room.getPlayerManager().getRoomPlayerList()) {
			GCTexasButtomDeal gcTexasButtonDeal = new GCTexasButtomDeal();
			gcTexasButtonDeal.setCardList(roomPlayer.getHuman()
					.getHumanTexasManager().getCardValueArray());
			roomPlayer.sendMessage(gcTexasButtonDeal);
		}
		
		// 发送底牌信息给盘观者
		for (Player roomPlayer : room.getPlayerManager().getWaitingPlayerList()) {

			GCTexasButtomDeal gcTexasButtonDeal = new GCTexasButtomDeal();
			int[] temp = { 0, 0 };
			gcTexasButtonDeal.setCardList(temp);
			roomPlayer.sendMessage(gcTexasButtonDeal);
		}
		
	}
	
	/**
	 * 德州房间flop
	 * @param room
	 */
	public void onFlop(TexasRoom room){
		
		
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],开始发送公共牌");
		
		int[] publicArry = new int[3];

		for (int i = 0; i < 3; i++) {
			publicArry[i] = room.getPublicCardList().get(i).getValue();
		}
		// 发送底牌信息给玩家
		GCTexasFlop gcTexasFlop = new GCTexasFlop();
		gcTexasFlop.setCardList(publicArry);
		broadcastMsg(room,(GCMessage) gcTexasFlop);
		
	}
	
	/**
	 * turn
	 * @param room
	 */
	public void onTurn(TexasRoom room){
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],开始发第四张牌");
		
		Card lastCard = room.getPublicCardList().get(room.getPublicCardList().size()-1);
		// 发送底牌信息给玩家
		GCTexasTurn gcTexasTurn = new GCTexasTurn();
		gcTexasTurn.setCard(lastCard.getValue());
		broadcastMsg(room,(GCMessage) gcTexasTurn);
	}
	
	/**
	 * river
	 * @param room
	 */
	public void onRiver(TexasRoom room){
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],开始发第五张牌");
		
		Card lastCard = room.getPublicCardList().get(room.getPublicCardList().size()-1);
		// 发送底牌信息给玩家
		GCTexasRiver gcTexasRiver = new GCTexasRiver();
		gcTexasRiver.setCard(lastCard.getValue());
		broadcastMsg(room,(GCMessage) gcTexasRiver);
	}
	
	/**
	 * 分池
	 */
	public void onSidePool(TexasRoom room){
		logger.debug("房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],分池");
		
		int tempMainPool = room.getBetPool();
		GCTexasSidePool gcTexasSidePool = new GCTexasSidePool();
		List<TexasPool> texasSidePoolList = room.getTexasSidePoolList();
		
		int[] sidePool = new int[texasSidePoolList.size() + 1];
		sidePool[0] = tempMainPool;
		for (int i = 0; i < texasSidePoolList.size(); i++) {
			sidePool[i + 1] = texasSidePoolList.get(i).getBet()
					* texasSidePoolList.get(i).getPlayerList().size();
			tempMainPool -= sidePool[i + 1];
		}
		
		sidePool[0] = tempMainPool;
		gcTexasSidePool.setSidePoolList(sidePool);

		broadcastMsg(room,gcTexasSidePool);
	}
	

	/**
	 * 结算放弃
	 */
	public void onSettleGiveUp(Player player,TexasRoom room){
		
		logger.debug("玩家["+player.getPassportId()+"],房间模式["+room.getTexasRoomEnum()+"],类型["+room.getId()+"],房间号["+room.getRid()+"],局号["+room.getGid()+"],结算放弃");
		
		int mainPool = room.getBetPool();

		for (int i = 0; i < room.getTexasSidePoolList().size(); i++) {
			mainPool -= room.getTexasSidePoolList().get(i).getBet()
					* room.getTexasSidePoolList().get(i).getPlayerList().size();
		}

		GCTexasSettleGiveup gcTexasSettleGiveup = new GCTexasSettleGiveup();

		int[] poolList = new int[room.getTexasSidePoolList().size() + 1];
		poolList[0] = mainPool;
		for (int i = 0; i < room.getTexasSidePoolList().size(); i++) {
			poolList[i + 1] = room.getTexasSidePoolList().get(i).getAllbet();
		}

		gcTexasSettleGiveup.setSettlePoolList(poolList);
		gcTexasSettleGiveup.setWinnerPos(player.getHuman()
				.getHumanTexasManager().getPos());
		broadcastMsg(room,gcTexasSettleGiveup);
	}
	
	/**
	 * 结算
	 */
	public void onSettle(TexasRoom room,List<TexasPoolSettleInfoData> texasPoolSettleInfoDataList,List<TexasRoomPlayerSettleCardsInfoData> texasRoomPlayerSettleCardsInfoDataList){
		GCTexasSettle gcTexasSettle = new GCTexasSettle();
		gcTexasSettle.setSettlePoolList(texasPoolSettleInfoDataList.toArray(new TexasPoolSettleInfoData[texasPoolSettleInfoDataList.size()]));
		gcTexasSettle.setPlayerList(texasRoomPlayerSettleCardsInfoDataList.toArray(new TexasRoomPlayerSettleCardsInfoData[texasRoomPlayerSettleCardsInfoDataList.size()]));
		broadcastMsg(room,gcTexasSettle);
	}
	

	/**
	 * 同步筹码
	*/
	public void onSyncGold(Player player,TexasRoom room){
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		GCTexasCoinsSync gcTexasCoinsSync = new GCTexasCoinsSync();
		gcTexasCoinsSync.setCoins(humanTexasManager.getCoins());
		gcTexasCoinsSync.setPos(humanTexasManager.getPos());
		broadcastMsg(room,gcTexasCoinsSync);
	}
	
	/**
	 * 发送牌类型
	 * @param player
	 * @param room
	 */
	public void onSendTexasHandCardType(Player player,TexasRoom room){

		GCTexasHandCard gcTexasHandCard = new GCTexasHandCard();
		gcTexasHandCard.setHandCardType(player.getHuman()
				.getHumanTexasManager().getTexasHandCard()
				.getTexasHandCardEnum().getIndex());
		player.sendMessage(gcTexasHandCard);
	}
	
	/**
	 * 房间销毁
	 */
	public void onTexasRoomDestroy(TexasRoom room){
		//发送房间销毁日志
		logger.warn("房间模式["+room.getTexasRoomEnum().getIndex()+"],类型id["+room.getId()+"],房间号["+room.getRid()+"]销毁");
		Globals.getLogService().sendTexasRoomLog(null,LogReasons.TexasRoomLogReason.DESTROY, LogReasons.TexasRoomLogReason.CREATE.getReasonText(),room.getRid(),room.getTexasRoomEnum().getIndex(), room.getId());
	
	}
	
	/**
	 * 房间等待时间
	 */ 
	public void onTexasRoomWait(TexasRoom room,long aWaitTime){
		
		if(!Globals.getServerConfig().isRobot())
			return ;
		
		//非普通房间
		if(!room.isNormal()){
			return;
		}
		
		
		
		//还没到时候
		if(aWaitTime<MAX_WAIT_TIME){
			return;
		}
		
		//发送请求加入机器人
		TexasRoomTemplate tempTexasRoomTemplate = Globals.getTemplateService().get(room.getId(), TexasRoomTemplate.class);
		if(tempTexasRoomTemplate.getOperRb()!=1)
			return;
		
		if(Globals.getOnlinePlayerService().isFull()){
			logger.warn("德州服务器已满");
			return;
		}
		
		int robotNum = robotNumForRoom(room);
		if(robotNum == room.getPlayerManager().getRoomPlayerList().size())
			return;
		
		long now = Globals.getTimeService().now();
		//判断是否已经发过请求了
		Long lastRequestTime = requestRobotMap.get(room);
		
		if(lastRequestTime !=null){
			long lastDuration =now- lastRequestTime;
			if(lastDuration < ROBOT_REQUEST_INTERVAL)
				return;
		}
		
		Globals.getRobotService().requestRobotJoin(room.getRid());
		requestRobotMap.put(room, now);
	}
	
	
	@Override
	public void afterInit() {
		List<TextasEntity> slotEntityList = Globals.getDaoService().getTextasDao().getAllTextas();
		
		if(slotEntityList == null){
			slotEntityList = new ArrayList<TextasEntity>();
		}
		
		for(TexasRoomTemplate tt : texasRoomTemplateList){
			boolean fly = true;
			for(TextasEntity entity : slotEntityList){
				if(tt.getId() == entity.getTexasId()){
					Textas textas = new Textas();
					textas.fromEntity(entity);
					textasMap.put(textas.getTexasId(), textas);
					fly = false;
					break;
				}
			}
			if(fly){
				addTextas(tt);
			}
		}
	
	}
	
	private void addTextas(TexasRoomTemplate tt){
	 long now = Globals.getTimeService().now();
   	 Textas textas = new Textas();
   	 textas.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.TEXASROOT));
   	 textas.setTexasId(tt.getId());
   	 textas.setJackpot(tt.getJackpotOriValue());
   	 textas.setCumuJackpot(0);
   	 textas.setCreateTime(Globals.getTimeService().now());
   	 textas.setInDb(false);
   	 textas.active();
   	 textas.setModified();
   	 textasMap.put(textas.getTexasId(), textas);
	}
	
	
	
	
	/**
	 * 获取德州扑克
	 * @param textasId
	 * @return
	 */
	public Textas getTextas(int textasId){
		return textasMap.get(textasId);
	}
	
	/**
	 * 发送信息给所有玩家
	 */
	public void broadcastMsg(TexasRoom room,GCMessage msg) {
		for (Player roomPlayer : room.getPlayerManager().getRoomPlayerList()) {
			roomPlayer.sendMessage(msg);
		}

		for (Player roomPlayer : room.getPlayerManager().getWaitingPlayerList()) {
			roomPlayer.sendMessage(msg);
		}
	}

	/**
	 * 同步信息给玩家
	 */
	public void syncMsg(TexasRoom room,Player roomPlayer, GCMessage msg) {
		for (Player tempRoomPlayer : room.getPlayerManager().getRoomPlayerList()) {
			if (roomPlayer != tempRoomPlayer) {
				tempRoomPlayer.sendMessage(msg);
			}
		}

		for (Player tempRoomPlayer : room.getPlayerManager().getWaitingPlayerList()) {
			tempRoomPlayer.sendMessage(msg);
		}
	}
	
	
	/**
	 * play 开始德州扑克游戏
	 * @param human
	 */
	public void onPlay(Human human){
		for(ITexasListener texasListner : texasListenerList){
		//	texasListner.onTexasPlay(human, human.getHumanTexasManager().getTexasRoom().getId());
		}
	}
	
	//win
	public void onPlayFinished(Human human,int handCardType,int winBet){
		for(ITexasListener texasListner : texasListenerList){
			texasListner.onTexasPlayFinished(human, human.getHumanTexasManager().getTexasRoom().getId(),handCardType, winBet);
		}
	}
	
	/**德州房间总人数 */
	
	public Map<Integer,Long> getTexasRoomMapByIds()
	{
		Map<Integer,Long> tempMap = new HashMap<Integer,Long>();
		Jedis jedis = this.jedisPool.getResource();
		
		if(jedis == null)
		{
			logger.error("请求房间总人数， jedis instance failed");
			return null;
		}
		
		Pipeline p = jedis.pipelined();
		for(Integer roomId : roomIdList)
		{
			p.get(redisKeyForTexasRoom(roomId));
		}
		List<Object> roomPackList= p.syncAndReturnAll();
		
		for(int i=0;i<roomPackList.size();i++)
		{
			if(roomPackList.get(i)==null)
				continue;
			tempMap.put(roomIdList.get(i), Long.parseLong((String) roomPackList.get(i)));
		}
		
		jedis.close();
		return tempMap;
	}
	
	/**德州房间人数key*/
	public String redisKeyForTexasRoom(int roomId){
		return TEXAS_PREFIX+String.valueOf(roomId);
	}
	
	/**德州sng房间人数key*/
	public String redisKeyForTexasSNGRoom(int sngId){
		return TEXAS_SNG_PREFIX+String.valueOf(sngId);
	}
	
	/**
	 * 德州房间机器人数
	 * @param room
	 * @return
	 */
	public int robotNumForRoom(TexasRoom room){
		int num = 0;
		for(Player player :room.getPlayerManager().getRoomPlayerList()){
			if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
				num+=1;
			}
		}
		return num;
	}
	
	/**
	 * 获取等级对应的彩金
	 * @param level
	 * @return
	 */
	public long getTexasJackpot(){
		long jackpot = 0l;
		for(Textas textas : textasMap.values()){
			long jack = textas.getJackpot();
			if(jackpot < jack){
				jackpot = jack;
			}
		}
		return jackpot;
	}
	
}
