using System.Collections;
public class ChatCGMessage{
	
  
 		/**
		 * 聊天
		 * @param channel 频道
		 * @param destRoleUUID 接收玩家id
		 * @param content 内容
		 * @param roomNumber 房间号
		 * @param msgType 消息类型（普通消息：0，可以加入房间的消息：1）
		 */
	public static void CG_CHAT_MSG(int channel,long destRoleUUID,string content,string roomNumber,int msgType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CHAT_MSG);
			msgBody.PutInt(channel);
			msgBody.PutLong(destRoleUUID);
			msgBody.PutString(content);
			msgBody.PutString(roomNumber);
			msgBody.PutInt(msgType);
			PlatformService.Send(msgBody);
		}
}