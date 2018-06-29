using System.Collections;
public class LuckyspinCGMessage{
	
  
 		/**
		 * 转盘
		 * @param free 免费
		 */
	public static void CG_LUCKY_SPIN(int free) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_LUCKY_SPIN);
			msgBody.PutInt(free);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 匹配
		 */
	public static void CG_LUCKY_MATCH() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_LUCKY_MATCH);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求大转盘数据
		 */
	public static void CG_BIG_ZHUANPAN() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BIG_ZHUANPAN);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 转动装盘
		 * @param isFree 1 免费转动 0 花钱转动
		 */
	public static void CG_SPIN_ZHUANPAN(int isFree) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SPIN_ZHUANPAN);
			msgBody.PutInt(isFree);
			PlatformService.Send(msgBody);
		}
}