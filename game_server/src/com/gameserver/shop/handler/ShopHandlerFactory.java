package com.gameserver.shop.handler;


/**
 * 商城处理器工厂
 * @author wayne
 *
 */
public class ShopHandlerFactory {

	private static final ShopMessageHandler handler = new ShopMessageHandler();

	public static ShopMessageHandler getHandler() 
	{
		return handler;
	}
	
}
