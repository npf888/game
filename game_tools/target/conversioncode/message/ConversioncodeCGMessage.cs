using System.Collections;
public class ConversioncodeCGMessage{
	
  
 		/**
		 * 兑换码兑换礼包
		 * @param code 兑换码
		 */
	public static void CG_CONVERSION_CODE(string code) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CONVERSION_CODE);
			msgBody.PutString(code);
			PlatformService.Send(msgBody);
		}
}