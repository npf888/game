package com.gameserver.baccart.data;

import com.gameserver.human.manager.HumanBaccartManager;
import com.gameserver.player.Player;

public class BaccartLightData {
	private int continueWin;
	private long playerId;
	private long minWins;
	public int getContinueWin() {
		return continueWin;
	}
	public void setContinueWin(int continueWin) {
		this.continueWin = continueWin;
	}
	
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
	public long getMinWins(){
		return minWins;
	}
	public void setMinWins(long minWins){
		this.minWins = minWins;
	}

	public static BaccartLightData convertFromPlayer(Player player){
		HumanBaccartManager humanBaccartManager= player.getHuman().getHumanBaccartManager();
		BaccartLightData tempBaccartLightData = new BaccartLightData();
		tempBaccartLightData.setContinueWin(humanBaccartManager.getContinueWins());
		tempBaccartLightData.setPlayerId(player.getPassportId());
		tempBaccartLightData.setMinWins(humanBaccartManager.getMinWins());
		return tempBaccartLightData;
	}

}
