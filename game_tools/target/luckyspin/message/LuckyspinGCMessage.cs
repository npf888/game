using System.Collections;

public class LuckyspinGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_LUCKY_SPIN,GC_LUCKY_SPIN);
		register(NetMessageType.GC_LUCKY_SPIN_INFO_DATA,GC_LUCKY_SPIN_INFO_DATA);
		register(NetMessageType.GC_LUCKY_MATCH,GC_LUCKY_MATCH);
		register(NetMessageType.GC_BIG_ZHUANPAN,GC_BIG_ZHUANPAN);
		register(NetMessageType.GC_SPIN_ZHUANPAN_FREE,GC_SPIN_ZHUANPAN_FREE);
		register(NetMessageType.GC_SPIN_ZHUANPAN_NOFREE,GC_SPIN_ZHUANPAN_NOFREE);
	}
 
  	/**
	 * 转盘
	 * @param pos 位置
	 */
	public void GC_LUCKY_SPIN(InputMessage data) 
	{
		int pos = data.GetInt();		
		LuckyspinHandler.Instance().GC_LUCKY_SPIN(pos);
	}
 
  	/**
	 * 转盘
	 * @param humanLuckySpinData 内容
	 */
	public void GC_LUCKY_SPIN_INFO_DATA(InputMessage data) 
	{
		HumanLuckySpinData humanLuckySpinData = new HumanLuckySpinData();
		humanLuckySpinData.lastSpinTime = data.GetLong();//上次转的时间
		LuckyspinHandler.Instance().GC_LUCKY_SPIN_INFO_DATA(humanLuckySpinData);
	}
 
  	/**
	 * 匹配
	 * @param pos 位置
	 */
	public void GC_LUCKY_MATCH(InputMessage data) 
	{
		int pos = data.GetInt();		
		LuckyspinHandler.Instance().GC_LUCKY_MATCH(pos);
	}
 
  	/**
	 * 请求大转盘数据
	 * @param isFree 是否有免费转动 1 有免费转动 0 没有
	 * @param loginPoint 登陆进度
	 */
	public void GC_BIG_ZHUANPAN(InputMessage data) 
	{
		int isFree = data.GetInt();		
		int loginPoint = data.GetInt();		
		LuckyspinHandler.Instance().GC_BIG_ZHUANPAN(isFree,loginPoint);
	}
 
  	/**
	 * 免费返回
	 * @param point 转盘停留位置
	 * @param loginPoint 登陆进度
	 * @param dailyReward 每日奖励
	 * @param vipReward VIP奖励
	 * @param friendReward 好友奖励
	 * @param levelReward 等级奖励
	 */
	public void GC_SPIN_ZHUANPAN_FREE(InputMessage data) 
	{
		int point = data.GetInt();		
		int loginPoint = data.GetInt();		
		int dailyReward = data.GetInt();		
		int vipReward = data.GetInt();		
		int friendReward = data.GetInt();		
		int levelReward = data.GetInt();		
		LuckyspinHandler.Instance().GC_SPIN_ZHUANPAN_FREE(point,loginPoint,dailyReward,vipReward,friendReward,levelReward);
	}
 
  	/**
	 * 还钱返回
	 * @param isFree 是否有免费转动 1 有免费转动 0 没有
	 * @param point 转盘停留位置
	 */
	public void GC_SPIN_ZHUANPAN_NOFREE(InputMessage data) 
	{
		int isFree = data.GetInt();		
		int point = data.GetInt();		
		LuckyspinHandler.Instance().GC_SPIN_ZHUANPAN_NOFREE(isFree,point);
	}
}