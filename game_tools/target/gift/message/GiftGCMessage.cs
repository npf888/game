using System.Collections;

public class GiftGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_REQUEST_GIFT,GC_REQUEST_GIFT);
		register(NetMessageType.GC_NEW_COMER_GIFT,GC_NEW_COMER_GIFT);
	}
 
  	/**
	 * 请求多种礼包
	 * @param startTime 开始计时点
	 * @param giftId 礼包ID
	 */
	public void GC_REQUEST_GIFT(InputMessage data) 
	{
		long startTime = data.GetLong();
		int giftId = data.GetInt();		
		GiftHandler.Instance().GC_REQUEST_GIFT(startTime,giftId);
	}
 
  	/**
	 * 新手礼包
	 * @param openShut 是否开启 礼包 （0：开启，1：关闭），如果是 1 就关闭礼包
	 * @param giftType 礼包类型 限时0，不限时 1
	 * @param leftTime 剩余时间
	 * @param pid 礼包 pid
	 */
	public void GC_NEW_COMER_GIFT(InputMessage data) 
	{
		int openShut = data.GetInt();		
		int giftType = data.GetInt();		
		long leftTime = data.GetLong();
		long pid = data.GetLong();
		GiftHandler.Instance().GC_NEW_COMER_GIFT(openShut,giftType,leftTime,pid);
	}
}