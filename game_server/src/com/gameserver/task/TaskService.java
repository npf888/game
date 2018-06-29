package com.gameserver.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.template.TemplateService;
import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanTaskManager;
import com.gameserver.task.data.TaskData;
import com.gameserver.task.msg.GCTaskInfoDataChange;
import com.gameserver.task.template.DailyExtraTemplate;
import com.gameserver.task.template.DailyTaskTemplate;

/**
 * 
 * @author 郭君伟
 *
 */
public class TaskService implements InitializeRequired {
	
	private Map<Integer,DailyTaskTemplate> taskMap = new HashMap<Integer,DailyTaskTemplate>();
	
	private Map<Integer,Map<Integer,List<DailyTaskTemplate>>>  refreshTaskNot = new HashMap<Integer,Map<Integer,List<DailyTaskTemplate>>>();
	
	private Map<Integer,Map<Integer,List<DailyTaskTemplate>>>  refreshTask = new HashMap<Integer,Map<Integer,List<DailyTaskTemplate>>>();
	
	private Map<Integer,DailyExtraTemplate> extraMap = new HashMap<Integer,DailyExtraTemplate>();

	@Override
	public void init() {
		
		TemplateService templateService = Globals.getTemplateService();

		taskMap = templateService.getAll(DailyTaskTemplate.class);
		
		for(Entry<Integer,DailyTaskTemplate> en : taskMap.entrySet()){
			
			DailyTaskTemplate template = en.getValue();
			if(template == null){
				continue;
			}
			int clientType = template.getClientType();
			
			int refreshtype = template.getRefreshtype();
			
			int leveljudge = template.getLeveljudge();
			
			if(leveljudge == 1){
				combinationTask(refreshTask,clientType,refreshtype,template);
			}else{
				combinationTask(refreshTaskNot,clientType,refreshtype,template);
			}
			
		}
		
		extraMap = templateService.getAll(DailyExtraTemplate.class);
		
	}
	
	private void combinationTask(Map<Integer,Map<Integer,List<DailyTaskTemplate>>>  dataMap,int clientType,int refreshtype,DailyTaskTemplate template){
		Map<Integer,List<DailyTaskTemplate>> map = dataMap.get(clientType);
		if(map == null){
			map = new HashMap<Integer,List<DailyTaskTemplate>>();
			dataMap.put(clientType, map);
		}
		List<DailyTaskTemplate> list = map.get(refreshtype);
		if(list == null){
			list = new ArrayList<DailyTaskTemplate>();
			map.put(refreshtype, list);
		}
		list.add(template);
	}
	
	/**
	 * 每天获取新的随机任务
	 * @return
	 */
	public List<TaskData> getTaskDataList(int level){
		List<TaskData> list = new ArrayList<TaskData>();
		for(Entry<Integer,Map<Integer,List<DailyTaskTemplate>>> en : refreshTaskNot.entrySet()){
			    Map<Integer,List<DailyTaskTemplate>> map1 = en.getValue();
			    for(List<DailyTaskTemplate> listdata : map1.values()){
			    	int index = RandomUtil.nextInt(0,listdata.size());
			    	DailyTaskTemplate data = listdata.get(index);
			    	TaskData taskData = new TaskData();
			    	taskData.setTaskId(data.getId());
			    	taskData.setFinished(0);
			    	taskData.setGetNums(0);
			    	list.add(taskData);
			    }
		}
		
		Map<Integer,List<TaskData>> dataMap = new HashMap<Integer,List<TaskData>>();
		
		for(Entry<Integer,Map<Integer,List<DailyTaskTemplate>>> en : refreshTask.entrySet()){
			int type = en.getKey();
			int oplevel = Globals.getSlotService().getOPenLevel(type);
			if(oplevel <= level){
				
				Map<Integer,List<DailyTaskTemplate>> map1 = en.getValue();
				
				for(Entry<Integer,List<DailyTaskTemplate>> enn : map1.entrySet()){
					
					List<DailyTaskTemplate> listdata = enn.getValue();
					
					int index = RandomUtil.nextInt(0,listdata.size());
					DailyTaskTemplate data = listdata.get(index);
					TaskData taskData = new TaskData();
					taskData.setTaskId(data.getId());
					taskData.setFinished(0);
					taskData.setGetNums(0);
					
					int keyy = enn.getKey();
					
					List<TaskData> listData = dataMap.get(keyy);
					
					if(listData == null){
						listData = new ArrayList<TaskData>();
						dataMap.put(keyy, listData);
					}
					listData.add(taskData);
					
				}
			}
		}
		
		for(List<TaskData> listData : dataMap.values()){
			list.add(listData.get(RandomUtil.nextInt(0,listData.size())));
		}
		
		return list;
	}
	
	/**
	 * 1 游戏内互动
	 * 2 赠送好友金币个数 
	 * 3 进行充值次数
	 * 5 进行老虎机游戏
	 * 
	 * 6 获得3个Wild组合
	 * 7 获得bouns游戏
	 * @param human
	 * @param type r任务表的 clientType
	 */
	public void spinSlot(Human human,int type,int refresh){
		 HumanTaskManager humanTaskManager = human.getHumanTaskManager();
		 
		//获取角色当前任务列表
		List<TaskData> dailyTaskList = humanTaskManager.getHumanTask().getDailyTaskList();
		for(TaskData taskData : dailyTaskList){
			
			int taskId = taskData.getTaskId();
			int finished = taskData.getFinished();
			
			DailyTaskTemplate template = taskMap.get(taskId);
			if(template == null){
				Loggers.taskLogger.error("DailyTaskTemplate for taskId: "+taskId+" is null");
				continue;
			}
			int clientType = template.getClientType();
			
			int refreshtype = template.getRefreshtype();
			
			if(clientType == type && refreshtype == refresh){
				taskData.setFinished(finished+1);
				humanTaskManager.getHumanTask().setModified();
				sendTaskData(human,taskData);
			}
		}
	}
	
	/**
	 * 8 赢得老虎机金币
	 * @param human
	 * @param type
	 * @param refresh
	 * @param win
	 */
	public void spinSlotWin(Human human,int type,int refresh,int win){
		HumanTaskManager humanTaskManager = human.getHumanTaskManager();
		
		//获取角色当前任务列表
		List<TaskData> dailyTaskList = humanTaskManager.getHumanTask().getDailyTaskList();
		for(TaskData taskData : dailyTaskList){
			int taskId = taskData.getTaskId();
			
			int finished = taskData.getFinished();
			
			DailyTaskTemplate template = taskMap.get(taskId);
			if(template == null){
				Loggers.taskLogger.error("DailyTaskTemplate for taskId: "+taskId+" is null");
				
				continue;
			}
			int clientType = template.getClientType();
			
			int refreshtype = template.getRefreshtype();
			
			if(clientType == type && refreshtype == refresh){
				taskData.setFinished(finished+win);
				humanTaskManager.getHumanTask().setModified();
				sendTaskData(human,taskData);
			}
		}
	}
	
	/**
	 * 9 中了bigwin 调用
	 * @param human
	 * @param type
	 * @param refresh
	 * @param bigWinType
	 */
	public void slotBigWin(Human human,int type,int refresh,int bigWinType){
		 HumanTaskManager humanTaskManager = human.getHumanTaskManager();
		 //获取角色当前任务列表
		 List<TaskData> dailyTaskList = humanTaskManager.getHumanTask().getDailyTaskList();
		 for(TaskData taskData : dailyTaskList){
				int taskId = taskData.getTaskId();
				
				int finished = taskData.getFinished();
				
				DailyTaskTemplate template = taskMap.get(taskId);
				if(template == null){
					Loggers.taskLogger.error("DailyTaskTemplate for taskId: "+taskId+" is null");
					continue;
				}
				int clientType = template.getClientType();
				
				int refreshtype = template.getRefreshtype();
				
				int serverType = template.getServerType();
				
				if(clientType == type && refreshtype == refresh && bigWinType == serverType){
					taskData.setFinished(finished+1);
					humanTaskManager.getHumanTask().setModified();
					sendTaskData(human,taskData);
				}
			}
		 
	}
	/**
	 * 4 充值固定金额
	 * @param human
	 * @param type
	 * @param refresh
	 * @param bigWinType
	 */
	public void slotTopup(Human human,int type,int refresh,int pid){
		HumanTaskManager humanTaskManager = human.getHumanTaskManager();
		//获取角色当前任务列表
		List<TaskData> dailyTaskList = humanTaskManager.getHumanTask().getDailyTaskList();
		for(TaskData taskData : dailyTaskList){
			int taskId = taskData.getTaskId();
			
			DailyTaskTemplate template = taskMap.get(taskId);
			
			if(template == null){
				Loggers.taskLogger.error("DailyTaskTemplate for taskId: "+taskId+" is null");
				
				continue;
			}
			int clientType = template.getClientType();
			
			int refreshtype = template.getRefreshtype();
			
			int condition1 = template.getCondition1();
			
			if(clientType == type && refreshtype == refresh && condition1 == pid){
				taskData.setFinished(pid);
				humanTaskManager.getHumanTask().setModified();
				sendTaskData(human,taskData);
			}
		}
		
	}
	
	/**
	 * 任务变化调用
	 * @param human
	 * @param taskData
	 */
	private void sendTaskData(Human human,TaskData taskData){
		GCTaskInfoDataChange gcTaskInfoDataChange =new GCTaskInfoDataChange();
		gcTaskInfoDataChange.setDailyTaskData(taskData);
		human.sendMessage(gcTaskInfoDataChange);
	}
	
	/**
	 * 获取任务模板数据
	 * @param taskId
	 * @return
	 */
	public DailyTaskTemplate getDailyTaskTemplate(int taskId){
		return taskMap.get(taskId);
	}
	
	public List<RandRewardData> getData(int id){
		return extraMap.get(id).getRewardList();
	}

	public int getExtraNum(int num) {
		int n = 0;
		for(Entry<Integer,DailyExtraTemplate> en : extraMap.entrySet()){
			int id  = en.getKey();
			  DailyExtraTemplate data = en.getValue();
			  if(data.getFinishNum() <= num){
				  if(id > n){
					  n = id;
				  }
			  }
		}
		return n;
	}
	
	public boolean isBoxId(int boxId){
		return extraMap.containsKey(boxId);
	}
	

}
