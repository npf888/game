using System.Collections;

public class CollectGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_CHARM_EXCHANGE,GC_CHARM_EXCHANGE);
		register(NetMessageType.GC_COLLECT_INIT,GC_COLLECT_INIT);
		register(NetMessageType.GC_GET_VOUCHERS,GC_GET_VOUCHERS);
		register(NetMessageType.GC_RAFFLE,GC_RAFFLE);
		register(NetMessageType.GC_CARD_EXCHANGE,GC_CARD_EXCHANGE);
	}
 
  	/**
	 * 魅力值兑换圈
	 * @param returnType 1成功 2 魅力值不够 3 请求物品不存在
	 */
	public void GC_CHARM_EXCHANGE(InputMessage data) 
	{
		int returnType = data.GetInt();		
		CollectHandler.Instance().GC_CHARM_EXCHANGE(returnType);
	}
 
  	/**
	 * 打开收集系统初始数据请求返回
	 * @param itemData1 抽奖券 
	 * @param itemData2 碎片数量 
	 */
	public void GC_COLLECT_INIT(InputMessage data) 
	{
		int i,size;
		ArrayList itemData1 = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ItemData1 itemData1_Datas= new ItemData1();
			itemData1_Datas.cardType = data.GetInt();//1 铜 2 银 3 金
			itemData1_Datas.num = data.GetInt();//数量
			itemData1.Add(itemData1_Datas);
		}
		ArrayList itemData2 = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ItemData2 itemData2_Datas= new ItemData2();
			itemData2_Datas.Itemid = data.GetInt();//碎片物品ID
			itemData2_Datas.num = data.GetInt();//数量
			itemData2.Add(itemData2_Datas);
		}
		CollectHandler.Instance().GC_COLLECT_INIT(itemData1,itemData2);
	}
 
  	/**
	 * 获得券通知
	 * @param itemId 物品ID
	 * @param num 数量
	 */
	public void GC_GET_VOUCHERS(InputMessage data) 
	{
		int itemId = data.GetInt();		
		int num = data.GetInt();		
		CollectHandler.Instance().GC_GET_VOUCHERS(itemId,num);
	}
 
  	/**
	 * 抽奖返回
	 * @param cardType 1 铜 2 银 3 金
	 * @param returnRes 1 成功 0失败
	 * @param Id 奖池ID 成功的前提下有效
	 */
	public void GC_RAFFLE(InputMessage data) 
	{
		int cardType = data.GetInt();		
		int returnRes = data.GetInt();		
		int Id = data.GetInt();		
		CollectHandler.Instance().GC_RAFFLE(cardType,returnRes,Id);
	}
 
  	/**
	 * 卡片兑换返回
	 * @param cardType 1 农场卡 2 建筑卡3 赌场卡
	 * @param returnRes 1 成功 0失败
	 */
	public void GC_CARD_EXCHANGE(InputMessage data) 
	{
		int cardType = data.GetInt();		
		int returnRes = data.GetInt();		
		CollectHandler.Instance().GC_CARD_EXCHANGE(cardType,returnRes);
	}
}