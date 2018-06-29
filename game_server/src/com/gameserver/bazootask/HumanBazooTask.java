package com.gameserver.bazootask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanBazooTaskEntity;
import com.gameserver.bazoo.template.LiarsDiceRoomTaskTemplate;
import com.gameserver.bazootask.data.BazooTaskData;
import com.gameserver.human.Human;

public class HumanBazooTask implements PersistanceObject<Long, HumanBazooTaskEntity>{
	
	private Human owner;
	private boolean inDb;
	private LifeCycle lifeCycle;
	
	
	
	private long id;
	private long passportId;
	private List<BazooTaskData> tasks = new ArrayList<BazooTaskData>();
	private Date createTime;
	private Date updateTime;

	public HumanBazooTask(Human human){
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner=human;
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
		return "HumanBazooTask#"+this.id;
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
		return this.passportId;
	}
	public Human getOwner() {
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

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	

	public List<BazooTaskData> getTasks() {
		return tasks;
	}

	public void setTasks(List<BazooTaskData> tasks) {
		this.tasks = tasks;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void active(){
		getLifeCycle().activate();
	}
	
	
	@Override
	public HumanBazooTaskEntity toEntity() {
		HumanBazooTaskEntity entity = new HumanBazooTaskEntity();
		entity.setId(id);
		entity.setPassportId(passportId);
		entity.setTask(JSONArray.toJSONString(tasks));
		entity.setCreateTime(createTime);
		entity.setUpdateTime(updateTime);
		return entity;
	}

	@Override
	public void fromEntity(HumanBazooTaskEntity entity) {
		this.setId(entity.getId());
		this.setPassportId(entity.getPassportId());
		this.setTasks(JSONArray.parseArray(entity.getTask(), BazooTaskData.class));
		this.setCreateTime(entity.getCreateTime());
		this.setUpdateTime(entity.getUpdateTime());
		this.setInDb(true);
		this.active();
		
	}

	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}

	@Override
	public void setModified() {
		onUpdate();
		
	}
	
	private void onUpdate()
	{
		if (owner != null) 
		{
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	
	/**
	 * 处理tasks
	 */
	public void checkAndSetValue(LiarsDiceRoomTaskTemplate template){
		for(BazooTaskData data :this.getTasks()){
			if(data.getTaskId() == template.getId()){
				//如果任务已完成 就 不能再改了
				if(data.getFinished() == 1){
					break;
				}
				data.setFinishTimes(data.getFinishTimes()+1);
				if(data.getFinishTimes() >= template.getCondition()){
					data.setFinished(1);
				}
				break;
			}
		}
	}
	
	

}
