package com.gameserver.gift;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanGiftEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 
 * @author 郭君伟
 *
 */
public class HumanGift implements PersistanceObject<Long, HumanGiftEntity> {
	
	
	private Human owner;
	
	private final LifeCycle lifeCycle;
	/** false 数据库没有 执行save 操作 true 执行update **/
	private boolean inDb;
	
	
	//=============================
	
	 /** 主键 **/
	 private long id;
	 /** 角色ID **/
	 private long charId;
	 /**优惠弹出礼包 **/
	 private int giftid;
	 /**优惠弹出礼包弹出世间点 **/
	 private long refreshTime;
	
	 /** **/
	 private long updateTime;
	 /** **/
	 private long createTime;
	 
	 
	 public HumanGift() {
			this.lifeCycle = new LifeCycleImpl(this);
	 }
	 
	 

	@Override
	public void setDbId(Long id) {
		this.id = id;
	}

	@Override
	public Long getDbId() {
		return id;
	}

	@Override
	public String getGUID() {
		return "HumanGiftNew#"+this.id;
	}

	@Override
	public boolean isInDb() {
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
	     this.inDb = inDb;
	}

	@Override
	public long getCharId() {
		
		return charId;
	}
	
	public void setCharId(long charId) {
		this.charId = charId;
	}
	

	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}



	public void setOwner(Human owner) {
		this.owner = owner;
	}


	public int getGiftid() {
		return giftid;
	}



	public void setGiftid(int giftid) {
		this.giftid = giftid;
	}



	public long getRefreshTime() {
		return refreshTime;
	}



	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}
	
	

	public long getCreateTime() {
		return createTime;
	}



	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}



	@Override
	public HumanGiftEntity toEntity() {
		HumanGiftEntity entity = new HumanGiftEntity();
		entity.setId(id);
		entity.setCharId(charId);
		entity.setGiftid(giftid);
		entity.setRefreshTime(refreshTime);
		entity.setUpdateTime(updateTime);
		entity.setCreateTime(createTime);
		return entity;
	}

	@Override
	public void fromEntity(HumanGiftEntity entity) {
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.giftid = entity.getGiftid();
		this.refreshTime = entity.getRefreshTime();
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
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
		return lifeCycle;
	}

	@Override
	public void setModified() {
		onUpdate();
	}

	private void onUpdate(){
		
		if (owner != null) {
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive()){
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

}
