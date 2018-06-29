package com.gameserver.bazootask;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.db.model.HumanBazooTaskEntity;
import com.gameserver.activity.HumanActivity;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.template.LiarsDiceRoomTaskTemplate;
import com.gameserver.bazootask.data.BazooTaskData;
import com.gameserver.bazootask.data.BazooTaskInfo;
import com.gameserver.bazootask.msg.GCBazooGetReward;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.manager.HumanBazooTaskManager;
import com.gameserver.player.Player;

public class HumanBazooTaskService implements InitializeRequired {
	private Logger logger = Loggers.BAZOO;
	
	//所有的任务
	private List<LiarsDiceRoomTaskTemplate> taskList = new ArrayList<LiarsDiceRoomTaskTemplate>();
	@Override
	public void init() {
		List<LiarsDiceRoomTaskTemplate> templateList = Globals.getBazooPubService().getDiceTaskList();
		taskList.addAll(templateList);
	}
	
	
	
	/**
	 * 用户会完成那些任务
	 * @param player
	 */
	public BazooTaskInfo[] getAllTasks(Player player){
		HumanBazooTask HumanBazooTask = player.getHuman().getHumanBazooTaskManager().getHumanBazooTask();
		
		
		Map<String,List<LiarsDiceRoomTaskTemplate>> templateMap = new HashMap<String,List<LiarsDiceRoomTaskTemplate>>();
		for(int i=0;i<taskList.size();i++){
			LiarsDiceRoomTaskTemplate template = taskList.get(i);
			String key = template.getRefreshtype()+""+template.getModeType()+""+template.getWayOfPlay();
			if(templateMap.containsKey(key)){
				List<LiarsDiceRoomTaskTemplate> templateList = templateMap.get(key);
				templateList.add(template);
			}else{
				List<LiarsDiceRoomTaskTemplate> templateList = new ArrayList<LiarsDiceRoomTaskTemplate>();
				templateList.add(template);
				templateMap.put(key, templateList);
				
			}
		}
		
		List<BazooTaskInfo> BazooTaskDataList = new ArrayList<BazooTaskInfo>();
		Set<String> set = templateMap.keySet();
		for(String key:set){
			List<LiarsDiceRoomTaskTemplate> teList = templateMap.get(key);
			Collections.sort(teList, new Comparator<LiarsDiceRoomTaskTemplate>() {
				@Override
				public int compare(LiarsDiceRoomTaskTemplate o1,
						LiarsDiceRoomTaskTemplate o2) {
					if(o1.getRewardNum() > o2.getRewardNum()){
						return 1;
					}else if(o1.getRewardNum() < o2.getRewardNum()){
						return -1;
					}else{
						return 0;
					}
				}
			});
			for(LiarsDiceRoomTaskTemplate task:teList){
				boolean find =false;
				for(BazooTaskData BazooTaskData:HumanBazooTask.getTasks()){
					if(task.getId() == BazooTaskData.getTaskId()){
						//如果是未完成   就是他了              如果是已将完成 未领奖  就是他了
						if(BazooTaskData.getFinished() == 0 || (BazooTaskData.getFinished() == 1 && BazooTaskData.getGetNums() == 0)){
							BazooTaskInfo BazooTaskInfo = getBazooTaskInfo(task,BazooTaskData);
							BazooTaskDataList.add(BazooTaskInfo);
							find=true;
							break;
						}
					}
				}
				//如果找到了 
				if(find){
					break;
				}
			}
		}
		
		BazooTaskInfo[] BazooTaskInfoArr = new BazooTaskInfo[BazooTaskDataList.size()];
		for(int i=0;i<BazooTaskDataList.size();i++){
			BazooTaskInfoArr[i]=BazooTaskDataList.get(i);
		}
		
		return BazooTaskInfoArr;
	}
	
	
	private BazooTaskInfo getBazooTaskInfo(LiarsDiceRoomTaskTemplate task,BazooTaskData BazooTaskData){
		BazooTaskInfo BazooTaskInfo = new BazooTaskInfo();
		BazooTaskInfo.setTaskId(task.getId());
		BazooTaskInfo.setRefreshtype(task.getRefreshtype());
		BazooTaskInfo.setModeType(task.getModeType());
		BazooTaskInfo.setWayOfPlay(task.getWayOfPlay());
		BazooTaskInfo.setCondition(task.getCondition());
		BazooTaskInfo.setRewardNum(task.getRewardNum());
		BazooTaskInfo.setNameId(task.getNameId());
		BazooTaskInfo.setDescrip(task.getDescrip());
		BazooTaskInfo.setIcon(task.getIcons());
		BazooTaskInfo.setBigType(task.getBigType());
		if(BazooTaskData != null){
			BazooTaskInfo.setFinishTimes(BazooTaskData.getFinishTimes());
			BazooTaskInfo.setIsFinish(BazooTaskData.getFinished());//已完成
		}
		return BazooTaskInfo;
	}
	
	
	public void inviteFriend(Player player){
		HumanBazooTaskManager manager = player.getHuman().getHumanBazooTaskManager();
		HumanBazooTask humanBazooTask= manager.getHumanBazooTask();
		for(int i=0;i<taskList.size();i++){
			LiarsDiceRoomTaskTemplate template = taskList.get(i);
			if(template.getWayOfPlay() == 7){//邀请好友的不在这里 处理
				humanBazooTask.checkAndSetValue(template);
				continue;
			}
		}
	}
	
	/**
	 * 用户会完成那些任务
	 * @param player
	 */
	public void finishTask(Player player){
		HumanBazooTaskManager manager = player.getHuman().getHumanBazooTaskManager();
//		HumanBazooWins maxHumanBazooWins = player.getHuman().getHumanBazooWinsManager().getMaxHumanBazooWins();
		RoomEveryUserInfo RoomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		HumanBazooTask humanBazooTask= manager.getHumanBazooTask();
		int modeType = RoomNumber.toRoomNumber(player.getHuman().getBazooRoom()).getModeType();
		for(int i=0;i<taskList.size();i++){
			LiarsDiceRoomTaskTemplate template = taskList.get(i);
			if(template.getWayOfPlay() == 7){//邀请好友的不在这里 处理
				continue;
			}
			if(template.getModeType() !=0 && template.getModeType()  != modeType){
				continue;
			}
			//任务
			if(template.getBigType() == 0){
				//只要玩 了 就算的
				if(template.getWayOfPlay() == 1){
					humanBazooTask.checkAndSetValue(template);
					continue;
				}
			//成就
			}else{
				//所有模式  局数 或者 是钱数
				if(template.getWayOfPlay() == 4 || template.getWayOfPlay() ==5){
					humanBazooTask.checkAndSetValue(template);
					continue;
				}
			}
			if(template.getModeType() == RoomNumber.MODE_TYPE_CLASSICAL){
					//每天刷新  和  每周刷新的 都一样 在用户进入 系统时 才处理 
					//2：必须赢了才算
					if(template.getWayOfPlay() == 2){
						//如果赢了 才算
						boolean isRight = player.getHuman().getBazooRoomEveryUserInfo().getClassicalUserInfo().isRight();
						if(isRight){
							humanBazooTask.checkAndSetValue(template);
						}
					}else if(template.getWayOfPlay() == 3){//必须连胜 才可以
						if(RoomEveryUserInfo.getWinTimes() > 0){
							humanBazooTask.checkAndSetValue(template);
						}
					}
			}else if(template.getModeType() == RoomNumber.MODE_TYPE_COW){
				  //2：必须赢了才算
					if(template.getWayOfPlay() == 2){
						//如果赢了 才算
						boolean isRight = player.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().isRight();
						if(isRight){
							humanBazooTask.checkAndSetValue(template);
						}
					}else if(template.getWayOfPlay() == 3){//必须连胜 才可以
						if(RoomEveryUserInfo.getWinTimes() > 0){
							humanBazooTask.checkAndSetValue(template);
						}
					}
			}else if(template.getModeType() == RoomNumber.MODE_TYPE_SHOWHAND){
				 //2：必须赢了才算
				if(template.getWayOfPlay() == 2){
					//如果赢了 才算
					long money = player.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getMoney();
					if(money>0){
						humanBazooTask.checkAndSetValue(template);
					}
				}else if(template.getWayOfPlay() == 3){//必须连胜 才可以
					if(RoomEveryUserInfo.getWinTimes() > 0){
						humanBazooTask.checkAndSetValue(template);
					}
				}
				
			}
			
				
		}
	}


	/**
	 * 如果 连胜 中断了 那么 清空 task中的数据（已完成的不用清除）
	 */
	public void clearAWinningStreak(Player player,int modeType){
		HumanBazooTask  task=player.getHuman().getHumanBazooTaskManager().getHumanBazooTask();
		List<LiarsDiceRoomTaskTemplate> templateList =getTemplateByType(3);
		for(LiarsDiceRoomTaskTemplate template:templateList){
			for(BazooTaskData data:task.getTasks()){
				if(template.getId() == data.getTaskId()&&data.getFinished() == 0&&data.getFinishTimes() >0){
					data.setFinishTimes(0);
					break;
				}
			}
		}
		task.setModified();
	}

	
	
	private List<LiarsDiceRoomTaskTemplate> getTemplateByType(int wayoftype){
		List<LiarsDiceRoomTaskTemplate> templateList = new ArrayList<LiarsDiceRoomTaskTemplate>();
		for(LiarsDiceRoomTaskTemplate template :taskList){
			if(template.getWayOfPlay() == wayoftype){
				templateList.add(template);
			}
		}
		
		return templateList;
	}
	private List<LiarsDiceRoomTaskTemplate> getTemplateByRefreshType(int refreshType){
		List<LiarsDiceRoomTaskTemplate> templateList = new ArrayList<LiarsDiceRoomTaskTemplate>();
		for(LiarsDiceRoomTaskTemplate template :taskList){
			if(template.getRefreshtype() == refreshType){
				templateList.add(template);
			}
		}
		
		return templateList;
	}
	private LiarsDiceRoomTaskTemplate getTemplateById(int id){
		List<LiarsDiceRoomTaskTemplate> templateList = new ArrayList<LiarsDiceRoomTaskTemplate>();
		for(LiarsDiceRoomTaskTemplate template :taskList){
			if(template.getId() == id){
				return template;
			}
		}
		return null;
	}
	public List<LiarsDiceRoomTaskTemplate> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<LiarsDiceRoomTaskTemplate> taskList) {
		this.taskList = taskList;
	}



	/**
	 * 获取所有的成就任务
	 * @param taskId 
	 * @param player
	 * @return
	 */
	public void getReward(Player player,int taskId){
		
		LiarsDiceRoomTaskTemplate template =	getTemplateById(taskId);
		long rewardNum = template.getRewardNum();
		RoomNumber roomNumber =  RoomNumber.toRoomNumber(player.getHuman().getBazooRoom());
		HumanBazooTask HumanBazooTask = player.getHuman().getHumanBazooTaskManager().getHumanBazooTask();
		List<BazooTaskData>  tasks = HumanBazooTask.getTasks();
		for(BazooTaskData data:tasks){
			if(data.getTaskId() == taskId){
				if(data.getFinished() == BazooTaskData.FINISH_NO){
					logger.info("[无双吹牛"+roomNumber.toString()+"]---[每日任务 领奖]---[taskId:"+taskId+"]---[任务未完成]");
					return;
				}
				
				if(data.getGetNums() >= 1){//已经领取过了
					logger.info("[无双吹牛"+roomNumber.toString()+"]---[每日任务 领奖]---[taskId:"+taskId+"]---[已经领取过]");
					return;
				}
				
				//最后就是通过
				data.setGetNums(data.getGetNums()+1);	
				
			}
		}
		HumanBazooTask.setModified();
		String lostDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_BAZOO_TASK.getReasonText(), taskId);
		player.getHuman().giveMoney(rewardNum, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BAZOO_TASK, lostDetailReason, -1, 1);
		
		
		/**
		 * 刷新 消息
		 */
		GCBazooGetReward GCBazooGetReward = new GCBazooGetReward();
		BazooTaskInfo[] info = Globals.getHumanBazooTaskService().getAllTasks(player);
		GCBazooGetReward.setBazooTaskInfo(info);
		player.sendMessage(GCBazooGetReward);
	}
	
	/**
	 * 获取所有的成就任务
	 * @param player
	 * @return
	 */
	public BazooTaskInfo[] getAllAchieveTasks(Player player) {
		/**
		 * 用户会完成那些任务
		 * @param player
		 */
		HumanBazooTask HumanBazooTask = player.getHuman().getHumanBazooTaskManager().getHumanBazooTask();
		List<LiarsDiceRoomTaskTemplate> templateList = getTemplateByRefreshType(3);
		BazooTaskInfo[] BazooTaskInfoArr = new BazooTaskInfo[templateList.size()];
		for(int i=0;i<templateList.size();i++){
			LiarsDiceRoomTaskTemplate template = templateList.get(i);
			BazooTaskInfo BazooTaskInfo= this.getBazooTaskInfo(template,null);
			for(BazooTaskData BazooTaskData:HumanBazooTask.getTasks()){
				if(BazooTaskData.getTaskId() == template.getId()){
					BazooTaskInfo.setFinishTimes(BazooTaskData.getFinishTimes());
					BazooTaskInfo.setIsFinish(BazooTaskData.getFinished());//已完成
					break;
				}
			}
			BazooTaskInfoArr[i]=BazooTaskInfo;
			
		}
		return BazooTaskInfoArr;
	}



	/**
	 * 从数据库里直接查询 achieveRate
	 */
	
	public String getAchieveRate(long passportId){
		HumanBazooTaskEntity HumanBazooTaskEntity = Globals.getDaoService().getHumanBazooTaskDao().getHumanBazooTaskByPassportId(passportId);
		List<BazooTaskData> tasks = new ArrayList<BazooTaskData>();
		tasks.addAll(JSONArray.parseArray(HumanBazooTaskEntity.getTask(), BazooTaskData.class));
		int finishNum=0;
		int totalNum=0;
		for(BazooTaskData bazooTaskData:tasks){
			if(bazooTaskData.getType() == BazooTaskData.ACHIEVE_TYPE){
				if(bazooTaskData.getFinished() == BazooTaskData.FINISH_YES){
					finishNum++;
				}
				totalNum++;
			}
		}
		return  finishNum+"/"+totalNum;
	}
	
	
}
