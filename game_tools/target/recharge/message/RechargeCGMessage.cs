using System.Collections;
public class RechargeCGMessage{
	
  
 		/**
		 * 请求订单
		 * @param productId 产品d
		 */
	public static void CG_REQUEST_ORDER(int productId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_REQUEST_ORDER);
			msgBody.PutInt(productId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求订单
		 * @param productId 产品d
		 */
	public static void CG_REQUEST_ORDER_MYCARD(int productId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_REQUEST_ORDER_MYCARD);
			msgBody.PutInt(productId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 验证订单mycard
		 * @param orderId 订单号
		 */
	public static void CG_VALIDATE_ORDER_MYCARD(long orderId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_VALIDATE_ORDER_MYCARD);
			msgBody.PutLong(orderId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 验证订MOL
		 * @param productId PID-1表示点卡支付
		 */
	public static void CG_REQUEST_ORDER_MOL(int productId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_REQUEST_ORDER_MOL);
			msgBody.PutInt(productId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 验证订单MOL
		 * @param orderId 订单号
		 * @param currencyCode 币种
		 * @param amount 单位是分
		 * @param paymentId MOL订单
		 * @param virtualCurrencyAmount 发放的游戏币PID为-1的时候有效
		 */
	public static void CG_VALIDATE_ORDER_MOL(long orderId,string currencyCode,int amount,string paymentId,string virtualCurrencyAmount) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_VALIDATE_ORDER_MOL);
			msgBody.PutLong(orderId);
			msgBody.PutString(currencyCode);
			msgBody.PutInt(amount);
			msgBody.PutString(paymentId);
			msgBody.PutString(virtualCurrencyAmount);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 验证订单亚马逊
		 * @param orderId 订单号
		 * @param receiptId 收据ID
		 * @param userId Amazon客户的 ID
		 */
	public static void CG_ORDER_AMAZON(long orderId,string receiptId,string userId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ORDER_AMAZON);
			msgBody.PutLong(orderId);
			msgBody.PutString(receiptId);
			msgBody.PutString(userId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 验证订单亚马逊
		 * @param amazonDeliveryList 道具奖励
		 */
	public static void CG_ORDER_AMAZON_DELIVERY(ArrayList amazonDeliveryList) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ORDER_AMAZON_DELIVERY);
			int i;
			msgBody.PutShort((short)amazonDeliveryList.Count);
			for(i=0; i<amazonDeliveryList.Count; i++){
	msgBody.PutInt((amazonDeliveryList[i] as AmazonDelivery).productId);
	msgBody.PutString((amazonDeliveryList[i] as AmazonDelivery).receiptId);
	msgBody.PutString((amazonDeliveryList[i] as AmazonDelivery).userId);
			}
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 验证订单
		 * @param signature 渠道订单id
		 * @param purchaseData 产品id
		 */
	public static void CG_VALIDATE_ORDER(string signature,string purchaseData) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_VALIDATE_ORDER);
			msgBody.PutString(signature);
			msgBody.PutString(purchaseData);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 优惠券是否存在
		 */
	public static void CG_COUPON_EXIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_COUPON_EXIST);
			PlatformService.Send(msgBody);
		}
}