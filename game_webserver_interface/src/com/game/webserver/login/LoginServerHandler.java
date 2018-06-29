package com.game.webserver.login;

import com.game.webserver.Config.LocalConfig;
import com.game.webserver.exception.LocalException;
import com.game.webserver.recharge.CheckGooglePlayRechargeParam;
import com.game.webserver.recharge.CheckGooglePlayRechargeRes;

public class LoginServerHandler implements ILoginServerHandler{

	private LocalConfig localConfig;
	
	public LoginServerHandler() throws LocalException
	{
		this.localConfig = new LocalConfig();
	}
	
	public LoginServerHandler(LocalConfig localConfig) {
		this.localConfig = localConfig;
		
	}
	
	public LocalConfig getLocalConfig(){
		return this.localConfig;
	}

	@Override
	public CheckLoginParams checkLogin(long userId, String userCode,
			String serverId) {
		
		CheckLoginParams checkLoginParams = new CheckLoginParams(localConfig.getRequestDomain(),"api/account/checkLogin",true);
		checkLoginParams.setServerId(Integer.parseInt(serverId));
		checkLoginParams.setUserCode(userCode);
		checkLoginParams.setUserId(userId);
		checkLoginParams.send();
	
		return checkLoginParams;
	}

	@Override
	public VisitorLoginParams visitorLogin(String deviceMac,String deviceId,String macAddress,String androidId) {
		// TODO Auto-generated method stub
		VisitorLoginParams visitorLoginParams = new VisitorLoginParams(localConfig.getRequestDomain(),"api/account/visitorLogin",true);
		visitorLoginParams.setDeviceMac(deviceMac);
		visitorLoginParams.setDeviceId(deviceId);
		visitorLoginParams.setMacAddress(macAddress);
		visitorLoginParams.setAndroidId(androidId);
		visitorLoginParams.send();
		return visitorLoginParams;
	}

	@Override
	public CheckGooglePlayRechargeParam verifyGooglePlayRecharge(
			String packageName,String productId,String token) {
		// TODO Auto-generated method stub
		CheckGooglePlayRechargeParam checkGooglePlayRechargeParam = new CheckGooglePlayRechargeParam(localConfig.getRequestDomain(),"api/recharge/googleplay.json",true);
		checkGooglePlayRechargeParam.setPackageName(packageName);
		checkGooglePlayRechargeParam.setProductId(productId);
		checkGooglePlayRechargeParam.setToken(token);
		checkGooglePlayRechargeParam.send();
		return checkGooglePlayRechargeParam;
	}

	@Override
	public ResetUpdateFbInfoParams resetUpdateFbInfo(long userId) {
		
		ResetUpdateFbInfoParams resetUpdateFbInfoParams = new ResetUpdateFbInfoParams(localConfig.getRequestDomain(),"api/fbInfoUpdated",true);
		resetUpdateFbInfoParams.setUserId(userId);
		resetUpdateFbInfoParams.send();
	
		return resetUpdateFbInfoParams;
	}
}
