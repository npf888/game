package com.gameserver.notice.schedule;

import java.util.Date;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.unit.GameUnitList;
import com.gameserver.player.Player;

public class ScheduleNotice implements LLISchedule{
	private Logger logger = Loggers.scheduleLogger;
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		String time=TimeUtils.formatYMDHMSTime(new Date().getTime());
		logger.debug("======>start notice time is:"+time);
		Globals.getNoticeService().checkNotices();
		
		
		try{
			GameUnitList<Player>  players = Globals.getOnlinePlayerService().getOnlinePlayers();
			for(Player player:players){
				//如果已经买了就拉倒
				if(player.getHuman().getNewGuyGift() == 0){
//					player.getHuman().getHumanNewComerManager().isEnd();
				}
			}
		}catch(Exception e){
			logger.error("新手礼包",e);
		}
	}


}
