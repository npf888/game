package com.gameserver.player.auth;


import org.slf4j.Logger;

import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.common.constants.TerminalTypeEnum;
import com.core.i18n.SysLangService;
import com.core.util.Assert;
import com.db.model.UserInfo;
import com.game.webserver.login.CheckLoginParams;
import com.game.webserver.login.CheckLoginRes;
import com.gameserver.common.Globals;

/**
 * 使用游戏平台进行用户登录校验:目前没有平台则作废
 * @author Thinker
 * 
 */
public class LocalUserAuthImpl implements UserAuth
{
	private SysLangService langService;
	
	private static final Logger logger = Loggers.loginLogger;
	
	public LocalUserAuthImpl(SysLangService langService)
	{
		Assert.notNull(langService);
		this.langService = langService;
	}
	
	@Override
	public AuthResult auth(long userId,String userCode,String serverId, String ip, TerminalTypeEnum terminalType)
	{
		AuthResult result = new AuthResult();
	
		// 请求local接口
		long begin = Globals.getTimeService().now();
		
		logger.info("开始用户认证：uerid="+userId+",userCode:="+userCode+",serverId="+serverId);
		
		CheckLoginParams checkLoginParams = Globals.getSynLoginServerHandler().checkLogin(userId, userCode, serverId);

			// 特例，统计时间跨度
		long time = (Globals.getTimeService().now() - begin) / (1000 * 1000);
		if (time > 1) {
			logger.info("#LocalUserAuthImpl auth Time:"+ time + "ms");
		}
		
		
		if (checkLoginParams.getErrorCode() == 0) {
			CheckLoginRes checkLoginRes = checkLoginParams.getRes();
			UserInfo userInfo=new UserInfo();
			userInfo.setId(checkLoginRes.getUserId());
			userInfo.setName(checkLoginRes.getName());
			userInfo.setImg(checkLoginRes.getImg());
			userInfo.setRole(checkLoginRes.getRole());
			userInfo.setFacebookId(checkLoginRes.getFacebookId());
			userInfo.setAccountId(checkLoginRes.getAccountId());
			userInfo.setUpdateFbInfo(checkLoginRes.isUpdateFbInfo());
			userInfo.setDeviceMac(checkLoginRes.getDeviceMac());
			result.userInfo = userInfo;
			result.success = true;
		} else 
		{
			logger.warn("平台验证失败, loginName = " + userId + " :: LocalUserAuthImpl.auth");
			result.success = false;
			result.message = getErrorInfo(checkLoginParams.getErrorCode());
		}
	
		
		return result;
	}


	
	/**
	 * 解析平台返回的错误代码
	 * 
	 * @param errorCode 平台返回的错误代码
	 * @return 解析出的错误信息
	 */
	private String getErrorInfo(int errorCode) 
	{
		return langService.read(LangConstants.LOGIN_UNKOWN_ERROR);
	}


}
