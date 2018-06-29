package com.gameserver.common.handler;

import com.gameserver.common.Globals;
import com.gameserver.common.msg.CGPing;
import com.gameserver.common.msg.GCPing;
import com.gameserver.player.Player;





/**
 * 各模块通用消息处理器
 * 
 * @author Thinker
 * 
 */
public class CommonMessageHandler 
{
	/**
	 * 处理 ping 消息
	 * 
	 * @param player
	 * @param cgPing
	 */
	public void handlePing(Player player, CGPing cgPing)
	{
		
		GCPing msg = new GCPing(Globals.getTimeService().now());
		
		player.sendMessage(msg);
	}

	
}
