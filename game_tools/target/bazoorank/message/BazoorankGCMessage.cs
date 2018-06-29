using System.Collections;

public class BazoorankGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_BAZOO_RANK_REQUEST,GC_BAZOO_RANK_REQUEST);
	}
 
  	/**
	 * 排行榜返回
	 * @param humanRankInfo 排行榜 人物信息
	 */
	public void GC_BAZOO_RANK_REQUEST(InputMessage data) 
	{
		int i,size;
		ArrayList humanRankInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			HumanRankInfo humanRankInfo_Datas= new HumanRankInfo();
			humanRankInfo_Datas.passportId =data.GetLong();
			humanRankInfo_Datas.vip = data.GetInt();//vip等级
			humanRankInfo_Datas.name = data.GetString();//名称
			humanRankInfo_Datas.headImg = data.GetString();//头像
			humanRankInfo_Datas.gold =data.GetLong();
			humanRankInfo.Add(humanRankInfo_Datas);
		}
		BazoorankHandler.Instance().GC_BAZOO_RANK_REQUEST(humanRankInfo);
	}
}