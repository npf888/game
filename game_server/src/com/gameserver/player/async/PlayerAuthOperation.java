package com.gameserver.player.async;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.common.constants.TerminalTypeEnum;
import com.core.async.ILocalRelativeOperation;
import com.gameserver.common.Globals;
import com.gameserver.player.LoginLogicalProcessor;
import com.gameserver.player.auth.UserAuth.AuthResult;

/**
 * 用户登录认证操作
 * 
 * @author Thinker
 * 
 */
public class PlayerAuthOperation implements ILocalRelativeOperation 
{
	private static final Logger logger = Loggers.loginLogger;
	/** 用户id */
	private long _userId = 0L;
	/** 用户随机码 */
	private String _userCode = "";
	/** 服务器id*/
	private String _serverId = "";
	
	/** IP 地址 */
	private String _ipAddr = null;

	/** 终端类型 */
	private TerminalTypeEnum _terminalType = null;
	/** 登录验证结果 */
	private AuthResult _authResult = null;
	/** 登录逻辑处理器 */
	private LoginLogicalProcessor _loginProc = null;
	/** 回调 */
	private ICallback _callback;

	/**
	 * 类参数构造器
	 * 
	 * @param userName
	 * @param password 
	 * @param ipAddr 
	 * @param tt 
	 * @param callback
	 * 
	 */
	public PlayerAuthOperation(long userId,String userCode,String serverId,String ipAddr,TerminalTypeEnum tt,ICallback callback)
	{
		this._userId = userId;
		this._userCode = userCode;
		this._serverId=serverId;
		this._ipAddr = ipAddr;
		this._terminalType = tt;
		this._callback = callback;
		this._loginProc = Globals.getLoginLogicalProcessor();
	}

	@Override
	public int doStart()
	{
		return STAGE_START_DONE;
	}

	@Override
	public int doIo()
	{
		//通过用户名和密码进行验证
		logger.info("通过用户名和密码进行验证");
		this._authResult = this.doAuth(this._userId,this._userCode,this._serverId,this._ipAddr,this._terminalType);
		return STAGE_IO_DONE;
	}
	
	/**
	 * 通过用户名和密码进行验证
	 * 
	 * @param userName
	 * @param userPass
	 * @param ipAddr
	 * @param terminalType
	 * @return 
	 * 
	 */
	private AuthResult doAuth(long userId,String userCode,String serverId,String ipAddr,TerminalTypeEnum terminalType)
	{
		try
		{
			return this._loginProc.authUser(userId, userCode,serverId, ipAddr, terminalType);
		} catch (Exception ex) 
		{
			Loggers.playerLogger.error(" Player: " + userId + " PlayerAuthOperation.doAuthWithCookie: " + ex);
		}
		return null;
	}
	

	@Override
	public int doStop()
	{
		if (this._callback != null) 
		{
			this._callback.onFinished(this._authResult);
		}
		return STAGE_STOP_DONE;
	}

	/**
	 * PlayerAuthOperation 回调接口
	 * 
	 * @author Thinker
	 *
	 */
	public static interface ICallback
	{
		/**
		 * 异步操作完成
		 * 
		 * @param result
		 */
		void onFinished(AuthResult result);
	}
}
