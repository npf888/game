using System.Collections;
public class BazoogiftCGMessage{
	
  
 		/**
		 * 发送礼物
		 * @param itemType 发送什么类型的道具（2:色钟，3：红包，4：礼物）
		 * @param toPlayerId 发给谁
		 * @param itemId 礼物ID
		 * @param number 礼物数量
		 */
	public static void CG_BAZOO_SEND_GIFT(int itemType,long toPlayerId,int itemId,int number) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_SEND_GIFT);
			msgBody.PutInt(itemType);
			msgBody.PutLong(toPlayerId);
			msgBody.PutInt(itemId);
			msgBody.PutInt(number);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 领取所有红包
		 * @param itemId 红包的itemID
		 */
	public static void CG_BAZOO_RED_PACKAGE(int itemId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_RED_PACKAGE);
			msgBody.PutInt(itemId);
			PlatformService.Send(msgBody);
		}
}