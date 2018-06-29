package com.core.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.helper.LLDateHelper;
import com.core.time.TimeService;
import com.core.util.TimeUtils;
import com.google.common.collect.Lists;



/**
 * 调度各种定时任务：主要包括排序,心跳执行,监控
 * @author Thinker
 */
public class LLScheduleServiceImpl implements LLScheduleService
{
	/**指定延迟时间并执行一次的任务 */
	public static final int SCHEDULE_ONCE_DELAY = 1;
	/** 指定执行时间并执行一次的任务 */
	public static final int SCHEDULE_ONCE_DATE = 2;
	/** 指定延迟时间并周期执行的任务 */
	public static final int SCHEDULE_PERIOD_DELAY = 3;
	
	private static final Logger logger = Loggers.scheduleLogger;
	/** 时间服务 */
	private final TimeService timeService;
	
	/** 定时任务List */
	List<CacheSchedule> cacheScheduleList = Lists.newArrayList();
	/** 当前时间 */
	private long curTime;
	
	public LLScheduleServiceImpl(TimeService timeService) 
	{
		this.timeService = timeService;
	}
	Comparator<CacheSchedule> sortComparator = new Comparator<CacheSchedule>()
	{
		@Override
		public int compare(CacheSchedule gs0,CacheSchedule gs1)
		{
			if (gs0 == null) return -1;
			if (gs1 == null) return 1;
			long diff=gs0.getNeedTime(curTime)-gs1.getNeedTime(curTime);
			if(diff>0) return 1;
			if(diff<0) return -1;
			return 0;
		}
	};
	/**
	 * 任务排序：先执行的在前面处理
	 * @param curTime
	 */
	private void sortSchedule(long curTime)
	{
		this.curTime=curTime;
		Collections.sort(cacheScheduleList, sortComparator);
	}

	
	/**
	 * 指定延迟时间并执行一次的任务
	 */
	@Override
	public void scheduleOnce(LLISchedule schedule,LLScheduleEnum scheduleEnum,long delay)
	{
		if(schedule==null) return;
		//判断类型是否存在
		if(scheduleEnum==null)
		{
			logger.error("scheduleOnce error scheduleEnum is null. delay:" + delay + " schedule:" + schedule);
			return;
		}
		
		if (logger.isInfoEnabled())
		{
			logger.info("scheduleOnce delay:" + delay +" scheduleEnum:"+scheduleEnum.getNameKey()+ " schedule:" + schedule);
		}
		long nowTime=timeService.now();
		CacheSchedule cacheSchedule = new CacheSchedule(schedule,SCHEDULE_ONCE_DELAY,
				scheduleEnum,nowTime+delay,0);
		cacheScheduleList.add(cacheSchedule);
		sortSchedule(nowTime);
	}

	/**
	 * 指定执行时间并执行一次的任务
	 */
	@Override
	public void scheduleOnce(LLISchedule schedule,LLScheduleEnum scheduleEnum,Date d)
	{
		if(schedule==null) return;
		//判断类型是否存在
		if(scheduleEnum==null)
		{
			logger.error("scheduleOnce error scheduleEnum is null. date:" + d + " schedule:" + schedule);
			return;
		}
		
		if (logger.isInfoEnabled())
		{
			logger.info("scheduleOnce date:" + d +" scheduleEnum:"+scheduleEnum.getNameKey()+ " schedule:" + schedule);
		}
		long nowTime=timeService.now();
		CacheSchedule cacheSchedule = new CacheSchedule(schedule,SCHEDULE_ONCE_DATE,
				scheduleEnum,d.getTime(),0);
		cacheScheduleList.add(cacheSchedule);
		sortSchedule(nowTime);
		
		/*for(CacheSchedule e:cacheScheduleList){
			if(e.getScheduleType() == SCHEDULE_ONCE_DATE){
				logger.info("===="+e.getScheduleEnum().getNameKey()+"========");
				
			}
		}*/
	}
	
	@Override
	public void scheduleWithFixedDelay(LLISchedule schedule,LLScheduleEnum scheduleEnum,long delay,long period)
	{
		if(schedule==null) return;
		//判断类型是否存在
		if(scheduleEnum==null)
		{
			logger.error("scheduleWithFixedDelay error scheduleEnum is null. delay:" + delay + " period:"
					+ period + " schedule:" + schedule);
			return;
		}
		
		if (logger.isInfoEnabled())
		{
			logger.info("scheduleWithFixedDelay delay:" + delay + " period:"
					+ period +" scheduleEnum:"+scheduleEnum.getNameKey()+ " schedule:" + schedule);
		}
		long nowTime=timeService.now();
		CacheSchedule cacheSchedule = new CacheSchedule(schedule,SCHEDULE_PERIOD_DELAY,
				scheduleEnum,nowTime+delay,period);
		cacheScheduleList.add(cacheSchedule);
		sortSchedule(nowTime);
	}


	/**
	 * 生成任务消息
	 */
	private String buildScheduleMsg(CacheSchedule cacheSchedule)
	{
		String msg="";
		if(cacheSchedule==null) return msg;
		msg+="CacheSchedule ";
		msg+=" scheduleEnum:"+cacheSchedule.getScheduleEnum().getNameKey();
		msg+=" executeTime:"+LLDateHelper.forDatetime(new Date(cacheSchedule.getExecuteTime()));
		msg+=" period:"+cacheSchedule.getPeriod();
		return msg;
	}
	@Override
	public  void heartBeat()
	{
		long nowTime=timeService.now();
		boolean ifSort=false;
		CacheSchedule cacheSchedule =null;
		
		for (int index=0; index < cacheScheduleList.size(); index++)
		{
			cacheSchedule=cacheScheduleList.get(index);
			if(cacheSchedule==null) continue;
			if(cacheSchedule.getNeedTime(nowTime)>0) break;
			//输出执行日志
			String msg=buildScheduleMsg(cacheSchedule);
			if(logger.isInfoEnabled())
			{
				//AAA 百家乐日志
				//logger.info("execute schedule:" + msg);
			}
			//捕获异常:以免影响后面的任务执行
			try 
	        {
				switch(cacheSchedule.getScheduleType())
				{
					case SCHEDULE_ONCE_DELAY:
					case SCHEDULE_ONCE_DATE:
						logger.info("executeTime"+TimeUtils.formatYMDHMSTime(cacheSchedule.getExecuteTime())+"----curtime+"+TimeUtils.formatYMDHMSTime(nowTime));
						logger.info("执行完-index:"+index+" 剩余的 定时任务信息==============cacheSchedule.getNeedTime(nowTime)>0="+(cacheSchedule.getNeedTime(nowTime)>0)+"---"+cacheSchedule.getScheduleEnum());
					
						/*for(int i=0;i<cacheScheduleList.size();i++){
							CacheSchedule c = cacheScheduleList.get(i);
							logger.info("--前"+c.getScheduleEnum().getNameKey());
						}*/
						cacheSchedule.getSchedule().execute();
						cacheScheduleList.remove(cacheSchedule);
						/*for(int i=0;i<cacheScheduleList.size();i++){
							CacheSchedule c = cacheScheduleList.get(i);
							logger.info("--后"+c.getScheduleEnum().getNameKey());
						}*/
						index--;
						break;
					case SCHEDULE_PERIOD_DELAY:
						ifSort=true;
						cacheSchedule.getSchedule().execute();
						cacheSchedule.setExecuteTime(cacheSchedule.getExecuteTime()+
								cacheSchedule.getPeriod());
						break;
					default:break;
				}
	        }catch(Throwable e)
	        {
	        	logger.error(e.getMessage(),e);
	        	e.printStackTrace();
	        }
		}
		
		if(ifSort) sortSchedule(nowTime);
	}
	@Override
	public void printScheduleInfo()
	{
		CacheSchedule cacheSchedule =null;
		for (int index=0; index < cacheScheduleList.size(); index++)
		{
			cacheSchedule=cacheScheduleList.get(index);
			if(cacheSchedule==null) continue;
			//输出执行日志
			String msg=buildScheduleMsg(cacheSchedule);
			if(logger.isInfoEnabled())
			{
				logger.info("printScheduleInfo:" + msg);
			}
		}
	}
	/**
	 * 定时任务：缓存任务对象主要用来扩展任务信息
	 * @author Thinker
	 * 
	 */
	private class CacheSchedule
	{
		/**定时任务*/
		private LLISchedule schedule;
		/**任务类型*/
		private int scheduleType;
		/**任务信息枚举*/
		private LLScheduleEnum scheduleEnum;
		/**任务执行时间*/
		private long executeTime;
		/**任务周期*/
		private long period;
		
		public CacheSchedule(LLISchedule schedule,int scheduleType,LLScheduleEnum scheduleEnum,
				long executeTime,long period)
		{
			this.schedule = schedule;
			this.scheduleType=scheduleType;
			this.scheduleEnum=scheduleEnum;
			this.executeTime=executeTime;
			this.period=period;
		}
		public long getNeedTime(long curTime)
		{
			return executeTime-curTime;
		}
		public LLISchedule getSchedule()
		{
			return schedule;
		}
		public int getScheduleType()
		{
			return scheduleType;
		}
		public LLScheduleEnum getScheduleEnum()
		{
			return scheduleEnum;
		}
		public long getExecuteTime() 
		{
			return executeTime;
		}
		public void setExecuteTime(long executeTime)
		{
			this.executeTime = executeTime;
		}
		public long getPeriod() 
		{
			return period;
		}
	}
}
