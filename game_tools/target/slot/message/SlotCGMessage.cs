using System.Collections;
public class SlotCGMessage{
	
  
 		/**
		 * 进入老虎机
		 * @param slotId 老虎机Id
		 */
	public static void CG_SLOT_ENTER(int slotId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_ENTER);
			msgBody.PutInt(slotId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 进入老虎机
		 * @param slotId 老虎机Id
		 * @param roomId 房间ID
		 */
	public static void CG_SLOT_ENTER_ROOM(int slotId,string roomId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_ENTER_ROOM);
			msgBody.PutInt(slotId);
			msgBody.PutString(roomId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 退出老虎机，删除用户所在当前老虎机的ID
		 * @param slotId 老虎机Id
		 */
	public static void CG_SLOT_OUT(int slotId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_OUT);
			msgBody.PutInt(slotId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * slot
		 * @param bet 下注
		 * @param free 是否免费
		 */
	public static void CG_SLOT_SLOT(int bet,int free) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_SLOT);
			msgBody.PutInt(bet);
			msgBody.PutInt(free);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * slot
		 * @param pos 盒子位置
		 */
	public static void CG_FREE_SLOT_REWARD(int pos) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_FREE_SLOT_REWARD);
			msgBody.PutInt(pos);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取老虎机列表
		 * @param soltType 老虎机类型
		 */
	public static void CG_SLOT_GET_LIST(int soltType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_GET_LIST);
			msgBody.PutInt(soltType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 竞赛开始
		 * @param tournamentType 竞赛类型
		 */
	public static void CG_SLOT_TOURNAMENT_START(int tournamentType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TOURNAMENT_START);
			msgBody.PutInt(tournamentType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 关闭竞赛面板
		 * @param tournamentType 竞赛类型
		 */
	public static void CG_SLOT_TOURNAMENT_CLOSE(int tournamentType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TOURNAMENT_CLOSE);
			msgBody.PutInt(tournamentType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 进入老虎机竞赛页面
		 */
	public static void CG_TOURNAMENT_GET_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_TOURNAMENT_GET_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求老虎机前3名排行数据
		 * @param tournamentType 比赛类型
		 */
	public static void CG_SLOT_GET_RANK(int tournamentType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_GET_RANK);
			msgBody.PutInt(tournamentType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 自己获奖记录
		 */
	public static void CG_SNG_RANK_INFO() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SNG_RANK_INFO);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 维密老虎机玩家选择ID
		 * @param id 维密老虎机玩家选择ID
		 */
	public static void CG_SLOT_TYPE12_CHOOSE(int id) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE12_CHOOSE);
			msgBody.PutInt(id);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 维密老虎机自由转动结束后发送这个消息
		 */
	public static void CG_SLOT_TYPE12_FREE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE12_FREE);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 宙斯老虎机抽奖
		 */
	public static void CG_SLOT_TYPE13_BOUNS_PRIZE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE13_BOUNS_PRIZE);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 狮身人面老虎机的bouns小游戏
		 * @param rewardPool bouns位置元素ID
		 */
	public static void CG_SLOT_TYPE15_BOUNS(int rewardPool) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE15_BOUNS);
			msgBody.PutInt(rewardPool);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 水晶魔法老虎机的scatter玩法
		 */
	public static void CG_SLOT_TYPE19_SCATTER() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE19_SCATTER);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 老虎老虎机bouns游戏
		 */
	public static void CG_SLOT_TYPE21_BOUNS() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE21_BOUNS);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 西部牛仔老虎机挖矿小游戏
		 * @param bet 下注
		 */
	public static void CG_SLOT_TYPE22(int bet) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE22);
			msgBody.PutInt(bet);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 东方龙老虎机初始化奖池信息
		 * @param bet 当前下注额
		 */
	public static void CG_SLOT_TYPE23_INIT_REWARD(int bet) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE23_INIT_REWARD);
			msgBody.PutInt(bet);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 巴西风情老虎机点击卡牌
		 */
	public static void CG_SLOT_TYPE24_BOUNS_PRIZE() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE24_BOUNS_PRIZE);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 巴西风情老虎机 去游戏 游戏
		 * @param gameType 要玩 哪个小游戏
		 * @param color 要是 桑巴小游戏 就传 color
		 */
	public static void CG_SLOT_TYPE24_BOUNS_GAME_GO(int gameType,int color) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE24_BOUNS_GAME_GO);
			msgBody.PutInt(gameType);
			msgBody.PutInt(color);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 忍者老虎机bouns小游戏
		 * @param position 用户选择的位置
		 */
	public static void CG_SLOT_TYPE25_BOUNS(int position) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE25_BOUNS);
			msgBody.PutInt(position);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 女巫魔法老虎机bouns小游戏
		 */
	public static void CG_SLOT_TYPE26_BOUNS() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE26_BOUNS);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 海底世界老虎机bouns小游戏
		 */
	public static void CG_SLOT_TYPE28_BOUNS() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE28_BOUNS);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 西方龙老虎机bouns小游戏
		 * @param position 龙蛋位置
		 */
	public static void CG_SLOT_TYPE29_BOUNS(int position) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE29_BOUNS);
			msgBody.PutInt(position);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 福尔摩斯老虎机bouns小游戏
		 * @param number 猜大小的第几个
		 */
	public static void CG_SLOT_TYPE30_BOUNS(int number) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE30_BOUNS);
			msgBody.PutInt(number);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 海盗老虎机bouns小游戏
		 * @param whichNum 第几个小游戏
		 */
	public static void CG_SLOT_TYPE31_BOUNS(int whichNum) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE31_BOUNS);
			msgBody.PutInt(whichNum);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 万圣节老虎机   用户玩 bonus小游戏 
		 * @param isGhost 是否过关（0,1,2,3关）： 只要调用这个接口 就是过关
		 */
	public static void CG_SLOT_TYPE38_BONUS(int isGhost) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_TYPE38_BONUS);
			msgBody.PutInt(isGhost);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求老虎机缓存消息
		 */
	public static void CG_GET_SLOT_CACHEMSG() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GET_SLOT_CACHEMSG);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 老虎机特效
		 * @param demoType 1:3个bonus 2 :4个bonus 3 :5个bonus 4:3个scatter 5:4个scatter 6:5个scatter 7:一列大号wild 8:2列大号wild 9:一列小号wild 10:2列小号wild 11:第一行随机出现相同元素 12:第一行出现3个jackpot 13:随机出现4个jackpot 14:随机出现5个jackpot 15: 随机出现6个jackpot 16: 随机出现7个jackpot 200:增加1个亿
		 */
	public static void CG_DEMO_TYPE(int demoType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_DEMO_TYPE);
			msgBody.PutInt(demoType);
			PlatformService.Send(msgBody);
		}
}