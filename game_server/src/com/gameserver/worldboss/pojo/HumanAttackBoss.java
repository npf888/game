package com.gameserver.worldboss.pojo;

import java.util.Date;

import com.common.constants.Loggers;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanAttackBossEntity;
import com.gameserver.human.Human;

/**
 * 用户攻击 boss的 个人信息
 * @author JavaServer
 *
 */
public class HumanAttackBoss implements PersistanceObject<Long, HumanAttackBossEntity>{

	private long id;
	private long userId;
	private long bossId;
	private long attackBlood;
	private long attackTotalBlood;
	private long attackAllTotalBlood;
	private Date createTime;
	
	private final LifeCycle lifeCycle;
	private Human owner;
	private boolean inDb;
	
	
	
	
	
	public HumanAttackBoss(Human human){
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner=human;
	}
	
	
	public long getAttackTotalBlood() {
		return attackTotalBlood;
	}
	public void setAttackTotalBlood(long attackTotalBlood) {
		this.attackTotalBlood = attackTotalBlood;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getBossId() {
		return bossId;
	}
	public void setBossId(long bossId) {
		this.bossId = bossId;
	}
	public long getAttackBlood() {
		return attackBlood;
	}
	public void setAttackBlood(long attackBlood) {
		this.attackBlood = attackBlood;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public long getAttackAllTotalBlood() {
		return attackAllTotalBlood;
	}

	public void setAttackAllTotalBlood(long attackAllTotalBlood) {
		this.attackAllTotalBlood = attackAllTotalBlood;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	@Override
	public void setDbId(Long id) {
		this.id=id;
	}
	@Override
	public Long getDbId() {
		return this.id;
	}
	@Override
	public String getGUID() {
		return "HumanAttackBoss#"+this.id;
	}
	@Override
	public boolean isInDb() {
		return this.inDb;
	}
	@Override
	public void setInDb(boolean inDb) {
		this.inDb=inDb;
	}
	@Override
	public long getCharId() {
		return userId;
	}
	
	
	
	@Override
	public HumanAttackBossEntity toEntity() {
		HumanAttackBossEntity entity = new HumanAttackBossEntity();
		entity.setId(getDbId());
		entity.setBossId(getBossId());
		entity.setUserId(getCharId());
		entity.setAttackBlood(getAttackBlood());
		entity.setCreateTime(getCreateTime());
		entity.setAttackTotalBlood(getAttackTotalBlood());
		entity.setAttackAllTotalBlood(getAttackAllTotalBlood());
		return entity;
	}
	@Override
	public void fromEntity(HumanAttackBossEntity entity) {
		this.setId(entity.getId());
		this.setUserId(entity.getUserId());
		this.setBossId(entity.getBossId());
		this.setAttackBlood(entity.getAttackBlood());
		this.setAttackTotalBlood(entity.getAttackTotalBlood());
		this.setAttackAllTotalBlood(entity.getAttackAllTotalBlood());
		this.setCreateTime(entity.getCreateTime());
		this.setInDb(true);
		this.active();
	}
	
	
	
	
	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
	@Override
	public void setModified() {
		onUpdate();
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
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				Loggers.WORLDBOSS.info("无线循环。。。。。。。。。。。。。。。。。。");
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
}
