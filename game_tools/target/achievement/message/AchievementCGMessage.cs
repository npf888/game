using System.Collections;
public class AchievementCGMessage{
	
  
 		/**
		 * 获取成就数据
		 * @param roleId 角色ID
		 */
	public static void CG_ACH_INFO(long roleId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ACH_INFO);
			msgBody.PutLong(roleId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 领取成就奖励
		 * @param id 成就ID
		 */
	public static void CG_RECEIVE_ACH(int id) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_RECEIVE_ACH);
			msgBody.PutInt(id);
			PlatformService.Send(msgBody);
		}
}