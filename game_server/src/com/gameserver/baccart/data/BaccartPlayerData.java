package com.gameserver.baccart.data;

import com.gameserver.human.manager.HumanBaccartManager;
import com.gameserver.player.Player;

/**
 * 百家乐玩家数据
 * @author wayne
 *
 */
public class BaccartPlayerData {
	private long playerId;
	private String name;
	private String img;
	private long gold;
	private int pos;
	private int vip;
	
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getGold() {
		return gold;
	}
	public void setGold(long gold) {
		this.gold = gold;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	/**
	 * @return the vip
	 */
	public int getVip() {
		return vip;
	}
	/**
	 * @param vip the vip to set
	 */
	public void setVip(int vip) {
		this.vip = vip;
	}
	
	public static  BaccartPlayerData convertFromPlayer(Player player){
		HumanBaccartManager tempHumanBaccartManager = player.getHuman().getHumanBaccartManager();
		
		BaccartPlayerData tempBaccartPlayerData = new BaccartPlayerData();
		tempBaccartPlayerData.setGold(tempHumanBaccartManager.getCoins());
		tempBaccartPlayerData.setImg(player.getHuman().getImg());
		tempBaccartPlayerData.setName(player.getHuman().getName());
		tempBaccartPlayerData.setPlayerId(player.getPassportId());
		tempBaccartPlayerData.setPos(tempHumanBaccartManager.getPos());
		tempBaccartPlayerData.setVip(player.getHuman().getHumanVipNewManager().getVipLv());
		//tempBaccartPlayerData.setVip(player.getHuman().getHumanVipManager().getHumanVip().getLevel());
		return tempBaccartPlayerData;
	}
}
