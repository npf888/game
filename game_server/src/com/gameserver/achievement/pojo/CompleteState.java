package com.gameserver.achievement.pojo;

public class CompleteState {

	/**唯一ID **/
	private int id;
	
	/**1 没有完成 2 已经完成但没有领取 3 已经领取 **/
	private int state;
	
	/**完成时间  **/
	private long completeTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(long completeTime) {
		this.completeTime = completeTime;
	}
	
	
	
	
}
