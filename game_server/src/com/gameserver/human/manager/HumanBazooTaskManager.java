package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.InitializeRequired;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanBazooTaskEntity;
import com.gameserver.bazoo.template.LiarsDiceRoomTaskTemplate;
import com.gameserver.bazootask.HumanBazooTask;
import com.gameserver.bazootask.data.BazooTaskData;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class HumanBazooTaskManager implements  InitializeRequired {
	private Human owner;
	private HumanBazooTask HumanBazooTask = null;
	public HumanBazooTaskManager(Human owner) {
		this.owner = owner;
	}
	
	
	@Override
	public void init() {
		HumanBazooTaskEntity HumanBazooTaskEntity = Globals.getDaoService().getHumanBazooTaskDao().getHumanBazooTaskByPassportId(owner.getPassportId());
		HumanBazooTask = new HumanBazooTask(owner);
		if(HumanBazooTaskEntity == null){
			HumanBazooTask.setInDb(false);
			HumanBazooTask.setDbId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.HUMANBAZOOTASK));
			HumanBazooTask.setPassportId(owner.getPassportId());
			List<LiarsDiceRoomTaskTemplate>  taskTemplateList = Globals.getHumanBazooTaskService().getTaskList();
			List<BazooTaskData> BazooTaskDataList = new ArrayList<BazooTaskData>();
			for(LiarsDiceRoomTaskTemplate template : taskTemplateList){
				BazooTaskData bazooTaskData = new BazooTaskData();
				bazooTaskData.setTaskId(template.getId());
				bazooTaskData.setFinished(0);
				bazooTaskData.setFinishTimes(0);
				if(template.getRefreshtype() == 3){
					bazooTaskData.setType(BazooTaskData.ACHIEVE_TYPE);
				}else{
					bazooTaskData.setType(BazooTaskData.TASK_TYPE);
				}
				bazooTaskData.setGetNums(0);
				BazooTaskDataList.add(bazooTaskData);
			}
			HumanBazooTask.setCreateTime(new Date());
			HumanBazooTask.setUpdateTime(new Date());
			HumanBazooTask.setTasks(BazooTaskDataList);
			HumanBazooTask.active();
			HumanBazooTask.setModified();
		}else{
			HumanBazooTask.fromEntity(HumanBazooTaskEntity);
			List<BazooTaskData> BazooTaskDataList = HumanBazooTask.getTasks();
			List<LiarsDiceRoomTaskTemplate>  taskTemplateList = Globals.getHumanBazooTaskService().getTaskList();
			for(LiarsDiceRoomTaskTemplate template : taskTemplateList){
				boolean exist =false;
				for(BazooTaskData data:BazooTaskDataList){
					if(data.getTaskId() == template.getId()){
						exist=true;
						break;
					}
				}
				if(!exist){
					BazooTaskData bazooTaskData = new BazooTaskData();
					bazooTaskData.setTaskId(template.getId());
					bazooTaskData.setFinished(0);
					bazooTaskData.setFinishTimes(0);
					if(template.getRefreshtype() == 3){
						bazooTaskData.setType(BazooTaskData.ACHIEVE_TYPE);
					}else{
						bazooTaskData.setType(BazooTaskData.TASK_TYPE);
					}
					bazooTaskData.setGetNums(0);
					BazooTaskDataList.add(bazooTaskData);
				}
			}
			
			
			Date updateTime = HumanBazooTask.getUpdateTime();
			long now = Globals.getTimeService().now();
			String ymdTime = TimeUtils.formatYMDTime(updateTime.getTime());
			String nowStr = TimeUtils.formatYMDTime(now);
			//如果不相等  说明跨天了（跨天 就要 清空 任务的数据）
			if(!nowStr.equals(ymdTime)){
				//初始化化 任务（注意是任务-每天清零，不是成就）
				for(BazooTaskData data:BazooTaskDataList){
					if(data.getType()==BazooTaskData.TASK_TYPE){//任务
						data.setFinished(0);
						data.setFinishTimes(0);
						data.setGetNums(0);
					}
				}
				HumanBazooTask.setUpdateTime(new Date());
			}
			HumanBazooTask.setModified();
		}
		
	}


	
	public String getAchieveRate(){
		HumanBazooTask HumanBazooTask =getHumanBazooTask();
		int finishNum=0;
		int totalNum=0;
		for(BazooTaskData bazooTaskData:HumanBazooTask.getTasks()){
			if(bazooTaskData.getType() == BazooTaskData.ACHIEVE_TYPE){
				if(bazooTaskData.getFinished() == BazooTaskData.FINISH_YES){
					finishNum++;
				}
				totalNum++;
			}
		}
		
		return finishNum+"/"+totalNum;
	}
	
	
	/**
	 * 获取 已经完成 但没有 领取的任务的数量
	 * @return
	 */
	public int getTaskNumber() {
		List<BazooTaskData> BazooTaskDataList = HumanBazooTask.getTasks();
		int finishNotGetNum = 0;
		for(BazooTaskData data:BazooTaskDataList){
			if(data.getFinished() == BazooTaskData.FINISH_YES && data.getGetNums() == 0){
				finishNotGetNum++;
			}
		}
		return finishNotGetNum;
	}
	
	
	public Human getOwner() {
		return owner;
	}
	public void setOwner(Human owner) {
		this.owner = owner;
	}
	public HumanBazooTask getHumanBazooTask() {
		return HumanBazooTask;
	}
	public void setHumanBazooTask(HumanBazooTask humanBazooTask) {
		HumanBazooTask = humanBazooTask;
	}




	
	




	
	
	
}
