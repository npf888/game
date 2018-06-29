using System.Collections;

public class ItemGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_HUMAN_BAG_INFO_DATA,GC_HUMAN_BAG_INFO_DATA);
		register(NetMessageType.GC_SEND_INTERACTIVE_ITEM,GC_SEND_INTERACTIVE_ITEM);
		register(NetMessageType.GC_GROUP_SEND_INTERACTIVE_ITEM,GC_GROUP_SEND_INTERACTIVE_ITEM);
		register(NetMessageType.GC_BAZOO_MALL_REQUEST,GC_BAZOO_MALL_REQUEST);
		register(NetMessageType.GC_BAZOO_ITEM_REQUEST,GC_BAZOO_ITEM_REQUEST);
		register(NetMessageType.GC_BAZOO_ITEM_CLOCK_CHANGE,GC_BAZOO_ITEM_CLOCK_CHANGE);
		register(NetMessageType.GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL,GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL);
		register(NetMessageType.GC_BAZOO_ITEM_BUY_BY_GOLD,GC_BAZOO_ITEM_BUY_BY_GOLD);
	}
 
  	/**
	 * 客户端请求商品信息
	 * @param itemList 商品信息
	 */
	public void GC_HUMAN_BAG_INFO_DATA(InputMessage data) 
	{
		int i,size;
		ArrayList itemList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ItemInfoData itemList_Datas= new ItemInfoData();
			itemList_Datas.uuid = data.GetString();//数据库id
			itemList_Datas.templateId = data.GetInt();//模板id
			itemList_Datas.overlap = data.GetInt();//数量
			itemList_Datas.beginTime =data.GetLong();
			itemList_Datas.duration =data.GetLong();
			itemList.Add(itemList_Datas);
		}
		ItemHandler.Instance().GC_HUMAN_BAG_INFO_DATA(itemList);
	}
 
  	/**
	 * 发送互动道具
	 * @param fromId 发送玩家id
	 * @param itemId 互动道具id
	 * @param toId 玩家id
	 */
	public void GC_SEND_INTERACTIVE_ITEM(InputMessage data) 
	{
		long fromId = data.GetLong();
		int itemId = data.GetInt();		
		long toId = data.GetLong();
		ItemHandler.Instance().GC_SEND_INTERACTIVE_ITEM(fromId,itemId,toId);
	}
 
  	/**
	 * 群体发送互动道具
	 * @param fromId 发送玩家id
	 * @param itemId 互动道具id
	 */
	public void GC_GROUP_SEND_INTERACTIVE_ITEM(InputMessage data) 
	{
		long fromId = data.GetLong();
		int itemId = data.GetInt();		
		ItemHandler.Instance().GC_GROUP_SEND_INTERACTIVE_ITEM(fromId,itemId);
	}
 
  	/**
	 * 商城的道具列表
	 * @param itemInfoDataNew 商城的道具列表
	 */
	public void GC_BAZOO_MALL_REQUEST(InputMessage data) 
	{
		int i,size;
		ArrayList itemInfoDataNew = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ItemInfoDataNew itemInfoDataNew_Datas= new ItemInfoDataNew();
			itemInfoDataNew_Datas.fromPlayerId =data.GetLong();
			itemInfoDataNew_Datas.itemId = data.GetInt();//道具ID
			itemInfoDataNew_Datas.itemType = data.GetInt();//道具类型
			itemInfoDataNew_Datas.itemName = data.GetString();//道具名称
			itemInfoDataNew_Datas.price =data.GetLong();
			itemInfoDataNew_Datas.img = data.GetString();//道具图片
			itemInfoDataNew_Datas.usingClockItemId = data.GetInt();//正在使用中的 色钟的ID
			itemInfoDataNew_Datas.isExist = data.GetInt();//是否已经存在 0：否，1：是
			itemInfoDataNew.Add(itemInfoDataNew_Datas);
		}
		ItemHandler.Instance().GC_BAZOO_MALL_REQUEST(itemInfoDataNew);
	}
 
  	/**
	 * 请求背包 返回
	 * @param itemInfoDataNew 新的背包信息
	 */
	public void GC_BAZOO_ITEM_REQUEST(InputMessage data) 
	{
		int i,size;
		ArrayList itemInfoDataNew = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ItemInfoDataNew itemInfoDataNew_Datas= new ItemInfoDataNew();
			itemInfoDataNew_Datas.fromPlayerId =data.GetLong();
			itemInfoDataNew_Datas.itemId = data.GetInt();//道具ID
			itemInfoDataNew_Datas.itemType = data.GetInt();//道具类型
			itemInfoDataNew_Datas.itemName = data.GetString();//道具名称
			itemInfoDataNew_Datas.price =data.GetLong();
			itemInfoDataNew_Datas.img = data.GetString();//道具图片
			itemInfoDataNew_Datas.usingClockItemId = data.GetInt();//正在使用中的 色钟的ID
			itemInfoDataNew_Datas.isExist = data.GetInt();//是否已经存在 0：否，1：是
			itemInfoDataNew.Add(itemInfoDataNew_Datas);
		}
		ItemHandler.Instance().GC_BAZOO_ITEM_REQUEST(itemInfoDataNew);
	}
 
  	/**
	 * 更换色钟返回
	 * @param itemId 目标色钟的itemId
	 */
	public void GC_BAZOO_ITEM_CLOCK_CHANGE(InputMessage data) 
	{
		int itemId = data.GetInt();		
		ItemHandler.Instance().GC_BAZOO_ITEM_CLOCK_CHANGE(itemId);
	}
 
  	/**
	 * 更换色钟 通知房间内所有人 自己更换了色钟
	 * @param playerId 更换色钟图片的玩家ID
	 * @param itemId 目标色钟的itemId
	 * @param img 道具图片
	 */
	public void GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL(InputMessage data) 
	{
		long playerId = data.GetLong();
		int itemId = data.GetInt();		
		string img = data.GetString();		
		ItemHandler.Instance().GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL(playerId,itemId,img);
	}
 
  	/**
	 * 用金币购买道具
	 * @param isSucess 是否成功0：成功，1：失败
	 * @param langId 失败后 多语言ID
	 * @param paramsList 参数列表
	 */
	public void GC_BAZOO_ITEM_BUY_BY_GOLD(InputMessage data) 
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
		ItemHandler.Instance().GC_BAZOO_ITEM_BUY_BY_GOLD(isSucess,langId,paramsList);
	}
}