using System.Collections;

public class ConversioncodeGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_CONVERSION_CODE,GC_CONVERSION_CODE);
	}
 
  	/**
	 * 兑换码兑换礼包返回
	 * @param randRewardList 道具奖励
	 */
	public void GC_CONVERSION_CODE(InputMessage data) 
	{
		int i,size;
		ArrayList randRewardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			RandRewardData randRewardList_Datas= new RandRewardData();
			randRewardList_Datas.rewardId = data.GetInt();//奖励id
			randRewardList_Datas.rewardCount = data.GetInt();//奖励数量
			randRewardList.Add(randRewardList_Datas);
		}
		ConversioncodeHandler.Instance().GC_CONVERSION_CODE(randRewardList);
	}
}