using System.Collections;

public class RanknewGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_RANK_LIST,GC_RANK_LIST);
		register(NetMessageType.GC_HUMAN_RANK,GC_HUMAN_RANK);
	}
 
  	/**
	 * 排行
	 * @param start 开始位置（包括）
	 * @param end 结束位置（包括）
	 * @param rankListData 排行数据
	 */
	public void GC_RANK_LIST(InputMessage data) 
	{
		int i,size;
		int start = data.GetInt();		
		int end = data.GetInt();		
		ArrayList rankListData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			RankListData rankListData_Datas= new RankListData();
			rankListData_Datas.countries = data.GetString();//国籍
			rankListData_Datas.userId =data.GetLong();
			rankListData_Datas.name = data.GetString();//名字
			rankListData_Datas.img = data.GetString();//头像
			rankListData_Datas.level = data.GetInt();//玩家等级
			rankListData_Datas.win =data.GetLong();
			rankListData_Datas.rank = data.GetInt();//排名
			rankListData_Datas.vipLevel = data.GetInt();//VIP等级 
			rankListData.Add(rankListData_Datas);
		}
		RanknewHandler.Instance().GC_RANK_LIST(start,end,rankListData);
	}
 
  	/**
	 * 请求自己的排名返回
	 * @param rank 自己的排名
	 * @param win 玩家积分
	 * @param refreshPoint 结算下次刷新点
	 */
	public void GC_HUMAN_RANK(InputMessage data) 
	{
		int rank = data.GetInt();		
		long win = data.GetLong();
		long refreshPoint = data.GetLong();
		RanknewHandler.Instance().GC_HUMAN_RANK(rank,win,refreshPoint);
	}
}