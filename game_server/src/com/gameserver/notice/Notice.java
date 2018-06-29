package com.gameserver.notice;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.NoticeEntity;
import com.gameserver.common.Globals;

/**
 * 公告
 * @author wayne
 *
 */

public class Notice implements PersistanceObject<Long, NoticeEntity>{

	/** 生命期 */
	private final LifeCycle lifeCycle;
	/**id*/
	private long id;
	//开始时间
	private long startTime;
	//结束时间
	private long endTime;
	//每天开始时间
	private int dailyStartTime;
	//每天结束时间
	private int dailyEndTime;
	//间隔时间
	private int intervalTime;
	//是否删除 0:未删除       1:已删除
	private int isDelete;
	//内容
	private String content;
	private long updateTime;
	private long createTime;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	/**上次广播时间*/
	private long lastNoticeTime;
	
	public Notice(){
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	@Override
	public LifeCycle getLifeCycle() {
		// TODO Auto-generated method stub
		return lifeCycle;
	}

	
	@Override
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id =id;
	}

	@Override
	public Long getDbId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return "notice#"+this.id;
	}

	@Override
	public boolean isInDb() {
		// TODO Auto-generated method stub
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		// TODO Auto-generated method stub
		this.inDb = inDb;
	}

	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return 0;
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

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
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

	@Override
	public NoticeEntity toEntity() {
		// TODO Auto-generated method stub
		NoticeEntity noticeEntity = new NoticeEntity();
		noticeEntity.setId(this.getDbId());
		noticeEntity.setStartTime(this.getStartTime());
		noticeEntity.setEndTime(this.getEndTime());
		noticeEntity.setDailyStartTime(this.getDailyStartTime());
		noticeEntity.setDailyEndTime(this.getDailyEndTime());
		noticeEntity.setIntervalTime(this.getIntervalTime());
		noticeEntity.setContent(this.getContent());
		noticeEntity.setIsDelete(this.getIsDelete());
		noticeEntity.setUpdateTime(this.getUpdateTime());
		noticeEntity.setCreateTime(this.getCreateTime());
		return noticeEntity;
	}

	@Override
	public void fromEntity(NoticeEntity entity) {
		// TODO Auto-generated method stub
		this.setDbId(entity.getId());
		this.setStartTime(entity.getStartTime());
		this.setEndTime(entity.getEndTime());
		this.setDailyStartTime(entity.getDailyStartTime());
		this.setDailyEndTime(entity.getDailyEndTime());
		this.setIntervalTime(entity.getIntervalTime());
		this.setContent(entity.getContent());
		this.setIsDelete(entity.getIsDelete());
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
		this.setInDb(true);
		this.active();
	}
	
	/**
	 * 激活此关系
	 */
	public void active() 
	{
		getLifeCycle().activate();
	}


	@Override
	public void setModified() {
		// TODO Auto-generated method stub
		onUpdate();
	}
	

	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate()
	{

		// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive())
		{
			//全球管理器的更新，不属于个人操作
			this.updateTime = Globals.getTimeService().now();
			Globals.getGlobalLogicRunner().getGlobalDataUpdater().addUpdate(lifeCycle);
		}
		
	}

	/**
	 * @return the lastNoticeTime
	 */
	public long getLastNoticeTime() {
		return lastNoticeTime;
	}

	/**
	 * @param lastNoticeTime the lastNoticeTime to set
	 */
	public void setLastNoticeTime(long lastNoticeTime) {
		this.lastNoticeTime = lastNoticeTime;
	}

}
