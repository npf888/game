using System.Collections;
public class MonthcardCGMessage{
	
  
 		/**
		 * 领取月卡
		 */
	public static void CG_MONTH_CARD_GET() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_MONTH_CARD_GET);
			PlatformService.Send(msgBody);
		}
}