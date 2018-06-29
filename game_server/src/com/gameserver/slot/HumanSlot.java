package com.gameserver.slot;


import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanSlotEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;


/**
 * 数据库映射类角色关联的
 * @author wayne
 *
 */
public class HumanSlot implements PersistanceObject<Long, HumanSlotEntity>{

	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;//唯一ID
	private long charId;//角色ID
	private long slotId;//老虎机id
	private long totalBet;//总共下注 永远累计
	private long totalRefund;//总共返还 永久累计
	private long totalJackpot;//总彩金
	private String slotType23Jackpot;//东方龙老虎机 的每个人的 奖池信息（奖池信息一直在变化），这个字段 只有东方龙老虎机专用 ; 这个字段包括5个奖池，每个奖池用 "," 分割
	private long updateTime;
	private long createTime;
	private boolean inDb;
	
	public HumanSlot()
	{
		this.lifeCycle = new LifeCycleImpl(this);
	}


	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}
	
	@Override
	public LifeCycle getLifeCycle() {
	
		return lifeCycle;
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





	/**
	 * @return the slotId
	 */
	public long getSlotId() {
		return slotId;
	}


	/**
	 * @param slotId the slotId to set
	 */
	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}


	/**
	 * @return the totalBet
	 */
	public long getTotalBet() {
		return totalBet;
	}


	/**
	 * @param totalBet the totalBet to set
	 */
	public void setTotalBet(long totalBet) {
		this.totalBet = totalBet;
	}


	/**
	 * @return the totalRefund
	 */
	public long getTotalRefund() {
		return totalRefund;
	}


	/**
	 * @param totalRefund the totalRefund to set
	 */
	public void setTotalRefund(long totalRefund) {
		this.totalRefund = totalRefund;
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
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public Long getDbId() {
	
		return this.id;
	}

	@Override
	public String getGUID() {

		return "humanslot#"+this.id;
	}

	@Override
	public boolean isInDb() {
	
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		
		this.inDb = inDb;
	}


	public long getCharId() {
	
		return charId;
	}

	
	public long getTotalJackpot() {
		return totalJackpot;
	}


	public void setTotalJackpot(long totalJackpot) {
		this.totalJackpot = totalJackpot;
	}


	public String getSlotType23Jackpot() {
		return slotType23Jackpot;
	}


	public void setSlotType23Jackpot(String slotType23Jackpot) {
		this.slotType23Jackpot = slotType23Jackpot;
	}


	@Override
	public HumanSlotEntity toEntity()
	{
		HumanSlotEntity entity = new HumanSlotEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setSlotId(getSlotId());
		entity.setTotalBet(getTotalBet());
		entity.setTotalRefund(this.getTotalRefund());
		entity.setSlotType23Jackpot(slotType23Jackpot);
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		entity.setTotalJackpot(totalJackpot);
		return entity;
	}

	@Override
	public void fromEntity(HumanSlotEntity entity)
	{
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.slotId = entity.getSlotId();
		this.totalBet = entity.getTotalBet();
		this.totalRefund = entity.getTotalRefund();
		this.totalJackpot = entity.getTotalJackpot();
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
	    this.setSlotType23Jackpot(entity.getSlotType23Jackpot());
		this.setInDb(true);
		this.active();
	}

	@Override
	public void setModified()
	{
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
