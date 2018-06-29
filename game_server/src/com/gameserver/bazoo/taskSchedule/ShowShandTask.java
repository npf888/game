package com.gameserver.bazoo.taskSchedule;

import java.util.ArrayList;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.service.TimeTask.BazooTask;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

public class ShowShandTask implements BazooTask{
	private Logger logger = Loggers.BAZOO;
	
	private String roomNumber;
	private Player lastPlayer;
	
	public ShowShandTask(String roomNumber,Player lastPlayer){
		this.roomNumber=roomNumber;
		this.lastPlayer=lastPlayer;
	}
	
	
	@Override
	public void execute() {
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[由服务器替用户叫号]---[--------------------------------------------------------------------]");
		Globals.getBazooShowHandService().swingSingle(lastPlayer,new ArrayList<Integer>(),false);
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---["+Thread.currentThread().getName()+"===============当前线程结束==================]");

		
	}


	public String getRoomNumber() {
		return roomNumber;
	}


	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}


	public Player getLastPlayer() {
		return lastPlayer;
	}


	public void setLastPlayer(Player lastPlayer) {
		this.lastPlayer = lastPlayer;
	}
	
	
	

}
