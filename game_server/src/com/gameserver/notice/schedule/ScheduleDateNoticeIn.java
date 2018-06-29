package com.gameserver.notice.schedule;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.notice.Notice;

public class ScheduleDateNoticeIn implements LLISchedule{
	private Logger logger = Loggers.scheduleLogger;
	private long noticeId;
	private Calendar cal;
	
	public ScheduleDateNoticeIn(long id,Calendar cal) {
		noticeId=id;
		this.cal=cal;
	}

	@Override
	public void execute() {
		Notice notice = Globals.getNoticeService().getNoticeById(noticeId);
		//如果被删除了就不运行了
		if(notice == null){
			return;
		}
		//如果被删除了就不运行了
		if(notice.getIsDelete() == 1){
			return;
		}
		logger.info("跑马灯-运行中------");
		
		Globals.getNoticeService().broadcast(notice.getContent());
		
		
		//看看时间是否 在 start和 end 时间段内
		long startTime = notice.getStartTime();
		long endTime = notice.getEndTime();
		
		//间隔时间
		long IntervalTime = notice.getIntervalTime()*60*1000;
		long now = Globals.getTimeService().now();
		long dailyEndTime = notice.getDailyEndTime();
		/**
		 * 如果超了时间  就退出跑马灯
		 */
		if(now >= endTime || now >=(startTime+dailyEndTime)){
			logger.info("跑马灯-今天-"+TimeUtils.dateToTimeString(new Date())+"-结束------");
			return;
		}
		
		/**
		 * 设置下一次
		 */
		cal.setTimeInMillis(cal.getTimeInMillis()+IntervalTime);
		logger.info("跑马灯-今天-"+TimeUtils.dateToTimeString(new Date())+"-下一次------");
		Globals.getScheduleService().scheduleOnce(new ScheduleDateNoticeIn(noticeId,cal), LLScheduleEnum.SCHEDULE_NOTICE,cal.getTime());
	}

}
