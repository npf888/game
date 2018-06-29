using System.Collections;

public class MiscGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_MISC_INFO_DATA,GC_MISC_INFO_DATA);
		register(NetMessageType.GC_ONLINE_REWARD,GC_ONLINE_REWARD);
		register(NetMessageType.GC_NEW_USER,GC_NEW_USER);
		register(NetMessageType.GC_KOREAN_SB,GC_KOREAN_SB);
		register(NetMessageType.GC_FB_INVITE,GC_FB_INVITE);
		register(NetMessageType.GC_FB_INVITE_GET_REWARD,GC_FB_INVITE_GET_REWARD);
		register(NetMessageType.GC_MISC_FB_INFO_DATA,GC_MISC_FB_INFO_DATA);
		register(NetMessageType.GC_FB_GET_REWARD,GC_FB_GET_REWARD);
		register(NetMessageType.GC_FB_THUMB_REWARD,GC_FB_THUMB_REWARD);
	}
 
  	/**
	 * 在线奖励
	 * @param humanMiscInfoData 内容
	 */
	public void GC_MISC_INFO_DATA(InputMessage data) 
	{
		HumanMiscInfoData humanMiscInfoData = new HumanMiscInfoData();
		humanMiscInfoData.onlineTime = data.GetLong();//在线时长
		humanMiscInfoData.currentOnlineRewardId = data.GetInt();//在线时长
		humanMiscInfoData.firstRechargeTime = data.GetLong();//首冲时间
		humanMiscInfoData.renameTimes = data.GetInt();//改名次数
		humanMiscInfoData.newUser = data.GetInt();//新手玩家
		MiscHandler.Instance().GC_MISC_INFO_DATA(humanMiscInfoData);
	}
 
  	/**
	 * 在线奖励
	 * @param reward 内容
	 */
	public void GC_ONLINE_REWARD(InputMessage data) 
	{
		RandRewardData reward = new RandRewardData();
		reward.rewardId = data.GetInt();//奖励id
		reward.rewardCount = data.GetInt();//奖励数量
		MiscHandler.Instance().GC_ONLINE_REWARD(reward);
	}
 
  	/**
	 * 新手引导
	 */
	public void GC_NEW_USER(InputMessage data) 
	{
		MiscHandler.Instance().GC_NEW_USER();
	}
 
  	/**
	 * 韩国推广奖励
	 * @param rewardNum 奖励
	 */
	public void GC_KOREAN_SB(InputMessage data) 
	{
		int rewardNum = data.GetInt();		
		MiscHandler.Instance().GC_KOREAN_SB(rewardNum);
	}
 
  	/**
	 * fb邀请
	 * @param fbInviteIdList 好友列表
	 */
	public void GC_FB_INVITE(InputMessage data) 
	{
		int i,size;
		ArrayList fbInviteIdList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			string fbInviteIdList_Datas = data.GetString();
			fbInviteIdList.Add(fbInviteIdList_Datas);
		}
		MiscHandler.Instance().GC_FB_INVITE(fbInviteIdList);
	}
 
  	/**
	 * fb邀请奖励
	 * @param randRewardList 道具奖励
	 */
	public void GC_FB_INVITE_GET_REWARD(InputMessage data) 
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
		MiscHandler.Instance().GC_FB_INVITE_GET_REWARD(randRewardList);
	}
 
  	/**
	 * 在线奖励
	 * @param humanMiscFBInfoData 内容
	 */
	public void GC_MISC_FB_INFO_DATA(InputMessage data) 
	{
		HumanMiscFBInfoData humanMiscFBInfoData = new HumanMiscFBInfoData();
		int j;
		humanMiscFBInfoData.fbReward = data.GetInt();//fb奖励
		ArrayList fbInviteList = new ArrayList();
		int fbInviteListSize = data.GetShort();
		for(j=0; j<fbInviteListSize; j++)
		{
			string fbInviteList_Datas = data.GetString();//fb邀请信息
			fbInviteList.Add(fbInviteList_Datas);
		}
		humanMiscFBInfoData.fbInviteList = fbInviteList;
		ArrayList fbInviteRewardList = new ArrayList();
		int fbInviteRewardListSize = data.GetShort();
		for(j=0; j<fbInviteRewardListSize; j++)
		{
			int fbInviteRewardList_Datas = data.GetInt();//fb邀请奖励信息
			fbInviteRewardList.Add(fbInviteRewardList_Datas);
		}
		humanMiscFBInfoData.fbInviteRewardList = fbInviteRewardList;
		humanMiscFBInfoData.fbThumb = data.GetInt();//fb点赞奖励 1 领取
		humanMiscFBInfoData.fbstartTime = data.GetLong();//fb邀请好友刷新时间
		MiscHandler.Instance().GC_MISC_FB_INFO_DATA(humanMiscFBInfoData);
	}
 
  	/**
	 * fb邀请奖励
	 * @param randRewardList 道具奖励
	 */
	public void GC_FB_GET_REWARD(InputMessage data) 
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
		MiscHandler.Instance().GC_FB_GET_REWARD(randRewardList);
	}
 
  	/**
	 * fb点赞奖励
	 * @param randRewardList 道具奖励
	 */
	public void GC_FB_THUMB_REWARD(InputMessage data) 
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
		MiscHandler.Instance().GC_FB_THUMB_REWARD(randRewardList);
	}
}