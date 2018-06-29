package com.robot.strategy.data;

import java.util.ArrayList;
import java.util.List;
import com.common.model.Card;
import com.gameserver.texas.data.TexasRoomPlayerInfoData;
import com.gameserver.texas.enums.RoomPlayerState;

public class ClientRoomPlayer {
	/**玩家*/
	private long playerId;
	/**玩家状态*/
	private RoomPlayerState playerState;
	/**总筹码*/
	private long coins;
	/**位置*/
	private int pos;
	/**当前押注*/
	private long currentBet;
	/**总押注*/
	private long allBet;
	/**底牌*/
	private List <Card> cardList = new ArrayList<Card>();
	
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public RoomPlayerState getPlayerState() {
		return playerState;
	}
	public void setPlayerState(RoomPlayerState playerState) {
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
	public List <Card> getCardList() {
		return cardList;
	}
	public void setCardList(List <Card> cardList) {
		this.cardList = cardList;
	}
	
	public void win(long winBet){
		this.coins = this.coins+winBet;
	}
	
	public static ClientRoomPlayer convertFromRoomPlayerInfoData(TexasRoomPlayerInfoData roomPlayerInfoData)
	{
		ClientRoomPlayer texasRoomPlayer = new ClientRoomPlayer();
		texasRoomPlayer.setCoins(roomPlayerInfoData.getCoins());
		texasRoomPlayer.setPlayerId(roomPlayerInfoData.getPlayerId());
		texasRoomPlayer.setPos(roomPlayerInfoData.getPos());
		texasRoomPlayer.setCurrentBet(roomPlayerInfoData.getCurrentBet());
		texasRoomPlayer.setAllBet(roomPlayerInfoData.getAllBet());
		return  texasRoomPlayer;
	}
	
	public void bet(long bet) {
		// TODO Auto-generated method stub
		coins -= bet;
		currentBet +=bet;
		allBet += bet;	
	}
	
	public void start() {
		// TODO Auto-generated method stub
		playerState = RoomPlayerState.GAMING;
	
	}
	
	public void clear(){
		playerState = RoomPlayerState.INIT;
		allBet = 0;
		currentBet = 0;
		cardList.clear();
	}
	
	public void startNextRound()
	{
		currentBet = 0;
	}
	
	public void giveUp()
	{
		playerState = RoomPlayerState.GIVEUP;
	}
}
