package com.gameserver.signin.handler;

import com.core.util.Assert;
import com.gameserver.common.Globals;
import com.gameserver.common.i18n.LangService;
import com.gameserver.human.Human;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.signin.msg.CGSignIn;

/**
 * 签到
 * @author wayne
 *
 */
public class SignInMessageHandler {

	public SignInMessageHandler(OnlinePlayerService onlinePlayerService,
			LangService langService) {
		// TODO Auto-generated constructor stub
	}

	public void handleSignIn(Player player, CGSignIn cgSignIn) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		Assert.notNull(human);
		long now = Globals.getTimeService().now();
		human.getHumanWeekSignInManager().signIn(cgSignIn.getDay(),now);
		
	}

}
