using System.Collections;
public class CollectCGMessage{
	
  
 		/**
		 * 魅力值兑换圈
		 * @param exchangeId exchange表ID
		 */
	public static void CG_CHARM_EXCHANGE(int exchangeId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CHARM_EXCHANGE);
			msgBody.PutInt(exchangeId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 打开收集系统初始数据请求
		 */
	public static void CG_COLLECT_INIT() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_COLLECT_INIT);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 抽奖
		 * @param cardType 1 铜 2 银 3 金
		 */
	public static void CG_RAFFLE(int cardType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_RAFFLE);
			msgBody.PutInt(cardType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 卡片兑换
		 * @param cardType 1 农场卡 2 建筑卡3 赌场卡
		 */
	public static void CG_CARD_EXCHANGE(int cardType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CARD_EXCHANGE);
			msgBody.PutInt(cardType);
			PlatformService.Send(msgBody);
		}
}