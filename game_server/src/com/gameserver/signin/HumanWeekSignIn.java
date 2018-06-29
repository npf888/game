package com.gameserver.signin;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanWeekSignInEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 签到
 * @author Thinker
 *
 */
public class HumanWeekSignIn implements PersistanceObject<Long, HumanWeekSignInEntity>
{
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	/**签到列表   2017-5-8 修改为登陆天数*/
	private List<Integer> signInList = new ArrayList<Integer>();

	private long updateTime;
	private long createTime;
	private boolean inDb;
	
	public HumanWeekSignIn()
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


	public List<Integer> getSignInList() {
		return signInList;
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

		return "weeksignin#"+this.id;
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

	@Override
	public HumanWeekSignInEntity toEntity()
	{
		HumanWeekSignInEntity entity = new HumanWeekSignInEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setSignInPack(JSON.toJSONString(getSignInList()));
		
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		
		return entity;
	}

	@Override
	public void fromEntity(HumanWeekSignInEntity entity)
	{
		this.id = entity.getId();
		this.charId = entity.getCharId();
		List<Integer> tempSignInDataList =JSON.parseArray(entity.getSignInPack(), Integer.class);
		this.getSignInList().addAll(tempSignInDataList);
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
	
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

