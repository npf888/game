package com.gameserver.task;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanTaskEntity;
import com.gameserver.human.Human;
import com.gameserver.task.data.TaskData;

/**
 * 任务
 * @author wayne
 *
 */
public class HumanTask implements PersistanceObject<Long, HumanTaskEntity>{
	
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private boolean inDb;

	private List<TaskData> dailyTaskList = new ArrayList<TaskData>();
	
	private List<TaskData> taskList = new ArrayList<TaskData>();
	
	/** 更新时间 */
	private long updateTime;
	/** 创建时间 */
	private long createTime;
	
	private String taskProgress;
	
	public HumanTask(Human owner){
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner = owner;
	}
	
	
	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
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
	
	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return charId;
	}
	
	public List<TaskData> getDailyTaskList(){
		return dailyTaskList;
	}
	
	public List<TaskData> getTaskList(){
		return taskList;
	}
	
	public void setDailyTaskList(List<TaskData> dailyTaskList) {
		this.dailyTaskList = dailyTaskList;
	}
	
	

	public String getTaskProgress() {
		return taskProgress;
	}


	public void setTaskProgress(String taskProgress) {
		this.taskProgress = taskProgress;
	}


	/**
	 * 删除任务
	 * @param taskId
	 */
	public void deleteTask(int taskId){
		int size = dailyTaskList.size();
		for(int i = 0;i < size;i++){
			TaskData td = dailyTaskList.get(i);
             if(td.getTaskId() == taskId){
            	 dailyTaskList.remove(i);
				return;
			}
		}
	}
	
	/**
	 * 添加任务
	 * @param taskData
	 */
	public void addTask(TaskData taskData){
		dailyTaskList.add(taskData);
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
		return "task#"+this.id;
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
	public LifeCycle getLifeCycle() {
		// TODO Auto-generated method stub
		return lifeCycle;
	}

	@Override
	public void setModified() {
		// TODO Auto-generated method stub
		onUpdate();
	}

	@Override
	public HumanTaskEntity toEntity() {
		// TODO Auto-generated method stub
		HumanTaskEntity entity = new HumanTaskEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setDailyTaskPack(JSON.toJSONString(dailyTaskList));
		entity.setTaskPack(JSON.toJSONString(taskList));
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		entity.setTaskProgress(taskProgress);
		return entity;
	}

	@Override
	public void fromEntity(HumanTaskEntity entity) {
		// TODO Auto-generated method stub
		this.id = entity.getId();
		this.charId = entity.getCharId();
		List<TaskData> tempDailyTaskList = JSON.parseArray(entity.getDailyTaskPack(), TaskData.class);
		this.getDailyTaskList().addAll(tempDailyTaskList);
		List<TaskData> tempTaskList = JSON.parseArray(entity.getTaskPack(), TaskData.class);
		this.getTaskList().addAll(tempTaskList);
		
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.taskProgress = entity.getTaskProgress();
		this.setInDb(true);
		this.active();
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
				//long now = Globals.getTimeService().now();
				//this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

}


