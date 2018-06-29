using System.Collections;
public class ClubCGMessage{
	
  
 		/**
		 * 打开俱乐部面板
		 */
	public static void CG_CLUB_PANEL() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_PANEL);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 创建俱乐部
		 * @param name 俱乐部名字
		 * @param notice 公告
		 * @param _type 类型 1 公开 2 需要申请  3 不可加入
		 * @param ico 图标
		 * @param limit 段位限制
		 */
	public static void CG_CLUB_CREATE(string name,string notice,int _type,int ico,int limit) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_CREATE);
			msgBody.PutString(name);
			msgBody.PutString(notice);
			msgBody.PutInt(_type);
			msgBody.PutInt(ico);
			msgBody.PutInt(limit);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐签到
		 */
	public static void CG_CLUB_SIGN() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_SIGN);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐捐献
		 * @param count 捐献数量
		 */
	public static void CG_CLUB_DONATE(int count) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_DONATE);
			msgBody.PutInt(count);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐搜索
		 * @param name 俱乐部名字
		 */
	public static void CG_CLUB_SEARCH(string name) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_SEARCH);
			msgBody.PutString(name);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐留言板面板
		 */
	public static void CG_CLUB_NOTE_PANEL() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_NOTE_PANEL);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐留言板发送
		 * @param msg 内容
		 */
	public static void CG_CLUB_NOTE_SEND(string msg) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_NOTE_SEND);
			msgBody.PutString(msg);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐留言板发送礼包
		 * @param pid 奖励id
		 */
	public static void CG_CLUB_NOTE_SEND_GIFT(int pid) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_NOTE_SEND_GIFT);
			msgBody.PutInt(pid);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取俱乐留邀请
		 */
	public static void CG_CLUB_INVATE_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_INVATE_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取俱乐留排行榜
		 * @param opType 1 活跃帮   2贡献榜
		 */
	public static void CG_CLUB_RANKING_lIST(int opType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_RANKING_lIST);
			msgBody.PutInt(opType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 加入俱乐部
		 * @param clubId 俱乐部id
		 */
	public static void CG_CLUB_JOIN(string clubId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_JOIN);
			msgBody.PutString(clubId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 拒绝加入俱乐部
		 * @param clubId 俱乐部id
		 */
	public static void CG_CLUB_NOT_JOIN(string clubId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_NOT_JOIN);
			msgBody.PutString(clubId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取俱乐留申请
		 */
	public static void CG_CLUB_APPLY_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_APPLY_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取俱乐申请列表
		 * @param playerId 玩家id
		 * @param opType 类型 0 决绝  1 同意
		 */
	public static void CG_CLUB_APPLY_OP(long playerId,int opType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_APPLY_OP);
			msgBody.PutLong(playerId);
			msgBody.PutInt(opType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 邀请某人加入俱乐部
		 * @param playerId 玩家id
		 */
	public static void CG_CLUB_INVATE(long playerId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_INVATE);
			msgBody.PutLong(playerId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 从俱乐部列表加入俱乐部
		 * @param clubId 俱乐部id
		 */
	public static void CG_CLUB_JOIN2(string clubId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_JOIN2);
			msgBody.PutString(clubId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 设置俱乐部
		 * @param notice 公告
		 * @param _type 类型 1 公开 2 需要申请  3 不可加入
		 * @param ico 图标
		 * @param limit 段位限制
		 */
	public static void CG_CLUB_EDIT(string notice,int _type,int ico,int limit) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_EDIT);
			msgBody.PutString(notice);
			msgBody.PutInt(_type);
			msgBody.PutInt(ico);
			msgBody.PutInt(limit);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐部改名字
		 * @param name 俱乐部名字
		 */
	public static void CG_CLUB_CHANGE_NAME(string name) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_CHANGE_NAME);
			msgBody.PutString(name);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 离开俱乐部
		 */
	public static void CG_CLUB_LEAVE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_LEAVE);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 踢出俱乐部
		 * @param playerId 玩家id
		 */
	public static void CG_CLUB_KICK(long playerId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_KICK);
			msgBody.PutLong(playerId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 授权
		 * @param playerId 玩家id
		 * @param opType 类型 0 降职 1 升职
		 */
	public static void CG_CLUB_PROMATE(long playerId,int opType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_PROMATE);
			msgBody.PutLong(playerId);
			msgBody.PutInt(opType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取俱乐部信息
		 * @param playerId 玩家id
		 */
	public static void CG_CLUB_INFO_GET(long playerId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_INFO_GET);
			msgBody.PutLong(playerId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐部留言删除
		 * @param msgId 留言id
		 */
	public static void CG_CLUB_NOTE_DELETE(string msgId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_NOTE_DELETE);
			msgBody.PutString(msgId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐部留言礼物
		 * @param msgId 留言id
		 */
	public static void CG_CLUB_GET_GIFT(string msgId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_GET_GIFT);
			msgBody.PutString(msgId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐部发起弹劾
		 */
	public static void CG_CLUB_TANHE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_TANHE);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 弹劾状态
		 */
	public static void CG_CLUB_TANHE_STATE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_TANHE_STATE);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 俱乐部弹劾同意/拒绝
		 * @param ret 类型 -1 拒绝 1 同意
		 */
	public static void CG_CLUB_TANHE_VOTE(int ret) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLUB_TANHE_VOTE);
			msgBody.PutInt(ret);
			PlatformService.Send(msgBody);
		}
}