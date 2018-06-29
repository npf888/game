using System.Collections;

public class VipGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_VIP_BUY,GC_VIP_BUY);
		register(NetMessageType.GC_VIP_GET,GC_VIP_GET);
		register(NetMessageType.GC_HUMAN_VIP_INFO_DATA,GC_HUMAN_VIP_INFO_DATA);
		register(NetMessageType.GC_VIP_CREATE_ROOM,GC_VIP_CREATE_ROOM);
	}
 
  	/**
	 * 购买vip
	 * @param randRewardList 道具奖励
	 */
	public void GC_VIP_BUY(InputMessage data) 
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
		VipHandler.Instance().GC_VIP_BUY(randRewardList);
	}
 
  	/**
	 * 领取vip
	 * @param randRewardList 道具奖励
	 */
	public void GC_VIP_GET(InputMessage data) 
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
		VipHandler.Instance().GC_VIP_GET(randRewardList);
	}
 
  	/**
	 * vip数据
	 * @param vipInfoData vip数据
	 */
	public void GC_HUMAN_VIP_INFO_DATA(InputMessage data) 
	{
		HumanVipInfoData vipInfoData = new HumanVipInfoData();
		vipInfoData.lastGetTime = data.GetLong();//vip领取时间
		vipInfoData.level = data.GetInt();//vip领取时间
		vipInfoData.buyTime = data.GetLong();//购买时间
		vipInfoData.days = data.GetInt();//天数
		VipHandler.Instance().GC_HUMAN_VIP_INFO_DATA(vipInfoData);
	}
 
  	/**
	 * vip开房
	 */
	public void GC_VIP_CREATE_ROOM(InputMessage data) 
	{
		VipHandler.Instance().GC_VIP_CREATE_ROOM();
	}
}