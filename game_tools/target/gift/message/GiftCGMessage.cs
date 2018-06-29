using System.Collections;
public class GiftCGMessage{
	
  
 		/**
		 * 请求多种礼包
		 */
	public static void CG_REQUEST_GIFT() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_REQUEST_GIFT);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 当时间为0时，调用这个接口 ,目的是返回提示信息
		 */
	public static void CG_REQUEST_GIFT_TIME_END() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_REQUEST_GIFT_TIME_END);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 新手礼包
		 */
	public static void CG_NEW_COMER_GIFT() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_NEW_COMER_GIFT);
			PlatformService.Send(msgBody);
		}
}