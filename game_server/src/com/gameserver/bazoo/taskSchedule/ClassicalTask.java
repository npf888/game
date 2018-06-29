package com.gameserver.bazoo.taskSchedule;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.CGTalkBig;
import com.gameserver.bazoo.service.TimeTask.BazooTask;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

public class ClassicalTask implements BazooTask{
	private Logger logger = Loggers.BAZOO;
	
	private String roomNumber;
	private PlayerInfo playerInfo;
	private int totalMem;
	private Player nextPlayer;
	
	public ClassicalTask(String roomNumber,int totalMem,PlayerInfo playerInfo,Player nextPlayer){
		this.roomNumber=roomNumber;
		this.playerInfo=playerInfo;
		this.totalMem=totalMem;
		this.nextPlayer=nextPlayer;
	}
	
	
	
	@Override
	public void execute() {
		//超过三秒结束(后台的时间要大于前台的时间)
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[时间到]---[由服务器替用户叫号]---[--------------------------------------------------------------------]");
		Player lastPlayer = playerInfo.getLastMan();
		CGTalkBig CGTalkBig = new CGTalkBig();
		if(lastPlayer == null){//一局 开始一个人 没有叫 后台 给他设定一个值
			CGTalkBig.setDiceNum(totalMem);
			CGTalkBig.setDiceValue(2);
		}else{
			//色子总共的个数
			int maxNum = totalMem*6;
			int maxValue = 6;
			RoomEveryUserInfo lastRoomEveryUserInfo= lastPlayer.getHuman().getBazooRoomEveryUserInfo();
			int lastDiceNumber = lastRoomEveryUserInfo.getClassicalUserInfo().getDiceNumber();
			int lastDiceValue = lastRoomEveryUserInfo.getClassicalUserInfo().getDiceValue();
			int lastDiceNumber1=lastDiceNumber+1;
			if(lastDiceValue > maxValue){
				lastDiceValue=maxValue;
			}
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[服务器执行]---[最大人数]---[lastDiceNumber:"+lastDiceNumber+"][lastDiceValue:"+lastDiceValue+"]");
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[服务器执行]---[最大人数]---[lastDiceNumber1:"+lastDiceNumber1+"][maxNum:"+maxNum+"]");
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[服务器执行]---[最大人数]---[lastDiceValue:"+lastDiceValue+"][maxValue:"+maxValue+"]");
			//个数已经到最大了 和点数 都到最大了
			if(lastDiceNumber1 >  maxNum && lastDiceValue == maxValue){
				logger.info("[无双吹牛"+roomNumber.toString()+"]---[服务器执行]---[最后结算]---[lastDiceValue:"+lastDiceValue+"][maxValue:"+maxValue+"]");
				//去抢开
				Globals.getBazooMService().robOpen(nextPlayer);
				return;
			}
			//点数到最大  个数 还没有到最大
			if(lastDiceNumber1 >= maxNum && lastDiceValue < maxValue){
				logger.info("[无双吹牛"+roomNumber.toString()+"]---[服务器执行]---[设置参数]---[lastDiceValue:"+lastDiceValue+"][maxValue:"+maxValue+"]");
				if(lastDiceNumber == maxNum){
					lastDiceValue+=1;
				}
				lastDiceNumber1=maxNum;
			}
			
			CGTalkBig.setDiceNum(lastDiceNumber1);
			CGTalkBig.setDiceValue(lastDiceValue);
		}
		
		Globals.getBazooMService().talkBig(nextPlayer, CGTalkBig.getDiceNum(), CGTalkBig.getDiceValue(),"后端");
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[服务器执行]---[执行结束===============执行结束==================执行结束]");
	}



	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}

	public int getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(int totalMem) {
		this.totalMem = totalMem;
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	
	
	
	
	
}
