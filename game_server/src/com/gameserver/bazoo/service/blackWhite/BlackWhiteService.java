package com.gameserver.bazoo.service.blackWhite;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.gameserver.bazoo.enums.RoomState;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.GCBlackWhiteCallNum;
import com.gameserver.bazoo.msg.GCBlackWhiteEndCount;
import com.gameserver.bazoo.msg.GCBlackWhiteWhoTurn;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteSwingLeft;
import com.gameserver.bazoo.schedule.BlackWhiteScheduleEndToStart;
import com.gameserver.bazoo.schedule.BlackWhiteScheduleSwingLeft;
import com.gameserver.bazoo.schedule.BlackWhiteScheduleWhoShouldSwing;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.taskSchedule.BlackWhiteTask;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.bazoo.util.ListToArrUtils;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 黑白 单双的 service
 * @author JavaServer
 *
 */
public class BlackWhiteService implements InitializeRequired{
	private Logger logger = Loggers.BAZOO;
	
	private BlackWhiteAssistService blackWhiteAssistService;
	private BlackWhiteLogic blackWhiteLogic;
	
	public BlackWhiteService(){
		blackWhiteAssistService = new BlackWhiteAssistService(); 
		blackWhiteLogic = new BlackWhiteLogic(); 
	}
	
	
	@Override
	public void init() {
		
	}
	
	
	
	/**
	 * 开始 游戏，统一摇一次色子，，统一摇色子  也表示开始, 给房间里的人 发消息 开始游戏
	 */
	public void beginTheGame(RoomNumber roomNumber,Room room){
		//获取房间的人数
		List<Player> roomPlayerList = room.getPlayersPartIn();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[红黑单双 绝对新一轮]---[开始]---[当前房间号::"+roomNumber.toString()+"---房间人数::"+roomPlayerList.size()+"]");
		Player nextPlayer=null;
		Player firstPlayer = room.getPlayerInfo().getEveryRoundFirstCallMan();
		if(firstPlayer==null){
			nextPlayer = room.getNextPlayer(roomPlayerList.get(0));
			room.getPlayerInfo().setEveryRoundFirstCallMan(nextPlayer);
		}else{
			nextPlayer = room.getNextPlayer(firstPlayer);
			room.getPlayerInfo().setEveryRoundFirstCallMan(nextPlayer);
		}
		//给所有人发送消息
		long[] passportIds = new long[roomPlayerList.size()];
		int[] leftNums = new int[roomPlayerList.size()];
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			passportIds[i]=p.getPassportId();
			leftNums[i]=5;
		}
		room.getPlayerInfo().changeTimes();//每次统一摇完  倍数 就加一
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			//设置出局
			p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setIsOut(0);
			List<Integer> diceList = Globals.getBazooPubService().getShakeDice().getDiceList();
			
			
			/*if(p.getPassportId() == 1409 || p.getPassportId() == 1414 || p.getPassportId() == 1411 || p.getPassportId() == 1408){
				diceList.clear();
				diceList.add(6);
				diceList.add(6);
				diceList.add(6);
				diceList.add(6);
				diceList.add(6);
			}*/
			
			p.getHuman().getBazooRoomEveryUserInfo().setDiceValues(diceList);
			logger.info("[无双吹牛]---[用户ID:"+p.getPassportId()+"][色子值："+DiceUtils.getStrFromDiceList(diceList)+"]");
			blackWhiteAssistService.sendDiceMessage(p,passportIds,leftNums,diceList,room.getPlayerInfo());
			
			Globals.getLogService().sendDiceBlackWhiteLog(p.getHuman(), LogReasons.DiceBlackWhiteLogReason.curDiceValues, LogReasons.DiceBlackWhiteLogReason.curDiceValues.getReasonText(), 
					roomNumber.getBet(), roomNumber.toString(), DiceUtils.getStrFromDiceList(diceList),
					p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getIsOut(), p.getHuman().getGold());
		}
		
		GCStateRoomBlackWhiteSwingLeft GCStateRoomBlackWhiteSwingLeft = new GCStateRoomBlackWhiteSwingLeft();
		GCStateRoomBlackWhiteSwingLeft.setMultiple(room.getPlayerInfo().getTimes());
		GCStateRoomBlackWhiteSwingLeft.setLeftDiceNum(leftNums);
		GCStateRoomBlackWhiteSwingLeft.setPassportId(passportIds);
		room.getRoomStateInfo().setRoomState(RoomState.BlackWhiteState.stateRoomBlackWhiteSwingLeft,GCStateRoomBlackWhiteSwingLeft);//统一摇完色子 之后的状态
		
		//等待一定的时间（前端需要做特效的时间）
		int waitingTime = Globals.getBazooPubService().getDiceConfig().getBlackWhiteBeginWaitTime();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[红黑单双 又一轮]---[第一个叫号的人]---[开始等待:时间："+waitingTime+"]---[应该叫号的用户ID::"+nextPlayer.getPassportId()+"]");
		Globals.getBazooPubService().setTimeExecute(new BlackWhiteScheduleWhoShouldSwing(nextPlayer,roomNumber),LLScheduleEnum.BLACK_WHITE_WHO_SHOULD_SWING,waitingTime,roomNumber);
	}

	/**
	 * 该谁叫号了
	 * @param nextPlayer
	 * @param roomNumber
	 * @param swing
	 */
	public void whoShouldSwing(Player nextPlayer,RoomNumber roomNumber,boolean swing){
		Room room = Globals.getBazooPubService().getRoomService().getRoom(roomNumber);
		if(room == null){
			return;
		}
		nextPlayer.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setCurUser(true);
		
		int nextTime = Globals.getBazooPubService().getDiceConfig().getBlackWhiteNextTime();
		GCBlackWhiteWhoTurn GCBlackWhiteWhoTurn = new GCBlackWhiteWhoTurn();
		GCBlackWhiteWhoTurn.setWhoTurnPassportId(nextPlayer.getPassportId());
		GCBlackWhiteWhoTurn.setLeftSecond(nextTime);
		
		List<Player> roomPlayerList = room.getPlayers();
		if(roomPlayerList != null && roomPlayerList.size()>0){
			room.sendMessage(roomPlayerList, GCBlackWhiteWhoTurn);
		}
		//后台记录当前 那个 开始叫号人的时间，3秒 到了 还没有 叫 就 替他叫（注意：后台的 时间 设置大于前台）
		PlayerInfo playerInfo = room.getPlayerInfo();
		playerInfo.setWaitingMan(nextPlayer);
		
		//这里修改时间 主要是为 用户进入房间请求状态那里设置的
		int now = Long.valueOf(Globals.getTimeService().now()/1000).intValue();
		GCBlackWhiteWhoTurn.setLeftSecond(now+nextTime);
		
		room.getRoomStateInfo().setRoomState(RoomState.BlackWhiteState.stateRoomBlackWhiteWaitSomeOne,GCBlackWhiteWhoTurn);//等待叫号
		
		BlackWhiteTask blackWhiteTask = new BlackWhiteTask(roomNumber.toString(),room.getRoomTotalNumPartIn(),playerInfo,nextPlayer,blackWhiteAssistService);
		Globals.getBazooPubService().getBazooScheduleService().addBazooSchedule(nextTime, roomNumber.toString(), blackWhiteTask);
	}
	
	
	/**
	 * 用户叫号
	 * 用户叫号 是 红黑单双中的一种 
	 */
	public void callNum(Player player,int diceType,String source){
		Room room = Globals.getBazooPubService().getRoomService().getRoom(player);
		
		if(room == null){
			logger.info("[无双吹牛]---[当前房间不存在::"+player.getHuman().getBazooRoom()+"]");
			return ;
		}
		//只有是 这种情况 才能叫号，在其他情况下都不能叫号
		if(room.getRoomStateInfo().getRoomState().getIndex() != RoomState.BlackWhiteState.stateRoomBlackWhiteWaitSomeOne.getIndex()){
			logger.info("[无双吹牛]---[当前房间状态不对，不能叫号::"+player.getHuman().getBazooRoom()+"][当前房间的状态："+room.getRoomStateInfo().getRoomState()+"]");
			return ;
		}
		logger.info("[无双吹牛]---[红黑单双]---[当前叫号]---[来源::"+source+"]");
		logger.info("[无双吹牛]---[红黑单双]---[当前叫号]---[当前用户ID::"+player.getPassportId()+"]---[diceType::"+diceType+"]");
		PlayerInfo info = room.getPlayerInfo();
		//当前叫号的用户 必须 等于 正在 等待叫号的用户 
		if(info.getWaitingMan().getPassportId() != player.getPassportId()){
			logger.info("[无双吹牛]---[当前用户不等于正在叫号的用户]---[当前叫号用户::"+player.getPassportId()+"][正在等待叫号的用户："+info.getWaitingMan().getPassportId()+"]");
			return ;
		}
		//验证 1:如果用户叫过了 就不能再叫了
		if(info.getLastMan() != null && info.getLastMan().getPassportId() == player.getPassportId()){
			logger.info("[无双吹牛]---[红黑单双]---[不能叫号]---[刚叫过]---[当前用户::"+player.getPassportId()+"]");
			return;
		}
		Globals.getBazooPubService().getBazooScheduleService().stopBazooSchedule(room.getRoomNumber().toString(), player);
		info.setLastMan(player);
		//消除色子
		blackWhiteLogic.logic(room.getPlayers(),diceType);
		//先扣钱（谁出局了扣谁的钱）
		blackWhiteLogic.countMoney(room.getPlayers(),room.getRoomNumber().getBet()*info.getTimes(),room.getRoomNumber().getBet(),info);
		
		
		//发送消息
		GCBlackWhiteCallNum GCBlackWhiteCallNum= blackWhiteAssistService.sendAllDiceMessage(player,diceType,info.getTimes(),room.getPlayers(),info);
		//设置状态
		room.getRoomStateInfo().setRoomState(RoomState.BlackWhiteState.stateRoomBlackWhiteSomeOneCall,GCBlackWhiteCallNum);//等待叫号
		
		
		//判断是否结束
		if(blackWhiteLogic.judgeEnd(room.getPlayers())){
			//统计
			blackWhiteLogic.countStatic(room.getPlayersPartIn(),info);
			
			GCBlackWhiteEndCount GCBlackWhiteEndCount =	blackWhiteAssistService.sendEndCount(room.getPlayers(),room.getRoomNumber().getBet());
			room.getRoomStateInfo().setRoomState(RoomState.BlackWhiteState.stateRoomBlackWhiteEnd,GCBlackWhiteEndCount);//等待叫号
			
			//等待一定的时间（前端需要做特效的时间）
			int waitingTime = Globals.getBazooPubService().getDiceConfig().getBlackWhiteEndWaitTime();
			logger.info("[无双吹牛"+room.getRoomNumber().toString()+"]---[黑白单双 本局结束]");
			Globals.getBazooPubService().setTimeExecute(new BlackWhiteScheduleEndToStart(room),LLScheduleEnum.BLACK_WHITE_END_TO_BEGIN,waitingTime,room.getRoomNumber());
			return;
		}
		
		//等待一定的时间（前端需要做特效的时间）
		int waitingTime = Globals.getBazooPubService().getDiceConfig().getBlackWhiteLeftWaitTime();
		logger.info("[无双吹牛"+room.getRoomNumber().toString()+"]---[黑白单双]---[摇剩下所有的号]---[等待重摇 时间::"+waitingTime+"]");
		Globals.getBazooPubService().setTimeExecute(new BlackWhiteScheduleSwingLeft(room),LLScheduleEnum.BLACK_WHITE_WHO_SHOULD_SWING,waitingTime,room.getRoomNumber());
			
		
	}
	
	
	/**
	 * 每次叫完号 都要统一摇一次色子，剩下几个 就摇几个
	 */
	public void swingLeft(Room room){
		if(room == null){
			return;
		}
		RoomNumber roomNumber = room.getRoomNumber();
		//获取房间的人数
		List<Player> roomPlayerList = room.getPlayersPartIn();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[摇剩下的色子]---[开始]---[当前房间号::"+roomNumber.toString()+"---房间人数::"+roomPlayerList.size()+"]");
		
		Player lastPlayer = room.getPlayerInfo().getLastMan();
		if(lastPlayer == null){
			lastPlayer=room.getPlayerInfo().getEveryRoundFirstCallMan();
		}
		Player nextPlayer = null;
		for(int i=0;i<10;i++){
			nextPlayer = room.getNextPlayer(lastPlayer);
			int isOut = nextPlayer.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getIsOut();
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[下一个人::"+nextPlayer.getPassportId()+"---isOut::"+isOut+"]");
			if(isOut == 1){//出局了
				lastPlayer=nextPlayer;
			}else{
				break;
			}
		}
		room.getPlayerInfo().setLastMan(lastPlayer);
		
		//给所有人发送消息
		List<Long> passportIds = new ArrayList<Long>();
		List<Integer> leftNums = new ArrayList<Integer>();
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			List<Integer> diceList = p.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			if(diceList.size()>0){
				passportIds.add(p.getPassportId());
				leftNums.add(diceList.size());
			}
		}
		room.getPlayerInfo().changeTimes();//每次统一摇完  倍数 就加一
		List<Player> allRoomPlayerList = room.getPlayers();
		for(int i=0;i<allRoomPlayerList.size();i++){
			Player p = allRoomPlayerList.get(i);
			List<Integer> diceLeftList = new ArrayList<Integer>();
			List<Integer> diceList = p.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			if(p.getHuman().getBazooRoomEveryUserInfo().getUserStatus() == RoomEveryUserInfo.USER_STATUS_PARTIN){
				if(diceList.size()>0){
					List<Integer> diceLeft = Globals.getBazooPubService().getShakeDice().getDiceListByNum(diceList.size());
					
					
					
					
					/*
					if(p.getPassportId() == 1409  ){
						diceLeft.clear();
						diceLeft.add(6);
						diceLeft.add(6);
						diceLeft.add(6);
						diceLeft.add(6);
						diceLeft.add(6);
					}*/
					
					
					diceLeftList.addAll(diceLeft);
					p.getHuman().getBazooRoomEveryUserInfo().setDiceValues(diceLeft);
				}
			}
			//给每个人发送自己的消息
			blackWhiteAssistService.sendDiceMessage(p,ListToArrUtils.LonglistToArr(passportIds),ListToArrUtils.IntegerlistToArr(leftNums),diceLeftList,room.getPlayerInfo());
			
			
			Globals.getLogService().sendDiceBlackWhiteLog(p.getHuman(), LogReasons.DiceBlackWhiteLogReason.curDiceValues, LogReasons.DiceBlackWhiteLogReason.curDiceValues.getReasonText(), 
					roomNumber.getBet(), roomNumber.toString(), DiceUtils.getStrFromDiceList(diceList),
					p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getIsOut(), p.getHuman().getGold());
		}
		
		GCStateRoomBlackWhiteSwingLeft GCStateRoomBlackWhiteSwingLeft = new GCStateRoomBlackWhiteSwingLeft();
		GCStateRoomBlackWhiteSwingLeft.setMultiple(room.getPlayerInfo().getTimes());
		GCStateRoomBlackWhiteSwingLeft.setLeftDiceNum(ListToArrUtils.IntegerlistToArr(leftNums));
		GCStateRoomBlackWhiteSwingLeft.setPassportId(ListToArrUtils.LonglistToArr(passportIds));
		room.getRoomStateInfo().setRoomState(RoomState.BlackWhiteState.stateRoomBlackWhiteSwingLeft,GCStateRoomBlackWhiteSwingLeft);//摇完剩下色子 之后的状态
		
		//等待一定的时间（前端需要做特效的时间）
		int waitingTime = Globals.getBazooPubService().getDiceConfig().getBlackWhiteWhoTurnTime();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[黑白单双 再一次消号]---[开始等待]---[应该叫号的用户ID::"+nextPlayer.getPassportId()+"]");
		Globals.getBazooPubService().setTimeExecute(new BlackWhiteScheduleWhoShouldSwing(nextPlayer,roomNumber),LLScheduleEnum.BLACK_WHITE_WHO_SHOULD_SWING,waitingTime,roomNumber);
	}
	
	/**
	 * 进入下一局
	 * @param room
	 */
	public void endToBegin(Room room) {
		Globals.getBazooPubService().endToStart(room.getRoomNumber(), room.getRoomNumber().getModeName());
	}
}
