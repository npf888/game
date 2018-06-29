package com.gameserver.notice.data;

import com.db.model.NoticeEntity;

/**
 * 
 * @author wayne
 *
 */
public class NoticeData {
	private long id;
	private long startTime;
	private long endTime;
	private int dailyStartTime;
	private int dailyEndTime;
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
	public int getDailyStartTime() {
		return dailyStartTime;
	}
	public void setDailyStartTime(int dailyStartTime) {
		this.dailyStartTime = dailyStartTime;
	}
	public int getDailyEndTime() {
		return dailyEndTime;
	}
	public void setDailyEndTime(int dailyEndTime) {
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
	
	public static NoticeEntity convertFromNoticeData(NoticeData noticeData){
		NoticeEntity noticeEntity = new NoticeEntity();
		noticeEntity.setId(noticeData.getId());
		noticeEntity.setContent(noticeData.getContent());
		noticeEntity.setCreateTime(noticeData.getCreateTime());
		noticeEntity.setDailyEndTime(noticeData.getDailyEndTime());
		noticeEntity.setDailyStartTime(noticeData.getDailyStartTime());
		noticeEntity.setEndTime(noticeData.getEndTime());
		noticeEntity.setId(noticeData.getId());
		noticeEntity.setIntervalTime(noticeData.getIntervalTime());
		noticeEntity.setStartTime(noticeData.getStartTime());
		noticeEntity.setUpdateTime(noticeData.getUpdateTime());
		return noticeEntity;
	}
}
