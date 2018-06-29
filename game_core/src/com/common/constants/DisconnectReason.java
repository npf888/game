package com.common.constants;


import java.util.List;


import com.core.enums.IndexedEnum;

import com.core.util.EnumUtil;


/**
 * 由于玩家被动断线时的错误原因定义
 * @author Thinker
 * 
 */
public enum DisconnectReason implements IndexedEnum
{
	/** 正常断线 */
	NORMAL(0),
	/** 处理消息时异常 */
	HANDLE_MSG_EXCEPTION(1),
	/** 心跳时异常 */
	HEARTBEAT_EXCEPTION(2),
	/** 账号被人从另一个客户端登录 */
	LOGIN_ON_ANOTHER_CLIENT(3),
	/** 角色加载完成时出现异常 */
	FINISH_LOAD_HUMAN_EXCEPTION(4),
	/** 进入游戏失败 */
	ENTER_SERVER_FAIL(5),
	/** 进入场景失败 */
	ENTER_SCENE_FAIL(6),
	/** GM踢人  */
	GM_KICK(7),
	/** 用户名连续登录时间限制 */
	PLAYER_MEET_LOGIN_INTERVAL(8),
	/** 登陆认证失败*/
	PLAYER_AUTH_LOGIN_INVALID(9),
	/** 10分钟后超时踢掉玩家*/
	LASTNETTIMEOUT(10),
	/** 锁号 */
	LOCK(11),
;

	private DisconnectReason(int index)
	{
		this.index =  index;
	}

	public final int index;

	private static List<DisconnectReason> indexes = IndexedEnumUtil.toIndexes(DisconnectReason.values());



	public static DisconnectReason valueOf(int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
}
