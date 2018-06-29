package com.gameserver.misc;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanMiscEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * misc
 * @author wayne
 *
 */
public class HumanMisc implements PersistanceObject<Long, HumanMiscEntity>{
	
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private boolean inDb;
	/**上一次领取时间*/
	private long lastGetTime;
	/**在线时间*/
	private long onlineTime;
	/**当前在线奖励id*/
	private int currentOnlineRewardId;
	/**首充时间*/
	private long firstRechargeTime;
	/**改名次数*/
	private int renameTimes;
	/**广告奖励*/
	private int adRewards;
	/**新玩家*/
	private int newUser;
	private int fbReward;
	/**fb 邀请*/
	private List<String> fbInviteList = new ArrayList<String>();
	/**fb 邀请奖励领取*/
	private List<Integer> fbInviteRewardList= new ArrayList<Integer>();
 	/** 更新时间 */
	private long updateTime;
	/** 创建时间 */
	private long createTime;
	/** FB 点赞奖励是否领取**/
	private int fbThumb;
	/** 好友刷新时间 点**/
	private long refreshTime;
	
	public HumanMisc(){
		this.lifeCycle = new LifeCycleImpl(this);
		
	}
	
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}
	
	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}
	
	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return charId;
	}
	
	@Override
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	@Override
	public Long getDbId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return "misc#"+this.id;
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
	
	public long getLastGetTime() {
		return lastGetTime;
	}

	public void setLastGetTime(long lastGetTime) {
		this.lastGetTime = lastGetTime;
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	public void setCurrentOnlineRewardId(int currentOnlineRewardId)
	{
		this.currentOnlineRewardId = currentOnlineRewardId;
	}

	public int getCurrentOnlineRewardId(){
		return currentOnlineRewardId;
	}
	public long getFirstRechargeTime() {
		return firstRechargeTime;
	}

	public void setFirstRechargeTime(long firstRechargeTime) {
		this.firstRechargeTime = firstRechargeTime;
	}

	public int getRenameTimes() {
		return renameTimes;
	}

	public void setRenameTimes(int renameTimes) {
		this.renameTimes = renameTimes;
	}



	/**
	 * @return the adRewards
	 */
	public int getAdRewards() {
		return adRewards;
	}

	/**
	 * @param adRewards the adRewards to set
	 */
	public void setAdRewards(int adRewards) {
		this.adRewards = adRewards;
	}

	public int getNewUser() {
		return newUser;
	}

	public void setNewUser(int newUser) {
		this.newUser = newUser;
	}
	
	
	
	public int getFbThumb() {
		return fbThumb;
	}

	public void setFbThumb(int fbThumb) {
		this.fbThumb = fbThumb;
	}

	/**
	 * @return the fbReward
	 */
	public int getFbReward() {
		return fbReward;
	}

	/**
	 * @param fbReward the fbReward to set
	 */
	public void setFbReward(int fbReward) {
		this.fbReward = fbReward;
	}

	public List<String> getFbInviteList(){
		return this.fbInviteList;
	}
	
	public List<Integer> getFbInviteRewardList(){
		return this.fbInviteRewardList;
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
	
	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}

	@Override
	public void setModified() {
		// TODO Auto-generated method stub
		onUpdate();
	}

	@Override
	public HumanMiscEntity toEntity() {
		// TODO Auto-generated method stub
		HumanMiscEntity entity = new HumanMiscEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setOnlineTime(getOnlineTime());
		entity.setLastGetTime(getLastGetTime());
		entity.setCurrentOnlineRewardId(currentOnlineRewardId);
		entity.setFirstRechargeTime(getFirstRechargeTime());
		entity.setRenameTimes(getRenameTimes());
		entity.setAdRewards(getAdRewards());
		entity.setNewUser(getNewUser());
		entity.setFbReward(getFbReward());
		entity.setFbInvitePack(JSON.toJSONString(this.fbInviteList));
		entity.setFbInviteRewardPack(JSON.toJSONString(this.fbInviteRewardList));
		entity.setFbThumb(fbThumb);
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		entity.setRefreshTime(refreshTime);
		return entity;
	}

	@Override
	public void fromEntity(HumanMiscEntity entity) {
		// TODO Auto-generated method stub
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.onlineTime = entity.getOnlineTime();
		this.updateTime = entity.getUpdateTime();
		this.lastGetTime = entity.getLastGetTime();
		this.renameTimes = entity.getRenameTimes();
		this.adRewards = entity.getAdRewards();
		this.newUser = entity.getNewUser();
		this.fbReward = entity.getFbReward();
		this.fbThumb = entity.getFbThumb();
		if(entity.getFbInvitePack()!=null){
			this.fbInviteList.addAll(JSON.parseArray(entity.getFbInvitePack(), String.class));
		}
		if(entity.getFbInviteRewardPack()!=null){
			this.fbInviteRewardList.addAll(JSON.parseArray(entity.getFbInviteRewardPack(), Integer.class));
		}
		
		this.firstRechargeTime = entity.getFirstRechargeTime();
		this.createTime = entity.getCreateTime();
		this.currentOnlineRewardId = entity.getCurrentOnlineRewardId();
		this.refreshTime = entity.getRefreshTime();
		this.setInDb(true);
		this.active();
	}

	/**
	 * 激活此2关系
	 */
	public void active(){
		getLifeCycle().activate();
	}
	
	private void onUpdate()
	{
		if (owner != null) 
		{
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	
}
