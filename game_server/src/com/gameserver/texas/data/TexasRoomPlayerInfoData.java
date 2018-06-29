package com.gameserver.texas.data;

/**
 * 
 * @author wayne
 *
 */
public class TexasRoomPlayerInfoData {
	
	private long playerId;
	private String name;
	private String img;
	private int vip;
	private int playerState;
	private long coins;
	private int pos;
	/**当前押注*/
	private long currentBet;
	/**总押注*/
	private long allBet;
	
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
	public int getPlayerState() {
		return playerState;
	}
	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}
	public long getCoins() {
		return coins;
	}
	public void setCoins(long coins) {
		this.coins = coins;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public long getCurrentBet() {
		return currentBet;
	}
	public void setCurrentBet(long currentBet) {
		this.currentBet = currentBet;
	}
	public long getAllBet() {
		return allBet;
	}
	public void setAllBet(long allBet) {
		this.allBet = allBet;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getVip() {
		return vip;
	}
	public void setVip(int vip) {
		this.vip = vip;
	}
	@Override
	public String  toString()
	{
		return String.format("玩家[%d],位置[%d],筹码[%d],当前下注[%d],总下注[%d]",getPlayerId(), getPos(),getCoins(),getCurrentBet(),getAllBet());
	
	}

	
}
