package com.gameserver.player;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 玩家退出服务器原因定义
 * @author Thinker
 */
public enum PlayerExitReason implements IndexedEnum 
{
	/** 未知退出 */
	LOGOUT(1),
	/** 玩家点击home键暂时退出 */
	TEMPORARYEXIT(2),
	/** GS停机 */
	SERVER_SHUTDOWN(3),
	/** 服务器处理出错 */
	SERVER_ERROR(4),
	/** 服务器人数已满 */
	SERVER_IS_FULL(5),
	/** GM踢掉 */
	GM_KICK(6),
	/** 自己把自己踢掉 */
	MULTI_LOGIN(7),
	/** 防沉迷阻止登录或者踢出 */
	WALLOW_KICK(8),
	/** GS踢人*/
	SERVER_KICK(9),
	/** 登陆认证失败*/
	PLAYER_AUTH_LOGIN_INVALID(10),
	/** 断网退出*/
	OFF_NETWORK(11),
	/** 主动退出*/
	INITIATIVE(12),
	/** 10分钟后踢掉玩家*/
	LASTNETTIMEOUT(13),
	/** 封号  */
	LOCK(14),
	/** 崩溃 */
	BREAKDOWN(15),
	;

	public final int index;
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	private PlayerExitReason(int index)
	{
		this.index = index;
	}
	
	private static final List<PlayerExitReason> values = IndexedEnumUtil.toIndexes(PlayerExitReason.values());

	public static PlayerExitReason valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}
}
