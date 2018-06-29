using System.Collections;
public class BazooachieveCGMessage{
	
  
 		/**
		 * 获取 成就(每个类型的第一个)
		 */
	public static void CG_BAZOO_ACHIEVE_FIRST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_ACHIEVE_FIRST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取 成就
		 */
	public static void CG_BAZOO_ACHIEVE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_ACHIEVE);
			PlatformService.Send(msgBody);
		}
}