package com.gameserver.bazoo.service.room;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.schedule.LLScheduleEnum;
import com.core.util.RandomUtil;
import com.gameserver.bazoo.data.BetTotalNum;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.data.ReturnPlayerInfo;
import com.gameserver.bazoo.data.ShowHandBet;
import com.gameserver.bazoo.enums.RoomState;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.CGModeChose;
import com.gameserver.bazoo.msg.GCModeChose;
import com.gameserver.bazoo.msg.GCRoomEnter;
import com.gameserver.bazoo.msg.GCRoomEnterNotAllow;
import com.gameserver.bazoo.msg.GCRoomInit;
import com.gameserver.bazoo.msg.GCRoomMatched;
import com.gameserver.bazoo.msg.GCRoomMatcheding;
import com.gameserver.bazoo.msg.GCRoomOut;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteWaitSomeOne;
import com.gameserver.bazoo.msg.GCStateRoomReady;
import com.gameserver.bazoo.msg.GCStateRoomRoundResult;
import com.gameserver.bazoo.msg.GCStateRoomRoundTurn;
import com.gameserver.bazoo.msg.GCStateRoomShowHandAllSwing;
import com.gameserver.bazoo.msg.GCStateRoomShowHandRoundResult;
import com.gameserver.bazoo.msg.GCStateRoomShowHandSingleSwing;
import com.gameserver.bazoo.schedule.ClassicalScheduleBeginTheGame;
import com.gameserver.bazoo.service.TimeTask.BazooScheduleService;
import com.gameserver.bazoo.service.classical.ClassicalUserInfo;
import com.gameserver.bazoo.service.cow.CowUserInfo;
import com.gameserver.bazoo.service.showHand.Card;
import com.gameserver.bazoo.template.LiarsDiceRoomAchieveTemplate;
import com.gameserver.bazoo.template.LiarsDiceRoomConfigTemplate;
import com.gameserver.bazoo.template.LiarsDiceRoomSignInTemplate;
import com.gameserver.bazoo.template.LiarsDiceRoomTaskTemplate;
import com.gameserver.bazoo.template.LiarsDiceRoomTemplate;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.bazoo.util.ListToArrUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.currency.Currency;
import com.gameserver.human.manager.HumanBazooWinsManager;
import com.gameserver.item.template.ItemNewTemplate;
import com.gameserver.player.Player;
import com.gameserver.player.enums.PlayerRoleEnum;

/**
 * 所有的模式公用的service
 * @author JavaServer
 *
 */
public class BazooPubService implements InitializeRequired{
	protected Logger logger = Loggers.BAZOO;
	private BazooScheduleService bazooScheduleService;
	private RoomService roomService;
	//摇色子
	private ShakeDice shakeDice;
	//modeType  List<LiarsDiceRoomTemplate>
	private Map<Integer,List<LiarsDiceRoomTemplate>> LiarsDiceRoomTemplate_bet_Map = new HashMap<Integer,List<LiarsDiceRoomTemplate>>();
	private LiarsDiceRoomConfigTemplate diceConfig = null;
	private Map<Integer,LiarsDiceRoomSignInTemplate> diceSignInMap = new HashMap<Integer,LiarsDiceRoomSignInTemplate>();
	private List<LiarsDiceRoomTaskTemplate> diceTaskList = new ArrayList<LiarsDiceRoomTaskTemplate>();
	private List<LiarsDiceRoomAchieveTemplate> diceAchieveList = new ArrayList<LiarsDiceRoomAchieveTemplate>();
	
	public BazooPubService(){
		this.bazooScheduleService = new BazooScheduleService();
		this.roomService = new RoomService(new AssistRoomService());
		this.shakeDice = new ShakeDice();
		Map<Integer, LiarsDiceRoomTemplate>  LiarsDiceRoomTemplateMap =   Globals.getTemplateService().getAll(LiarsDiceRoomTemplate.class);
		int mode = 0;
		for(LiarsDiceRoomTemplate LiarsDiceRoomTemplate:LiarsDiceRoomTemplateMap.values()){
			if(LiarsDiceRoomTemplate.getModeType() != mode){
				mode=LiarsDiceRoomTemplate.getModeType();
				List<LiarsDiceRoomTemplate> LiarsDiceRoomTemplateList = new ArrayList<LiarsDiceRoomTemplate>();
				LiarsDiceRoomTemplateList.add(LiarsDiceRoomTemplate);
				LiarsDiceRoomTemplate_bet_Map.put(mode, LiarsDiceRoomTemplateList);
			}else{
				List<LiarsDiceRoomTemplate>  LiarsDiceRoomTemplateList = LiarsDiceRoomTemplate_bet_Map.get(mode);
				LiarsDiceRoomTemplateList.add(LiarsDiceRoomTemplate);
			}
		}
		
		Map<Integer, LiarsDiceRoomConfigTemplate>  LiarsDiceRoomConfigTemplateMap =   Globals.getTemplateService().getAll(LiarsDiceRoomConfigTemplate.class);
		for(LiarsDiceRoomConfigTemplate ll:LiarsDiceRoomConfigTemplateMap.values()){
			diceConfig=ll;
		}
		
		Map<Integer, LiarsDiceRoomSignInTemplate>  LiarsDiceRoomSignInTemplateMap =   Globals.getTemplateService().getAll(LiarsDiceRoomSignInTemplate.class);
		for(LiarsDiceRoomSignInTemplate ll:LiarsDiceRoomSignInTemplateMap.values()){
			diceSignInMap.put(ll.getNameInt(), ll);
		}
		
		Map<Integer, LiarsDiceRoomTaskTemplate>  LiarsDiceRoomTaskTemplateMap =   Globals.getTemplateService().getAll(LiarsDiceRoomTaskTemplate.class);
		for(LiarsDiceRoomTaskTemplate ll:LiarsDiceRoomTaskTemplateMap.values()){
			diceTaskList.add(ll);
		}
		
		Map<Integer, LiarsDiceRoomAchieveTemplate>  LiarsDiceRoomAchieveTemplateMap =   Globals.getTemplateService().getAll(LiarsDiceRoomAchieveTemplate.class);
		for(LiarsDiceRoomAchieveTemplate ll:LiarsDiceRoomAchieveTemplateMap.values()){
			diceAchieveList.add(ll);
		}
	}
	
	
	@Override
	public void init() {
		/**
		 * 创建一根线程 ，用户用户循环叫色子
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				bazooScheduleService.runRunRun();
			}
		}).start();;
		
	}
	public BazooScheduleService getBazooScheduleService() {
		return bazooScheduleService;
	}


	/**
	 * 心跳
	 * @param player
	 */
	public void bazooHeartBeat(Player player) {
		Room room = roomService.getRoom(player);
		if(room !=null){
//			RoomNumber roomNumber = room.getRoomNumber();
//			logger.info("[无双吹牛"+roomNumber.toString()+"]---[收到玩家心跳]---[当前玩家::"+player.getPassportId()+"]");
		}else{
//			logger.info("[无双吹牛-没有在房间里]---[收到玩家心跳]---[当前玩家::"+player.getPassportId()+"]");
		}
	}
	
	
	
	
	
	/**
	 * 首先 设置模式
	 * @param player
	 * @param cgModeChose
	 */

	public void handleModeChose(Player player, CGModeChose cgModeChose) {
		RoomNumber roomNumber =	player.getHuman().getBazooRoomNumber();
		roomNumber.setModeType(cgModeChose.getModeType());
		GCModeChose GCModeChose = this.getRoomBetTotalNum(player,cgModeChose.getModeType());
		player.sendMessage(GCModeChose);
		
		logger.info("[无双吹牛]---[模式选择]---[当前用户ID::"+player.getPassportId()+"]---[模式-房间号::"+roomNumber.toString()+"]");
		
	}
	
	
	public boolean judgeBetExist(int modeType,int bet){
		
		List<LiarsDiceRoomTemplate> templateList= this.LiarsDiceRoomTemplate_bet_Map.get(modeType);
		for(LiarsDiceRoomTemplate template :templateList){
			if(template.getSmallBet() == bet){
				return true;
			}
		}
		return false;
	}
	/**
	 * 用户选择模式 后 返回的信息
	 * @return
	 */

	private GCModeChose getRoomBetTotalNum(Player player,int modeType) {
		GCModeChose GCModeChose = new GCModeChose();
		Map<Integer,Integer> betNum = roomService.getRoomMemByModeType(modeType,this.getBets(player.getHuman().getBazooRoomNumber()));
		List<BetTotalNum> BetTotalNumList = new ArrayList<BetTotalNum>();
		for(Entry<Integer,Integer> entry:betNum.entrySet()){
			int bet = entry.getKey();
			int totalNum = entry.getValue();
			BetTotalNum BetTotalNum = new BetTotalNum();
			BetTotalNum.setBet(bet);
			BetTotalNum.setTotalNum(totalNum+RandomUtil.nextInt(50, 100));
			BetTotalNumList.add(BetTotalNum);
		}
		Collections.sort(BetTotalNumList, new Comparator<BetTotalNum>() {

			@Override
			public int compare(BetTotalNum o1, BetTotalNum o2) {
				if(o1.getBet() > o2.getBet()){
					return 1;
				}else if(o1.getBet() < o2.getBet()){
					return -1;
				}else{
					return 0;
				}
			}
		});
		BetTotalNum[] BetTotalNumArr = new BetTotalNum[BetTotalNumList.size()];
		for(int i=0;i<BetTotalNumList.size();i++){
			BetTotalNumArr[i]=BetTotalNumList.get(i);
		}
		GCModeChose.setBetTotalNum(BetTotalNumArr);
		return GCModeChose;
	}
	
	
	
	
	/**
	 * 1、进入 房间 之前要判断  用户有没有在其他房间 如果有的话不允许进入
	 * 2、把删除标志位  设置上数
	 * @param player
	 * @param cgRoomEnter
	 */
	@SuppressWarnings("unchecked")
	public boolean beforeEnterBazooRoom(Player player) {
		String bazooRoom = player.getHuman().getBazooRoom();
		logger.info("[beforeEnterBazooRoom：无双吹牛--------][玩家的房间号1：："+player.getHuman().getBazooRoomNumber().toString()+"]");
		logger.info("[beforeEnterBazooRoom：无双吹牛--------][玩家的房间号2：："+bazooRoom+"]");
		if(StringUtils.isBlank(bazooRoom)){
			return false;
		}
		Room room = roomService.getRoom(RoomNumber.toRoomNumber(bazooRoom));
		logger.info("[beforeEnterBazooRoom：无双吹牛"+bazooRoom+"][玩家的房间号3：："+bazooRoom+"]");
		if(room == null){
			return false;
		}
		logger.info("[无双吹牛"+bazooRoom+"]---[玩家的房间号：："+bazooRoom+"]");
		List<Player> playerList = room.getPlayers();
		if(playerList != null && playerList.size()>0){
			for(Player p:playerList){
				logger.info("[无双吹牛"+bazooRoom+"]---[进入房间前查看房间里的用户ID：："+p.getPassportId()+"]");
				if(p.getPassportId() == player.getPassportId()){
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	
	
	/**
	 * 最终结算完毕 会调用此方法
	 */
	public void endToStart(RoomNumber roomNumber,String type){
		//看看 是不是 应该移除房间
		Room room = roomService.removeRoom(roomNumber);
		//一个人都没有了 那么就返回
		if(room==null ){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---["+type+"]---[新一轮无法开始]---[房间人数0]--[房间被移除]");
			return;
		}else if(room.getPlayersPartIn().size() <= 1){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---["+type+"]---[新一轮无法开始]---[进入匹配状态]---[房间参战人数::"+room.getPlayersPartIn().size()+"]");
		}
		logger.info("[无双吹牛"+roomNumber.toString()+"]---["+type+"]---[后台开始 新一轮]---[进入匹配状态]---[房间参战人数::"+room.getPlayersPartIn().size()+"]");
		if(room.getPlayersPartIn().size() == 1){
			room.getRoomStateInfo().setRoomState(RoomState.stateRoomEnter,null);
		}else{
			int waitingTime = this.getDiceConfig().getWaitingTime();
			GCStateRoomReady GCStateRoomReady = new GCStateRoomReady();
			GCStateRoomReady.setLeftSecond(waitingTime);
			room.getRoomStateInfo().setRoomState(RoomState.stateRoomReady,GCStateRoomReady);//进入 准备状态
		}
		/**
		 * 根据状态 发送消息
		 */
		List<Player> players = room.getPlayers();
		for(Player p:players){
			this.sendMessageByRoomState(p);
		}
		for(Player p:players){
			//当前 用户 获取其他用户的信息(当前用户进入房间 要看到 其他用户的信息)  初始化消息
			GCRoomInit gCRoomInit = this.getAllPlayers(p);
			p.sendMessage(gCRoomInit);
		}
		Globals.getBazooRpcService().startRemoteRobot(roomNumber.toString());
		this.startGameByModeType(room.getPlayersPartIn().get(0),room,true,false);
	}
	
	
	
	
	
	/**
	 * 1、进入 房间
	 * 2、看房间里的人是否 超过两个 ，如果超过两个 ，就倒计时(根据配置) 开始新的一轮 吹牛
	 * @param player
	 * @param cgRoomEnter
	 */
	@SuppressWarnings("unchecked")
	public void enterBazooRoom(Player player,int bet,int priOrPub) {
		//只有公共房间才判断 bet是不是存在 ,私人 不判断
		if(priOrPub == RoomNumber.PUB_ROOM){
			boolean betExist = Globals.getBazooPubService().judgeBetExist(player.getHuman().getBazooRoomNumber().getModeType(), bet);
			if(!betExist){
				GCRoomEnterNotAllow GCRoomEnterNotAllow = new GCRoomEnterNotAllow();
				GCRoomEnterNotAllow.setLangId(LangConstants.DICE_USER_ROOM_NOT_EXIST);
				GCRoomEnterNotAllow.setParamsList(new String[]{""+bet});
				player.sendMessage(GCRoomEnterNotAllow);
				logger.info("[无双吹牛]---[进入房间]---[用户不能进入]---[当前下注额不存在bet:"+bet+"]");
				return;
			}
		}
		//先看看用户有没有 在上一个房间里  如果在就要等 上一个房间 的那一把 玩完
		boolean isInRoom = Globals.getBazooPubService().beforeEnterBazooRoom(player);
		if(isInRoom){
			//如果用户还在上一个房间里 那么 得看看 modeType 和  bet数 是不是 相等 不相等 就不让进入
			RoomNumber goneInRoomNumber = player.getHuman().getBazooRoomNumber();
			RoomNumber curRoomNumber = RoomNumber.toRoomNumber(player.getHuman().getBazooRoom());
			if(goneInRoomNumber.getModeType() != curRoomNumber.getModeType() || bet != curRoomNumber.getBet()){
				goneInRoomNumber.setBet(bet);
				GCRoomEnterNotAllow GCRoomEnterNotAllow = new GCRoomEnterNotAllow();
				GCRoomEnterNotAllow.setLangId(LangConstants.DICE_USER_NOT_OUT_ROOM);
				GCRoomEnterNotAllow.setParamsList(new String[]{curRoomNumber.toString()});
				player.sendMessage(GCRoomEnterNotAllow);
				logger.info("[无双吹牛]---[进入房间]---[用户不能进入"+goneInRoomNumber.toString()+"房间，还在"+curRoomNumber.toString()+" 房间中]---[当前用户ID::"+player.getPassportId()+"]---[当前用户名::"+player.getHuman().getName()+"]");
				return;
			}else{
				logger.info("[无双吹牛]---[进入房间]---[用户进入-刚退出的房间]---[当前用户ID::"+player.getPassportId()+"]---[当前用户名::"+player.getHuman().getName()+"]");
			}
		}
		
		//当前的倍场
		RoomNumber roomNumber = player.getHuman().getBazooRoomNumber();
		roomNumber.setBet(bet);
		roomNumber.setPubOrPri(priOrPub);
		if(!isInRoom){//只有 用户不在这个房间的 时候 才判断房间到底有没有 满
			//判断 房间是否已满  满了 不让进入房间
			Room tRoom = roomService.getRoom(roomNumber);
			if(tRoom != null && (tRoom.getPlayersNotPureWatch().size()>=tRoom.getMaxNum())){
				logger.info("[无双吹牛"+roomNumber.toString()+"]---[进入房间失败]---[房间已满,tRoom.isFullOrNot():"+tRoom.isFullOrNot()+"-人数："+tRoom.getPlayersPartIn().size()+"]---[当前用户::"+player.getPassportId()+"]");
				GCRoomEnterNotAllow GCRoomEnterNotAllow = new GCRoomEnterNotAllow();
				GCRoomEnterNotAllow.setLangId(LangConstants.DICE_USER_ROOM_FULL);
				GCRoomEnterNotAllow.setParamsList(new String[]{roomNumber.toString()});
				player.sendMessage(GCRoomEnterNotAllow);
				return;
			}
		}
		//判断用户的钱 够不够 如果不够 就不让创建房间 或者 不让进入房间
		if(Room.judgeGold(player.getHuman().getGold(), roomNumber)){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[进入房间失败]---[金币不足,金币数量:"+player.getHuman().getGold()+"]---[当前用户::"+player.getPassportId()+"]");
			GCRoomEnterNotAllow GCRoomEnterNotAllow = new GCRoomEnterNotAllow();
			GCRoomEnterNotAllow.setLangId(LangConstants.DICE_USER_GOLD_NOT_ENOUGH);
			GCRoomEnterNotAllow.setParamsList(new String[]{});
			GCRoomEnterNotAllow.setParamsList(new String[]{Room.getNeedGold(roomNumber)+""});
			player.sendMessage(GCRoomEnterNotAllow);
			return;
		}
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始进入房间]---[当前用户::"+player.getPassportId()+"]");
		//房间人数（最大）
		int maxNum = this.getLiarsDiceRoomTemplate(roomNumber).getRoomNum();
		//如果用户还在房间里  那么就把状态改过来
		player.getHuman().getBazooRoomEveryUserInfo().setIsInRoom(RoomEveryUserInfo.USER_IN_ROOM,player.getPassportId());
		if(!isInRoom){
			//把用户添加到房间
			roomService.addUserToRoom(player,maxNum);
			player.getHuman().getBazooRoomEveryUserInfo().setWinTimes(0);
			
			//只有 真人 进入公共房间才能开 机器人
			if(player.getPlayerRoleEnum() != PlayerRoleEnum.ROBOT && priOrPub == RoomNumber.PUB_ROOM){
				//添加机器人
				Globals.getBazooRpcService().startRemoteRobot(player.getHuman().getBazooRoom());
			}
			
		}
		//添加状态
		Room room = roomService.getRoom(roomNumber);
		
		
		
		if(isInRoom){//本身就在房间中 就恢复 在房间中的状态
			player.getHuman().getBazooRoomEveryUserInfo().setUserStatus(RoomEveryUserInfo.USER_STATUS_PARTIN);
		}else{
			player.getHuman().getBazooRoomEveryUserInfo().setUserStatus(room.getRoomStateInfo().judgeState());
		}
		RoomEveryUserInfo RoomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始进入房间]---[当前用户::"+player.getPassportId()+"][isInRoom::"+RoomEveryUserInfo.getIsInRoom()+"][userStatus::"+RoomEveryUserInfo.getUserStatus()+"]");
		
		for(Player p:room.getPlayers()){
			// 0:用户已经退出房间，1:用户没有退出房间
			logger.info("[无双吹牛"+room.getRoomNumber().toString()+"]---[开始进入房间-循环]---[当前用户::"+p.getPassportId()+"][inRoom::"+p.getHuman().getBazooRoomEveryUserInfo().getIsInRoom()+"]");
		}
		this.startGameByModeType(player,room,false,isInRoom);
		
		
	}
	
	
	/**
	 * 经典模式 开始游戏
	 * @param modeType
	 */
	public  void startGameByModeType(Player player,Room room,boolean matchedSend,boolean inRoom){
		 // 查看房间有多少人(必须是参与游戏的人), 如果超过两个 同时 当前房间的状态 是未开始，  就开始倒计时  进行 新的一轮 吹牛
		RoomNumber roomNumber = room.getRoomNumber();
		int totalNum = room.getRoomTotalNum(RoomEveryUserInfo.USER_STATUS_PARTIN);
		//只有登录进来的人才会收到此消息(登录进来的人 如果原先在房间内 就不要发送此消息)
		if(!matchedSend && !inRoom){
			//还有其他用户要看到  当前用户的信息
			this.sendCurPlayerToOther(player,roomNumber.getBet(),roomNumber);
		}
		
		//只能一个人 一个人的走这里
//		synchronized(this){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---["+roomNumber.getModeName()+"-状态-前]---[匹配开始前]---[room.getRoomState():"+room.getRoomStateInfo().getRoomState()+"]");
			int waitingTime = this.getDiceConfig().getWaitingTime();
			GCStateRoomReady GCStateRoomReady = new GCStateRoomReady();
			GCStateRoomReady.setLeftSecond(waitingTime);
			if(room.getRoomStateInfo().getRoomState().getIndex() == RoomState.stateRoomMatching.getIndex()){
				room.getRoomStateInfo().setRoomState(RoomState.stateRoomReady,GCStateRoomReady);//进入 准备状态
				logger.info("[无双吹牛"+roomNumber.toString()+"]---["+roomNumber.getModeName()+"-状态-后]---[匹配开始后]---[room.getRoomState():"+room.getRoomStateInfo().getRoomState()+"]");
				//匹配到玩家
				GCRoomMatched  GCRoomMatched = new GCRoomMatched();
				GCRoomMatched.setWaitTime(waitingTime);
				List<Player> allPlayers = room.getPlayers();
				if(allPlayers != null && allPlayers.size() >0){
					for(Player p :allPlayers){
						p.sendMessage(GCRoomMatched);
					}
				}
				
				logger.info("[无双吹牛"+roomNumber.toString()+"]---["+roomNumber.getModeName()+"]---[匹配成功]---[等待 "+waitingTime+" 秒开始游戏]---[总人数::"+totalNum+"]");
				this.setTimeExecute(new ClassicalScheduleBeginTheGame(roomNumber.toString()),LLScheduleEnum.CLASSICAL_BEGIN_THE_GAME,waitingTime,roomNumber);
			}else if(room.getRoomStateInfo().getRoomState().getIndex() == RoomState.stateRoomReady.getIndex()){
				if(matchedSend){
					logger.info("[无双吹牛"+roomNumber.toString()+"]---["+roomNumber.getModeName()+"]---[匹配成功]---[等待 "+waitingTime+" 秒开始游戏]---[总人数::"+totalNum+"]");
					this.setTimeExecute(new ClassicalScheduleBeginTheGame(roomNumber.toString()),LLScheduleEnum.CLASSICAL_BEGIN_THE_GAME,waitingTime,roomNumber);
				}
			}
//		}
		
		//只有登录进来的人才会收到此消息
		if(!matchedSend){
			/**
			 * 根据状态 发送消息
			 */
			this.sendMessageByRoomState(player);
			
			//当前 用户 获取其他用户的信息(当前用户进入房间 要看到 其他用户的信息)  初始化消息
			GCRoomInit gCRoomInit = this.getAllPlayers(player);
			player.sendMessage(gCRoomInit);
			
		}
		//只有一个人的时候 让 用户 处于等待状态 发送此消息
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始进入房间]---[当前用户::"+player.getPassportId()+"][RoomState::"+room.getRoomStateInfo().getRoomState()+"]");
		if(room.getRoomStateInfo().getRoomState().getIndex() == RoomState.stateRoomEnter.getIndex()){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始进入房间]---[当前用户::"+player.getPassportId()+"][RoomState::"+room.getRoomStateInfo().getRoomState()+"]");
			sendOutMessage(player,room);
		}
		RoomEveryUserInfo RoomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始进入房间]---[当前用户::"+player.getPassportId()+"][isInRoom::"+RoomEveryUserInfo.getIsInRoom()+"][userStatus::"+RoomEveryUserInfo.getUserStatus()+"]");

		//日志
		Globals.getLogService().sendDiceClassicalRoomLog(player.getHuman(),LogReasons.DiceClassicalRoomLogReason.in, 
				LogReasons.DiceClassicalRoomLogReason.in.getReasonText(), roomNumber.getBet(), roomNumber.toString(), 
				LogReasons.DiceClassicalRoomLogReason.in.getReason(),room.getRoomTotalNum(), roomService.getRoomEveryIds(roomNumber));
	}
	
	
	public void sendOutMessage(Player player,Room room){
		GCRoomMatcheding GCRoomMatcheding = new GCRoomMatcheding();
		player.sendMessage(GCRoomMatcheding);
		//用户退出房间 发送消息
		outRoomMessage(player,room);
	}
	
	public void outRoomMessage(Player player,Room room){
		GCRoomOut GCRoomOut = new GCRoomOut();
		long[] beRemovedPassportIds = ListToArrUtils.listToArr(room.getShuldBeDeletedPlayers());
		if(beRemovedPassportIds != null && beRemovedPassportIds.length > 0){
			GCRoomOut.setBeRemovedPassportIds(beRemovedPassportIds);
			player.sendMessage(GCRoomOut);
		}
	}
	
	/**
	 * 退出房间
	 * @param player
	 * @param cGRoomOut
	 */
	public void outBazooRoom(Player player){
		Room room = roomService.getRoom(player);
		if(room != null){
			room.deletePlayer(player);
			
			/**
			 * 检查房间 玩游戏的人 一个都没有了
			 */
			roomService.removePurRoom(room.getRoomNumber());
			//日志
			Globals.getLogService().sendDiceClassicalRoomLog(player.getHuman(),
					LogReasons.DiceClassicalRoomLogReason.out, 
					LogReasons.DiceClassicalRoomLogReason.out.getReasonText(), 
					0, room.getRoomNumber().toString(), 
					LogReasons.DiceClassicalRoomLogReason.out.getReason(),
					room.getRoomTotalNum(), roomService.getRoomEveryIds(room.getRoomNumber()));
		}
	}
	
	
	public LiarsDiceRoomTemplate getLiarsDiceRoomTemplate(RoomNumber roomNumber){
		List<LiarsDiceRoomTemplate> list =  this.getLiarsDiceRoomTemplate_bet_Map().get(roomNumber.getModeType());
		for(LiarsDiceRoomTemplate t:list){
			if(t.getSmallBet() == roomNumber.getBet()){
				return t;
			}
		}
		//私人房间 查不到 自己建立一个
		LiarsDiceRoomTemplate template = new LiarsDiceRoomTemplate();
		template.setExtractValue(500);
		template.setRoomNum(6);
		return template;
	}
	
	
	
	/**
	 * 获取 某个模式下所有 bet
	 * @param roomNumber
	 * @return
	 */
	public List<Integer> getBets(RoomNumber roomNumber){
		List<LiarsDiceRoomTemplate> list =  this.getLiarsDiceRoomTemplate_bet_Map().get(roomNumber.getModeType());
		List<Integer> betList = new ArrayList<Integer>();
		for(LiarsDiceRoomTemplate t:list){
			betList.add(t.getSmallBet());
		}
		return betList;
	}

	/**
	 * 设置 定时任务 执行
	 * @param LLISchedule
	 * @param roomNumber 
	 */
	public void setTimeExecute(LLISchedule LLISchedule,LLScheduleEnum sLLScheduleEnum,int time, RoomNumber roomNumber){
		//开始 下一轮
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, time);
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[设置定时任务]--------------------------------");
		Globals.getScheduleService().scheduleOnce(LLISchedule, sLLScheduleEnum, cal.getTime());
	}
	
	
	public void sendCurPlayerToOther(Player player,int bet,RoomNumber roomNumber){
		Room room = roomService.getRoom(player);
		List<Player> playerList = room.getPlayers();
		for(int i=0;i<playerList.size();i++){
			Player p =playerList.get(i);
			if(p == null){
				continue;
			}
			//给其他人发消息
			if(p.getPassportId() != player.getPassportId()){
				GCRoomEnter curGCRoomEnter = new GCRoomEnter();
				ReturnPlayerInfo[] otherPlayerInfoArr = new ReturnPlayerInfo[1];
				ReturnPlayerInfo OtherPlayerInfo = new ReturnPlayerInfo();
				OtherPlayerInfo.setCurGold(player.getHuman().getGold());
				OtherPlayerInfo.setDiceContainer(null);
				OtherPlayerInfo.setHeadImg(player.getHuman().getImg());
				OtherPlayerInfo.setName(player.getHuman().getName());
				OtherPlayerInfo.setPassportId(player.getPassportId());
				OtherPlayerInfo.setSeat(player.getHuman().getBazooRoomEveryUserInfo().getSeat());
				OtherPlayerInfo.setGirlFlag(player.getHuman().getGirl());
				OtherPlayerInfo.setUserStatus(player.getHuman().getBazooRoomEveryUserInfo().getUserStatus());
				
				
				if(roomNumber.getModeType() == RoomNumber.MODE_TYPE_COW){
					OtherPlayerInfo.setDiceType(p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getCowNum());
				}else if(roomNumber.getModeType() == RoomNumber.MODE_TYPE_SHOWHAND){
					Card card = p.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard();
					if(card!=null){
						OtherPlayerInfo.setDiceType(card.getCardNum());
					}else{
						OtherPlayerInfo.setDiceType(-1);
					}
				}else{
					OtherPlayerInfo.setDiceType(-1);
				}
				OtherPlayerInfo.setDiceValues(DiceUtils.getArrFromDiceList(p.getHuman().getBazooRoomEveryUserInfo().getDiceValues()));
				OtherPlayerInfo.setRedDiceValues(DiceUtils.getArrFromDiceList(p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getRedDiceValues()));
				
				otherPlayerInfoArr[0]=OtherPlayerInfo;
				curGCRoomEnter.setReturnPlayerInfo(otherPlayerInfoArr);
				p.sendMessage(curGCRoomEnter);
			}
		}
	}
	public GCRoomInit getAllPlayers(Player player){
		Room room = roomService.getRoom(player);
		RoomNumber roomNumber = room.getRoomNumber();
		PlayerInfo PlayerInfo = room.getPlayerInfo();
		
		
		GCRoomInit gCRoomInit = new GCRoomInit();
		gCRoomInit.setRoomNum(roomNumber.toString());
		
		RoomEveryUserInfo roomEveryUserInfo = null;
		if(PlayerInfo.getLastMan() != null){
			gCRoomInit.setLastPassportId(PlayerInfo.getLastMan().getPassportId());
			roomEveryUserInfo = PlayerInfo.getLastMan().getHuman().getBazooRoomEveryUserInfo();
		}
		gCRoomInit.setPubOrPri(roomNumber.getPubOrPri());
		if(PlayerInfo.isOnePoint()){
			gCRoomInit.setOnePoint(1);
		}else{
			gCRoomInit.setOnePoint(0);
		}
		/**
		 * 几个时间
		 */
		gCRoomInit.setGuessWait(this.getDiceConfig().getGuessTime());
		gCRoomInit.setTurnWait(this.getDiceConfig().getNextTime());
		gCRoomInit.setWaitTime(this.getDiceConfig().getWaitingTime());
		
		gCRoomInit.setCowSwingToBegin(this.getDiceConfig().getCowSwingToBegin());
		gCRoomInit.setCowOneRoundTime(this.getDiceConfig().getCowOneRoundTime());
		gCRoomInit.setCowLookDiceValueTime(this.getDiceConfig().getCowLookDiceValueTime());
		gCRoomInit.setCowEndCountTime(this.getDiceConfig().getCowEndCountTime());
		
		//每个人摇几次
		gCRoomInit.setReShakingTimes(7);
		gCRoomInit.setShowHandEndToStart(this.getDiceConfig().getShowHandEndToStart());
		gCRoomInit.setShowHandShakeTime(this.getDiceConfig().getShowHandShakeTime());
		gCRoomInit.setShowHandWhoTurn(this.getDiceConfig().getShowHandWhoTurn());
		
		gCRoomInit.setEndCountInfo(new EndCountInfo[0]);
		gCRoomInit.setShowHandBet(new ShowHandBet[0]);
		if(room.getRoomNumber().getModeType() == RoomNumber.MODE_TYPE_COW){
			List<Player> players = room.getPlayersPartIn();
			for(Player p:players){
				if(p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getBanker() == CowUserInfo.isBanker){
					logger.info("[无双吹牛]---[庄家ID:"+p.getPassportId()+"]");
					gCRoomInit.setBankPassportId(p.getPassportId());
					break;
				}
			}
		}
		
		GCMessage  gcMessage = room.getRoomStateInfo().getStateMessage();
		if(gcMessage instanceof GCStateRoomShowHandRoundResult){
			GCStateRoomShowHandRoundResult GCStateRoomShowHandRoundResult = (GCStateRoomShowHandRoundResult)gcMessage;
			gCRoomInit.setEndCountInfo(GCStateRoomShowHandRoundResult.getEndCountInfo());
			
		}else if(gcMessage instanceof GCStateRoomRoundResult){
			GCStateRoomRoundResult GCStateRoomRoundResult = (GCStateRoomRoundResult)gcMessage;
			gCRoomInit.setEndCountInfo(GCStateRoomRoundResult.getEndCountInfo());
			
		}else if(gcMessage instanceof GCStateRoomShowHandSingleSwing){
			GCStateRoomShowHandSingleSwing GCStateRoomShowHandSingleSwing = (GCStateRoomShowHandSingleSwing)gcMessage;
			gCRoomInit.setShowHandBet(GCStateRoomShowHandSingleSwing.getShowHandBet());
		}
		
		
		List<ReturnPlayerInfo> allPlayerInfoList = new ArrayList<ReturnPlayerInfo>();
		List<Player> players = room.getPlayers();
		//获取其他用户信息
		if(players != null && players.size() >0){
			for(int i=0;i<players.size();i++){
				Player p = players.get(i);
					ReturnPlayerInfo allPlayerInfo = new ReturnPlayerInfo();
					allPlayerInfo.setCurGold(p.getHuman().getGold());
					allPlayerInfo.setHeadImg(p.getHuman().getImg());
					logger.info("[无双吹牛]---[当前用户ID::"+p.getPassportId()+"]"+"---[humanIMG::"+p.getHuman().getImg()+"]");
					allPlayerInfo.setName(p.getHuman().getName());
					allPlayerInfo.setPassportId(p.getPassportId());
					allPlayerInfo.setSeat(p.getHuman().getBazooRoomEveryUserInfo().getSeat());
					allPlayerInfo.setGirlFlag(p.getHuman().getGirl());
					allPlayerInfo.setUserStatus(p.getHuman().getBazooRoomEveryUserInfo().getUserStatus());
					if(roomNumber.getModeType() == RoomNumber.MODE_TYPE_COW){
						allPlayerInfo.setDiceType(p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getCowNum());
					}else if(roomNumber.getModeType() == RoomNumber.MODE_TYPE_SHOWHAND){
						Card card = p.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard();
						if(card!=null){
							allPlayerInfo.setDiceType(card.getCardNum());
						}else{
							allPlayerInfo.setDiceType(-1);
						}
					}else{
						allPlayerInfo.setDiceType(-1);
					}
					allPlayerInfo.setDiceValues(DiceUtils.getArrFromDiceList(p.getHuman().getBazooRoomEveryUserInfo().getDiceValues()));
					allPlayerInfo.setRedDiceValues(DiceUtils.getArrFromDiceList(p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getRedDiceValues()));
					allPlayerInfo.setWinTimes(p.getHuman().getBazooRoomEveryUserInfo().getWinTimes());
					if(roomEveryUserInfo == null){
						allPlayerInfo.setNum(0);
						allPlayerInfo.setValue(0);
					}else{
						allPlayerInfo.setNum(roomEveryUserInfo.getClassicalUserInfo().getDiceNumber());
						allPlayerInfo.setValue(roomEveryUserInfo.getClassicalUserInfo().getDiceValue());
					}
					ItemNewTemplate template = Globals.getItemNewService().getUseingClock(p);
					allPlayerInfo.setClockItemId(template.getId());
					allPlayerInfo.setClockImg("http://"+Globals.getServerConfig().getHttpDataAddress()+"/clock/"+template.getIcon()+".png");
					allPlayerInfoList.add(allPlayerInfo);
			}
		}
		if(allPlayerInfoList.size() == 0){
			ReturnPlayerInfo[] aaPlayerInfoArr = new ReturnPlayerInfo[1];
			ReturnPlayerInfo allPlayerInfo = new ReturnPlayerInfo();
			allPlayerInfo.setSeat(0);
			allPlayerInfo.setPassportId(0);
			allPlayerInfo.setCurGold(0);
			allPlayerInfo.setNum(0);
			allPlayerInfo.setValue(0);
			allPlayerInfo.setGirlFlag(0);
			allPlayerInfo.setUserStatus(0);
			allPlayerInfo.setDiceType(-1);
			allPlayerInfo.setDiceValues(new int[0]);
			allPlayerInfo.setRedDiceValues(new int[0]);
			aaPlayerInfoArr[0]=allPlayerInfo;
			gCRoomInit.setReturnPlayerInfo(aaPlayerInfoArr);
			return  gCRoomInit;
		}
		ReturnPlayerInfo[] OtherPlayerInfoArr = new ReturnPlayerInfo[allPlayerInfoList.size()];
		for(int i=0;i<allPlayerInfoList.size();i++){
			OtherPlayerInfoArr[i]=allPlayerInfoList.get(i);
		}
		gCRoomInit.setReturnPlayerInfo(OtherPlayerInfoArr);
		return gCRoomInit;
	}

	
	
	/**
	 * 根据状态返回消息
	 * @param player
	 */
	public void sendMessageByRoomState(Player player) {
		Room room = roomService.getRoom(player);
		if(room == null){
			return;
		}
		GCMessage  gcMessage = room.getRoomStateInfo().getStateMessage();
		if(gcMessage instanceof GCStateRoomRoundTurn){
			GCStateRoomRoundTurn GCStateRoomRoundTurn = (GCStateRoomRoundTurn)gcMessage;
			int now =  GCStateRoomRoundTurn.getLeftSecond() - Long.valueOf(Globals.getTimeService().now()/1000).intValue();
			GCStateRoomRoundTurn.setLeftSecond(now);
			player.sendMessage(GCStateRoomRoundTurn);
			return;
		}
		if(gcMessage instanceof GCStateRoomShowHandAllSwing){
			GCStateRoomShowHandAllSwing GCStateRoomShowHandAllSwing = (GCStateRoomShowHandAllSwing)gcMessage;
			long now =  GCStateRoomShowHandAllSwing.getLeftSecond() - Globals.getTimeService().now();
			GCStateRoomShowHandAllSwing.setLeftSecond(now);
			player.sendMessage(GCStateRoomShowHandAllSwing);
			return;
		}
		if(gcMessage instanceof GCStateRoomShowHandSingleSwing){
			GCStateRoomShowHandSingleSwing GCStateRoomShowHandSingleSwing = (GCStateRoomShowHandSingleSwing)gcMessage;
			long now =  GCStateRoomShowHandSingleSwing.getLeftSecond() - Globals.getTimeService().now();
			GCStateRoomShowHandSingleSwing.setLeftSecond(now);
			player.sendMessage(GCStateRoomShowHandSingleSwing);
			return;
		}
		if(gcMessage instanceof GCStateRoomBlackWhiteWaitSomeOne){
			GCStateRoomBlackWhiteWaitSomeOne GCStateRoomBlackWhiteWaitSomeOne = (GCStateRoomBlackWhiteWaitSomeOne)gcMessage;
			long now =  GCStateRoomBlackWhiteWaitSomeOne.getLeftSecond() - Globals.getTimeService().now();
			GCStateRoomBlackWhiteWaitSomeOne.setLeftSecond(now);
			player.sendMessage(GCStateRoomBlackWhiteWaitSomeOne);
			return;
		}
		player.sendMessage(gcMessage);
	
	}
	
	
	
	
	public ShakeDice getShakeDice() {
		return shakeDice;
	}


	public void setShakeDice(ShakeDice shakeDice) {
		this.shakeDice = shakeDice;
	}


	public Map<Integer, List<LiarsDiceRoomTemplate>> getLiarsDiceRoomTemplate_bet_Map() {
		return LiarsDiceRoomTemplate_bet_Map;
	}

	public void setLiarsDiceRoomTemplate_bet_Map(
			Map<Integer, List<LiarsDiceRoomTemplate>> liarsDiceRoomTemplate_bet_Map) {
		LiarsDiceRoomTemplate_bet_Map = liarsDiceRoomTemplate_bet_Map;
	}

	public LiarsDiceRoomConfigTemplate getDiceConfig() {
		return diceConfig;
	}

	public void setDiceConfig(LiarsDiceRoomConfigTemplate diceConfig) {
		this.diceConfig = diceConfig;
	}
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public Map<Integer, LiarsDiceRoomSignInTemplate> getDiceSignInMap() {
		return diceSignInMap;
	}
	public void setDiceSignInMap(
			Map<Integer, LiarsDiceRoomSignInTemplate> diceSignInMap) {
		this.diceSignInMap = diceSignInMap;
	}
	public List<LiarsDiceRoomTaskTemplate> getDiceTaskList() {
		return diceTaskList;
	}
	public void setDiceTaskList(List<LiarsDiceRoomTaskTemplate> diceTaskList) {
		this.diceTaskList = diceTaskList;
	}

	public List<LiarsDiceRoomAchieveTemplate> getDiceAchieveList() {
		return diceAchieveList;
	}
	public void setDiceAchieveList(
			List<LiarsDiceRoomAchieveTemplate> diceAchieveList) {
		this.diceAchieveList = diceAchieveList;
	}

	/**
	 * 定时清除 房间里只有一个人 而且 他已经退出了的房间
	 */
	public void removeSingleOneOutRoom() {
		List<Room> rooms = this.roomService.getRooms();
		List<Room> shouldBeRemoveRooms = new ArrayList<Room>();
		for(Room room:rooms){
			if(room.getPlayersNotPureWatch().size() == 1){
				List<Player> player = room.getPlayersNotPureWatch();
				int isInRoom  = player.get(0).getHuman().getBazooRoomEveryUserInfo().getIsInRoom();
				if(isInRoom == 0){//用户已经退出房间了
					shouldBeRemoveRooms.add(room);
				}
			}
			if(room.getPlayersNotPureWatch().size() == 0){
				shouldBeRemoveRooms.add(room);
			}
		}
//		logger.info("--------------------定时清理-------------------------");
		rooms.removeAll(shouldBeRemoveRooms);
		
	}


	/**
	 * 说话扣钱
	 */
	
	public boolean speakCostGold(Player player,int msgType){
		RoomNumber roomNumber = RoomNumber.toRoomNumber(player.getHuman().getBazooRoom());
		if(msgType == 1){
			long cost = 500;
			//如果不够下一场 就不让说话了
			boolean ok = Room.judgeGold(player.getHuman().getGold()-cost, roomNumber);
			if(ok){
				logger.info("[无双吹牛]---[金币数不够]---[无法扣除 speak 的金币]---[当前金币："+player.getPassportId()+"]");
				return false;
			}
			String lostDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_BAZOO_COST_SPEAK.getReasonText(),cost);
			player.getHuman().costMoney(cost, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BAZOO_COST_SPEAK, lostDetailReason, -1, 1);
		}else{
			long cost = 500;
			//如果不够下一场 就不让说话了
			boolean ok = Room.judgeGold(player.getHuman().getGold()-cost, roomNumber);
			if(ok){
				logger.info("[无双吹牛]---[金币数不够]---[无法扣除 speak 的金币]---[当前金币："+player.getPassportId()+"]");
				return false;
			}
			String lostDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_BAZOO_COST_SPEAK.getReasonText(),cost);
			player.getHuman().costMoney(cost, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BAZOO_COST_SPEAK, lostDetailReason, -1, 1);
		}
		
		return true;
		
	}
	
	/**
	 * 抽水:获胜者赢取的筹码的比例（万分比）
	 * 获胜 这 摇扣一部分 钱（即：抽水）
	 * @param player
	 * @param num
	 * @param value
	 * @return
	 */

	public void costWinnerMoney(Player player,long gold){
		RoomNumber roomNumber =RoomNumber.toRoomNumber(player.getHuman().getBazooRoom());
		int bet = roomNumber.getBet();
		long cost= gold*this.getLiarsDiceRoomTemplate(roomNumber).getExtractValue()/10000;
		//抽掉流水
		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_CLASSICAL_LIUSHUI_COST.getReasonText(), roomNumber.toString(),cost);
		player.getHuman().costMoney(cost, Currency.GOLD, false, LogReasons.GoldLogReason.BAZOO_CLASSICAL_LIUSHUI_COST, detailReason, -1, 1);
		
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[结算抽水]---[当前用户ID::"+player.getPassportId()+"]---[抽水::"+cost+"]");
	}
	
	
	//计算连胜
	public void countWinTimes(List<Player> playersPartIn,int modeType) {
		for(Player player:playersPartIn){
			HumanBazooWinsManager manager = player.getHuman().getHumanBazooWinsManager();
			RoomEveryUserInfo RoomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
			boolean isRight = false;
			if(modeType == RoomNumber.MODE_TYPE_CLASSICAL){
				ClassicalUserInfo ClassicalUserInfo = RoomEveryUserInfo.getClassicalUserInfo();
				isRight = ClassicalUserInfo.isRight();
			}else if(modeType == RoomNumber.MODE_TYPE_COW || modeType == RoomNumber.MODE_TYPE_SHOWHAND || modeType == RoomNumber.MODE_TYPE_BLACK_WHITE){
				long money = RoomEveryUserInfo.getMoney();
				if(money > 0){
					isRight = true;
				}
			}
			
			if(isRight){//如果赢了就加1
				RoomEveryUserInfo.setWinTimes(RoomEveryUserInfo.getWinTimes()+1);
				//然以保存导数据库里
				manager.saveBazooWins(modeType, RoomEveryUserInfo.getWinTimes());
				
			}else{//如果输了 就 清零
				RoomEveryUserInfo.setWinTimes(0);
				Globals.getHumanBazooTaskService().clearAWinningStreak(player, modeType);
			}
		}
			
	}
	
	
}
