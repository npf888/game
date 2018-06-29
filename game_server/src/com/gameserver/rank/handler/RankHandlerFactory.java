package com.gameserver.rank.handler;

import com.gameserver.common.Globals;

/**
 * 排行
 * @author wayne
 *
 */
public class RankHandlerFactory {

	private static final RankMessageHandler handler = new RankMessageHandler(Globals.getOnlinePlayerService(),Globals.getLangService());

	public static RankMessageHandler getHandler() 
	{
		return handler;
	}
}
