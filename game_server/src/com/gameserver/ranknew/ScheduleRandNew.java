package com.gameserver.ranknew;

import com.core.schedule.LLISchedule;
import com.gameserver.common.Globals;

/**
 * 24小时执行
 * @author 郭君伟
 *
 */
public class ScheduleRandNew implements LLISchedule {

	public ScheduleRandNew(){}
	
	@Override
	public void execute() {
	  
      Globals.getRankNewServer().execute();
      
	}

}
