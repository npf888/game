using System.Collections;
public class ActivityCGMessage{
	
  
 		/**
		 * 活动列表
		 */
	public static void CG_ACTIVITY_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ACTIVITY_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取活动进度数据
		 */
	public static void CG_ACTIVITY_PROGRESS() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ACTIVITY_PROGRESS);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 用户领取活动奖励
		 * @param activityId 活动id
		 * @param indexId 具体小条件Id数组的索引下标
		 */
	public static void CG_GET_ACTIVITY_REWARD(long activityId,int indexId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GET_ACTIVITY_REWARD);
			msgBody.PutLong(activityId);
			msgBody.PutInt(indexId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 返回   周、月特惠充值活动的时间
		 */
	public static void CG_MONTH_WEEK_LEFT_TIME() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_MONTH_WEEK_LEFT_TIME);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 还有未领取的活动的金币
		 */
	public static void CG_STILL_HAVE_ACTIVITY_GOLD() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_STILL_HAVE_ACTIVITY_GOLD);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 功能
		 */
	public static void CG_FUNCTION() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FUNCTION);
			PlatformService.Send(msgBody);
		}
}