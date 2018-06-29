using System.Collections;

public class TreasuryGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_TREASURY,GC_TREASURY);
	}
 
  	/**
	 * 返回当前存钱罐的 对象
	 * @param typeTreasury 类型 总共 六种   0,1,2,3,4,5 
	 * @param curGold 当前金币
	 * @param maxTreasury 作者:存储上限
	 * @param vipPointTreasury 作者:VIP点数奖励
	 */
	public void GC_TREASURY(InputMessage data) 
	{
		long typeTreasury = data.GetLong();
		long curGold = data.GetLong();
		long maxTreasury = data.GetLong();
		long vipPointTreasury = data.GetLong();
		TreasuryHandler.Instance().GC_TREASURY(typeTreasury,curGold,maxTreasury,vipPointTreasury);
	}
}