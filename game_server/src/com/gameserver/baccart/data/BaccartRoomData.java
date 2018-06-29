package com.gameserver.baccart.data;

public class BaccartRoomData {
	private int roomId;
	private long jackpot;
	private int closed;
	private int num;
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public long getJackpot() {
		return jackpot;
	}
	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}
	
	/**
	 * @return the closed
	 */
	public int getClosed() {
		return closed;
	}
	/**
	 * @param closed the closed to set
	 */
	public void setClosed(int closed) {
		this.closed = closed;
	}
	@Override
	public String toString(){
	
		return "房间["+roomId+"],人数["+this.num+"],jackpot["+this.jackpot+"]";
	
	}
}
