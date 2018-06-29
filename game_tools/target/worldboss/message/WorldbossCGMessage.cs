using System.Collections;
public class WorldbossCGMessage{
	
  
 		/**
		 * 开启或者关闭面板
		 * @param panelType 0:开启，1:关闭
		 */
	public static void CG_OPEN_PANEL(int panelType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_OPEN_PANEL);
			msgBody.PutInt(panelType);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 获取boss信息
		 */
	public static void CG_GET_BOSS_INFO() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GET_BOSS_INFO);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 伤害 排行榜 的 信息 请求
		 */
	public static void CG_GET_RANK_INFO() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GET_RANK_INFO);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 进入老虎机主动请求
		 */
	public static void CG_REFRESH_BOSS_INFO() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_REFRESH_BOSS_INFO);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * boss的开始结束信息
		 */
	public static void CG_BOSS_START_END_INFO() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BOSS_START_END_INFO);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 自己攻击的血量 
		 */
	public static void CG_RETURN_BLOOD() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_RETURN_BLOOD);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 主动请求比赛之前 多少分钟
		 */
	public static void CG_BEFORE_START() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BEFORE_START);
			PlatformService.Send(msgBody);
		}
}