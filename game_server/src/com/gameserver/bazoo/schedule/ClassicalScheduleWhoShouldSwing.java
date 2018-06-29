package com.gameserver.bazoo.schedule;

import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 无双吹牛 
 * 一个房间内  绝对的第一次开始，如果 开始过一次， 那么下一次开始 就不是 这个开始
 * @author JavaServer
 *
 */
public class ClassicalScheduleWhoShouldSwing implements LLISchedule{


	private Player nextPlayer;
	private RoomNumber roomNumber;
	public ClassicalScheduleWhoShouldSwing(Player nextPlayer,RoomNumber roomNumber)
	{
		this.nextPlayer=nextPlayer;
		this.roomNumber=roomNumber;
	}


	@Override
	public void execute() {
		Globals.getBazooMService().whoShouldSwing(nextPlayer,roomNumber,true);;
	}

}
