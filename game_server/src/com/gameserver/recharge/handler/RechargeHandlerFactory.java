package com.gameserver.recharge.handler;

/**
 * 充值处理
 * @author wayne
 *
 */
public class RechargeHandlerFactory {

	private static RechargeMessageHandler rechargeMessageHandler = new RechargeMessageHandler();
	private static RechargeMessageRedisHandler rechargeMessageRedisHandler = new RechargeMessageRedisHandler();
	
	public static RechargeMessageHandler getHandler() {
		// TODO Auto-generated method stub
		return rechargeMessageHandler;
	}
	public static RechargeMessageRedisHandler getRedisHandler() {
		// TODO Auto-generated method stub
		return rechargeMessageRedisHandler;
	}

}
