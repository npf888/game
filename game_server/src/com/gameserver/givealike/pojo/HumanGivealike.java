package com.gameserver.givealike.pojo;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanGivealikeEntity;
import com.gameserver.human.Human;

public class HumanGivealike implements PersistanceObject<Long, HumanGivealikeEntity> {
	private long id;
	private long userId;
	private int slotType;
	private int slotBet;
	private Date createTime;
	private String content;
	private Human owner;
	
	private final LifeCycle lifeCycle;
	/** false 数据库没有 执行save 操作 true 执行update **/
	private boolean inDb;
	
	
	public HumanGivealike(Human human){
		this.owner=human;
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getSlotType() {
		return slotType;
	}
	public void setSlotType(int slotType) {
		this.slotType = slotType;
	}
	public int getSlotBet() {
		return slotBet;
	}
	public void setSlotBet(int slotBet) {
		this.slotBet = slotBet;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		return "HumanGivealike#"+this.id;
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
		return owner.getPassportId();
	}
	@Override
	public HumanGivealikeEntity toEntity() {
		HumanGivealikeEntity entity = new HumanGivealikeEntity();
		entity.setId(id);
		entity.setUserId(getUserId());
		entity.setSlotType(getSlotType());
		entity.setSlotBet(getSlotBet());
		entity.setContent(getContent());
		entity.setCreateTime(getCreateTime());
		return entity;
	}
	@Override
	public void fromEntity(HumanGivealikeEntity entity) {
		this.setId(entity.getId());
		this.setUserId(entity.getUserId());
		this.setSlotType(entity.getSlotType());
		this.setSlotBet(entity.getSlotBet());
		this.setContent(entity.getContent());
		this.setCreateTime(entity.getCreateTime());
		this.setInDb(true);
		this.active();
	}
	
	/**
	 * 激活此2关系
	 */
	public void active(){
		getLifeCycle().activate();
	}
	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}
	@Override
	public void setModified() {
		onUpdate();
	}

	private void onUpdate(){
		
		if (owner != null) {
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive()){
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	
	
	
}
