using System.Collections;
public class BazootaskCGMessage{
	
  
 		/**
		 * 获取 任务
		 */
	public static void CG_BAZOO_TASK() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_TASK);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取 所有的成就任务
		 */
	public static void CG_BAZOO_ACHIEVE_TASK() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_ACHIEVE_TASK);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取 金币
		 * @param taskId 相应的ID
		 */
	public static void CG_BAZOO_GET_REWARD(int taskId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_GET_REWARD);
			msgBody.PutInt(taskId);
			PlatformService.Send(msgBody);
		}
}