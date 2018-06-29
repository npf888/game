using System.Collections;
public class RelationCGMessage{
	
  
 		/**
		 * 客户端请求好友列表
		 */
	public static void CG_LOAD_FRIEND_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_LOAD_FRIEND_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求好友列表
		 */
	public static void CG_LOAD_FRIEND_REQUEST_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_LOAD_FRIEND_REQUEST_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求添加好友
		 * @param playerId 玩家id
		 */
	public static void CG_REQUEST_FRIEND(long playerId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_REQUEST_FRIEND);
			msgBody.PutLong(playerId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端处理好友请求
		 * @param playerId 请求id
		 * @param result 处理结果
		 */
	public static void CG_APPLY_FRIEND(long playerId,int result) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_APPLY_FRIEND);
			msgBody.PutLong(playerId);
			msgBody.PutInt(result);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端删除好友请求
		 * @param friendId 好友id
		 */
	public static void CG_DELETE_FRIEND(long friendId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_DELETE_FRIEND);
			msgBody.PutLong(friendId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求好友礼物列表
		 */
	public static void CG_LOAD_FRIEND_GIFT_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_LOAD_FRIEND_GIFT_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 发送礼物
		 * @param friendId 好友id
		 */
	public static void CG_FRIEND_GIFT(long friendId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FRIEND_GIFT);
			msgBody.PutLong(friendId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 领取礼物
		 * @param giftId 礼物id
		 */
	public static void CG_FRIEND_GIFT_GET(long giftId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FRIEND_GIFT_GET);
			msgBody.PutLong(giftId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * facebook好友
		 * @param friendList 好友
		 */
	public static void CG_FACEBOOK_FRIENDS_SYNC(ArrayList friendList) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FACEBOOK_FRIENDS_SYNC);
			int i;
			msgBody.PutShort((short)friendList.Count);
			for(i=0; i<friendList.Count; i++){
	msgBody.PutString(friendList[i].ToString());
			}
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * facebook好友个数
		 * @param friendNum 好友个数
		 */
	public static void CG_FACEBOOK_NUM(int friendNum) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FACEBOOK_NUM);
			msgBody.PutInt(friendNum);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求陌生人列表
		 */
	public static void CG_STRANGER_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_STRANGER_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 进入好友房间
		 * @param friendId 好友ID
		 */
	public static void CG_ENTER_FRIENDS_ROOM(long friendId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ENTER_FRIENDS_ROOM);
			msgBody.PutLong(friendId);
			PlatformService.Send(msgBody);
		}
}