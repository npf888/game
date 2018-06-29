package com.gameserver.human.manager;

import java.util.List;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.core.uuid.UUIDType;
import com.db.model.HumanTaskEntity;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.task.HumanTask;
import com.gameserver.task.data.TaskData;
import com.gameserver.task.msg.GCTaskInfoData;
import com.gameserver.task.msg.GCTaskProgress;


/**
 * 任务管理器
 * @author 郭君伟
 *
 */
public class HumanTaskManager  implements InitializeRequired{
	/** 主人 */
	private Human owner;
	
	private HumanTask humanTask;
	
	public HumanTaskManager(Human owner){
		this.owner=owner;
	}
	
	public Human getOwner() {
		return owner;
	}
	

	public void setOwner(Human owner) {
		this.owner = owner;
	}
	
	public HumanTask getHumanTask(){
		return humanTask;
	}
	
	public void load()
	{
		HumanTaskEntity humanTaskEntity = Globals.getDaoService()
				.getHumanTaskDao().getTaskByCharId(owner.getPassportId());
		humanTask = new HumanTask(owner);
		if (humanTaskEntity == null) {
			long now = Globals.getTimeService().now();
			
			humanTask.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANTASKID));
			humanTask.setCharId(owner.getPassportId());
			humanTask.setCreateTime(Globals.getTimeService().now());
			humanTask.setInDb(false);
			humanTask.active();
			humanTask.setModified();
			return;
		}
		
		humanTask.fromEntity(humanTaskEntity);
		
		if(humanTask.getDailyTaskList().isEmpty()){
			List<TaskData> list = Globals.getTaskNewService().getTaskDataList((int)owner.getLevel());
			if(list != null){
				humanTask.setDailyTaskList(list);
				humanTask.setModified();
			}
		}
	}


	
	@Override
	public void init() {
		checkIfRefresh();
	}

	
	
	/**
	 * 更新
	 */
	public void checkIfRefresh(){
		long now = Globals.getTimeService().now();
		long updateTime = humanTask.getUpdateTime();
		long dayTime = Globals.getConfigTempl().getDaytime()*60*60*1000;
		if(now - updateTime > dayTime){
			List<TaskData> list = Globals.getTaskNewService().getTaskDataList((int)owner.getLevel());
			if(list != null){
				humanTask.setUpdateTime(now);
				humanTask.setDailyTaskList(list);
				humanTask.setTaskProgress("");
				
				humanTask.setModified();
			}
			
		}
	}
	
	/**
	 * 获取任务数据
	 * @param tId
	 * @return
	 */
	public TaskData dailyTaskDataById(int tId){
		for(TaskData taskData : humanTask.getDailyTaskList()){
			if(taskData.getTaskId() == tId){
				return taskData;
			}
		}
		return null;
	}
	
	/**
	 * 领取任务奖励
	 * @param taskData
	 */
	public void getDailyTaskReward(TaskData taskData){
		
		taskData.setGetNums(taskData.getGetNums()+1);

		humanTask.setModified();
		
		Globals.getLogService().sendDailyTaskLog(owner, LogReasons.DailyTaskLogReason.GET_DAILY_TASK_REWARD,  LogReasons.DailyTaskLogReason.FINISH_DAILY_TASK.getReasonText(), taskData.getTaskId(), taskData.getFinished(), taskData.getGetNums());

	}
	
	/**
	 * 已经领取的任务
	 * @return
	 */
	public int getTaskNum(){
	  List<TaskData> list = humanTask.getDailyTaskList();
		 int num = 0;
   	  for(TaskData taskData : list){
   		  if(taskData.getGetNums() > 0){
   			  num++;
   		  }
   	  }
   	  return num;
	}
	
      public void receive(Integer boxId) {

    	 int num = getTaskNum();

    	 //int id =  Globals.getTaskNewService().getExtraNum(num);
    	 
    	 String taskProgress = humanTask.getTaskProgress();
    	 
    	 boolean fly = true;
    	 if(taskProgress.equals("")){
    		 taskProgress = boxId.toString();
    	 }else{
    		String[] strs = taskProgress.split(",");
    		for(String s : strs){
    			if(s.equals(String.valueOf(boxId))){
    				fly = false;
    			}
    		}
    		if(fly){
    			taskProgress = taskProgress+","+boxId;
    		}
    	 }
    	 
    	 humanTask.setTaskProgress(taskProgress);
    	 humanTask.setModified();
		 this.owner.sendMessage(buildTaskProgressData(boxId,num));
		 
		 if(fly){
			 List<RandRewardData> listRew = Globals.getTaskNewService().getData(boxId);
		     CommonLogic.getInstance().giveRandReward(owner, listRew, LogReasons.GoldLogReason.DAILY_TASK, LogReasons.DiamondLogReason.DAILY_TASK, LogReasons.CharmLogReason.DAILY_TASK,LogReasons.ItemLogReason.DAILY_TASK, false);
		 }
	 }
	
    public GCTaskProgress buildTaskProgressData(int boxId,int taskNum){
    	GCTaskProgress message = new GCTaskProgress();
    	message.setBoxId(boxId);
    	message.setTaskNum(taskNum);
    	message.setTaskProcges(humanTask.getTaskProgress());
    	return message;
    }
    public GCTaskProgress buildTaskData(){
    	GCTaskProgress message = new GCTaskProgress();
    	message.setTaskNum(getTaskNum());
    	message.setTaskProcges(humanTask.getTaskProgress());
    	return message;
    }
    
    
	/**
	 * 全部通知客户端
	 * @return
	 */
	public GCTaskInfoData buildTaskInfoData(){
		GCTaskInfoData gcTaskInfoData = new GCTaskInfoData();
		gcTaskInfoData.setDailyTaskDataList(humanTask.getDailyTaskList().toArray(new TaskData[humanTask.getDailyTaskList().size()]));
		return gcTaskInfoData;
	}

	
}
