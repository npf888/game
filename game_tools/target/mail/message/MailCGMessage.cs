using System.Collections;
public class MailCGMessage{
	
  
 		/**
		 * 客户端请求邮件列表
		 * @param mailKind 邮件类型
		 */
	public static void CG_LOAD_MAIL_LIST(int mailKind) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_LOAD_MAIL_LIST);
			msgBody.PutInt(mailKind);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求发送邮件
		 * @param roleId 目标人id
		 * @param content 邮件内容
		 * @param title 邮件标题
		 * @param randReward 邮件奖励
		 */
	public static void CG_SEND_MAIL(long roleId,string content,string title,ArrayList randReward) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SEND_MAIL);
			int i;
			msgBody.PutLong(roleId);
			msgBody.PutString(content);
			msgBody.PutString(title);
			msgBody.PutShort((short)randReward.Count);
			for(i=0; i<randReward.Count; i++){
	msgBody.PutInt((randReward[i] as RandRewardData).rewardId);
	msgBody.PutInt((randReward[i] as RandRewardData).rewardCount);
			}
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求读取邮件
		 * @param mailId 邮件id
		 */
	public static void CG_READ_MAIL(long mailId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_READ_MAIL);
			msgBody.PutLong(mailId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求全部奖品领取奖励
		 * @param mailId 邮件id
		 */
	public static void CG_DEAL_WITH_REWARD(long mailId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_DEAL_WITH_REWARD);
			msgBody.PutLong(mailId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求删除邮件
		 * @param mailIdList 邮件Id列表
		 */
	public static void CG_DELETE_MAIL(ArrayList mailIdList) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_DELETE_MAIL);
			int i;
			msgBody.PutShort((short)mailIdList.Count);
			for(i=0; i<mailIdList.Count; i++){
	msgBody.PutLong((long)mailIdList[i]);
			}
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 一键领取邮件
		 */
	public static void CG_RECEIVE_ALL() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_RECEIVE_ALL);
			PlatformService.Send(msgBody);
		}
}