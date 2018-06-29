package com.gameserver.mail.handler;

import com.core.util.Assert;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.mail.MailLogic;
import com.gameserver.mail.redismsg.MailAddRedisMessage;
import com.gameserver.player.Player;


/**
 * 邮件redis
 * @author wayne
 *
 */
public class MailRedisMessageHandler {

	/**
	 * 邮件接收
	 * @param mailAddRedisMessage
	 */
	public void handleMailAddRedisMessage(
			MailAddRedisMessage mailAddRedisMessage) {
		// TODO Auto-generated method stub
		long playerId = mailAddRedisMessage.getMailEntity().getCharId();
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(playerId);
		if(player==null)
			return;
		
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		MailLogic.getInstance().receiveMail(human,mailAddRedisMessage.getMailEntity());
	}

}
