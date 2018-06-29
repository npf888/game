using System.Collections;
public class MiscCGMessage{
	
  
 		/**
		 * 在线奖励
		 */
	public static void CG_ONLINE_REWARD() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ONLINE_REWARD);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 新手引导
		 */
	public static void CG_NEW_USER() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_NEW_USER);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 韩国推广奖励
		 * @param rewardNum 奖励
		 */
	public static void CG_KOREAN_SB(int rewardNum) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_KOREAN_SB);
			msgBody.PutInt(rewardNum);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * fb邀请
		 * @param fbInviteIdList 好友列表
		 */
	public static void CG_FB_INVITE(ArrayList fbInviteIdList) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FB_INVITE);
			int i;
			msgBody.PutShort((short)fbInviteIdList.Count);
			for(i=0; i<fbInviteIdList.Count; i++){
	msgBody.PutString(fbInviteIdList[i].ToString());
			}
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * fb邀请奖励
		 * @param rewardId 奖励项 
		 */
	public static void CG_FB_INVITE_GET_REWARD(int rewardId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FB_INVITE_GET_REWARD);
			msgBody.PutInt(rewardId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * fb奖励
		 */
	public static void CG_FB_GET_REWARD() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FB_GET_REWARD);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * fb点赞奖励
		 */
	public static void CG_FB_THUMB_REWARD() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FB_THUMB_REWARD);
			PlatformService.Send(msgBody);
		}
}