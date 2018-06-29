package com.gameserver.activity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.ActivityEntity;
import com.gameserver.activity.enums.ActivityTypeEnum;
import com.gameserver.activity.enums.PageLinkEnum;
import com.gameserver.common.Globals;


/**
 * 活动模板数据
 * @author wayne
 *
 */
public class Activity implements PersistanceObject<Long, ActivityEntity>{


	/** 生命期 */
	private final LifeCycle lifeCycle;
	private long id;
	private ActivityTypeEnum activityType;
	private PageLinkEnum pageLinkEnum;
	private String title;
	private String title_cn;
	private String title_tw;
	private String activityDesc;
	private String activityDesc_cn;
	private String activityDesc_tw;
	private String hall_pic;
	private String hall_pic_cn;
	private String hall_pic_tw;
	private String pic;
	private String pic_cn;
	private String pic_tw;
	private List<ActivityRewardRule> ruleList = new ArrayList<ActivityRewardRule>();
	private int daily;
	private long startTime;
	private long endTime;
	private long updateTime;
	private long createTime;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	public Activity(){
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
		return "activity#"+this.id;
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




	public ActivityTypeEnum getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityTypeEnum activityType) {
		this.activityType = activityType;
	}

	public PageLinkEnum getPageLinkEnum() {
		return pageLinkEnum;
	}

	public void setPageLinkEnum(PageLinkEnum pageLinkEnum) {
		this.pageLinkEnum = pageLinkEnum;
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

	public void setActivityDesc(String desc) {
		this.activityDesc = desc;
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

	public List<ActivityRewardRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<ActivityRewardRule> ruleList) {
		this.ruleList = ruleList;
	}

	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
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
	public ActivityEntity toEntity() {
		// TODO Auto-generated method stub
		ActivityEntity activityEntity = new ActivityEntity();
		activityEntity.setId(this.getDbId());
		activityEntity.setStartTime(this.getStartTime());
		activityEntity.setEndTime(this.getEndTime());
		activityEntity.setTitle(this.getTitle());
		activityEntity.setTitle_cn(this.getTitle_cn());
		activityEntity.setTitle_tw(this.getTitle_tw());
		activityEntity.setActivityDesc(this.getActivityDesc());
		activityEntity.setActivityDesc_cn(this.getActivityDesc_cn());
		activityEntity.setActivityDesc_tw(this.getActivityDesc_tw());
		activityEntity.setPic(this.getPic());
		activityEntity.setPic_cn(this.getPic_cn());
		activityEntity.setPic_tw(this.getPic());
		activityEntity.setHall_pic(this.getHall_pic());
		activityEntity.setHall_pic_cn(this.getHall_pic_cn());
		activityEntity.setHall_pic_tw(this.getHall_pic_tw());
		
		
		activityEntity.setDaily(this.getDaily());
		activityEntity.setActivityType(this.getActivityType().getIndex());
		activityEntity.setPageLink(this.getPageLinkEnum().getIndex());
		
		activityEntity.setRulePack(JSON.toJSONString(this.getRuleList()));
		activityEntity.setStartTime(this.getStartTime());
		activityEntity.setEndTime(this.getEndTime());
		activityEntity.setUpdateTime(this.getUpdateTime());
		activityEntity.setCreateTime(this.getCreateTime());
		return activityEntity;
	}

	@Override
	public void fromEntity(ActivityEntity entity) {
		// TODO Auto-generated method stub
		this.setDbId(entity.getId());
		this.setStartTime(entity.getStartTime());
		this.setEndTime(entity.getEndTime());
		this.setTitle(entity.getTitle());
		this.setTitle_cn(entity.getTitle_cn());
		this.setTitle_tw(entity.getTitle_tw());
		this.setActivityDesc(entity.getActivityDesc());
		this.setActivityDesc_cn(entity.getActivityDesc_cn());
		this.setActivityDesc_tw(entity.getActivityDesc_tw());
		this.setPic(entity.getPic());
		this.setPic_cn(entity.getPic_cn());
		this.setPic_tw(entity.getPic_tw());
		this.setHall_pic(entity.getHall_pic());
		this.setHall_pic_cn(entity.getHall_pic_cn());
		this.setHall_pic_tw(entity.getHall_pic_tw());
		
		
		this.setDaily(entity.getDaily());
		this.setPageLinkEnum(PageLinkEnum.valueOf(entity.getPageLink()));
		ActivityTypeEnum tempType = ActivityTypeEnum.valueOf(entity.getActivityType());
		this.setActivityType(tempType);
		this.getRuleList().addAll(JSON.parseArray(entity.getRulePack(), ActivityRewardRule.class));
		this.setStartTime(entity.getStartTime());
		this.setEndTime(entity.getEndTime());
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
	 * 物品被删除时调用,恢复默认值,并触发删除机制
	 * 
	 */
	public void onDelete()
	{
		this.lifeCycle.destroy();
		Globals.getGlobalLogicRunner().getGlobalDataUpdater().addDelete(this.getLifeCycle());
	
	}
	
	/**
	 * 当前时间和结束时间比较
	 * @return true:结束了活动
	 */
	public boolean isEnd(){
		long now = Globals.getTimeService().now();
		return now>this.getEndTime();
	}
	

	
	/**
	 * 判断是否在开始时间和结束时间
	 * @return true:表示激活
	 */
	public boolean isActive(){
		long now = Globals.getTimeService().now();
		return now>this.getStartTime() && now<this.getEndTime();
	}
	
	public ActivityRewardRule getActivityRewardRuleByIndex(int index){
		return ruleList.get(index);
	}

	public String getHall_pic() {
		return hall_pic;
	}

	public void setHall_pic(String hall_pic) {
		this.hall_pic = hall_pic;
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
	
}
