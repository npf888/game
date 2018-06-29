using System.Collections;
public class SigninCGMessage{
	
  
 		/**
		 * 签到
		 * @param day 签到天数
		 */
	public static void CG_SIGN_IN(int day) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SIGN_IN);
			msgBody.PutInt(day);
			PlatformService.Send(msgBody);
		}
}