package com.gameserver.player.auth;

import com.common.constants.TerminalTypeEnum;
import com.db.model.UserInfo;

/**
 * 用户认证接口
 * @author Thinker
 */
public interface UserAuth
{
	/**
	 * 验证用户名和密码
	 * 
	 * @param userName
	 * @param password
	 * @param ip
	 * @param loginType
	 * @param terminalType 
	 * @return
	 */
	public AuthResult auth(long userId,String userCode,String serverId, String ip, TerminalTypeEnum terminalType);
	/**
	 * 验证用户名和密码的结果
	 * 
	 * @author Thinker
	 * 
	 */
	public static final class AuthResult
	{
		/** 验证是否成功 */
		public boolean success;
		/** 验证失败是的提示消息 */
		public String message;
		/** 验证成功后取得的对应UserInfo对象 */
		public UserInfo userInfo;
		/** 玩家在线累计时长，秒数，Local接口累计 */
		public long localAccOnlineTime;
	}
}
