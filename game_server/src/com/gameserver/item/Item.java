package com.gameserver.item;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.core.uuid.UUIDType;
import com.db.model.HumanItemEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;


/**
 * 道具
 * @author wayne
 *
 */
public class Item implements PersistanceObject<Long, HumanItemEntity>{
	

	private Human owner;
	/** 生命期 */
	private final LifeCycle lifeCycle;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	private long id;
	private long fromPlayerId;
	private long charId;
	/**模板id*/
	private int templateId;
	/**叠加数*/
	private int overlap;
	/**开始时间*/
	private long beginTime;
	/**持续时间*/
	private long duration;
	private int useing;//是否正在使用中 0：否，1：是
	/**更新时间*/
	private long updateTime;
	/**创建时间*/
	private long createTime;
	
	public Item()
	{
		this.lifeCycle = new LifeCycleImpl(this);
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
	
	public Human getOwner() {
		return this.owner;
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
		return "humanitem#" + this.id;
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
	
	public void setCharId(long charId)
	{
		this.charId = charId;
	}
	
	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return this.charId;
	}

	

	public long getFromPlayerId() {
		return fromPlayerId;
	}

	public void setFromPlayerId(long fromPlayerId) {
		this.fromPlayerId = fromPlayerId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getOverlap() {
		return overlap;
	}

	public void setOverlap(int overlap) {
		this.overlap = overlap;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public long getCreateTime() {
		return createTime;
	}


	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	
	
	public int getUseing() {
		return useing;
	}

	public void setUseing(int useing) {
		this.useing = useing;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 判断是否过期
	 * @return
	 */
	public boolean ifExpire(){
		if(this.getBeginTime()==0)
			return false;
		long now = Globals.getTimeService().now();
		if(now>this.getBeginTime()+this.getDuration())
			return true;
		return false;
	}



	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
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
		onUpdate();
	}
	
	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate()
	{
		if (owner != null) 
		{
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.setUpdateTime(now) ;
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

	@Override
	public HumanItemEntity toEntity() {
		// TODO Auto-generated method stub
		HumanItemEntity humanItemEntity=new HumanItemEntity();
		humanItemEntity.setId(this.getDbId());
		humanItemEntity.setCharId(this.getCharId());
		humanItemEntity.setBeginTime(this.getBeginTime());
		humanItemEntity.setDuration(this.getDuration());
		humanItemEntity.setOverlap(this.getOverlap());
		humanItemEntity.setTemplateId(this.getTemplateId());
		humanItemEntity.setCreateTime(this.getCreateTime());
		humanItemEntity.setUpdateTime(this.getUpdateTime());
		humanItemEntity.setUseing(this.useing);
		humanItemEntity.setFromPlayerId(this.fromPlayerId);
		return humanItemEntity;
	}

	@Override
	public void fromEntity(HumanItemEntity entity) {
		// TODO Auto-generated method stub
		this.setDbId(entity.getId());
		this.setCharId(entity.getCharId());
		this.setBeginTime(entity.getBeginTime());
		this.setDuration(entity.getDuration());
		this.setOverlap(entity.getOverlap());
		this.setTemplateId(entity.getTemplateId());
		this.setCreateTime(entity.getCreateTime());
		this.setUpdateTime(entity.getUpdateTime());
		this.setUseing(entity.getUseing());
		this.setFromPlayerId(entity.getFromPlayerId());
		this.setInDb(true);
		this.active();
	
	}
	
	public static Item createNewInstance(int templateId,int overlap)
	{
		
		Assert.isTrue(overlap>0,"道具数量不能是负数");
		long now = Globals.getTimeService().now();
		Item item = new Item();
		item.setOverlap(overlap) ;
		item.setDbId(Globals.getUUIDService().getNextUUID(now, UUIDType.HUMANITEMID));
		item.setTemplateId(templateId);
		item.setUpdateTime(now);
		item.setCreateTime(now);
		return item;
	}
}
