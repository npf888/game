using System.Collections;
public class BazoorankCGMessage{
	
  
 		/**
		 * 用户当前所有的金币  排行榜 
		 */
	public static void CG_BAZOO_RANK_TOTAL_GOLD_REQUEST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_RANK_TOTAL_GOLD_REQUEST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 盈利 的 排行榜 
		 * @param dateType 按什么查询：： 1:天   2:周  3:月
		 */
	public static void CG_BAZOO_RANK_REQUEST(int dateType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_RANK_REQUEST);
			msgBody.PutInt(dateType);
			PlatformService.Send(msgBody);
		}
}