package com.gameserver.texas;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.player.Player;

/**
 * 德州池底
 * @author wayne
 *
 */
public class TexasPool {
	
	private int bet;
	private List<Player> playerList = new ArrayList<Player>();
	
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public List<Player> getPlayerList() {
		return playerList;
	}	
	
	public int getAllbet()
	{
		return  bet* playerList.size();
	}
}
