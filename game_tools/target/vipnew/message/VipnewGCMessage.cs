using System.Collections;

public class VipnewGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_VIP_NEW_DATA,GC_VIP_NEW_DATA);
	}
 
  	/**
	 * vip数据
	 * @param humanVipNewInfoData vip数据
	 */
	public void GC_VIP_NEW_DATA(InputMessage data) 
	{
		HumanVipNewInfoData humanVipNewInfoData = new HumanVipNewInfoData();
		humanVipNewInfoData.vipLevel = data.GetInt();//vip等级
		humanVipNewInfoData.vipPoint = data.GetInt();//当前vip点
		VipnewHandler.Instance().GC_VIP_NEW_DATA(humanVipNewInfoData);
	}
}