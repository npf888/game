using System.Collections;

public class BazoogiftGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_BAZOO_SEND_GIFT,GC_BAZOO_SEND_GIFT);
		register(NetMessageType.GC_BAZOO_RED_PACKAGE,GC_BAZOO_RED_PACKAGE);
	}
 
  	/**
	 * 返回
	 * @param fromPlayerGold 发送者的金币数量
	 * @param fromPlayerId 谁发的
	 * @param toPlayerId 发给谁
	 * @param itemId 礼物ID
	 * @param number 礼物数量
	 * @param itemType 礼物类型(2:色钟，3：红包，4：礼物)
	 */
	public void GC_BAZOO_SEND_GIFT(InputMessage data) 
	{
		long fromPlayerGold = data.GetLong();
		long fromPlayerId = data.GetLong();
		long toPlayerId = data.GetLong();
		int itemId = data.GetInt();		
		int number = data.GetInt();		
		int itemType = data.GetInt();		
		BazoogiftHandler.Instance().GC_BAZOO_SEND_GIFT(fromPlayerGold,fromPlayerId,toPlayerId,itemId,number,itemType);
	}
 
  	/**
	 * 领取所有红包 返回
	 * @param isSucess 是否成功0：成功，1：失败
	 * @param langId 失败后 多语言ID
	 * @param paramsList 参数列表
	 */
	public void GC_BAZOO_RED_PACKAGE(InputMessage data) 
	{
		int i,size;
		int isSucess = data.GetInt();		
		int langId = data.GetInt();		
		ArrayList paramsList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			string paramsList_Datas = data.GetString();
			paramsList.Add(paramsList_Datas);
		}
		BazoogiftHandler.Instance().GC_BAZOO_RED_PACKAGE(isSucess,langId,paramsList);
	}
}