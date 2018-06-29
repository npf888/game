
package com.gameserver.relation;


import com.common.InitializeRequired;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanFriendGiftEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;


/**
 * 好友
 * @author wayne
 *
 */
public class FriendGift implements PersistanceObject<Long, HumanFriendGiftEntity>, InitializeRequired {

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
	/**领取时间*/
	private long getTime;
	/**更新时间*/
	private long updateTime;
	/**创建时间*/
	private long createTime;
	
	public FriendGift(){
		this.lifeCycle = new LifeCycleImpl(this);
	}

	public FriendGift(Human owner) 
	{
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner = owner;
	}
	
	@Override
	public void init()
	{
	}
	
	public void setOwner(Human owner) {
		// TODO Auto-generated method stub
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
		return "friendgift#" + this.id;
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
	

	

	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public long getGetTime() {
		return getTime;
	}

	public void setGetTime(long getTime) {
		this.getTime = getTime;
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
	public HumanFriendGiftEntity toEntity() 
	{
		HumanFriendGiftEntity humanFriendGiftEntity=new HumanFriendGiftEntity();
		humanFriendGiftEntity.setId(this.id);
		humanFriendGiftEntity.setCharId(this.charId);
		humanFriendGiftEntity.setFriendId(this.getFriendId());
		humanFriendGiftEntity.setGetTime(this.getGetTime());
		humanFriendGiftEntity.setUpdateTime(this.updateTime);
		humanFriendGiftEntity.setCreateTime(this.createTime);
		return humanFriendGiftEntity;
	}

	@Override
	public void fromEntity(HumanFriendGiftEntity entity)
	{
		this.setDbId(entity.getId());
		this.setCharId(entity.getCharId());
		this.setInDb(true);
		this.setFriendId(entity.getFriendId());
		this.setGetTime(entity.getGetTime());
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
	
	/**
	 * 是否领取了
	 * @return
	 */
	public boolean ifGet(){
		return this.getTime!=0;
	}

	

}
