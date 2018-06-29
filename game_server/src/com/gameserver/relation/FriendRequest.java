package com.gameserver.relation;


import com.common.InitializeRequired;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.FriendRequestEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;


/**
 * 好友
 * @author wayne
 *
 */
public class FriendRequest implements PersistanceObject<Long, FriendRequestEntity>, InitializeRequired {

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
	private long sendId;
	/**更新时间*/
	private long updateTime;
	/**创建时间*/
	private long createTime;

	public FriendRequest(Human owner) 
	{
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner = owner;
	}
	
	@Override
	public void init()
	{
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
		return "friendrequest#" + this.id;
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
	
	public void setSendId(long sendId)
	{
		this.sendId = sendId;
	}
	

	public long getSendId() {
		return sendId;
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
	public FriendRequestEntity toEntity() 
	{
		FriendRequestEntity friendRequestEntity=new FriendRequestEntity();
		friendRequestEntity.setId(this.id);
		friendRequestEntity.setCharId(this.charId);
		friendRequestEntity.setSendId(this.sendId);
		friendRequestEntity.setUpdateTime(this.updateTime);
		friendRequestEntity.setCreateTime(this.createTime);
		return friendRequestEntity;
	}

	@Override
	public void fromEntity(FriendRequestEntity entity)
	{
		this.setDbId(entity.getId());
		this.setCharId(entity.getCharId());
		this.setInDb(true);
		this.setSendId(entity.getSendId());
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
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
