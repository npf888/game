using System.Collections;
	/**
	 * 常量定义：
	 *	【消息显示类型】1- 普通消息类			2-	重要消息类			3-在当前对话窗口直接显示此信息 			4-在好友闪动处显示
	 */
public class CommonCGMessage{
	
  
 		/**
		 * 客户端用于时间同步的消息
		 */
	public static void CG_PING() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_PING);
			PlatformService.Send(msgBody);
		}
}