package com.gameserver.bazoo.data;
/**
 * 输的人 会向赢得人发送 倍
 * 这个就是 赢得人 的集合 
 * @author JavaServer
 *
 */
public class Winners {

	private long passportId;//赢得人的ID
	private int multiple;//倍数
	public long getPassportId() {
		return passportId;
	}
	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	
	
	
}
