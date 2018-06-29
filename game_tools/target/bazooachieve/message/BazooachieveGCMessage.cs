using System.Collections;

public class BazooachieveGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_BAZOO_ACHIEVE,GC_BAZOO_ACHIEVE);
	}
 
  	/**
	 * 获取 成就 返回
	 * @param bazooAchieveInfo 任务列表
	 */
	public void GC_BAZOO_ACHIEVE(InputMessage data) 
	{
		int i,size;
		ArrayList bazooAchieveInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BazooAchieveInfo bazooAchieveInfo_Datas= new BazooAchieveInfo();
			bazooAchieveInfo_Datas.bigtype = data.GetInt();//大的分类：1：胜利者，2：实践家，3：资本家
			bazooAchieveInfo_Datas.modeType = data.GetInt();//吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈
			bazooAchieveInfo_Datas.wayOfPlay = data.GetInt();//小的分类：1：胜场数，2：连胜数，3：玩的局数，4：钱数，5：段位数
			bazooAchieveInfo_Datas.condition = data.GetInt();//应该满足的 条件
			bazooAchieveInfo_Datas.rewardNum = data.GetInt();//应当给予的奖励
			bazooAchieveInfo_Datas.finishVlaues = data.GetInt();//用户完成次数 或者 金币
			bazooAchieveInfo_Datas.isFinish = data.GetInt();//是否完成 0:未完成，1:已完成
			bazooAchieveInfo_Datas.nameId = data.GetInt();//名称ID
			bazooAchieveInfo_Datas.descrip = data.GetInt();//描述ID
			bazooAchieveInfo_Datas.icon = data.GetString();//图标
			bazooAchieveInfo.Add(bazooAchieveInfo_Datas);
		}
		BazooachieveHandler.Instance().GC_BAZOO_ACHIEVE(bazooAchieveInfo);
	}
}