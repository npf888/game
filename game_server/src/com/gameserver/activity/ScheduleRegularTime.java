package com.gameserver.activity;

import java.util.Date;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.human.Human;
/**
 * 周 特惠充值活动
 * @author JavaServer
 *
 */
public class ScheduleRegularTime  implements LLISchedule{
	
	private Logger logger = Loggers.scheduleLogger;

	private int packDurationTime;//持续时间 已经算成 天了
	private Human ower;
	private Date now;
	private HumanMonthWeek humanMonthWeek;
	public ScheduleRegularTime(HumanMonthWeek humanMonthWeek,Date now,Human ower,int packDurationTime){
		this.now=now;
		this.ower=ower;
		this.packDurationTime=packDurationTime;
		this.humanMonthWeek=humanMonthWeek;
	}
	@Override
	public void execute() {
		
		try {
			//结束特惠 礼包
			ower.getHumanRegularTimeManager().sendMessage(0, 1,0l,now, "结束");
			ower.getHumanRegularTimeManager().removeHumanMonthWeek(humanMonthWeek);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	
	
	
	
	
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public int getPackDurationTime() {
		return packDurationTime;
	}
	public void setPackDurationTime(int packDurationTime) {
		this.packDurationTime = packDurationTime;
	}
	public Human getOwer() {
		return ower;
	}
	public void setOwer(Human ower) {
		this.ower = ower;
	}
	public Date getNow() {
		return now;
	}
	public void setNow(Date now) {
		this.now = now;
	}
	
	
	

}
