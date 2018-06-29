using System.Collections;
public class TreasuryCGMessage{
	
  
 		/**
		 * 返回当前存钱罐的 对象
		 */
	public static void CG_TREASURY() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TREASURY);
			PlatformService.Send(msgBody);
		}
}