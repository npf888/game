using System.Collections;

public class SigninGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_SIGN_IN_INFO_DATA,GC_SIGN_IN_INFO_DATA);
		register(NetMessageType.GC_SIGN_IN,GC_SIGN_IN);
	}
 
  	/**
	 * 签到
	 * @param dayList 签到天数
	 */
	public void GC_SIGN_IN_INFO_DATA(InputMessage data) 
	{
		int i,size;
		ArrayList dayList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int dayList_Datas = data.GetInt();
			dayList.Add(dayList_Datas);
		}
		SigninHandler.Instance().GC_SIGN_IN_INFO_DATA(dayList);
	}
 
  	/**
	 * 签到
	 * @param day 签到天数
	 * @param randRewardList 道具奖励
	 */
	public void GC_SIGN_IN(InputMessage data) 
	{
		int i,size;
		int day = data.GetInt();		
		ArrayList randRewardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			RandRewardData randRewardList_Datas= new RandRewardData();
			randRewardList_Datas.rewardId = data.GetInt();//奖励id
			randRewardList_Datas.rewardCount = data.GetInt();//奖励数量
			randRewardList.Add(randRewardList_Datas);
		}
		SigninHandler.Instance().GC_SIGN_IN(day,randRewardList);
	}
}