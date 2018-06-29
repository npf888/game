using System.Collections;
public class ItemCGMessage{
	
  
 		/**
		 * 发送互动道具
		 * @param itemId 互动道具id
		 * @param playerId 玩家id
		 */
	public static void CG_SEND_INTERACTIVE_ITEM(int itemId,long playerId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SEND_INTERACTIVE_ITEM);
			msgBody.PutInt(itemId);
			msgBody.PutLong(playerId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 群体发送互动道具
		 * @param itemId 互动道具id
		 */
	public static void CG_GROUP_SEND_INTERACTIVE_ITEM(int itemId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GROUP_SEND_INTERACTIVE_ITEM);
			msgBody.PutInt(itemId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求商城的道具列表
		 */
	public static void CG_BAZOO_MALL_REQUEST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_MALL_REQUEST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求背包
		 * @param itemType 请求哪种类型的 1:所有,2:色钟，3：红包，4：礼物
		 */
	public static void CG_BAZOO_ITEM_REQUEST(int itemType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_ITEM_REQUEST);
			msgBody.PutInt(itemType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 更换色钟
		 * @param itemId 目标色钟的itemId
		 */
	public static void CG_BAZOO_ITEM_CLOCK_CHANGE(int itemId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_ITEM_CLOCK_CHANGE);
			msgBody.PutInt(itemId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 用金币购买道具
		 * @param itemId 目标色钟的itemId
		 */
	public static void CG_BAZOO_ITEM_BUY_BY_GOLD(int itemId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_ITEM_BUY_BY_GOLD);
			msgBody.PutInt(itemId);
			PlatformService.Send(msgBody);
		}
}