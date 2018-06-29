using System.Collections;

public class ActivityGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_ACTIVITY_LIST,GC_ACTIVITY_LIST);
		register(NetMessageType.GC_UPDATE_ACTIVITY,GC_UPDATE_ACTIVITY);
		register(NetMessageType.GC_HUMAN_ACTIVITY_REWARD_DATA_LIST,GC_HUMAN_ACTIVITY_REWARD_DATA_LIST);
		register(NetMessageType.GC_UPDATE_HUMAN_ACTIVITY_REWARD,GC_UPDATE_HUMAN_ACTIVITY_REWARD);
		register(NetMessageType.GC_HUNAMN_PROGRESS,GC_HUNAMN_PROGRESS);
		register(NetMessageType.GC_HUNAMN_PROGRESS_SINGLE,GC_HUNAMN_PROGRESS_SINGLE);
		register(NetMessageType.GC_GET_ACTIVITY_REWARD,GC_GET_ACTIVITY_REWARD);
		register(NetMessageType.GC_MONTH_OR_WEEK,GC_MONTH_OR_WEEK);
		register(NetMessageType.GC_MONTH_WEEK_LEFT_TIME,GC_MONTH_WEEK_LEFT_TIME);
		register(NetMessageType.GC_STILL_HAVE_ACTIVITY_GOLD,GC_STILL_HAVE_ACTIVITY_GOLD);
		register(NetMessageType.GC_FUNCTION_LEFT_TIME,GC_FUNCTION_LEFT_TIME);
	}
 
  	/**
	 * 活动列表
	 * @param activityDataList 活动信息
	 */
	public void GC_ACTIVITY_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList activityDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ActivityData activityDataList_Datas= new ActivityData();
			activityDataList_Datas.activityId =data.GetLong();
			activityDataList_Datas.title = data.GetString();//标题
			activityDataList_Datas.title_cn = data.GetString();//中文标题
			activityDataList_Datas.title_tw = data.GetString();//繁体标题
			activityDataList_Datas.desc = data.GetString();//描述
			activityDataList_Datas.desc_cn = data.GetString();//中文描述
			activityDataList_Datas.desc_tw = data.GetString();//繁体描述
			activityDataList_Datas.hall_pic_tw = data.GetString();//大厅图片,中文繁体
			activityDataList_Datas.hall_pic_cn = data.GetString();//大厅图片,中文简体
			activityDataList_Datas.hall_pic = data.GetString();//大厅图片
			activityDataList_Datas.pic = data.GetString();//图片
			activityDataList_Datas.pic_cn = data.GetString();//图片描述
			activityDataList_Datas.pic_tw = data.GetString();//图片描述
			activityDataList_Datas.link = data.GetInt();//跳转链接
			activityDataList_Datas.ruleSre = data.GetString();//规则数据
			activityDataList_Datas.startTime =data.GetLong();
			activityDataList_Datas.endTime =data.GetLong();
			activityDataList_Datas.fullValue = data.GetString();//全服累计金币 或者 次数 或者其他
			activityDataList.Add(activityDataList_Datas);
		}
		ActivityHandler.Instance().GC_ACTIVITY_LIST(activityDataList);
	}
 
  	/**
	 * 活动更新
	 * @param activityDataList 活动信息
	 */
	public void GC_UPDATE_ACTIVITY(InputMessage data) 
	{
		ActivityData activityDataList = new ActivityData();
		activityDataList.activityId = data.GetLong();//vip领取时间
		activityDataList.title = data.GetString();//标题
		activityDataList.title_cn = data.GetString();//中文标题
		activityDataList.title_tw = data.GetString();//繁体标题
		activityDataList.desc = data.GetString();//描述
		activityDataList.desc_cn = data.GetString();//中文描述
		activityDataList.desc_tw = data.GetString();//繁体描述
		activityDataList.hall_pic_tw = data.GetString();//大厅图片,中文繁体
		activityDataList.hall_pic_cn = data.GetString();//大厅图片,中文简体
		activityDataList.hall_pic = data.GetString();//大厅图片
		activityDataList.pic = data.GetString();//图片
		activityDataList.pic_cn = data.GetString();//图片描述
		activityDataList.pic_tw = data.GetString();//图片描述
		activityDataList.link = data.GetInt();//跳转链接
		activityDataList.ruleSre = data.GetString();//规则数据
		activityDataList.startTime = data.GetLong();//开始时间
		activityDataList.endTime = data.GetLong();//结束时间
		activityDataList.fullValue = data.GetString();//全服累计金币 或者 次数 或者其他
		ActivityHandler.Instance().GC_UPDATE_ACTIVITY(activityDataList);
	}
 
  	/**
	 * 用户活动奖励列表
	 * @param humanSimpleRewardInfoData 活动奖励信息
	 */
	public void GC_HUMAN_ACTIVITY_REWARD_DATA_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList humanSimpleRewardInfoData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			HumanSimpleRewardInfoData humanSimpleRewardInfoData_Datas= new HumanSimpleRewardInfoData();
			int humanSimpleRewardInfoData_j;
			humanSimpleRewardInfoData_Datas.activityId =data.GetLong();
				ArrayList humanSimpleRewardInfoData_stateListist = new ArrayList();
				int humanSimpleRewardInfoData_stateLististSize = data.GetShort();
				for(humanSimpleRewardInfoData_j=0; humanSimpleRewardInfoData_j<humanSimpleRewardInfoData_stateLististSize; humanSimpleRewardInfoData_j++){
					int humanSimpleRewardInfoData_stateListist_Datas = data.GetInt();//活动领取状态数据
					humanSimpleRewardInfoData_stateListist.Add(humanSimpleRewardInfoData_stateListist_Datas);
				}
			humanSimpleRewardInfoData_Datas.stateListist = humanSimpleRewardInfoData_stateListist;
			humanSimpleRewardInfoData.Add(humanSimpleRewardInfoData_Datas);
		}
		ActivityHandler.Instance().GC_HUMAN_ACTIVITY_REWARD_DATA_LIST(humanSimpleRewardInfoData);
	}
 
  	/**
	 * 用户活动奖励列表更新
	 * @param humanSimpleRewardInfoData 活动奖励信息
	 */
	public void GC_UPDATE_HUMAN_ACTIVITY_REWARD(InputMessage data) 
	{
		HumanSimpleRewardInfoData humanSimpleRewardInfoData = new HumanSimpleRewardInfoData();
		int j;
		humanSimpleRewardInfoData.activityId = data.GetLong();//活动id
		ArrayList stateListist = new ArrayList();
		int stateLististSize = data.GetShort();
		for(j=0; j<stateLististSize; j++)
		{
			int stateListist_Datas = data.GetInt();//活动领取状态数据
			stateListist.Add(stateListist_Datas);
		}
		humanSimpleRewardInfoData.stateListist = stateListist;
		ActivityHandler.Instance().GC_UPDATE_HUMAN_ACTIVITY_REWARD(humanSimpleRewardInfoData);
	}
 
  	/**
	 * 活动进度数据
	 * @param humanActivitySmallData 活动进度数据
	 */
	public void GC_HUNAMN_PROGRESS(InputMessage data) 
	{
		int i,size;
		ArrayList humanActivitySmallData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			HumanActivitySmallData humanActivitySmallData_Datas= new HumanActivitySmallData();
			int humanActivitySmallData_j;
			humanActivitySmallData_Datas.activityId =data.GetLong();
				ArrayList humanActivitySmallData_smallValue = new ArrayList();
				int humanActivitySmallData_smallValueSize = data.GetShort();
				for(humanActivitySmallData_j=0; humanActivitySmallData_j<humanActivitySmallData_smallValueSize; humanActivitySmallData_j++){
					int humanActivitySmallData_smallValue_Datas = data.GetInt();//活动完成进度数据
					humanActivitySmallData_smallValue.Add(humanActivitySmallData_smallValue_Datas);
				}
			humanActivitySmallData_Datas.smallValue = humanActivitySmallData_smallValue;
			humanActivitySmallData.Add(humanActivitySmallData_Datas);
		}
		ActivityHandler.Instance().GC_HUNAMN_PROGRESS(humanActivitySmallData);
	}
 
  	/**
	 * 活动进度数据
	 * @param humanActivitySmallData 活动进度数据
	 */
	public void GC_HUNAMN_PROGRESS_SINGLE(InputMessage data) 
	{
		HumanActivitySmallData humanActivitySmallData = new HumanActivitySmallData();
		int j;
		humanActivitySmallData.activityId = data.GetLong();//活动id
		ArrayList smallValue = new ArrayList();
		int smallValueSize = data.GetShort();
		for(j=0; j<smallValueSize; j++)
		{
			int smallValue_Datas = data.GetInt();//活动完成进度数据
			smallValue.Add(smallValue_Datas);
		}
		humanActivitySmallData.smallValue = smallValue;
		ActivityHandler.Instance().GC_HUNAMN_PROGRESS_SINGLE(humanActivitySmallData);
	}
 
  	/**
	 * 用户领取活动奖励
	 * @param result 领取结果 0 失败 1 成功
	 * @param activityId 活动id
	 * @param indexId 具体小条件Id数组的索引下标
	 */
	public void GC_GET_ACTIVITY_REWARD(InputMessage data) 
	{
		int result = data.GetInt();		
		long activityId = data.GetLong();
		int indexId = data.GetInt();		
		ActivityHandler.Instance().GC_GET_ACTIVITY_REWARD(result,activityId,indexId);
	}
 
  	/**
	 * 直接推送的  周、月特惠充值活动
	 * @param mwtype 周：0，月：1
	 * @param startOrEnd 开始：0，结束：1
	 * @param leftTime 周或者月的剩余时间
	 */
	public void GC_MONTH_OR_WEEK(InputMessage data) 
	{
		int mwtype = data.GetInt();		
		int startOrEnd = data.GetInt();		
		long leftTime = data.GetLong();
		ActivityHandler.Instance().GC_MONTH_OR_WEEK(mwtype,startOrEnd,leftTime);
	}
 
  	/**
	 * 返回    周、月特惠充值活动的时间
	 * @param weekLeftTime 秒
	 * @param monthLeftTime 秒
	 */
	public void GC_MONTH_WEEK_LEFT_TIME(InputMessage data) 
	{
		long weekLeftTime = data.GetLong();
		long monthLeftTime = data.GetLong();
		ActivityHandler.Instance().GC_MONTH_WEEK_LEFT_TIME(weekLeftTime,monthLeftTime);
	}
 
  	/**
	 * 还有未领取的活动的金币
	 */
	public void GC_STILL_HAVE_ACTIVITY_GOLD(InputMessage data) 
	{
		ActivityHandler.Instance().GC_STILL_HAVE_ACTIVITY_GOLD();
	}
 
  	/**
	 * 功能
	 * @param img 图片
	 * @param leftTime 剩余时间
	 * @param functionType 类型  1 筹码   2 物品  3 礼包  4 功能性付费
	 */
	public void GC_FUNCTION_LEFT_TIME(InputMessage data) 
	{
		int i,size;
		string img = data.GetString();		
		long leftTime = data.GetLong();
		ArrayList functionType = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int functionType_Datas = data.GetInt();
			functionType.Add(functionType_Datas);
		}
		ActivityHandler.Instance().GC_FUNCTION_LEFT_TIME(img,leftTime,functionType);
	}
}