package com.gameserver.bazoo.service.classical;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.enums.RoomState;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.CGRoomCreate;
import com.gameserver.bazoo.msg.GCGuessBigSmall;
import com.gameserver.bazoo.msg.GCGuessOpen;
import com.gameserver.bazoo.msg.GCRobOpen;
import com.gameserver.bazoo.msg.GCStateRoomRoundOpen;
import com.gameserver.bazoo.msg.GCStateRoomRoundResult;
import com.gameserver.bazoo.msg.GCStateRoomRoundTurn;
import com.gameserver.bazoo.msg.GCTalkBig;
import com.gameserver.bazoo.schedule.ClassicalScheduleEndCountOnce;
import com.gameserver.bazoo.schedule.ClassicalScheduleEndToStart;
import com.gameserver.bazoo.schedule.ClassicalScheduleGuessBefore;
import com.gameserver.bazoo.schedule.ClassicalScheduleWhoShouldSwing;
import com.gameserver.bazoo.service.room.BazooPubService;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.service.room.RoomService;
import com.gameserver.bazoo.taskSchedule.ClassicalTask;
import com.gameserver.bazoo.util.DiceInfoUtil;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.bazoo.util.ListToArrUtils;
import com.gameserver.bazoo.util.RobotJudgeUtil;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanBazooWinsManager;
import com.gameserver.player.Player;

/**
 * 无双吹牛的 服务  没有用redis
 * @author JavaServer
 *
 */
public class ClassicalService implements InitializeRequired{
	private Logger logger = Loggers.BAZOO;
	//房间
	private RoomService roomService;
	private BazooPubService bazooPubService;
	private AssistClassicalService assistClassicalService;
	public ClassicalService(BazooPubService bazooPubService){
		this.bazooPubService=bazooPubService;
		this.roomService = bazooPubService.getRoomService();
		this.assistClassicalService=new AssistClassicalService(roomService,bazooPubService);
	}
	
	
	
	public void init() {
		
	}
	
	
	
	/**
	 * 开始 游戏，统一摇一次色子，，统一摇色子  也表示开始, 给房间里的人 发消息 开始游戏
	 */
	public void beginTheGame(RoomNumber roomNumber,Room room){
		//获取房间的人数
		List<Player> roomPlayerList = room.getPlayersPartIn();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[新一轮]---[开始]---[当前房间号::"+roomNumber.toString()+"---房间人数::"+roomPlayerList.size()+"]");
		Player nextPlayer=null;
		Player firstPlayer = room.getPlayerInfo().getEveryRoundFirstCallMan();
		if(firstPlayer==null){
			nextPlayer = room.getNextPlayer(roomPlayerList.get(0));
			room.getPlayerInfo().setEveryRoundFirstCallMan(nextPlayer);
		}else{
			nextPlayer = room.getNextPlayer(firstPlayer);
			room.getPlayerInfo().setEveryRoundFirstCallMan(nextPlayer);
		}
		//给普通人发送消息
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			List<Integer> diceList = this.bazooPubService.getShakeDice().getDiceList();
			
			/*if(p.getPassportId() == 1427){
				diceList.clear();
				diceList.add(1);
				diceList.add(3);
				diceList.add(4);
				diceList.add(5);
				diceList.add(5);
			}else if(p.getPassportId() == 1414){
				diceList.clear();
				diceList.add(1);
				diceList.add(2);
				diceList.add(2);
				diceList.add(5);
				diceList.add(6);
			}else if(p.getPassportId() == 1411){
				diceList.clear();
				diceList.add(1);
				diceList.add(2);
				diceList.add(4);
				diceList.add(4);
				diceList.add(6);
			}*/
			
			p.getHuman().getBazooRoomEveryUserInfo().setDiceValues(diceList);
			assistClassicalService.sendDiceMessage(p,diceList);
		}
		
		//统一给机器人发送消息（包含当前房间所有玩家的色子的值）
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			if(RobotJudgeUtil.judgeRobot(p)){
				assistClassicalService.sendDiceToRobotMessage(p, roomPlayerList);
			}
		}
		
		
		room.getRoomStateInfo().setRoomState(RoomState.stateRoomRoundBegin,null);//统一摇完色子 之后的状态
		//等待一定的时间（前端需要做特效的时间）
//		int waitingTime = bazooPubService.getDiceConfig().getWaitingTime();
		int waitingTime = bazooPubService.getDiceConfig().getWhoTurnTime();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[新一轮]---[第一个叫号的人]---[开始等待]---[应该叫号的用户ID::"+nextPlayer.getPassportId()+"]");
		bazooPubService.setTimeExecute(new ClassicalScheduleWhoShouldSwing(nextPlayer,roomNumber),LLScheduleEnum.CLASSICAL_WHO_SHOULD_SWING,waitingTime,roomNumber);
	}
	/**
	 * 通知所有人 该谁叫号了
	 * @param nextPlayer
	 * @param first 
	 */
	public void whoShouldSwing(Player nextPlayer,RoomNumber roomNumber, boolean first) {
		Room room = roomService.getRoom(roomNumber);
		if(room == null){
			return;
		}
		nextPlayer.getHuman().getBazooRoomEveryUserInfo().getClassicalUserInfo().setCurUser(true);
		List<Player> roomPlayerList = room.getPlayers();
		if(roomPlayerList != null && roomPlayerList.size()>0){
			for(int i=0;i<roomPlayerList.size();i++){
				Player p = roomPlayerList.get(i);
				assistClassicalService.sendSomeOneShouldSwingMessage(p,nextPlayer);
			}
		}
		//后台记录当前 那个 开始叫号人的时间，3秒 到了 还没有 叫 就 替他叫（注意：后台的 时间 设置大于前台）
		RoomEveryUserInfo roomEveryUserInfo = nextPlayer.getHuman().getBazooRoomEveryUserInfo();
		Player nextNextPlayer  = room.getNextPlayer(nextPlayer);
		PlayerInfo playerInfo = room.getPlayerInfo();
		playerInfo.setWaitingMan(nextPlayer);
		int nextTime = this.bazooPubService.getDiceConfig().getNextTime();
		
		GCStateRoomRoundTurn GCStateRoomRoundTurn = new GCStateRoomRoundTurn();
		GCStateRoomRoundTurn.setWhoTurnPassportId(nextPlayer.getPassportId());
		int now = Long.valueOf(Globals.getTimeService().now()/1000).intValue();
		GCStateRoomRoundTurn.setLeftSecond(now+nextTime);
		room.getRoomStateInfo().setRoomState(RoomState.ClassicalState.stateRoomRoundTurn,GCStateRoomRoundTurn);//等待叫号

		/*room.getRoomThreadInfo().execute(nextTime,room.getRoomTotalNumPartIn(),nextPlayer,playerInfo,roomNumber,first);*/
		/*roomEveryUserInfo.getClassicalUserInfo().setTime(nextTime,room.getRoomTotalNumPartIn(),nextPlayer,nextNextPlayer,playerInfo,roomNumber);*/
		
		ClassicalTask ClassicalTask = new ClassicalTask(roomNumber.toString(),room.getRoomTotalNumPartIn(),playerInfo,nextPlayer);
		long nowLong = Globals.getTimeService().now();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[每次定时任务设置的时间："+nowLong+"]---[延长后时间:"+(nowLong+nextTime*1000)+"][延长时间:"+nextTime+"]");
		this.bazooPubService.getBazooScheduleService().addBazooSchedule(nextTime, roomNumber.toString(), ClassicalTask);
	}


	/**
	 * 个人 单独摇色子
	 * 每次单独摇色子 要给其他人发钱
	 */
	
	@SuppressWarnings("unchecked")
	public void  singleSwingDice(Player player){
		Room room = roomService.getRoom(player);
		RoomNumber roomNumber = room.getRoomNumber();
		long gold = 0l;
		PlayerInfo playerInfo = room.getPlayerInfo();
		//已经开始叫号了
		if(playerInfo.isEpicycleStatus()){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[摇色子]---[重摇]---[已经开始叫号了]---[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		List<Player> roomPlayerList = room.getPlayers();
		long goldBefore = player.getHuman().getBazooGold();
		//把自己的钱 扣掉 ，给其他人加上
		List<Integer> diceList =this.bazooPubService.getShakeDice().getDiceList();
		//给用户加钱
		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_CLASSICAL_SINGLE_COST.getReasonText(), roomNumber.toString(),gold);
		player.getHuman().costMoney(gold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_CLASSICAL_SINGLE_COST, detailReason, -1, 1);
		//排行榜 扣钱
		Globals.getHumanBankService().addGold(player, -gold);
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			if(p.getPassportId() != player.getPassportId()){
				//给用户加钱
				String lostDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_CLASSICAL_OTHER_SINGLE_GIVE.getReasonText(), roomNumber.toString(),gold);
				p.getHuman().giveMoney(gold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_CLASSICAL_OTHER_SINGLE_GIVE, lostDetailReason, -1, 1);
				
				//排行榜 加钱
				Globals.getHumanBankService().addGold(p, gold);
			}
		}
		//把摇的色子加进去
		player.getHuman().getBazooRoomEveryUserInfo().setDiceValues(diceList);
		//返回给用户就可以
		assistClassicalService.sendSingleDiceMessage(player,diceList);
		//通知其他人自己重摇了
		assistClassicalService.sendSingleDiceToOtherMessage(player);
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[摇色子]---[重摇]---[当前用户ID::"+player.getPassportId()+"]");
		
		
		Globals.getLogService().sendDiceClassicalBazooGoldLog(player.getHuman(), LogReasons.DiceClassicalBazooGoldLogReason.againSwingGoldChange, 
				LogReasons.DiceClassicalBazooGoldLogReason.againSwingGoldChange.getReasonText(),
				0, roomNumber.toString(), LogReasons.DiceClassicalBazooGoldLogReason.againSwingGoldChange.getReason(), 
				LogReasons.DiceClassicalBazooGoldLogReason.againSwingGoldChange.getReasonText(),goldBefore, gold, player.getHuman().getBazooGold(), null);
	}
	
	
	
	/**
	 * 
	 * 每个人轮流
	 * 叫号
	 * 开始吹牛
	 * @param isback 
	 * @Param num 几个数
	 * @Param value 色子的值
	 * 
	 */
	
	public void talkBig(Player player,int num,int value, String isback){
		logger.info("-----------------------------------------------------------开始开始--------开始开始---------开始开始-----------------------------------------------------------------");
		Room room = roomService.getRoom(player);
		PlayerInfo info = room.getPlayerInfo();
		RoomNumber roomNumber = room.getRoomNumber();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始叫号]---[用户叫号:"+player.getPassportId()+"]---[开始叫号]---[isback::"+isback+"]");
		if(info.getLastMan() != null){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始叫号]---[info.getLastMan()::"+info.getLastMan().getPassportId()+"]---[isback::"+isback+"]");
		}
		//房间公共信息
		if(info != null && info.isRobOpen()){
			//别人已经抢开 这里再叫 就没有用了   ---  结束 over
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[用户叫号]---[别人已经抢开]---[当前用户ID:"+player.getPassportId()+"]---[当前用户名::"+player.getHuman().getName()+"]");
			return;
		}
		if(info.getLastMan() != null && info.getLastMan().getPassportId() == player.getPassportId()){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[上一次叫的人 和 本次一样]---[info.getLastMan()::"+info.getLastMan().getPassportId()+"]");
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[上一次叫的人 和 本次一样]---[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		//验证 
		if(!assistClassicalService.validatePlayer(roomNumber,player,info,num,value)){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[用户叫号]---[验证不通过]---[当前用户ID:"+player.getPassportId()+"]---[当前用户名::"+player.getHuman().getName()+"]");
			return;
		}
		room.getRoomStateInfo().setRoomState(RoomState.ClassicalState.stateRoomCallDice,null);//已经叫号
		if("前端".equals(isback)){//如果是前端发送 要中断后台 的等待线程
			//结束上一个用户的等待线程
			/*assistClassicalService.endWaitingMan(info,roomNumber);*/
			/*room.getRoomThreadInfo().endWaitingMan();*/
			this.bazooPubService.getBazooScheduleService().stopBazooSchedule(roomNumber.toString(),player);
		}
		RoomEveryUserInfo roomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始叫号]---[当前用户ID::"+player.getPassportId()+"]---["+num+"-个-"+value+"]---[isCurUser:"+roomEveryUserInfo.getClassicalUserInfo().isCurUser()+"]");
		//首先 判断一下 是不是当前用户在叫号
		if(!roomEveryUserInfo.getClassicalUserInfo().isCurUser()){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始叫号]---[当前用户没有叫号]---[当前用户ID::"+player.getPassportId()+"]---["+num+"-个-"+value+"]");
			return;
		}
		
		
		//如果当前用户正在观战 就 过
		if(roomEveryUserInfo.getUserStatus() == RoomEveryUserInfo.USER_STATUS_WATCH){
			return;
		}
		
		
		//通过
		info.setLastMan(player);
		
		
		if(!info.isOnePoint() && value == 1){
			info.setOnePoint(true);
		}
			
		roomEveryUserInfo.getClassicalUserInfo().setDiceNumber(num);
		roomEveryUserInfo.getClassicalUserInfo().setDiceValue(value);				
		//该谁了
		Player nextPlayer = room.getNextPlayer(player);
		if(nextPlayer != null){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[--------------------------------------------------------------------------------------------------]");
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[上下用户]---[上一个叫号用户]---[上一个叫用户ID::"+player.getPassportId()+"]---[下一个叫号用户名称::"+player.getHuman().getName()+"]");
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[上下用户]---[下一个叫号用户]---[下一个叫用户ID::"+nextPlayer.getPassportId()+"]---[下一个叫号用户名称::"+nextPlayer.getHuman().getName()+"]");
		}else{
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[开始叫号]---[下一个叫号用户]---[下一个叫用户::NULL]");
		}
		
		
		
		/**
		 * 给房间内所有人发送消息 告诉 每个人 轮到谁了，也就是 该谁叫号了
		 */
		//当前用户的房间 所有人
		GCTalkBig GCTalkBig = new GCTalkBig();
		if(info.isOnePoint()){
			GCTalkBig.setOnePoint(1);
		}else{
			GCTalkBig.setOnePoint(0);
		}
		GCTalkBig.setWhoTurnPassportId(nextPlayer.getPassportId());
		GCTalkBig.setCallDiceNum(roomEveryUserInfo.getClassicalUserInfo().getDiceNumber());
		GCTalkBig.setCallDiceValue(roomEveryUserInfo.getClassicalUserInfo().getDiceValue());
		GCTalkBig.setCurPassportId(player.getPassportId());
		room.sendMessage(room.getPlayers(), GCTalkBig);
		
		/**
		 * 如果叫 到 最大 就让 最后一个抢开
		 * 
		 */
		if(!assistClassicalService.validateMax(room,nextPlayer,num,value)){
			this.robOpen(nextPlayer);
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[当前用户叫到最大号]---[当前用户ID:"+player.getPassportId()+"]-[num:"+num+"--value:"+value+"]--[抢开的用户:"+nextPlayer.getPassportId()+"]");
			return;
		}
		
		
		//马上通知前端  谁可以开始叫号了
		whoShouldSwing(nextPlayer,roomNumber,false);
		
		Globals.getLogService().sendDiceClassicalCallNumLog(player.getHuman(), LogReasons.DiceClassicalCallNumLogReason.call, 
				LogReasons.DiceClassicalCallNumLogReason.call.getReasonText(), 
				0, roomNumber.toString(), num+"-"+value, null);
		logger.info("-----------------------------------------------------------结束结束--------结束结束---------结束结束-----------------------------------------------------------------");
		
	}
	
	
	
	/*
	 * 抢开 一定是 反对最后一个用户的 叫法
	 */
	public synchronized void robOpen(Player player){
		Room room = roomService.getRoom(player);
		if(room == null){
			return;
		}
		RoomNumber roomNumber = room.getRoomNumber();
		PlayerInfo playerInfo = room.getPlayerInfo();
		int userStatus = player.getHuman().getBazooRoomEveryUserInfo().getUserStatus();
		if(userStatus == RoomEveryUserInfo.USER_STATUS_WATCH){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[不能抢开-用户出去观看状态]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		if(userStatus == RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[不能抢开-用户属于纯粹的观光者]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		//抢开的人不能抢开自己
		if(playerInfo.getLastMan() == null){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[不能抢开-还么有出现叫号的人]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		if(player.getPassportId() == playerInfo.getLastMan().getPassportId()){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[抢开的人不能抢开自己]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		RoomEveryUserInfo roomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		/*assistClassicalService.endWaitingMan(playerInfo,roomNumber);*/
		/*room.getRoomThreadInfo().endWaitingMan();*/
		this.bazooPubService.getBazooScheduleService().stopBazooSchedule(roomNumber.toString(),player);
		//如果有人抢开了   就 拉倒
		if(playerInfo.isRobOpen()){
			return;
		}
		//设置抢开人 抢开信息
		playerInfo.setRobOpen(true);
		playerInfo.setRobMan(player);
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[用户抢开]---[当前用户ID::"+player.getPassportId()+"][被抢开用户ID::"+playerInfo.getLastMan().getPassportId()+"]");
		
		//最后一个 叫号的用户信息
		Player lastPlayer = playerInfo.getLastMan();
		ClassicalUserInfo lastClassicalUserInfo = lastPlayer.getHuman().getBazooRoomEveryUserInfo().getClassicalUserInfo();
		//计算抢开是否正确
		boolean isRight = assistClassicalService.countNum(player,lastClassicalUserInfo.getDiceNumber(),lastClassicalUserInfo.getDiceValue());
		//给抢开的人 设置上结果
		roomEveryUserInfo.getClassicalUserInfo().setIfGuess(true);
		roomEveryUserInfo.getClassicalUserInfo().setRight(isRight);
		lastClassicalUserInfo.setIfGuess(true);
		lastClassicalUserInfo.setRight(!isRight);
		playerInfo.setRight(isRight);
		//给房间内所有人发送消息
		List<Player> roomPlayerList = room.getPlayers();
		//设置倍数
		playerInfo.countLen(room,roomPlayerList);
		//给所有人发送消息
		GCRobOpen GCRobOpen = new GCRobOpen();
		GCRobOpen.setRobPassportId(player.getPassportId());
		GCRobOpen.setMultiple((playerInfo.getLen()-1)*2);
		room.sendMessage(roomPlayerList, GCRobOpen);
		
		// 抢开 之后 ，竞猜 之前  前端需要动画 ，所以要 等待一段时间 
		int ir = 0;
		if(isRight){
			ir=1;
		}
		Globals.getLogService().sendDiceClassicalGuessLog(player.getHuman(),
				LogReasons.DiceClassicalGuessLogReason.rob,
				LogReasons.DiceClassicalGuessLogReason.rob.getReasonText(),
				0,
				roomNumber.toString(), ir, ir, null);
		//设定竞猜时间 触发结算
		int guessBeforeTime = bazooPubService.getDiceConfig().getGuessBeforeTime();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[抢开完毕]---[等待动画时间::"+guessBeforeTime+"]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
		
		GCStateRoomRoundOpen GCStateRoomRoundOpen = new GCStateRoomRoundOpen();
		GCStateRoomRoundOpen.setRobMultiple(playerInfo.getLen());
		GCStateRoomRoundOpen.setRobPassportId(playerInfo.getRobMan().getPassportId());
		room.getRoomStateInfo().setRoomState(RoomState.ClassicalState.stateRoomRoundOpen,GCStateRoomRoundOpen);//抢开 状态
		//计算连胜
		this.bazooPubService.countWinTimes(room.getPlayersPartIn(),RoomNumber.MODE_TYPE_CLASSICAL);
		//计算任务
		for(Player p:room.getPlayersPartIn()){
			//给 每一个人  计算    相应的  任务 或者成就
			Globals.getHumanBazooTaskService().finishTask(p);
		}
		
		//小于2就 没有竞猜环节
		if(room.getPlayersPartIn().size() <= 2){
			//直接去结算
			bazooPubService.setTimeExecute(new ClassicalScheduleEndCountOnce(roomNumber),LLScheduleEnum.CLASSICAL_END_COUNT,guessBeforeTime-2,roomNumber);
		}else{
			bazooPubService.setTimeExecute(new ClassicalScheduleGuessBefore(player,roomNumber),LLScheduleEnum.CLASSICAL_GUESS_BEFORE,guessBeforeTime,roomNumber);
		}
		
		
		
	}
	


	/**
	 * 大于两个人 才有竞猜
	 * @param player
	 * @param roomNumber
	 */
	public void guessBefore(Player player,RoomNumber roomNumber){
		Room room = this.roomService.getRoom(roomNumber);
		if(room == null){
			return;
		}
		room.sendMessage(room.getPlayers(), new GCGuessOpen());//通知 发送 竞猜窗口
		//设定竞猜时间 触发结算
		int guessTime = bazooPubService.getDiceConfig().getGuessTime();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[竞猜等待]---[设置竞猜等待时间::"+guessTime+"]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
		room.getRoomStateInfo().setRoomState(RoomState.ClassicalState.stateRoomRoundGuess,null);//竞猜的状态
		bazooPubService.setTimeExecute(new ClassicalScheduleEndCountOnce(roomNumber),LLScheduleEnum.CLASSICAL_END_COUNT,guessTime,roomNumber);
	}
	
	/**
	 * 
	 * 
	 * 在别人 抢开 之后 
	 * 每个人 猜大 或者 猜小 
	 * @param player
	 * @param bigSmall
	 * 
	 * 
	 */
	
	public void everyGuessBigSmall(Player player,int bigSmall){
		Room room = roomService.getRoom(player);
		if(room == null){
			logger.info("[无双吹牛]---[竞猜]---[当前用户名,当前房间为空]");
			return;
		}
		RoomNumber roomNumber = room.getRoomNumber();
		PlayerInfo playerInfo = room.getPlayerInfo();
		if(playerInfo.getLastMan() == null || playerInfo.getRobMan() == null ){//竞猜时机 不对
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[竞猜时机 不对]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		
		int userStatus = player.getHuman().getBazooRoomEveryUserInfo().getUserStatus();
		if(userStatus == RoomEveryUserInfo.USER_STATUS_WATCH){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[不能竞猜-用户出去观看状态]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		if(userStatus == RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[不能竞猜-用户属于纯粹的观光者]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		//如果是 最后一个叫号的人 ，就不允许叫号了
		if(playerInfo.getLastMan().getPassportId() == player.getPassportId()){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[竞猜大小]---[最后一个叫号的人不允许叫号]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		if(playerInfo.getRobMan().getPassportId() == player.getPassportId()){
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[竞猜大小]---[抢开的人不允许叫号]---[当前用户名::"+player.getHuman().getName()+"]"+"[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		RoomEveryUserInfo roomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		//说明参与竞猜了
		roomEveryUserInfo.getClassicalUserInfo().setIfGuess(true);
		//等等与 1 说明是 够了 否则 就是 不够
		if(bigSmall == 1){//你猜的 是 够
			if(playerInfo.isRight()){//抢开的人猜对了  说明 你猜错了
				roomEveryUserInfo.getClassicalUserInfo().setRight(false);
			}else{
				roomEveryUserInfo.getClassicalUserInfo().setRight(true);
			}
		}else{//你猜的是 不够
			if(playerInfo.isRight()){//抢开的人猜对了  说明 你也猜对了
				roomEveryUserInfo.getClassicalUserInfo().setRight(true);
			}else{
				roomEveryUserInfo.getClassicalUserInfo().setRight(false);
			}
		}
		
		//给前端发送消息， 主要是 触发 小手 的动作 （当前用户同意的 是 哪个人）
		GCGuessBigSmall GCGuessBigSmall = new GCGuessBigSmall();
		if(bigSmall == 1){
			GCGuessBigSmall.setAgreePassportId(playerInfo.getLastMan().getPassportId());
		}else{
			GCGuessBigSmall.setAgreePassportId(playerInfo.getRobMan().getPassportId());
		}
		GCGuessBigSmall.setPassportId(player.getPassportId());
		
		room.sendMessage(room.getPlayers(), GCGuessBigSmall);
		
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[竞猜大小]---[抢开的用户ID::"+playerInfo.getRobMan().getPassportId()+"][被抢开的用户ID::"+playerInfo.getLastMan().getPassportId()+"]");
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[竞猜大小]---[抢开是否正确::"+playerInfo.isRight()+"]"+"[当前用户ID::"+player.getPassportId()+"-bigSmall-"+bigSmall+"]---[是否正确::"+roomEveryUserInfo.getClassicalUserInfo().isRight()+"]");
		
		Globals.getLogService().sendDiceClassicalGuessLog(player.getHuman(),LogReasons.DiceClassicalGuessLogReason.guess,
				LogReasons.DiceClassicalGuessLogReason.guess.getReasonText(),0,roomNumber.toString(), bigSmall, bigSmall, null);
	
	}
	
	
	/**
	 * 结算
	 * 
	 *@Param player 这个房间里最后 的那个  下注的人 
	 * 
	 */
	
	public void endCount(RoomNumber roomNumber){
		Room room = roomService.getRoom(roomNumber);
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[结算方法]---[endCount："+roomNumber+"]");
		//最后一个 叫号的用户信息
		PlayerInfo playerInfo = room.getPlayerInfo();
		//算一下 抢开的人 和  最后一个交点的人之间的距离
		int len = playerInfo.getLen();
		//对某个房间进行结算  计算 有几 个几 看看 是否正确
//		boolean isRight = playerInfo.isRight();
		//所有partIn的用户
		List<Player> partInPlayerList = room.getPlayersPartIn();
		
		/**
		 * 找出 对的人
		 */
		List<Player> rightPlayers = new ArrayList<Player>();
		List<Player> wrongPlayers = new ArrayList<Player>();
		//没有参与竞猜的人
		List<Player> noGuessPlayers = new ArrayList<Player>();
		
		//每次结算之前 把 money 清空
		for(Player p:room.getPlayers()){
			p.getHuman().getBazooRoomEveryUserInfo().setMoney(0l);
		}
		for(Player p:partInPlayerList){
			
			RoomEveryUserInfo roomEveryUserInfo= p.getHuman().getBazooRoomEveryUserInfo();
			
			//首先用户 得跟着参与了
			if(roomEveryUserInfo.getClassicalUserInfo().isIfGuess()){
				boolean right = roomEveryUserInfo.getClassicalUserInfo().isRight();
				logger.info("[无双吹牛"+roomNumber.toString()+"]---[对错---输赢]---[当前用户ID:"+p.getPassportId()+"---right:"+ right+"]");
				if(right){
					rightPlayers.add(p);
				}else{
					wrongPlayers.add(p);
				}
			}else{
				logger.info("[无双吹牛"+roomNumber.toString()+"]---[没有---竞猜]---[当前用户ID:"+p.getPassportId()+"]");
				noGuessPlayers.add(p);
			}
		}
		
		logger.info("[无双吹牛]---[开始结算][对的人:"+ListToArrUtils.playerIdToStr(rightPlayers)+"][错的人:"+ListToArrUtils.playerIdToStr(wrongPlayers)+"]");
		/**
		 * 然后 对的人  扣 错的人 的钱
		 */
		List<EndCountInfo> EndCountInfoList = new ArrayList<EndCountInfo>();
		for(Player rPlayer:rightPlayers){
			long totalWinGold = 0;
			RoomEveryUserInfo rp = rPlayer.getHuman().getBazooRoomEveryUserInfo();
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[结算---赢赢赢赢赢赢赢赢-前]---[当前用户ID::"+ rPlayer.getPassportId()+"]---[是否正确::"+rp.getClassicalUserInfo().isRight()+"]---[输赢::"+rp.getMoney()+"]---["+rPlayer.getHuman().getGold()+"]");
			Human rHuman = rPlayer.getHuman();
			int multiple=1;
			//如果是抢开的人  翻倍
			if(rHuman.getPassportId() == playerInfo.getRobMan().getPassportId()){
				if(len > 1){
					multiple = (len-1)*2;
				}
			}
			
			for(Player wPlayer:wrongPlayers){
				RoomEveryUserInfo wp = wPlayer.getHuman().getBazooRoomEveryUserInfo();
				logger.info("[无双吹牛"+roomNumber.toString()+"]---[结算---输输输输输输输输-前]---[当前用户ID::"+ wPlayer.getPassportId()+"]---[输之前::"+wp.getMoney()+"]---["+wPlayer.getHuman().getGold()+"]");
				
				Human wHuman = wPlayer.getHuman();
				if(wHuman.getPassportId() == playerInfo.getRobMan().getPassportId()){
					if(len > 1){
						multiple = (len-1)*2;
					}
				}
				long gold = roomNumber.getBet()*multiple;
				totalWinGold+=gold;
				long tMoney = Math.abs(wp.getMoney())+gold;
				wp.setMoney(-tMoney);//输了多少钱
				EndCountInfoList.add(assistClassicalService.getEndCountInfo(wPlayer,wp,-gold,0));
				logger.info("[无双吹牛"+roomNumber.toString()+"]---[结算---输输输输输输输输-后]---[当前用户ID::"+wPlayer.getPassportId()+"]---[len:"+len+"]---[gold:"+gold+"]---[totalWinGold:"+totalWinGold+"]---[输了多少::"+-gold+"]---[输赢::"+wp.getMoney()+"]---["+wPlayer.getHuman().getGold()+"]");

			}
			rp.setMoney(totalWinGold);//赢了多少钱
			//给用户加钱
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_CLASSICAL_WIN.getReasonText(), roomNumber.toString(),totalWinGold);
			rHuman.giveMoney(totalWinGold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_CLASSICAL_WIN, detailReason, -1, 1);
			
			rp.setMultiple(Long.valueOf(totalWinGold).intValue()/roomNumber.getBet());
			EndCountInfoList.add(assistClassicalService.getEndCountInfo(rPlayer,rp,totalWinGold,0));
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[[结算---赢赢赢赢赢赢赢赢-后]---[当前用户ID::"+rPlayer.getPassportId()+"]---[赢了多少::"+totalWinGold+"]---[输赢::"+rp.getMoney()+"]---["+rPlayer.getHuman().getGold()+"]");
			
			//扣掉 流水
			this.bazooPubService.costWinnerMoney(rPlayer,totalWinGold);
			
			
			//排行榜 加钱
			Globals.getHumanBankService().addGold(rPlayer, totalWinGold);
		}
		//算一下输的人的倍数
		for(Player wPlayer:wrongPlayers){
			RoomEveryUserInfo wp = wPlayer.getHuman().getBazooRoomEveryUserInfo();
			long money = wp.getMoney();//总共输了多少钱
			wp.setMultiple(Long.valueOf(money).intValue()/roomNumber.getBet());
			long costMoney = Math.abs(money);
			//扣掉用户输的钱
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_CLASSICAL_COST.getReasonText(), roomNumber.toString(),costMoney);
			wPlayer.getHuman().costMoney(costMoney, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_CLASSICAL_COST,  detailReason, -1, 1);
			
			//排行榜 扣钱
			Globals.getHumanBankService().addGold(wPlayer,-costMoney);
		}
		//修改倍数
		for(EndCountInfo info:EndCountInfoList){
			Player player = room.getPlayerById(info.getPassportId());
			if(player != null){
				info.setMultiple(player.getHuman().getBazooRoomEveryUserInfo().getMultiple());
			}
		}
		//没有参与竞猜的人
		for(Player p:noGuessPlayers){
			EndCountInfoList.add(assistClassicalService.getEndCountInfo(p,p.getHuman().getBazooRoomEveryUserInfo(),0,0));
		}
		
		/**
		 * 发送日志
		 */
		for(Player p:room.getPlayers()){
			RoomEveryUserInfo everyRoomEveryUserInfo =p.getHuman().getBazooRoomEveryUserInfo();
			RoomNumber everyRoomNumber =p.getHuman().getBazooRoomNumber();
			/**
			 * 结束 给所有 dice的 消息发送 结束消息
			 */
			int ir=0;
			if(everyRoomEveryUserInfo.getClassicalUserInfo().isRight()){
				ir=1;
			}
			Globals.getLogService().sendDiceClassicalGuessLog(p.getHuman(),
					LogReasons.DiceClassicalGuessLogReason.guess,
					LogReasons.DiceClassicalGuessLogReason.guess.getReasonText(),
					0,
					everyRoomNumber.toString(), ir, ir, "---end---");
			
			Globals.getLogService().sendDiceClassicalCallNumLog(p.getHuman(), 
					LogReasons.DiceClassicalCallNumLogReason.call, LogReasons.DiceClassicalCallNumLogReason.call.getReasonText(), 
					0, everyRoomNumber.toString(), 
					everyRoomEveryUserInfo.getClassicalUserInfo().getDiceNumber()+"-"+everyRoomEveryUserInfo.getClassicalUserInfo().getDiceValue(), "---end---");
			
			Globals.getLogService().sendDiceClassicalBazooGoldLog(p.getHuman(), 
					LogReasons.DiceClassicalBazooGoldLogReason.againSwingGoldChange, 
					LogReasons.DiceClassicalBazooGoldLogReason.againSwingGoldChange.getReasonText(),
					0, 
					everyRoomNumber.toString(), 
					LogReasons.DiceClassicalBazooGoldLogReason.againSwingGoldChange.getReason(), 
					LogReasons.DiceClassicalBazooGoldLogReason.againSwingGoldChange.getReasonText(),
					0, everyRoomEveryUserInfo.getMoney(), p.getHuman().getBazooGold(), "---end---");
		}
		//个人的 信息变化记录
		for(Player p:room.getPlayersPartIn()){
			Globals.getHumanBazooPersonalService().addGold(p, p.getHuman().getBazooRoomEveryUserInfo().getMoney(),null);
		}
		/**
		 * 设置上发送金币的用户(用于前端显示的：飞金币)
		 */
		int[] winPassportId = new int[rightPlayers.size()];
		int[] winMultiple = new int[rightPlayers.size()];
		for(int i=0;i<rightPlayers.size();i++){
			Player p = rightPlayers.get(i);
			winPassportId[i]=Long.valueOf(p.getPassportId()).intValue();
			winMultiple[i]=p.getHuman().getBazooRoomEveryUserInfo().getMultiple();
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[结算赢得人]---[winPassportId-"+i+"::"+winPassportId[i]+"---winMultiple-"+i+winMultiple[i]+"]");
		}
		for(EndCountInfo EndCountInfo:EndCountInfoList){
			EndCountInfo.setWinMultiple(winMultiple);
			EndCountInfo.setWinPassportId(winPassportId);
		}
		//给房间里的每个人发送消息
		assistClassicalService.endCountSendMessage(roomNumber,EndCountInfoList);
		
		//给机器人充钱
		roomService.addGoldForRobot(room);
		//开始 下一轮
		int compareTime = bazooPubService.getDiceConfig().getCompareTime();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[结算末尾]---[compareTime::"+compareTime+"]");
		
		
		EndCountInfo[] EndCountInfoArr = new EndCountInfo[EndCountInfoList.size()];
		for(int i=0;i<EndCountInfoList.size();i++){
			EndCountInfoArr[i]=EndCountInfoList.get(i);
		}
		GCStateRoomRoundResult GCStateRoomRoundResult = new GCStateRoomRoundResult();
		GCStateRoomRoundResult.setEndCountInfo(EndCountInfoArr);
		room.getRoomStateInfo().setRoomState(RoomState.ClassicalState.stateRoomRoundResult,GCStateRoomRoundResult);//结算的状态
		
		bazooPubService.setTimeExecute(new ClassicalScheduleEndToStart(roomNumber),LLScheduleEnum.CLASSICAL_END_TO_START,compareTime,roomNumber);
	}
	
	
	
	


	
	
	

	
	
}
