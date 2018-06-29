using System.Collections;
public class TexasCGMessage{
	
  
 		/**
		 * 德州房间类型列表
		 */
	public static void CG_TEXAS_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 加入德州房间号
		 * @param roomId 房间id
		 */
	public static void CG_JOIN_TEXAS_ROOM_ID(long roomId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_JOIN_TEXAS_ROOM_ID);
			msgBody.PutLong(roomId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 加入德州房间类型
		 * @param roomId 房间id
		 */
	public static void CG_JOIN_TEXAS(int roomId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_JOIN_TEXAS);
			msgBody.PutInt(roomId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 离开德州房间
		 */
	public static void CG_LEAVE_TEXAS() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_LEAVE_TEXAS);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州看牌
		 */
	public static void CG_TEXAS_LOOK() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_LOOK);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州跟注
		 */
	public static void CG_TEXAS_FOLLOW() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_FOLLOW);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州加注
		 * @param addBet 加注额度
		 */
	public static void CG_TEXAS_ADD_BET(long addBet) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_ADD_BET);
			msgBody.PutLong(addBet);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州allin
		 */
	public static void CG_TEXAS_ALL_IN() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_ALL_IN);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州allin
		 */
	public static void CG_TEXAS_GIVE_UP() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_GIVE_UP);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州玩家坐下
		 * @param pos 位置
		 */
	public static void CG_TEXAS_SEAT(int pos) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_SEAT);
			msgBody.PutInt(pos);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州玩家退出
		 */
	public static void CG_TEXAS_EXIT() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_EXIT);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州改变自动补充筹码
		 * @param isAuto 补充
		 */
	public static void CG_TEXAS_AUTO(int isAuto) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_AUTO);
			msgBody.PutInt(isAuto);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州补充筹码
		 * @param complement 补充数
		 */
	public static void CG_TEXAS_COMPLEMENT(long complement) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_COMPLEMENT);
			msgBody.PutLong(complement);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州打赏
		 */
	public static void CG_TEXAS_TIPS() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_TIPS);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州人数
		 * @param people 人数
		 */
	public static void CG_TEXAS_PEOPLE_SETTING(int people) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_PEOPLE_SETTING);
			msgBody.PutInt(people);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州快速开始
		 */
	public static void CG_TEXAS_QUICK_START() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_QUICK_START);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州sng列表
		 */
	public static void CG_TEXAS_SNG_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_SNG_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州sng报名
		 * @param sngId sng id
		 */
	public static void CG_JOIN_TEXAS_SNG(int sngId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_JOIN_TEXAS_SNG);
			msgBody.PutInt(sngId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州vip列表
		 */
	public static void CG_TEXAS_VIP_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TEXAS_VIP_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州加入vip房间
		 * @param vipId vipId
		 * @param password 密码
		 */
	public static void CG_JOIN_TEXAS_VIP(long vipId,string password) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_JOIN_TEXAS_VIP);
			msgBody.PutLong(vipId);
			msgBody.PutString(password);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 德州补充筹码数
		 * @param playerId 玩家id
		 */
	public static void CG_HUMAN_TEXAS_INFO_DATA_SEARCH(long playerId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_HUMAN_TEXAS_INFO_DATA_SEARCH);
			msgBody.PutLong(playerId);
			PlatformService.Send(msgBody);
		}
}