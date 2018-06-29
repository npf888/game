package com.core.msg;

/**
 * 定义消息类型,规则如下:
 * 
 * <pre>
 * 1.所有消息类型均为short类型，消息类型保证惟一
 * 2.系统内部消息以SYS_开头
 * 3.客户端发往GameServer的以CG_开头 
 * 4.GameServer发往客户端的以GC_开头 
 * 5.保留消息类型0-100,给系统内部一些特殊消息使用
 * 6.每个子系统或模块的消息类型定义应放在一起
 * </pre>
 * @author Thinker
 */
public final class MessageType
{
	/** Flash socket 发送的policy request请求协议 "<policy" 中第3,4两个字节ol的16进制表示,28524 */
	public static final short FLASH_POLICY = 0x6f6c;
	public static final short QQ_POLICY = 0x775f;
	public static final short MSG_UNKNOWN = 0;
	public static final short GC_QQPOLICY = 51;

	/* === 系统内部消息类型定义开始,范围0~100 === */
	public static final short SYS_SESSION_OPEN = 1;
	public static final short SYS_SESSION_CLOSE = 2;
	public static final short SYS_SCHEDULE = 3;
	public static final short SCHD_ASYNC_FINISH = 10;
	public static final short SCHD_PLAYER_ASYNC_FINISH = 11;
	public static final short SYS_TEST_MSG_LENGTH = 14;
	public static final short SYS_TEST_FLOOD_BYTE_ATTACK = 15;
	public static final short GC_CONTINUOUS_LANDING_ACTIVE_PAGE=16;
	public static final short GC_REG_APN_LINK = 17;
	public static final short CG_REG_APN_LINK = 18;
	public static final short GC_USER_CHARGE =19;
	public static final short CG_USER_CHARGE = 20;
	
	
	private MessageType()
	{
		
	}
}
