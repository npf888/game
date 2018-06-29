package com.robot.strategy.data;

import com.gameserver.baccart.data.BaccartPlayerData;

public class ClientBaccaratRoomPlayer {
	
	private long playerId;
	private long coins;
	private String name;
	private String img;

	private int pos;
	
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getCoins() {
		return coins;
	}

	public void setCoins(long coins) {
		this.coins = coins;
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



	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public void win(long winCoins){
		this.coins += winCoins;
	}
	
	public void bet(long betNum){
		this.coins -= betNum;
	}
	
	
	public static ClientBaccaratRoomPlayer convertFrom(BaccartPlayerData baccartPlayerData){
		ClientBaccaratRoomPlayer clientBaccaratRoomPlayer = new ClientBaccaratRoomPlayer();
		clientBaccaratRoomPlayer.setCoins(baccartPlayerData.getGold());
		clientBaccaratRoomPlayer.setPlayerId(baccartPlayerData.getPlayerId());
		clientBaccaratRoomPlayer.setImg(baccartPlayerData.getImg());
		clientBaccaratRoomPlayer.setPos(baccartPlayerData.getPos());
		clientBaccaratRoomPlayer.setName(baccartPlayerData.getName());
		return clientBaccaratRoomPlayer;
	}

}
