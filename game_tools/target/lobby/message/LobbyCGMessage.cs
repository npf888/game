using System.Collections;
public class LobbyCGMessage{
	
  
 		/**
		 * 请求最高彩金获得者前20位
		 * @param operationType 请求类型 1 请求最近彩金获得者 2 获取自己适合的游戏类型彩金
		 */
	public static void CG_JACKPOT_LIST_DATA(int operationType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_JACKPOT_LIST_DATA);
			msgBody.PutInt(operationType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 具体游戏类型彩金
		 * @param gameType 游戏类型 百家乐 1 德州扑克 2 老虎机 classic：3  老虎机 四大美人：4 老虎机 水果：5
		 * @param gameId 游戏的id
		 */
	public static void CG_GAMETYPE_JACKPOT(int gameType,int gameId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GAMETYPE_JACKPOT);
			msgBody.PutInt(gameType);
			msgBody.PutInt(gameId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 返回游戏彩金数
		 * @param bet 根据bet数 获取相应的彩金值
		 */
	public static void CG_NEW_JACKPOT(int bet) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_NEW_JACKPOT);
			msgBody.PutInt(bet);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 主动请求 进入老虎机后的第一个页面展示的 四个阶段的 彩金数
		 * @param slotType 根据bet数 获取的相应的彩金值
		 */
	public static void CG_SLOT_NEW_JACKPOTS(int slotType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_SLOT_NEW_JACKPOTS);
			msgBody.PutInt(slotType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求所有老虎机的最高彩金（slotType->jackpot）
		 */
	public static void CG_ALL_SLOT_NEW_JACKPOTS() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ALL_SLOT_NEW_JACKPOTS);
			PlatformService.Send(msgBody);
		}
}