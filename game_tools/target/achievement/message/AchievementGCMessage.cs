using System.Collections;

public class AchievementGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_ACH_INFO,GC_ACH_INFO);
		register(NetMessageType.GC_RECEIVE_ACH,GC_RECEIVE_ACH);
	}
 
  	/**
	 * 获取成就数据返回
	 * @param achievementStateData 成就完成情况
	 * @param achievementsProgressData 成就进度
	 */
	public void GC_ACH_INFO(InputMessage data) 
	{
		int i,size;
		ArrayList achievementStateData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			AchievementStateData achievementStateData_Datas= new AchievementStateData();
			achievementStateData_Datas.id = data.GetInt();//成就ID
			achievementStateData_Datas.state = data.GetInt();//1 没有完成 2 已经完成但没有领取 3 已经领取
			achievementStateData_Datas.completeTime =data.GetLong();
			achievementStateData.Add(achievementStateData_Datas);
		}
		ArrayList achievementsProgressData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			AchievementsProgressData achievementsProgressData_Datas= new AchievementsProgressData();
			achievementsProgressData_Datas.serverType = data.GetInt();//大类型
			achievementsProgressData_Datas.smalType = data.GetInt();//小类型
			achievementsProgressData_Datas.value = data.GetString();//当前值
			achievementsProgressData.Add(achievementsProgressData_Datas);
		}
		AchievementHandler.Instance().GC_ACH_INFO(achievementStateData,achievementsProgressData);
	}
 
  	/**
	 * 领取成就奖励返回
	 * @param id 成就ID
	 * @param result 结果 0 失败 1 成功
	 */
	public void GC_RECEIVE_ACH(InputMessage data) 
	{
		int id = data.GetInt();		
		int result = data.GetInt();		
		AchievementHandler.Instance().GC_RECEIVE_ACH(id,result);
	}
}