using System.Collections;
public class TaskCGMessage{
	
  
 		/**
		 * 领取奖励
		 * @param tId 任务id
		 */
	public static void CG_DAILY_TASK_GET(int tId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_DAILY_TASK_GET);
			msgBody.PutInt(tId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 领取任务进度奖励
		 * @param boxId 宝箱ID
		 */
	public static void CG_TASK_PROGRESS(int boxId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TASK_PROGRESS);
			msgBody.PutInt(boxId);
			PlatformService.Send(msgBody);
		}
}