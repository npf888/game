package com.gameserver.activity.data;


import com.db.model.ActivityEntity;


public class ActivityEntityData {
	private long id;
	private int activityType;
	private int pageLink;
	private String title;
	private String title_cn;
	private String title_tw;
	private String activityDesc;
	private String activityDesc_cn;
	private String activityDesc_tw;
	private String pic;
	private String pic_cn;
	private String pic_tw;
	private String rulePack;
	private int daily;
	private long startTime;
	private long endTime;
	private long updateTime;
	private long createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getActivityType() {
		return activityType;
	}
	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
	public int getPageLink() {
		return pageLink;
	}
	public void setPageLink(int pageLink) {
		this.pageLink = pageLink;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle_cn() {
		return title_cn;
	}
	public void setTitle_cn(String title_cn) {
		this.title_cn = title_cn;
	}
	public String getTitle_tw() {
		return title_tw;
	}
	public void setTitle_tw(String title_tw) {
		this.title_tw = title_tw;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getActivityDesc_cn() {
		return activityDesc_cn;
	}
	public void setActivityDesc_cn(String activityDesc_cn) {
		this.activityDesc_cn = activityDesc_cn;
	}
	public String getActivityDesc_tw() {
		return activityDesc_tw;
	}
	public void setActivityDesc_tw(String activityDesc_tw) {
		this.activityDesc_tw = activityDesc_tw;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPic_cn() {
		return pic_cn;
	}
	public void setPic_cn(String pic_cn) {
		this.pic_cn = pic_cn;
	}
	public String getPic_tw() {
		return pic_tw;
	}
	public void setPic_tw(String pic_tw) {
		this.pic_tw = pic_tw;
	}
	public String getRulePack() {
		return rulePack;
	}
	public void setRulePack(String rulePack) {
		this.rulePack = rulePack;
	}
	public int getDaily() {
		return daily;
	}
	public void setDaily(int daily) {
		this.daily = daily;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
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
	
	
	public static  ActivityEntity convertFromActivityEntityData(ActivityEntityData activityEntityData){
		ActivityEntity activityEntity = new ActivityEntity();
		activityEntity.setId(activityEntityData.getId());
		activityEntity.setStartTime(activityEntityData.getStartTime());
		activityEntity.setEndTime(activityEntityData.getEndTime());
		activityEntity.setTitle(activityEntityData.getTitle());
		activityEntity.setTitle_cn(activityEntityData.getTitle_cn());
		activityEntity.setTitle_tw(activityEntityData.getTitle_tw());
		activityEntity.setActivityDesc(activityEntityData.getActivityDesc());
		activityEntity.setActivityDesc_cn(activityEntityData.getActivityDesc_cn());
		activityEntity.setActivityDesc_tw(activityEntityData.getActivityDesc_tw());
		activityEntity.setPic(activityEntityData.getPic());
		activityEntity.setPic_cn(activityEntityData.getPic_cn());
		activityEntity.setPic_tw(activityEntityData.getPic_tw());
		activityEntity.setDaily(activityEntityData.getDaily());
		activityEntity.setPageLink(activityEntityData.getPageLink());
		
		activityEntity.setActivityType(activityEntityData.getActivityType());
		activityEntity.setRulePack(activityEntityData.getRulePack());
		activityEntity.setStartTime(activityEntityData.getStartTime());
		activityEntity.setEndTime(activityEntityData.getEndTime());
		activityEntity.setUpdateTime(activityEntityData.getUpdateTime());
		activityEntity.setCreateTime(activityEntityData.getCreateTime());
		return activityEntity;
	}
}
