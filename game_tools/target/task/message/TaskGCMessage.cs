using System.Collections;

public class TaskGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_DAILY_TASK_GET,GC_DAILY_TASK_GET);
		register(NetMessageType.GC_TASK_INFO_DATA,GC_TASK_INFO_DATA);
		register(NetMessageType.GC_TASK_INFO_DATA_CHANGE,GC_TASK_INFO_DATA_CHANGE);
		register(NetMessageType.GC_TASK_PROGRESS,GC_TASK_PROGRESS);
	}
 
  	/**
	 * 领取奖励
	 * @param tId 任务id
	 * @param randRewardList 道具奖励
	 */
	public void GC_DAILY_TASK_GET(InputMessage data) 
	{
		int i,size;
		int tId = data.GetInt();		
		ArrayList randRewardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			RandRewardData randRewardList_Datas= new RandRewardData();
			randRewardList_Datas.rewardId = data.GetInt();//奖励id
			randRewardList_Datas.rewardCount = data.GetInt();//奖励数量
			randRewardList.Add(randRewardList_Datas);
		}
		TaskHandler.Instance().GC_DAILY_TASK_GET(tId,randRewardList);
	}
 
  	/**
	 * 任务
	 * @param dailyTaskDataList 任务信息
	 */
	public void GC_TASK_INFO_DATA(InputMessage data) 
	{
		int i,size;
		ArrayList dailyTaskDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			TaskData dailyTaskDataList_Datas= new TaskData();
			dailyTaskDataList_Datas.taskId = data.GetInt();//vip领取时间
			dailyTaskDataList_Datas.finished = data.GetInt();//完成数
			dailyTaskDataList_Datas.getNums = data.GetInt();//领取次数
			dailyTaskDataList.Add(dailyTaskDataList_Datas);
		}
		TaskHandler.Instance().GC_TASK_INFO_DATA(dailyTaskDataList);
	}
 
  	/**
	 * 任务改变
	 * @param dailyTaskData 任务信息
	 */
	public void GC_TASK_INFO_DATA_CHANGE(InputMessage data) 
	{
		TaskData dailyTaskData = new TaskData();
		dailyTaskData.taskId = data.GetInt();//vip领取时间
		dailyTaskData.finished = data.GetInt();//完成数
		dailyTaskData.getNums = data.GetInt();//领取次数
		TaskHandler.Instance().GC_TASK_INFO_DATA_CHANGE(dailyTaskData);
	}
 
  	/**
	 * 领取任务进度奖励返回
	 * @param boxId 宝箱ID
	 * @param taskNum 已完成任务个数
	 * @param taskProcges 已经领取奖励ID逗号隔开
	 */
	public void GC_TASK_PROGRESS(InputMessage data) 
	{
		int boxId = data.GetInt();		
		int taskNum = data.GetInt();		
		string taskProcges = data.GetString();		
		TaskHandler.Instance().GC_TASK_PROGRESS(boxId,taskNum,taskProcges);
	}
}