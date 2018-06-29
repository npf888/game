package com.game.webserver.login;

import com.game.webserver.recharge.CheckGooglePlayRechargeParam;

/**
 * 
 * @author wayne
 *
 */
public interface ILoginServerHandler {

	/**
	 * 用户登录
	 * @param deviceMac
	 * @return
	 */
	public VisitorLoginParams visitorLogin(String deviceMac,String deviceId,String macAddress,String androidId);
	
	/**
	 * 用户登录验证
	 * @param userId
	 * @param userCode
	 * @param timeLong
	 * @param serverId
	 * @return
	 */
	public CheckLoginParams checkLogin(long userId,String userCode,String serverId);
	
	/**
	 * google play 验证
	 */
	public CheckGooglePlayRechargeParam verifyGooglePlayRecharge(String packageName,String productId,String token);

	public ResetUpdateFbInfoParams resetUpdateFbInfo(long userId);
	
}
