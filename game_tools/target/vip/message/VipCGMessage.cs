using System.Collections;
public class VipCGMessage{
	
  
 		/**
		 * 购买vip
		 * @param vipLevel 购买档次
		 */
	public static void CG_VIP_BUY(int vipLevel) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_VIP_BUY);
			msgBody.PutInt(vipLevel);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 领取vip
		 */
	public static void CG_VIP_GET() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_VIP_GET);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * vip开房
		 * @param vipId vip房间类型d
		 * @param password 密码
		 */
	public static void CG_VIP_CREATE_ROOM(int vipId,string password) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_VIP_CREATE_ROOM);
			msgBody.PutInt(vipId);
			msgBody.PutString(password);
			PlatformService.Send(msgBody);
		}
}