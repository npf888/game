using System.Collections;
public class BazooCGMessage{
	
  
 		/**
		 * 修改昵称
		 * @param nickname 用户要变成的昵称
		 */
	public static void CG_BAZOO_CHANGE_NAME(string nickname) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_CHANGE_NAME);
			msgBody.PutString(nickname);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 心跳
		 */
	public static void CG_BAZOO_HEART_BEAT() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_HEART_BEAT);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 选择模式
		 * @param modeType 模式类型：1：经典吹牛模式，2：牛牛模式，3：梭哈模式，4：单挑模式，5：单机模式
		 */
	public static void CG_MODE_CHOSE(int modeType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_MODE_CHOSE);
			msgBody.PutInt(modeType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 创建房间（创建的房间都是私人房间）
		 * @param modeType 模式
		 * @param bet 倍数
		 * @param password 创建房间的 密码
		 */
	public static void CG_ROOM_CREATE(int modeType,int bet,string password) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ROOM_CREATE);
			msgBody.PutInt(modeType);
			msgBody.PutInt(bet);
			msgBody.PutString(password);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 搜索私人房间（根据房间号）
		 * @param roomNumber 房间号
		 */
	public static void CG_ROOM_PRI_SEARCH(string roomNumber) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ROOM_PRI_SEARCH);
			msgBody.PutString(roomNumber);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求 列出所有的房间
		 */
	public static void CG_ROOM_PRI_List() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ROOM_PRI_List);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 加入私人房间（根据房间号加入的）
		 * @param roomNumber 房间号
		 * @param password 加入房间需要此房间的 密码
		 */
	public static void CG_ROOM_PRI_JOIN(string roomNumber,string password) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ROOM_PRI_JOIN);
			msgBody.PutString(roomNumber);
			msgBody.PutString(password);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 加入公共房间（根据房间号加入的）
		 * @param roomNumber 房间号
		 */
	public static void CG_ROOM_PUB_JOIN(string roomNumber) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ROOM_PUB_JOIN);
			msgBody.PutString(roomNumber);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 进入房间
		 * @param bet 倍数
		 */
	public static void CG_ROOM_ENTER(int bet) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ROOM_ENTER);
			msgBody.PutInt(bet);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 退出房间
		 * @param bet 倍数
		 */
	public static void CG_ROOM_OUT(int bet) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ROOM_OUT);
			msgBody.PutInt(bet);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 单独摇色子
		 */
	public static void CG_DICE_SINGLE_SWING() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_DICE_SINGLE_SWING);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 每个人轮流 叫号 
		 * @param diceNum 色子的数量
		 * @param diceValue 色子的值
		 */
	public static void CG_TALK_BIG(int diceNum,int diceValue) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TALK_BIG);
			msgBody.PutInt(diceNum);
			msgBody.PutInt(diceValue);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 抢开 
		 */
	public static void CG_ROB_OPEN() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ROB_OPEN);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 抢开 之后 其他人 竞猜大小 
		 * @param bigSmall 1:大于         0:小于
		 */
	public static void CG_GUESS_BIG_SMALL(int bigSmall) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GUESS_BIG_SMALL);
			msgBody.PutInt(bigSmall);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 牛牛 模式:单独摇号
		 * @param diceValues 将要被 重摇的  色子的值的集合
		 */
	public static void CG_COW_SINGLE_SWING(ArrayList diceValues) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_COW_SINGLE_SWING);
			int i;
			msgBody.PutShort((short)diceValues.Count);
			for(i=0; i<diceValues.Count; i++){
	msgBody.PutInt((int)diceValues[i]);
			}
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 梭哈  模式:选择色子（某个）
		 * @param diceIndex 用户选择的某个色子的索引
		 */
	public static void CG_SHOW_HAND_SINGLE_SWICH(int diceIndex) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SHOW_HAND_SINGLE_SWICH);
			msgBody.PutInt(diceIndex);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 梭哈  模式:取消 选择色子（某个）
		 * @param diceIndex 用户  取消选择的某个色子的索引
		 */
	public static void CG_SHOW_HAND_SINGLE_SWICH_CANCEL(int diceIndex) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SHOW_HAND_SINGLE_SWICH_CANCEL);
			msgBody.PutInt(diceIndex);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 梭哈  模式:单独摇色子
		 * @param diceValues 将要被 重摇的  色子的值的集合
		 */
	public static void CG_SHOW_HAND_SINGLE_SWING(ArrayList diceValues) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SHOW_HAND_SINGLE_SWING);
			int i;
			msgBody.PutShort((short)diceValues.Count);
			for(i=0; i<diceValues.Count; i++){
	msgBody.PutInt((int)diceValues[i]);
			}
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 每个人轮流 叫号 
		 * @param diceType 1:红，2：黑，3：单，4：双
		 */
	public static void CG_BLACK_WHITE_CALL_NUM(int diceType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BLACK_WHITE_CALL_NUM);
			msgBody.PutInt(diceType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 大厅通知前端, 状态 是否有没做的事，主动请求
		 */
	public static void CG_BAZOO_HALL_STATUS() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_HALL_STATUS);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 调用 魔法表情
		 * @param sendPassportId 谁发的
		 * @param receivePassportId 给谁发的
		 * @param magicFace 发送的魔法表情
		 */
	public static void CG_BAZOO_MAGIC_FACE(long sendPassportId,long receivePassportId,string magicFace) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_MAGIC_FACE);
			msgBody.PutLong(sendPassportId);
			msgBody.PutLong(receivePassportId);
			msgBody.PutString(magicFace);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 存储新手的进度(每调用一次 -->某个模式进度完毕)
		 * @param ModeType 模式(1:经典, 2:牛牛, 3:梭哈, 4:红黑)
		 */
	public static void CG_BAZOO_NEWGUY_PROCESS(int ModeType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_NEWGUY_PROCESS);
			msgBody.PutInt(ModeType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 博趣平台入口
		 * @param pcOrMobile 是pc端还是手机端，PC端传入'pc',手机端传入'mobile'
		 */
	public static void CG_BAZOO_BOQU(string pcOrMobile) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_BOQU);
			msgBody.PutString(pcOrMobile);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * facebook 的广告 每看一次 加多少金币
		 * @param gold 增加的金币数量
		 */
	public static void CG_BAZOO_FACEBOOK_ADD_GOLD(int gold) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_FACEBOOK_ADD_GOLD);
			msgBody.PutInt(gold);
			PlatformService.Send(msgBody);
		}
}