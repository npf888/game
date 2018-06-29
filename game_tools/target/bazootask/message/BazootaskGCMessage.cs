using System.Collections;

public class BazootaskGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_BAZOO_TASK,GC_BAZOO_TASK);
		register(NetMessageType.GC_BAZOO_ACHIEVE_TASK,GC_BAZOO_ACHIEVE_TASK);
		register(NetMessageType.GC_BAZOO_GET_REWARD,GC_BAZOO_GET_REWARD);
	}
 
  	/**
	 * 签到返回
	 * @param bazooTaskInfo 任务列表
	 */
	public void GC_BAZOO_TASK(InputMessage data) 
	{
		int i,size;
		ArrayList bazooTaskInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BazooTaskInfo bazooTaskInfo_Datas= new BazooTaskInfo();
			bazooTaskInfo_Datas.taskId = data.GetInt();//任务ID
			bazooTaskInfo_Datas.refreshtype = data.GetInt();//刷新类型  按天:1、按周:2、按月 等等
			bazooTaskInfo_Datas.modeType = data.GetInt();//吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈
			bazooTaskInfo_Datas.bigType = data.GetInt();//大的分类：0:任务，成就：1：胜利者，2：实践家，3：资本家
			bazooTaskInfo_Datas.wayOfPlay = data.GetInt();//玩的方式：1：只要玩了就算，2：必须赢了才算，3:连胜 
			bazooTaskInfo_Datas.condition = data.GetInt();//应该满足的 条件
			bazooTaskInfo_Datas.rewardNum = data.GetInt();//应当给予的奖励
			bazooTaskInfo_Datas.finishTimes = data.GetInt();//用户完成次数
			bazooTaskInfo_Datas.isFinish = data.GetInt();//是否完成 0:未完成，1:已完成
			bazooTaskInfo_Datas.nameId = data.GetInt();//名称ID
			bazooTaskInfo_Datas.descrip = data.GetInt();//描述ID
			bazooTaskInfo_Datas.icon = data.GetString();//图标
			bazooTaskInfo.Add(bazooTaskInfo_Datas);
		}
		BazootaskHandler.Instance().GC_BAZOO_TASK(bazooTaskInfo);
	}
 
  	/**
	 * 签到返回  所有的成就任务
	 * @param bazooTaskInfo 任务列表
	 */
	public void GC_BAZOO_ACHIEVE_TASK(InputMessage data) 
	{
		int i,size;
		ArrayList bazooTaskInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BazooTaskInfo bazooTaskInfo_Datas= new BazooTaskInfo();
			bazooTaskInfo_Datas.taskId = data.GetInt();//任务ID
			bazooTaskInfo_Datas.refreshtype = data.GetInt();//刷新类型  按天:1、按周:2、按月 等等
			bazooTaskInfo_Datas.modeType = data.GetInt();//吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈
			bazooTaskInfo_Datas.bigType = data.GetInt();//大的分类：0:任务，成就：1：胜利者，2：实践家，3：资本家
			bazooTaskInfo_Datas.wayOfPlay = data.GetInt();//玩的方式：1：只要玩了就算，2：必须赢了才算，3:连胜 
			bazooTaskInfo_Datas.condition = data.GetInt();//应该满足的 条件
			bazooTaskInfo_Datas.rewardNum = data.GetInt();//应当给予的奖励
			bazooTaskInfo_Datas.finishTimes = data.GetInt();//用户完成次数
			bazooTaskInfo_Datas.isFinish = data.GetInt();//是否完成 0:未完成，1:已完成
			bazooTaskInfo_Datas.nameId = data.GetInt();//名称ID
			bazooTaskInfo_Datas.descrip = data.GetInt();//描述ID
			bazooTaskInfo_Datas.icon = data.GetString();//图标
			bazooTaskInfo.Add(bazooTaskInfo_Datas);
		}
		BazootaskHandler.Instance().GC_BAZOO_ACHIEVE_TASK(bazooTaskInfo);
	}
 
  	/**
	 * 刷新所有的 task列表
	 * @param bazooTaskInfo 任务列表
	 */
	public void GC_BAZOO_GET_REWARD(InputMessage data) 
	{
		int i,size;
		ArrayList bazooTaskInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BazooTaskInfo bazooTaskInfo_Datas= new BazooTaskInfo();
			bazooTaskInfo_Datas.taskId = data.GetInt();//任务ID
			bazooTaskInfo_Datas.refreshtype = data.GetInt();//刷新类型  按天:1、按周:2、按月 等等
			bazooTaskInfo_Datas.modeType = data.GetInt();//吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈
			bazooTaskInfo_Datas.bigType = data.GetInt();//大的分类：0:任务，成就：1：胜利者，2：实践家，3：资本家
			bazooTaskInfo_Datas.wayOfPlay = data.GetInt();//玩的方式：1：只要玩了就算，2：必须赢了才算，3:连胜 
			bazooTaskInfo_Datas.condition = data.GetInt();//应该满足的 条件
			bazooTaskInfo_Datas.rewardNum = data.GetInt();//应当给予的奖励
			bazooTaskInfo_Datas.finishTimes = data.GetInt();//用户完成次数
			bazooTaskInfo_Datas.isFinish = data.GetInt();//是否完成 0:未完成，1:已完成
			bazooTaskInfo_Datas.nameId = data.GetInt();//名称ID
			bazooTaskInfo_Datas.descrip = data.GetInt();//描述ID
			bazooTaskInfo_Datas.icon = data.GetString();//图标
			bazooTaskInfo.Add(bazooTaskInfo_Datas);
		}
		BazootaskHandler.Instance().GC_BAZOO_GET_REWARD(bazooTaskInfo);
	}
}