package com.gameserver.relation;


import com.common.InitializeRequired;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.FriendEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;


/**
 * 好友
 * @author wayne
 *
 */
public class Friend implements PersistanceObject<Long, FriendEntity>, InitializeRequired {

	/** 主人 */
	private Human owner;
	/** 生命期 */
	private final LifeCycle lifeCycle;
	/** 是否已经在数据库中 */
	private boolean inDb;
	/** 主键 */
	private long id;
	/** 所属角色ID */
	private long charId;
	/** 好友列表 */
	private long friendId;
	/**facebook*/
	private int facebook;
	/** 用户 发出的申请好友同意之后，在每次登录的时候都会判断下是否有好友同意了 他的申请，如果有就把该状态改为1，否则是0*/
	private long agree;
	/**礼物时间*/
	private long giftTime;
	/**更新时间*/
	private long updateTime;
	/**创建时间*/
	private long createTime;

	public Friend() 
	{
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	@Override
	public void init()
	{
	}
	
	/**
	 * 设置主人
	 * 
	 * @param owner
	 * @throws IllegalArgumentException
	 *             当owner为空时抛出
	 */
	public void setOwner(Human owner) 
	{
		Assert.notNull(owner);
		this.owner = owner;
	}
	
	public Human getOwner()
	{
		return this.owner;
	}

	@Override
	public void setDbId(Long id) {
		this.id=id;
	}

	@Override
	public Long getDbId() {
		return id;
	}

	@Override
	public String getGUID()
	{
		return "friend#" + this.id;
	}

	@Override
	public boolean isInDb() {
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb=inDb;
	}
	
	public void setCharId(long charId)
	{
		this.charId = charId;
	}
	
	@Override
	public long getCharId() {
		return charId;
	}
	
	public void setFriendId(long friendId)
	{
		this.friendId = friendId;
	}
	
	public long getFriendId()
	{
		return this.friendId;
	}
	

	/**
	 * @return the facebook
	 */
	public int getFacebook() {
		return facebook;
	}

	/**
	 * @param facebook the facebook to set
	 */
	public void setFacebook(int facebook) {
		this.facebook = facebook;
	}

	public long getGiftTime() {
		return giftTime;
	}

	public void setGiftTime(long giftTime) {
		this.giftTime = giftTime;
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

	public long getAgree() {
		return agree;
	}

	public void setAgree(long agree) {
		this.agree = agree;
	}

	@Override
	public FriendEntity toEntity() 
	{
		FriendEntity friendEntity=new FriendEntity();
		friendEntity.setId(this.id);
		friendEntity.setCharId(this.charId);
		friendEntity.setFriendId(this.friendId);
		friendEntity.setFacebook(this.getFacebook());
		friendEntity.setGiftTime(this.giftTime);
		friendEntity.setAgree(this.agree);
		friendEntity.setUpdateTime(this.updateTime);
		friendEntity.setCreateTime(this.createTime);
		return friendEntity;
	}

	@Override
	public void fromEntity(FriendEntity entity)
	{
		this.setDbId(entity.getId());
		this.setCharId(entity.getCharId());
		this.setInDb(true);
		this.setFriendId(entity.getFriendId());
		this.setGiftTime(entity.getGiftTime());
		this.setFacebook(entity.getFacebook());
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
		this.setAgree(entity.getAgree());
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
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public void setModified() {
		onUpdate();
	}
	
	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate()
	{
		if (owner != null) 
		{
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				this.updateTime = Globals.getTimeService().now();
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	/**
	 * 物品被删除时调用,恢复默认值,并触发删除机制
	 * 
	 */
	public void onDelete()
	{
		this.lifeCycle.destroy();
		this.getOwner().getPlayer().getDataUpdater().addDelete(this.getLifeCycle());
	}

}
