using System.Collections;

public class ShopGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_SHOP_LIST,GC_SHOP_LIST);
		register(NetMessageType.GC_BUY_ITEM,GC_BUY_ITEM);
	}
 
  	/**
	 * 客户端请求商品信息
	 * @param shopList 商品信息
	 */
	public void GC_SHOP_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList shopList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ShopInfoData shopList_Datas= new ShopInfoData();
			shopList_Datas.shopId = data.GetInt();//商品id
			shopList_Datas.price = data.GetInt();//商品价格
			shopList.Add(shopList_Datas);
		}
		ShopHandler.Instance().GC_SHOP_LIST(shopList);
	}
 
  	/**
	 * 客户端购买成功
	 * @param shopId 商品id
	 */
	public void GC_BUY_ITEM(InputMessage data) 
	{
		int shopId = data.GetInt();		
		ShopHandler.Instance().GC_BUY_ITEM(shopId);
	}
}