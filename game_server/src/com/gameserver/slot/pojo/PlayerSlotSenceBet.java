package com.gameserver.slot.pojo;
/**
 * 
 * player-slot-bet
 * 
 * 用户在 哪个老虎机 的哪个 场景 的 哪个 倍数 下
 * @author JavaServer
 *
 */
public class PlayerSlotSenceBet {

	//id 表里的 主键ID
	private int slotId;
	private long playerId;
	//当前用户的bet
	private int bet;
	public int getSlotId() {
		return slotId;
	}
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	
	
	
}
