package com.gameserver.bazootask.handler;

import com.gameserver.bazoo.msg.GCBazooHallStatus;
import com.gameserver.bazootask.data.BazooTaskInfo;
import com.gameserver.bazootask.msg.CGBazooAchieveTask;
import com.gameserver.bazootask.msg.CGBazooGetReward;
import com.gameserver.bazootask.msg.CGBazooTask;
import com.gameserver.bazootask.msg.GCBazooAchieveTask;
import com.gameserver.bazootask.msg.GCBazooTask;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

public class BazootaskMessageHandler {

	public void handleBazooTask(Player player, CGBazooTask cgBazooTask) {
		BazooTaskInfo[] BazooTaskInfoArr  = Globals.getHumanBazooTaskService().getAllTasks(player);
		GCBazooTask GCBazooTask = new GCBazooTask();
		GCBazooTask.setBazooTaskInfo(BazooTaskInfoArr);
		player.sendMessage(GCBazooTask);
	}

	/**
	 * 领取金币
	 * @param player
	 * @param cgBazooGetReward
	 */
	public void handleBazooGetReward(Player player,
			CGBazooGetReward cgBazooGetReward) {
		int taskId = cgBazooGetReward.getTaskId();
		Globals.getHumanBazooTaskService().getReward(player,taskId);
		
		GCBazooHallStatus GCBazooHallStatus = player.getHuman().getHumanBazooManager().getStatus();
		player.sendMessage(GCBazooHallStatus);
	}

	
	/**
	 * 获取所有的成就任务
	 * @param player
	 * @param cgBazooGetReward
	 */
	public void handleBazooAchieveTask(Player player,
			CGBazooAchieveTask cgBazooAchieveTask) {
		BazooTaskInfo[] BazooTaskInfoArr = Globals.getHumanBazooTaskService().getAllAchieveTasks(player);
		GCBazooAchieveTask GCBazooAchieveTask = new GCBazooAchieveTask();
		GCBazooAchieveTask.setBazooTaskInfo(BazooTaskInfoArr);
		player.sendMessage(GCBazooAchieveTask);
	}

}
