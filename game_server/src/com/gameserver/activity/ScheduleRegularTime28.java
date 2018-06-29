package com.gameserver.activity;

import java.text.ParseException;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.util.TimeUtils;
import com.gameserver.activity.msg.GCMonthOrWeek;
import com.gameserver.human.Human;
/**
 * 作废 不用了
 * @author JavaServer
 *
 */
public class ScheduleRegularTime28 implements LLISchedule{
	
	private Logger logger = Loggers.scheduleLogger;
	private String startRegularTime;
	private String now;
	private Human ower;
	private int packCycleTime;
	private int packDurationTime;
	public ScheduleRegularTime28(String regularTime,String now,int packCycleTime,int packDurationTime,Human ower){
		this.startRegularTime=regularTime;
		this.now=now;
		this.ower=ower;
		this.packCycleTime=packCycleTime;
		this.packDurationTime=packDurationTime;
	}
	@Override
	public void execute() {
		try {
			if(startRegularTime.equals(now)){
				//直接发送开始消息
				GCMonthOrWeek gCMonthOrWeek = new GCMonthOrWeek();
				gCMonthOrWeek.setMwtype(0);//周
				gCMonthOrWeek.setStartOrEnd(0);
				ower.getPlayer().sendMessage(gCMonthOrWeek);
				logger.info("+++开始消息发送 当当当++startRegularTime::"+startRegularTime+" -- now::"+now);
			}else{
				//发送结束消息  每周 有两天
				long startRegularTimeLong = TimeUtils.getYMDTime(startRegularTime);
				long nowLong = TimeUtils.getYMDTime(now);
				//结束消息发送    每周的第三天 发送消息
				long diff = (nowLong-startRegularTimeLong)%(packCycleTime*24*3600*1000);
				long one = packDurationTime*24*3600*1000;
				//开始消息发送 
				if(diff==0){
					GCMonthOrWeek gCMonthOrWeek = new GCMonthOrWeek();
					gCMonthOrWeek.setMwtype(0);//周
					gCMonthOrWeek.setStartOrEnd(0);
					ower.getPlayer().sendMessage(gCMonthOrWeek);
					logger.info("+++开始消息发送++startRegularTimeLong::"+startRegularTimeLong+" -- nowLong::"+nowLong+" -- diff::"+diff+" -- one::"+one);
				}
				//结束消息的发送
				if(diff == one){
					GCMonthOrWeek gCMonthOrWeek = new GCMonthOrWeek();
					gCMonthOrWeek.setMwtype(1);//周
					gCMonthOrWeek.setStartOrEnd(1);
					ower.getPlayer().sendMessage(gCMonthOrWeek);
					logger.info("+++结束消息的发送++startRegularTimeLong::"+startRegularTimeLong+" -- nowLong::"+nowLong+" -- diff::"+diff+" -- one::"+one);
				}
				logger.info("startRegularTimeLong::"+startRegularTimeLong+" -- nowLong::"+nowLong+" -- diff::"+diff+" -- one::"+one);
			}
		} catch (ParseException e) {
			logger.error("", e);
		}
		
	}
	
	public void e(){
		
	}

}
