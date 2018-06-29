using System.Collections;

public class WeekcardGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_WEEK_CARD_GET,GC_WEEK_CARD_GET);
		register(NetMessageType.GC_HUMAN_WEEK_CARD_INFO_DATA,GC_HUMAN_WEEK_CARD_INFO_DATA);
	}
 
  	/**
	 * 领取周卡
	 * @param randRewardList 道具奖励
	 */
	public void GC_WEEK_CARD_GET(InputMessage data) 
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
		WeekcardHandler.Instance().GC_WEEK_CARD_GET(randRewardList);
	}
 
  	/**
	 * 周卡数据
	 * @param weekCardInfoData 周卡数据
	 */
	public void GC_HUMAN_WEEK_CARD_INFO_DATA(InputMessage data) 
	{
		HumanWeekCardInfoData weekCardInfoData = new HumanWeekCardInfoData();
		weekCardInfoData.beginTime = data.GetLong();//开始时间
		weekCardInfoData.getTime = data.GetLong();//领取时间
		weekCardInfoData.duration = data.GetLong();//持续时间
		WeekcardHandler.Instance().GC_HUMAN_WEEK_CARD_INFO_DATA(weekCardInfoData);
	}
}