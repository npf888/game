package com.gameserver.http.json;

public class PlayerVO {

	private long passportId;
	private boolean threadExist;
	private int inRoom;
	public long getPassportId() {
		return passportId;
	}
	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}
	public boolean isThreadExist() {
		return threadExist;
	}
	public void setThreadExist(boolean threadExist) {
		this.threadExist = threadExist;
	}
	public int getInRoom() {
		return inRoom;
	}
	public void setInRoom(int inRoom) {
		this.inRoom = inRoom;
	}
	
}
