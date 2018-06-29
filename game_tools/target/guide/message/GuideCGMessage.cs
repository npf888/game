using System.Collections;
public class GuideCGMessage{
	
  
 		/**
		 * 点击道具商城，调用此接口
		 */
	public static void CG_ITEM_INVOKE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ITEM_INVOKE);
			PlatformService.Send(msgBody);
		}
}