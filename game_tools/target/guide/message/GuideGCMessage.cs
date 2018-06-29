using System.Collections;

public class GuideGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_PAY_GUIDE,GC_PAY_GUIDE);
	}
 
  	/**
	 * 返回给客户端 付费新手引导
	 * @param payType 1:treasury-小金猪    2:exp-经验加速卡     3:preference-特惠礼包      4:club-俱乐部      5:monthcard-月卡    6:coupon-优惠券
	 */
	public void GC_PAY_GUIDE(InputMessage data) 
	{
		int payType = data.GetInt();		
		GuideHandler.Instance().GC_PAY_GUIDE(payType);
	}
}