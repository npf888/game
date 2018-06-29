using System.Collections;
public class ShopCGMessage{
	
  
 		/**
		 * 客户端请求商品信息
		 */
	public static void CG_SHOP_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SHOP_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求购买
		 * @param shopId 商品id
		 */
	public static void CG_BUY_ITEM(int shopId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BUY_ITEM);
			msgBody.PutInt(shopId);
			PlatformService.Send(msgBody);
		}
}