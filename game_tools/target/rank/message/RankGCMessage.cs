using System.Collections;

public class RankGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_COMMON_RANK,GC_COMMON_RANK);
	}
 
  	/**
	 * 排行
	 * @param selfRank 自己排名
	 * @param page 页面
	 * @param rankType 排行版类型
	 * @param rankDataList 排行数据
	 */
	public void GC_COMMON_RANK(InputMessage data) 
	{
		int i,size;
		long selfRank = data.GetLong();
		int page = data.GetInt();		
		int rankType = data.GetInt();		
		ArrayList rankDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			RankData rankDataList_Datas= new RankData();
			rankDataList_Datas.uId =data.GetLong();
			rankDataList_Datas.name = data.GetString();//名字
			rankDataList_Datas.img = data.GetString();//图片
			rankDataList_Datas.score =data.GetLong();
			rankDataList.Add(rankDataList_Datas);
		}
		RankHandler.Instance().GC_COMMON_RANK(selfRank,page,rankType,rankDataList);
	}
}