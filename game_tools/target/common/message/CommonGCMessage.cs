using System.Collections;

	/**
	 * 常量定义
	 *	【消息显示类型】1- 普通消息类			2-	重要消息类			3-在当前对话窗口直接显示此信息 			4-在好友闪动处显示
	 */
public class CommonGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_HANDSHAKE,GC_HANDSHAKE);
		register(NetMessageType.GC_PING,GC_PING);
		register(NetMessageType.GC_SYSTEM_MESSAGE,GC_SYSTEM_MESSAGE);
	}
 
  	/**
	 * 服务器准备好之后,告知客户端可以进行登录操作
	 */
	public void GC_HANDSHAKE(InputMessage data) 
	{
		CommonHandler.Instance().GC_HANDSHAKE();
	}
 
  	/**
	 * 服务器端响应的时间同步的消息
	 * @param timestamp 服务器当前时间戳
	 */
	public void GC_PING(InputMessage data) 
	{
		long timestamp = data.GetLong();
		CommonHandler.Instance().GC_PING(timestamp);
	}
 
  	/**
	 * 系统提示消息
	 * @param code 消息内容
	 * @param showType 消息显示类型
	 */
	public void GC_SYSTEM_MESSAGE(InputMessage data) 
	{
		int code = data.GetInt();		
		int showType = data.GetShort();		
		CommonHandler.Instance().GC_SYSTEM_MESSAGE(code,showType);
	}
}