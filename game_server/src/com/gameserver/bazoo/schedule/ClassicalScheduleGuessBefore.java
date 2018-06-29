package com.gameserver.bazoo.schedule;

import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 抢卡之后   竞猜之前 前端 有一段做动画的时间
 * @author JavaServer
 *
 */
public class ClassicalScheduleGuessBefore implements LLISchedule{


	private RoomNumber roomNumber;
	private Player player;
	public ClassicalScheduleGuessBefore(Player player, RoomNumber roomNumber)
	{
		this.player=player;
		this.roomNumber=roomNumber;
	}


	@Override
	public void execute() {
		Globals.getBazooMService().guessBefore(player,roomNumber);
	}

}
