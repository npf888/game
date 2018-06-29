using System.Collections;
public class BazoosigninCGMessage{
	
  
 		/**
		 * 签到
		 */
	public static void CG_BAZOO_SIGNIN() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_SIGNIN);
			PlatformService.Send(msgBody);
		}
}