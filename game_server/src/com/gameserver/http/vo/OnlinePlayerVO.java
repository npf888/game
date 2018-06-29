package com.gameserver.http.vo;
/**
 * 实时在线人数的一些信息
 * ${item.name }
 * ${item.passportId }
 * ${item.girlFlag }
 * ${item.device }
 * ${item.channelType }
 * ${item.gold }
 * @author JavaServer
 *
 */
public class OnlinePlayerVO {

	private String name;
	private long passportId;
	private int girlFlag;
	private String device;
	private int channelType;
	private long gold;
	private long totalMinute;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public int getGirlFlag() {
		return girlFlag;
	}

	public void setGirlFlag(int girlFlag) {
		this.girlFlag = girlFlag;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getTotalMinute() {
		return totalMinute;
	}

	public void setTotalMinute(long totalMinute) {
		this.totalMinute = totalMinute;
	}

	
	
}
