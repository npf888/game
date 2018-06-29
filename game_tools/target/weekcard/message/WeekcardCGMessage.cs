using System.Collections;
public class WeekcardCGMessage{
	
  
 		/**
		 * 领取周卡
		 */
	public static void CG_WEEK_CARD_GET() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_WEEK_CARD_GET);
			PlatformService.Send(msgBody);
		}
}