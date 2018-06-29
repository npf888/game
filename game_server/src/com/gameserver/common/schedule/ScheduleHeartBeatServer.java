package com.gameserver.common.schedule;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;

import com.common.constants.DisconnectReason;
import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.session.ISession;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerExitReason;
import com.gameserver.player.msg.GCNotifyException;

/**
 * 心跳服务器
 * @author wayne
 *
 */
public class ScheduleHeartBeatServer  implements LLISchedule{
	
	private Logger logger = Loggers.scheduleLogger;
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		String time=TimeUtils.formatYMDHMSTime(new Date().getTime());
		logger.debug("======>start HeartBeatServer time is:"+time);
		Globals.getServerStatusService().syncStatus();
		
	}
	
}
