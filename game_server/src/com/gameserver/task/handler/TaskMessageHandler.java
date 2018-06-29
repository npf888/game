package com.gameserver.task.handler;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanTaskManager;
import com.gameserver.player.Player;
import com.gameserver.task.data.TaskData;
import com.gameserver.task.msg.CGDailyTaskGet;
import com.gameserver.task.msg.CGTaskProgress;
import com.gameserver.task.msg.GCDailyTaskGet;
import com.gameserver.task.msg.GCTaskInfoData;
import com.gameserver.task.template.DailyTaskTemplate;


/**
 * 任务处理器
 * @author wayne
 *
 */
public class TaskMessageHandler {
	
	private Logger logger = Loggers.taskLogger;

	public void handleDailyTaskGet(Player player, CGDailyTaskGet cgDailyTaskGet) {
		
		Human human = player.getHuman();
		
		int taskId = cgDailyTaskGet.getTId();
		
		//判断是否有此任务奖励
		DailyTaskTemplate dailyTaskTemplate = Globals.getTaskNewService().getDailyTaskTemplate(taskId);
		
		if(dailyTaskTemplate == null)
		{
			logger.warn("玩家["+human.getPassportId()+"]领取了不存在的任务奖励["+cgDailyTaskGet.getTId()+"]");
			return;
		}
		
		HumanTaskManager humanTaskManager = human.getHumanTaskManager();
		
		
		TaskData taskData = humanTaskManager.dailyTaskDataById(taskId);
		
		if(taskData == null){
			logger.warn("玩家["+human.getPassportId()+"]领取的任务["+cgDailyTaskGet.getTId()+"] 不存在");
			return;
		}
		//判断是否被领取了
		if(taskData.getGetNums() >=1){
			logger.warn("玩家["+human.getPassportId()+"]已经领取了每日任务奖励["+cgDailyTaskGet.getTId()+"]");
			return;
		}
		
		//判断条件是否足够
		if(taskData.getFinished() < dailyTaskTemplate.getCondition1()){
			logger.warn("玩家["+human.getPassportId()+"]已经领取了每日任务奖励["+cgDailyTaskGet.getTId()+"]但是完成度不足,完成度["+taskData.getFinished()+"]");
			return;
		}
		
		humanTaskManager.getDailyTaskReward(taskData);
		
		CommonLogic.getInstance().giveRandReward(human, dailyTaskTemplate.getRewardList(), LogReasons.GoldLogReason.DAILY_TASK, LogReasons.DiamondLogReason.DAILY_TASK, LogReasons.CharmLogReason.DAILY_TASK,LogReasons.ItemLogReason.DAILY_TASK, false);
		
		GCDailyTaskGet gcDailyTaskGet = new GCDailyTaskGet();
		gcDailyTaskGet.setTId(cgDailyTaskGet.getTId());
		gcDailyTaskGet.setRandRewardList(dailyTaskTemplate.getRewardList().toArray(new RandRewardData[dailyTaskTemplate.getRewardList().size()]));
		player.sendMessage(gcDailyTaskGet);
		
		GCTaskInfoData gcTaskInfoData= humanTaskManager.buildTaskInfoData();
		player.sendMessage(gcTaskInfoData);
	}

	public void handleTaskProgress(Player player, CGTaskProgress cgTaskProgress) {
		
		Human human = player.getHuman();
		
		int boxId = cgTaskProgress.getBoxId();
		
		if(Globals.getTaskNewService().isBoxId(boxId)){
			human.getHumanTaskManager().receive(boxId);
		}else{
			logger.error("角色ID 【"+human.getPassportId()+"】请求的任务宝箱ID "+boxId+"  不存在");
		}
		
		
		
		
	}

}
