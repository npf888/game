package com.gameserver.baccart;


import com.core.schedule.LLISchedule;
import com.gameserver.common.Globals;


/**
 * 百家乐房间
 * @author wayne
 *
 */
public class ScheduleBaccartRoom implements LLISchedule{


	public ScheduleBaccartRoom()
	{
		
	}


	@Override
	public void execute() {
		Globals.getBaccartService().tick();
	}
}
