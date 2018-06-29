package com.gameserver.http.vo;
/**
 * 跑马灯
 * @author JavaServer
 *
 */
public class NoticeVO {
	private long id;
	private long startTime;
	private long endTime;
	private long dailyStartTime;
	private long dailyEndTime;
	private int intervalTime;
	private String content;
	private long updateTime;
	private long createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getDailyStartTime() {
		return dailyStartTime;
	}
	public void setDailyStartTime(long dailyStartTime) {
		this.dailyStartTime = dailyStartTime;
	}
	public long getDailyEndTime() {
		return dailyEndTime;
	}
	public void setDailyEndTime(long dailyEndTime) {
		this.dailyEndTime = dailyEndTime;
	}
	public int getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	
}
