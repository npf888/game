package com.gameserver.baccart.data;

import com.db.model.HumanBaccartEntity;
import com.gameserver.human.manager.HumanBaccartManager;
import com.gameserver.player.Player;

public class HumanBaccartData {
	
	private long gameNum;
	private long winNum;
	private long beaconNum;
	private long playerId;
	
	public long getGameNum() {
		return gameNum;
	}
	public void setGameNum(long gameNum) {
		this.gameNum = gameNum;
	}
	public long getWinNum() {
		return winNum;
	}
	public void setWinNum(long winNum) {
		this.winNum = winNum;
	}
	public long getBeaconNum() {
		return beaconNum;
	}
	public void setBeaconNum(long beaconNum) {
		this.beaconNum = beaconNum;
	}
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
	public static HumanBaccartData convertFromPlayer(Player player){
		HumanBaccartManager tempHumanBaccartManager=player.getHuman().getHumanBaccartManager();
		HumanBaccartData tempHumanBaccartData = new HumanBaccartData();
		tempHumanBaccartData.setBeaconNum(tempHumanBaccartManager.getHumanBaccart().getBeaconNum());
		tempHumanBaccartData.setGameNum(tempHumanBaccartManager.getHumanBaccart().getGameNum());
		tempHumanBaccartData.setPlayerId(player.getPassportId());
		tempHumanBaccartData.setWinNum(tempHumanBaccartManager.getHumanBaccart().getWinNum());
		return tempHumanBaccartData;
	}
	
	public static HumanBaccartData convertFromHumanBaccartEntity(HumanBaccartEntity tempHumanBaccartEntity){
		
		HumanBaccartData tempHumanBaccartData = new HumanBaccartData();
		tempHumanBaccartData.setBeaconNum(tempHumanBaccartEntity.getBeaconNum());
		tempHumanBaccartData.setGameNum(tempHumanBaccartEntity.getGameNum());
		tempHumanBaccartData.setPlayerId(tempHumanBaccartEntity.getCharId());
		tempHumanBaccartData.setWinNum(tempHumanBaccartEntity.getWinNum());
		return tempHumanBaccartData;
	}
}
