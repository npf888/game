package com.gameserver.vip.handler;

/**
 * vip
 * @author wayne
 *
 */
public class VipHandlerFactory {
	
	private static VipMessageHandler vipMessageHandler = new VipMessageHandler();
	public static VipMessageHandler getHandler() {
		// TODO Auto-generated method stub
		return vipMessageHandler;
	}

}
