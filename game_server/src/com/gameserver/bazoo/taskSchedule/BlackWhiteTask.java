package com.gameserver.bazoo.taskSchedule;

import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.data.BlackWhiteNum;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.CGBlackWhiteCallNum;
import com.gameserver.bazoo.service.TimeTask.BazooTask;
import com.gameserver.bazoo.service.blackWhite.BlackWhiteAssistService;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

public class BlackWhiteTask implements BazooTask{
private Logger logger = Loggers.BAZOO;
	
	private String roomNumber;
	private PlayerInfo playerInfo;
	private int totalMem;
	private Player nextPlayer;
	private BlackWhiteAssistService blackWhiteAssistService;
	
	public BlackWhiteTask(String roomNumber,int totalMem,PlayerInfo playerInfo,Player nextPlayer,BlackWhiteAssistService blackWhiteAssistService){
		this.roomNumber=roomNumber;
		this.playerInfo=playerInfo;
		this.totalMem=totalMem;
		this.nextPlayer=nextPlayer;
		this.blackWhiteAssistService=blackWhiteAssistService;
	}
	
	
	
	@Override
	public void execute() {
		//超过三秒结束(后台的时间要大于前台的时间)
		logger.info("[无双吹牛"+roomNumber.toString()+"][红黑单双 时间到]===============[由服务器替用户叫号]====[当前用户::"+nextPlayer.getPassportId()+"]");
		
		CGBlackWhiteCallNum CGBlackWhiteCallNum = new CGBlackWhiteCallNum();
		CGBlackWhiteCallNum.setDiceType(replaceUserCall(nextPlayer));
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[红黑单双 服务器执行 ]---[当前叫号]---[lastDiceValue:"+CGBlackWhiteCallNum.getDiceType()+"]");
		Globals.getBlackWhiteService().callNum(nextPlayer, CGBlackWhiteCallNum.getDiceType(),"服务器");
		
		
		logger.info("============================================执行定时任务的结束============================================15");
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
	
	/**
	 * 服务器 替用户叫号
	 */
	
	private int replaceUserCall(Player player){
		
		List<Integer> diceValues = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
		BlackWhiteNum BlackWhiteNum = blackWhiteAssistService.setNum(diceValues);
		if(diceValues.size() > BlackWhiteNum.getRed()){
			return 1;
		}
		if(diceValues.size() > BlackWhiteNum.getBlack()){
			return 2;
		}
		if(diceValues.size() > BlackWhiteNum.getSingle()){
			return 3;
		}
		if(diceValues.size() > BlackWhiteNum.getDoubles()){
			return 4;
		}
		return 0;
	}
	
	

}
