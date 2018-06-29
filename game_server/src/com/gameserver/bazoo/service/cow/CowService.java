package com.gameserver.bazoo.service.cow;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons.DiceCowLogReason;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.gameserver.bazoo.enums.RoomState;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.GCCowSingleSwing;
import com.gameserver.bazoo.msg.GCCowSingleSwingBegin;
import com.gameserver.bazoo.msg.GCCowSingleSwingEnd;
import com.gameserver.bazoo.msg.GCStateRoomSingleSwingEnd;
import com.gameserver.bazoo.schedule.CowScheduleBeginToSwing;
import com.gameserver.bazoo.schedule.CowScheduleEndCountOnce;
import com.gameserver.bazoo.schedule.CowScheduleEndToStart;
import com.gameserver.bazoo.schedule.CowScheduleSwingToEnd;
import com.gameserver.bazoo.service.room.BazooPubService;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.service.room.RoomService;
import com.gameserver.bazoo.service.room.ShakeDice;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 牛牛 模式
 * @author JavaServer
 *
 */
public class CowService implements InitializeRequired{
	private Logger logger = Loggers.BAZOO;
	
	//房间
	private RoomService roomService;
	//摇色子
	private ShakeDice cowShakeDice;
	//计算 逻辑
	private CowLogic cowLogic;
	
	//辅助的service
	private AssistCowService assistCowService;
	
	
	private BazooPubService bazooPubService;
	public CowService(BazooPubService bazooPubService){
		this.bazooPubService=bazooPubService;
	}
	public void init() {
		this.roomService = bazooPubService.getRoomService();
		this.cowShakeDice = bazooPubService.getShakeDice();
		this.cowLogic = new CowLogic();
		this.assistCowService = new AssistCowService();
		
	}
	
	
	/**
	 * 统一摇一次色子
	 */
	public void swingAll(RoomNumber roomNumber,Room room){
		//选出下一个庄家
		List<Player> playersPartIn = room.getPlayersPartIn();
		Player nextPlayer=null;
		Player firstPlayer = room.getPlayerInfo().getEveryRoundFirstCallMan();
		if(firstPlayer==null){
			nextPlayer = room.getNextPlayer(playersPartIn.get(0));
			room.getPlayerInfo().setEveryRoundFirstCallMan(nextPlayer);
		}else{
			nextPlayer = room.getNextPlayer(firstPlayer);
			room.getPlayerInfo().setEveryRoundFirstCallMan(nextPlayer);
		}
		for(Player p:playersPartIn){
			p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().setBanker(CowUserInfo.notBanker);
		}
		nextPlayer.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().setBanker(CowUserInfo.isBanker);
		
		logger.info("[无双吹牛]---[牛牛模式]---[庄家ID]---[当前用户::"+nextPlayer.getPassportId()+"]");
		
		for(int i=0;i<playersPartIn.size();i++){
			Player player = playersPartIn.get(i);
			RoomEveryUserInfo roomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
			//开始每个人 每个人 的摇
			List<Integer> dices = roomEveryUserInfo.getDiceValues();
			cowShakeDice.shakeAll(dices);
			cowLogic.compareName(roomEveryUserInfo,dices);
			assistCowService.swingAllMessage(player,roomEveryUserInfo,nextPlayer,0);
			
			String diceStr = DiceUtils.getStrFromDiceList(dices);
			logger.info("[无双吹牛]---[牛牛模式]---[统一摇]---[当前用户::"+player.getPassportId()+"]---[当前色子值:"+diceStr+"]["+roomEveryUserInfo.getCowUserInfo().getCowName()+"]["+roomEveryUserInfo.getCowUserInfo().getCowName()+"]");
			
			Globals.getLogService().sendDiceCowLog(player.getHuman(), DiceCowLogReason.curDiceValues, null, roomNumber.getBet(), roomNumber.toString(), diceStr, roomEveryUserInfo.getCowUserInfo().getCowName()+"-"+roomEveryUserInfo.getCowUserInfo().getCowNum());
		}
		room.getRoomStateInfo().setRoomState(RoomState.stateRoomRoundBegin,null);//统一摇完色子 之后的状态
		//等待多长时间后 开始重摇
		int swingToBegin = bazooPubService.getDiceConfig().getCowSwingToBegin();
		logger.info("[无双吹牛]---[牛牛模式]---[开始状态-重摇开始之前]---[等待时间::"+swingToBegin+"]");
		bazooPubService.setTimeExecute(new CowScheduleBeginToSwing(room),LLScheduleEnum.COW_END_COUNT_ONCE,swingToBegin,roomNumber);
		
	}
	
	/**
	 * 开始重摇
	 * @param room
	 */
	public void beginToSwing(Room room){
		RoomNumber roomNumber = room.getRoomNumber();
		GCCowSingleSwingBegin GCCowSingleSwingBegin = new GCCowSingleSwingBegin();
		room.sendMessage(room.getPlayers(), GCCowSingleSwingBegin);
		
		room.getRoomStateInfo().setRoomState(RoomState.CowState.stateRoomSingleSwingBegin,null);//重摇中的状态
		//等待多长时间后 结束一局
		int cowOneRoundTime = bazooPubService.getDiceConfig().getCowOneRoundTime();
		logger.info("[无双吹牛]---[牛牛模式]---[重摇开始]---[等待时间::"+cowOneRoundTime+"]");
		bazooPubService.setTimeExecute(new CowScheduleSwingToEnd(room),LLScheduleEnum.COW_END_COUNT_ONCE,cowOneRoundTime,roomNumber);
		
	}
	/**
	 * 重摇结束 ,前端 开始查看 每个人 是什么值（几小牛 几小牛 的）
	 * 这个状态下并没有结算
	 * @param room
	 */
	public void SwingToEnd(Room room){
		RoomNumber roomNumber = room.getRoomNumber();
		GCCowSingleSwingEnd GCCowSingleSwingEnd = assistCowService.getGCCowSingleSwingEnd(room);
		room.sendMessage(room.getPlayers(), GCCowSingleSwingEnd);
		
		GCStateRoomSingleSwingEnd GCStateRoomSingleSwingEnd= new GCStateRoomSingleSwingEnd();
		GCStateRoomSingleSwingEnd.setDiceInfo(GCCowSingleSwingEnd.getDiceInfo());
		room.getRoomStateInfo().setRoomState(RoomState.CowState.stateRoomSingleSwingEnd,GCStateRoomSingleSwingEnd);//重摇结束的状态
		//等待多长时间后 结束一局
		int cowLookDiceValueTime = bazooPubService.getDiceConfig().getCowLookDiceValueTime();
		cowLookDiceValueTime = cowLookDiceValueTime*room.getPlayersPartIn().size();
		logger.info("[无双吹牛]---[牛牛模式]---[重摇结束-用户查看其他人的色子]---[等待时间::"+cowLookDiceValueTime+"]");
		bazooPubService.setTimeExecute(new CowScheduleEndCountOnce(room),LLScheduleEnum.COW_END_COUNT_ONCE,cowLookDiceValueTime,roomNumber);
		
	}
	
	/**
	 * 单独摇色子
	 */
	
	public void swingSingle(Player player,int[] valueArr){
		Room room = roomService.getRoom(player);
		if(room  == null){
			return;
		}
		//只有这一局 在进行中 才可以 摇
		if(room.getRoomStateInfo().getRoomState().getIndex() != RoomState.CowState.stateRoomSingleSwingBegin.getIndex()){
			logger.info("[无双吹牛]---[牛牛模式]---[单独摇号]---[本局没未处在 进行中]---[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		CowUserInfo cowUserInfo = player.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo();
		//只能重摇一次
		if(cowUserInfo.getShakeNum() >=1){
			logger.info("[无双吹牛]---[牛牛模式]---[单独摇号]---[已经摇过::"+cowUserInfo.getShakeNum()+"次,所以不能再摇了]");
			return;
		}
		cowUserInfo.setShakeNum(cowUserInfo.getShakeNum()+1);
		logger.info("[无双吹牛]---[牛牛模式]---[单独摇号]---["+player.getPassportId()+"]");
		
		RoomEveryUserInfo roomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		List<Integer> dices = roomEveryUserInfo.getDiceValues();
		List<Integer> valueList = new ArrayList<Integer>();
		for(int i=0;i<valueArr.length;i++){
			valueList.add(valueArr[i]);
		}
		cowShakeDice.shakeSome(dices, valueList);
		cowLogic.compareName(roomEveryUserInfo,dices);
		for(Player p:room.getPlayers()){
			if(p.getPassportId() == player.getPassportId()){
				assistCowService.swingSingleMessage(player,roomEveryUserInfo);
			}else{
				GCCowSingleSwing  GCCowSingleSwing = new GCCowSingleSwing();
				GCCowSingleSwing.setPassportId(player.getPassportId());
				GCCowSingleSwing.setCowNameInt(0);
				GCCowSingleSwing.setDiceValues(new int[0]);
				GCCowSingleSwing.setRedDiceValues(new int[0]);
				p.sendBazooMessage(GCCowSingleSwing);
			}
		}
		
		
		String diceStr = DiceUtils.getStrFromDiceList(dices);
		logger.info("[无双吹牛]---[牛牛模式]---[统一摇]---[当前用户::"+player.getPassportId()+"]---[当前色子值:"+diceStr+"]["+roomEveryUserInfo.getCowUserInfo().getCowName()+"]["+roomEveryUserInfo.getCowUserInfo().getCowName()+"]");
		Globals.getLogService().sendDiceCowLog(player.getHuman(), DiceCowLogReason.curDiceValues, null, room.getRoomNumber().getBet(), room.getRoomNumber().toString(), diceStr, roomEveryUserInfo.getCowUserInfo().getCowName()+"-"+roomEveryUserInfo.getCowUserInfo().getCowNum());

	}
	
	/**
	 * 结算
	 */
	public void endCount(Room room){
		RoomNumber roomNumber = room.getRoomNumber();
		cowLogic.endCount(room,bazooPubService);
		//给机器人充钱
		roomService.addGoldForRobot(room);
		//等待多长时间后 结束一局
		int cowEndCountTime = bazooPubService.getDiceConfig().getCowEndCountTime();
		cowEndCountTime = cowEndCountTime*room.getPlayersPartIn().size();
		logger.info("[无双吹牛]---[牛牛模式]---[结算完毕-等待下一轮]---[等待时间::"+cowEndCountTime+"]");
		bazooPubService.setTimeExecute(new CowScheduleEndToStart(roomNumber),LLScheduleEnum.COW_END_TO_START,cowEndCountTime,roomNumber);
				
	}
	
	
	
	
}
