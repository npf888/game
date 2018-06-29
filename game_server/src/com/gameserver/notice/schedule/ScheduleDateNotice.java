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

public class ScheduleDateNotice implements LLISchedule{
	private Logger logger = Loggers.scheduleLogger;
	private long  noticeId;
	private Calendar calendar;
	public ScheduleDateNotice(long id,Calendar calendar) {
		noticeId=id;
		this.calendar=calendar;
	}

	@Override
	public void execute() {
		Notice notice = Globals.getNoticeService().getNoticeById(noticeId);
		if(notice == null){
			return;
		}
		//如果被删除了就不运行了
		if(notice.getIsDelete() == 1){
			return;
		}
		logger.info("跑马灯-预备开始------");
		//看看时间是否 在 start和 end 时间段内  日期
		long startTime = notice.getStartTime();
		long endTime = notice.getEndTime();
		//几点
		long dailyStartTime = notice.getDailyStartTime();
		long dailyEndTime = notice.getDailyEndTime();
		
		//间隔时间
		long IntervalTime = notice.getIntervalTime()*60*1000;
		long now = Globals.getTimeService().now();
		/**
		 * 首先在日期范围内
		 */
		if(now>=(startTime+dailyStartTime) && now < (startTime+dailyEndTime)){
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(now+IntervalTime);
			logger.info("跑马灯-今天-"+TimeUtils.dateToTimeString(cal.getTime())+"-正式开始------");
			Globals.getScheduleService().scheduleOnce(new ScheduleDateNoticeIn(noticeId,cal), LLScheduleEnum.SCHEDULE_NOTICE,cal.getTime());
		}
		
		/**
		 * 设置明天 所以要加上  24小时
		 */
		long tomorrow = now+24*60*60*1000;
		if(tomorrow >= startTime || tomorrow <endTime){
			/**
			 * 设置下一次 跑马灯
			 */
			calendar.setTimeInMillis(calendar.getTimeInMillis()+dailyStartTime);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			Globals.getScheduleService().scheduleOnce(new ScheduleDateNotice(noticeId,calendar), LLScheduleEnum.SCHEDULE_NOTICE, calendar.getTime());
		}
	}

}
