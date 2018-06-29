package com.gameserver.activity.data;

import com.alibaba.fastjson.JSON;
import com.gameserver.activity.Activity;

/**
 * 活动数据 
 * @author wayne
 *
 */
public class ActivityData {
	
	private long activityId;
	private String title;
	private String title_cn;
	private String title_tw;
	private String desc;
	private String desc_cn;
	private String desc_tw;
	//private ActivitySmallData[] smallActivity;
	private String ruleSre;
	
	private String hall_pic_cn;
	private String hall_pic_tw;
	private String hall_pic;
	private String pic;
	private String pic_cn;
	private String pic_tw;
	private int link;
	private long startTime;
	private long endTime;
	/***全服所有人当前充值的金币***/
	private String fullValue;
	
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
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
	public int getLink() {
		return link;
	}
	public void setLink(int link) {
		this.link = link;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc_cn() {
		return desc_cn;
	}
	public void setDesc_cn(String desc_cn) {
		this.desc_cn = desc_cn;
	}
	public String getDesc_tw() {
		return desc_tw;
	}
	public void setDesc_tw(String desc_tw) {
		this.desc_tw = desc_tw;
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
	
	public String getHall_pic() {
		return hall_pic;
	}
	public void setHall_pic(String hall_pic) {
		this.hall_pic = hall_pic;
	}
	public String getRuleSre() {
		return ruleSre;
	}
	public void setRuleSre(String ruleSre) {
		this.ruleSre = ruleSre;
	}
	
	public String getHall_pic_cn() {
		return hall_pic_cn;
	}
	public void setHall_pic_cn(String hall_pic_cn) {
		this.hall_pic_cn = hall_pic_cn;
	}
	public String getHall_pic_tw() {
		return hall_pic_tw;
	}
	public void setHall_pic_tw(String hall_pic_tw) {
		this.hall_pic_tw = hall_pic_tw;
	}
	
	
	
	public String getFullValue() {
		return fullValue;
	}
	public void setFullValue(String fullValue) {
		this.fullValue = fullValue;
	}
	public static ActivityData convertFromActivity(Activity activity){
		ActivityData activityData =new ActivityData();
		activityData.setActivityId(activity.getDbId());
		activityData.setTitle(activity.getTitle());
		activityData.setTitle_cn(activity.getTitle_cn());
		activityData.setTitle_tw(activity.getTitle_tw());
		activityData.setDesc(activity.getActivityDesc());
		activityData.setDesc_cn(activity.getActivityDesc_cn());
		activityData.setDesc_tw(activity.getActivityDesc_tw());
		activityData.setPic(activity.getPic());
		activityData.setPic_cn(activity.getPic_cn());
		activityData.setPic_tw(activity.getPic_tw());
		activityData.setHall_pic(activity.getHall_pic());
		activityData.setHall_pic_cn(activity.getHall_pic_cn());
		activityData.setHall_pic_tw(activity.getHall_pic_tw());
		
		activityData.setLink(activity.getPageLinkEnum().getIndex());
		activityData.setStartTime(activity.getStartTime());
		activityData.setEndTime(activity.getEndTime());
		//List<ActivityRewardRule> list = activity.getRuleList();
		activityData.setRuleSre(JSON.toJSONString(activity.getRuleList()));
		activityData.setFullValue("0");//TODO 
		/*List<ActivitySmallData> smalllist = new ArrayList<ActivitySmallData>();
		for(ActivityRewardRule rule :  list){
			ActivitySmallData small = new ActivitySmallData();
			List<ActivityCondition> conditionList = new ArrayList<ActivityCondition>();
			List<RandRewardData> rewardList = new ArrayList<RandRewardData>();
			for(Entry<String,Integer> en : rule.getParamsMap().entrySet()){
				ActivityCondition ac = new ActivityCondition();
				ac.setConditionType(Integer.valueOf(en.getKey()));
				ac.setValueDate(en.getValue());
				conditionList.add(ac);
			}
			rewardList.addAll(rule.getRewardList());
			
			small.setConditionList(conditionList.toArray(new ActivityCondition[conditionList.size()]));
			small.setRewardList(rewardList.toArray(new RandRewardData[rewardList.size()]));
			
			smalllist.add(small);
		}
		activityData.setSmallActivity(smalllist.toArray(new ActivitySmallData[smalllist.size()]));*/
		
		return activityData;
	}
}
