package com.gameserver.bazoo.schedule;

import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

public class BlackWhiteScheduleWhoShouldSwing implements LLISchedule{


	private Player nextPlayer;
	private RoomNumber roomNumber;
	public BlackWhiteScheduleWhoShouldSwing(Player nextPlayer,RoomNumber roomNumber)
	{
		this.nextPlayer=nextPlayer;
		this.roomNumber=roomNumber;
	}


	@Override
	public void execute() {
		Globals.getBlackWhiteService().whoShouldSwing(nextPlayer,roomNumber,true);;
	}

}
