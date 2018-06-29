using System.Collections;
public class NewbieCGMessage{
	
  
 		/**
		 * 新手存盘点
		 * @param step 步骤id
		 */
	public static void CG_SAVE_POINT(int step) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SAVE_POINT);
			msgBody.PutInt(step);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取存盘点
		 */
	public static void CG_GET_SAVE_POINT() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GET_SAVE_POINT);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 新手存盘点
		 */
	public static void CG_SLOT_NEWBIE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_NEWBIE);
			PlatformService.Send(msgBody);
		}
}