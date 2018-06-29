package com.gameserver.bazoo.service.showHand;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.LogReasons.DiceShowHandLogReason;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.enums.RoomState;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.GCShowHandSingleSwich;
import com.gameserver.bazoo.msg.GCShowHandSingleSwichCancel;
import com.gameserver.bazoo.msg.GCShowHandSingleSwing;
import com.gameserver.bazoo.msg.GCShowHandUnifySwing;
import com.gameserver.bazoo.msg.GCStateRoomShowHandAllSwing;
import com.gameserver.bazoo.msg.GCStateRoomShowHandSingleSwing;
import com.gameserver.bazoo.schedule.ShowHandScheduleEndToBegin;
import com.gameserver.bazoo.schedule.ShowHandScheduleWhoTurnSwing;
import com.gameserver.bazoo.service.room.BazooPubService;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.service.room.RoomService;
import com.gameserver.bazoo.service.room.ShakeDice;
import com.gameserver.bazoo.taskSchedule.ShowShandTask;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.player.Player;
/**
 * 梭哈模式的service
 * @author JavaServer
 *
 */
public class ShowHandService implements InitializeRequired{
	
	private Logger logger = Loggers.BAZOO;
	//房间
	private RoomService roomService;
	private BazooPubService bazooPubService;
	private ShowHandLogic showHandLogic;
	private AssistShowHandService assistShowHandService;
	private ShakeDice shakeDice;
	
	public ShowHandService(BazooPubService bazooPubService){
		this.bazooPubService=bazooPubService;
		
	}
	
	@Override
	public void init() {
		this.assistShowHandService=new AssistShowHandService();
		this.showHandLogic=new ShowHandLogic();
		this.roomService = bazooPubService.getRoomService();
		this.shakeDice = bazooPubService.getShakeDice();
		
	}

	
	/**
	 * 单独摇色子
	 */
	
	public void swingSingle(Player player,List<Integer> valueList,boolean who){
		Room room = roomService.getRoom(player);
		if(room == null){
			return;
		}
		RoomNumber roomNumber = room.getRoomNumber();
		PlayerInfo playerInfo = room.getPlayerInfo();
		
		//状态不对的时候 也不能摇 
		if(room.getRoomStateInfo().getRoomState().getIndex() != RoomState.ShowHandState.stateRoomShowHandSingleSwing.getIndex()){
			logger.info("[无双吹牛]---[梭哈 模式]---[状态不对]---[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		//该不该摇
		if(!playerInfo.getLastMans().contains(player)){
			logger.info("[无双吹牛]---[梭哈 模式]---[不该你摇]---[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		//如果是用户摇的 那么就得 打断 他自己的线程
		if(who){
			/*player.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().interrupt(player, roomNumber);*/
			this.bazooPubService.getBazooScheduleService().stopBazooSchedule(roomNumber.toString(),player);
		}
		logger.info("[无双吹牛]---[梭哈 模式]---[剩余重摇次数]---[剩余次数::"+playerInfo.getLeftTimes()+"]");
		if(playerInfo.getLeftTimes()<1){
			logger.info("[无双吹牛]---[梭哈 模式]---[重摇总次数 完毕 不可以 再摇]---[当前用户ID::"+player.getPassportId()+"]");
			return;
		}
		playerInfo.setCallTimes(playerInfo.getCallTimes()-1);
		playerInfo.setLeftTimes(playerInfo.getLeftTimes()-1);
		playerInfo.getLastMans().remove(player);
		
		List<Player> partIns = room.getPlayersPartIn();
		//当前场次 bet
		int bet = roomNumber.getBet();
//		long costMoney = bet*(partIns.size()-1);
		long costMoney = 0;
		int costMultiple = 0;
		//给其他用户加钱
		for(Player p:partIns){
			if(p.getPassportId() ==player.getPassportId()){
				continue;
			}
			RoomEveryUserInfo roomEveryUserInfo = p.getHuman().getBazooRoomEveryUserInfo();
			Card card = showHandLogic.judgeCards(roomEveryUserInfo.getDiceValues());;
			//如果是 豹子要给两倍
			int curMultiple = 1;
			if(card.getCardNum() == 6){
				curMultiple=2;
			}
			long curMoney = bet*curMultiple;
			costMultiple+=curMultiple;
			costMoney+=bet*curMultiple;
			//每一局 总的输赢
			roomEveryUserInfo.setMoney(roomEveryUserInfo.getMoney()+curMoney);
			roomEveryUserInfo.setMultiple(roomEveryUserInfo.getMultiple()+curMultiple);
			
			//每一局 当前这一次的输赢
			roomEveryUserInfo.getShowHandUserInfo().setMoney(curMoney);
			roomEveryUserInfo.getShowHandUserInfo().setMultiple(curMultiple);
			
			//给用户加钱
			String detailReasonAdd = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_SHOWHAND_SINGLE_WIN.getReasonText(), roomNumber.toString(),curMoney);
			p.getHuman().giveMoney(curMoney, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_SHOWHAND_SINGLE_WIN, detailReasonAdd, -1, 1);
			
			//排行榜 给其他用户加钱
			Globals.getHumanBankService().addGold(p, curMoney);
		}
		//排行榜 给自己减钱
		Globals.getHumanBankService().addGold(player, -costMoney);
		
		//当前用户 每一局总的输赢
		RoomEveryUserInfo curRoomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		curRoomEveryUserInfo.setMoney(curRoomEveryUserInfo.getMoney()-costMoney);
		curRoomEveryUserInfo.setMultiple(curRoomEveryUserInfo.getMultiple()-costMultiple);
		
		//当前用户 每一局 每一次 的输赢
		curRoomEveryUserInfo.getShowHandUserInfo().setMoney(-costMoney);
		curRoomEveryUserInfo.getShowHandUserInfo().setMultiple(costMultiple);
		
		//每次摇色子 都要扣钱（给房间其他人发钱）
		String detailReasonCost = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_SHOWHAND_SINGLE_COST.getReasonText(), roomNumber.toString(),costMoney);
		player.getHuman().costMoney(costMoney, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_SHOWHAND_SINGLE_COST, detailReasonCost, -1, 1);
		
		
		
		
		List<Integer> dices = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
		List<Integer> newDices = shakeDice.shakeSome(dices, valueList);
		
		
		
		Card card = showHandLogic.judgeCards(newDices);
		//设置上是什么牌
		player.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().setCard(card);
		
		String newDiceStr = DiceUtils.getStrFromDiceList(newDices);
		ShowHandUserInfo ShowHandUserInfo =player.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[梭哈 模式]---[当前用户::"+player.getPassportId()+"]"+"---[色子值：："+newDiceStr+"]"+"---["+card.getCardName()+"]["+card.getCardNum()+"]");
		Globals.getLogService().sendDiceShowHandLog(player.getHuman(), DiceShowHandLogReason.curDiceValues, 
				null, room.getRoomNumber().getBet(), room.getRoomNumber().toString(), newDiceStr, ShowHandUserInfo.getCard().getCardName()+"-"+ShowHandUserInfo.getCard().getCardNum());

		GCShowHandSingleSwing GCShowHandSingleSwing= assistShowHandService.sendGCShowHandSingleSwing(newDices,player,card,room);
		
		//每次单独摇完色子之后  都看看  这一局是不是该结束了
		if(endOrNot(room)){
			//扣掉流水
			assistShowHandService.costTheWinnerGold(bazooPubService,room);
			//发送 结算信息
			assistShowHandService.sendGCShowHandEndCount(room);
			//给机器人充钱
			roomService.addGoldForRobot(room);
			// 等一会进入下一局
			int endToStart = bazooPubService.getDiceConfig().getShowHandEndToStart();
			logger.info("[无双吹牛]---[梭哈 模式]---[等一会进入下一局]---[等待时间::"+endToStart+"]");
			bazooPubService.setTimeExecute(new ShowHandScheduleEndToBegin(room),LLScheduleEnum.SHOW_HAND_WHO_TURN_SWING,endToStart,roomNumber);
			return;
		}
		
		int whoTurn = bazooPubService.getDiceConfig().getShowHandWhoTurn();
		long nTime=Globals.getTimeService().now()+whoTurn*1000;
		
		
		logger.info("[无双吹牛]---[梭哈 模式]---[单独摇完 -CallTimes::"+playerInfo.getCallTimes()+"]");
		if(playerInfo.getCallTimes() > 0){
			logger.info("[无双吹牛]---[梭哈 模式]---[单独摇完 -还剩下几个叫号的人::"+playerInfo.getCallTimes()+" 没有叫]");
			return;
		}
		
		//设置状态
		GCStateRoomShowHandSingleSwing GCStateRoomShowHandSingleSwing =assistShowHandService.setGCStateRoomShowHandSingleSwing(GCShowHandSingleSwing,room,player,nTime);
		/*room.getRoomStateInfo().setRoomState(RoomState.ShowHandState.stateRoomShowHandSingleSwing,GCStateRoomShowHandSingleSwing);//单独摇完色子 之后的状态
		 */
		GCStateRoomShowHandAllSwing GCStateRoomShowHandAllSwing = new GCStateRoomShowHandAllSwing();
		GCStateRoomShowHandAllSwing.setLeftTimes(playerInfo.getLeftTimes());
		GCStateRoomShowHandAllSwing.setDiceInfo(GCStateRoomShowHandSingleSwing.getDiceInfo());
		GCStateRoomShowHandAllSwing.setLeftSecond(nTime);
		room.getRoomStateInfo().setRoomState(RoomState.ShowHandState.stateRoomShowHandAllSwing,GCStateRoomShowHandAllSwing);//统一摇完色子 之后的状态
		//隔一段时间  就 下一个人 摇 
		logger.info("[无双吹牛]---[梭哈 模式]---[单独摇完 -下一个最小的人 开始摇]---[等待时间::"+whoTurn+"]");
		bazooPubService.setTimeExecute(new ShowHandScheduleWhoTurnSwing(room),LLScheduleEnum.SHOW_HAND_WHO_TURN_SWING,whoTurn,roomNumber);
		
		
	}
	
	/**
	 * 看看是否 本局该结束了
	 * 超过  总人数*7 就结束了
	 * @param room
	 * @return
	 */
	private boolean endOrNot(Room room) {
		PlayerInfo playerInfo = room.getPlayerInfo();
		//小于1 就结束了 
		if(playerInfo.getLeftTimes() < 1){
			return true;
		}
		//还有一种情况 全是 豹子 1 的时候结束
		List<Player> players = room.getPlayersPartIn();
		boolean isEnd = true;
		for(Player p:players){
			List<Integer> diceValues = p.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			for(Integer value:diceValues){
				if(value != 1){
					isEnd=false;
					break;
				}
			}
			if(!isEnd){
				break;
			}
		}
		
		return isEnd;
	}

	/**
	 * 统一摇色子
	 */
	public void swingAll(RoomNumber roomNumber,Room room){
		List<Player> allPlayerList = room.getPlayersPartIn();
		PlayerInfo playerInfo = room.getPlayerInfo();
		List<DiceInfo> DiceInfoList = new ArrayList<DiceInfo>();
		for(Player player :allPlayerList){
			List<Integer> dices = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			List<Integer> newDices = shakeDice.shakeAll(dices);
			
			
			Card card = showHandLogic.judgeCards(newDices);
			//设置上是什么牌
			player.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().setCard(card);
			player.getHuman().getBazooRoomEveryUserInfo().setDiceValues(newDices);
			DiceInfo DiceInfoVO = new DiceInfo();
			DiceInfoVO.setPassportId(player.getPassportId());
			DiceInfoVO.setCowNameInt(card.getCardNum());
			
			int[] newDiceArr = DiceUtils.getArrFromDiceList(newDices);
			
			String newDiceStr = DiceUtils.getStrFromDiceList(newDices);
			ShowHandUserInfo ShowHandUserInfo = player.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo();
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[统一摇]---[梭哈 模式]---[当前用户::"+player.getPassportId()+"]"+"---[色子值：："+newDiceStr+"]"+"---["+card.getCardName()+"]["+card.getCardNum()+"]");
			Globals.getLogService().sendDiceShowHandLog(player.getHuman(), DiceShowHandLogReason.curDiceValues, 
					null, room.getRoomNumber().getBet(), room.getRoomNumber().toString(), newDiceStr, ShowHandUserInfo.getCard().getCardName()+"-"+ShowHandUserInfo.getCard().getCardNum());

			DiceInfoVO.setDiceValues(newDiceArr);
			DiceInfoVO.setRedDiceValues(new int[0]);
			DiceInfoList.add(DiceInfoVO);
		}
		
		GCShowHandUnifySwing GCShowHandUnifySwing = new GCShowHandUnifySwing();
		DiceInfo[] diceInfoArr = new DiceInfo[DiceInfoList.size()];
		for(int i=0;i<DiceInfoList.size();i++){
			diceInfoArr[i]=DiceInfoList.get(i);
		}
		GCShowHandUnifySwing.setDiceInfo(diceInfoArr);
		playerInfo.setLeftTimes(room.getPlayersPartIn().size()*7);
		GCShowHandUnifySwing.setLeftTimes(playerInfo.getLeftTimes());
		room.sendMessage(room.getPlayers(), GCShowHandUnifySwing);
		
		int whoTurn = bazooPubService.getDiceConfig().getShowHandWhoTurn();
		//设置状态
		GCStateRoomShowHandAllSwing GCStateRoomShowHandAllSwing = new GCStateRoomShowHandAllSwing();
		GCStateRoomShowHandAllSwing.setLeftTimes(playerInfo.getLeftTimes());
		GCStateRoomShowHandAllSwing.setDiceInfo(GCShowHandUnifySwing.getDiceInfo());
		GCStateRoomShowHandAllSwing.setLeftSecond(Globals.getTimeService().now()+whoTurn*1000);
		room.getRoomStateInfo().setRoomState(RoomState.ShowHandState.stateRoomShowHandAllSwing,GCStateRoomShowHandAllSwing);//统一摇完色子 之后的状态
		
		// 隔一段时间  就 下一个人 摇 
		logger.info("[无双吹牛]---[梭哈 模式]---[单独摇完 -下一个最小的人 开始摇]---[等待时间::"+whoTurn+"]");
		bazooPubService.setTimeExecute(new ShowHandScheduleWhoTurnSwing(room),LLScheduleEnum.SHOW_HAND_WHO_TURN_SWING,whoTurn,roomNumber);
		
	}

	/**
	 * 该轮到 谁摇色子了 （最小的那个人摇色子）
	 * @param room
	 */
	public void whoTurnSwing(Room room) {
		
		// 取出牌最小的人
		PlayerInfo playerInfo = room.getPlayerInfo();
		List<Player> allPlayerList = room.getPlayersPartIn();
		List<Player> lessPlayers = showHandLogic.getLittlePlayer(allPlayerList);
		playerInfo.setLastMans(lessPlayers);
		playerInfo.setCallTimes(lessPlayers.size());
		assistShowHandService.sendGCShowHandLittleSwing(lessPlayers,room);
		
		logger.info("[无双吹牛]---[梭哈 模式]---[当前重摇人数]---[几个人::"+lessPlayers.size()+"]");
		
		/**
		 * 设置等待线程
		 */
		int waitingTime = bazooPubService.getDiceConfig().getShowHandShakeTime();
		for(Player lessPlayer:lessPlayers){
			/*ShowHandUserInfo showHandUserInfo = lessPlayer.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo();
			showHandUserInfo.setTime(waitingTime, lessPlayer, room.getRoomNumber());*/
			
			ShowShandTask ShowShandTask = new ShowShandTask(room.getRoomNumber().toString(),lessPlayer);
			this.bazooPubService.getBazooScheduleService().addBazooSchedule(waitingTime, room.getRoomNumber().toString(), ShowShandTask);
		}
		
		long nTime = Globals.getTimeService().now()+waitingTime*1000;
		//设置状态 
		GCStateRoomShowHandSingleSwing GCStateRoomShowHandSingleSwing =assistShowHandService.setGCStateRoomShowHandSingleSwing(null,room,null,nTime);
		room.getRoomStateInfo().setRoomState(RoomState.ShowHandState.stateRoomShowHandSingleSwing,GCStateRoomShowHandSingleSwing);//单独摇完色子 之后的状态
	}

	/**
	 * 进入下一局
	 * @param room
	 */
	public void endToBegin(Room room) {
		this.bazooPubService.endToStart(room.getRoomNumber(), room.getRoomNumber().getModeName());
		
	}

	/**
	 * 用户选择 色子
	 * @param player
	 * @param i 
	 */
	public void singleSwich(Player player, int diceIndex) {
		Room room = roomService.getRoom(player);
		if(room == null){
			return;
		}
		GCShowHandSingleSwich GCShowHandSingleSwich = new GCShowHandSingleSwich();
		GCShowHandSingleSwich.setDiceIndex(diceIndex);
		GCShowHandSingleSwich.setPassportId(player.getPassportId());
		room.sendMessage(room.getPlayers(), GCShowHandSingleSwich);
	}

	/**
	 * 用户取消 选择 色子
	 * @param player
	 * @param i 
	 */
	public void singleSwichCancel(Player player, int diceIndex) {
		Room room = roomService.getRoom(player);
		if(room == null){
			return;
		}
		GCShowHandSingleSwichCancel GCShowHandSingleSwichCancel = new GCShowHandSingleSwichCancel();
		GCShowHandSingleSwichCancel.setDiceIndex(diceIndex);
		GCShowHandSingleSwichCancel.setPassportId(player.getPassportId());
		room.sendMessage(room.getPlayers(), GCShowHandSingleSwichCancel);
		
	}
	
	
	/**
	 * 用于 签到 的 
	 */
	
	public int judgeDice(List<Integer> diceValues){
		return showHandLogic.judgeCards(diceValues).getCardNum();
	}
}
