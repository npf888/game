package com.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.core.time.TimeService;

/**
 * 时间的工具类
 * @author Thinker
 *  
 */
public class TimeUtils
{
	/** 毫秒 */
	public static final long MILLI_SECOND = TimeUnit.MILLISECONDS.toMillis(1);
	/** 秒 */
	public static final long SECOND = TimeUnit.SECONDS.toMillis(1);
	/** 分 */
	public static final long MIN = TimeUnit.MINUTES.toMillis(1);
	/** 时 */
	public static final long HOUR = TimeUnit.HOURS.toMillis(1);
	/** 天 */
	public static final long DAY = TimeUnit.DAYS.toMillis(1);

	/** 每分钟秒数 */
	public static final int SECONDS_MIN = (int) (MIN / SECOND);
	/** 每小时秒数 */
	public static final int SECONDS_HOUR = (int) (HOUR / SECOND);
	/** 每天小时数 */
	public static final int HOUR_DAY = (int) (DAY / HOUR);
	/** 一周的天数 */
	private static final int DAYOFWEEK_CARDINALITY = 7;

	/** 年月日 时分, 格式如: 2011-01-11 01:10 */
	private static final DateFormat ymdhmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/** 年月日，格式如1970-07-10 */
	private static final DateFormat ymdFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	/** 小时和分钟数，格式如10:20 */
	private static final DateFormat hmFormat = new SimpleDateFormat("HH:mm");
	private static final Calendar calendar = Calendar.getInstance();
	public static TimeZone TIME_ZONE;
	
	private static final SimpleDateFormat hmsFormat = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * 年月日时分秒 格式：2012-04-13 16：00：00
	 */
	private static final DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 去除字符串时间中的秒
	 * @param time
	 * @return
	 */
	public static String removeSec(String time){
		if(StringUtils.isEmpty(time))return "";
		Date d=null;
		try {
			d = hmsFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return hmFormat.format(d);
	}
 
	/**
	 * 判断是否合法的时间格式(HH:mm:ss)
	 * 
	 * @param dayTime
	 * @return
	 */
	public static boolean isValidDayTime(String dayTime) {
		try {
			String[] _timeStr = dayTime.split(":");
			int _hour = Integer.parseInt(_timeStr[0]);
			int _minute = Integer.parseInt(_timeStr[1]);
			int _second = Integer.parseInt(_timeStr[2]);
			if (_hour < 0 || _hour > 23) {
				return false;
			}

			if (_minute < 0 || _minute > 59) {
				return false;
			}

			if (_second < 0 || _second > 59) {
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断是否合法的时间格式(HH:mm)
	 * 
	 * @param hhmm
	 * @return
	 */
	public static boolean isValidHhMmTime(String hhmm) {
		try {
			String[] _timeStr = hhmm.split(":");
			int _hour = Integer.parseInt(_timeStr[0]);
			int _minute = Integer.parseInt(_timeStr[1]);
			if (_hour < 0 || _hour > 23) {
				return false;
			}

			if (_minute < 0 || _minute > 59) {
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据创建时间和有效时间计算截止时间
	 * 
	 * @param start
	 *            物品的创建时间
	 * @param validTime
	 *            物品的有效时间长度
	 * @param timeUnit
	 *            有效时间的单位 {@link TimeUtils#MILLI_SECOND} ~ {@link TimeUtils#DAY}
	 * @return 物品的截止时间
	 */
	public static long getDeadLine(Timestamp start, long validTime,
			long timeUnit) {
		return TimeUtils.getDeadLine(start.getTime(), validTime, timeUnit);
	}

	/**
	 * 根据创建时间和有效时间计算截止时间
	 * 
	 * @param start
	 *            物品的创建时间
	 * @param validTime
	 *            物品的有效时间长度
	 * @param timeUnit
	 *            有效时间的单位 {@link TimeUtils#MILLI_SECOND} ~ {@link TimeUtils#DAY}
	 * @return 物品的截止时间
	 */
	public static long getDeadLine(long start, long validTime, long timeUnit) {
		return start + validTime * timeUnit;
	}

	/**
	 * 获取当天零点时间
	 * 
	 * @return
	 */
	public static long getTodayBegin(TimeService timeService) {
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TimeZone.getDefault());
		_calendar.setTimeInMillis(timeService.now());
		_calendar.set(Calendar.HOUR_OF_DAY, 0);
		_calendar.set(Calendar.MINUTE, 0);
		_calendar.set(Calendar.SECOND, 0);
		_calendar.set(Calendar.MILLISECOND, 0);
		return _calendar.getTimeInMillis();
	}
	/**
	 * 获取特定时间当天零点时间
	 * 
	 * @return
	 */
	public static long getTodayBeginTime(long timeService) {
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TimeZone.getDefault());
		_calendar.setTimeInMillis(timeService);
		_calendar.set(Calendar.HOUR_OF_DAY, 0);
		_calendar.set(Calendar.MINUTE, 0);
		_calendar.set(Calendar.SECOND, 0);
		_calendar.set(Calendar.MILLISECOND, 0);
		return _calendar.getTimeInMillis();
	}
	
	/**
	 * 获取特定日期当天的零点时间
	 * 
	 * @return
	 */
	public static long getBeginOfDay(long time) {
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TimeZone.getDefault());
		_calendar.setTimeInMillis(time);
		_calendar.set(Calendar.HOUR_OF_DAY, 0);
		_calendar.set(Calendar.MINUTE, 0);
		_calendar.set(Calendar.SECOND, 0);
		_calendar.set(Calendar.MILLISECOND, 0);
		return _calendar.getTimeInMillis();
	}
	
	/**
	 * 获得一周开始时间
	 */
	public static long getBeginOfWeek(long time){
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TimeZone.getDefault());
		_calendar.setTimeInMillis(time);

		_calendar.set(Calendar.HOUR_OF_DAY, 0);
		_calendar.set(Calendar.MINUTE, 0);
		_calendar.set(Calendar.SECOND, 0);
		_calendar.set(Calendar.MILLISECOND, 0);
		_calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY); // Monday
		return _calendar.getTimeInMillis();
	}
	
	/**
	 * 获取特定日期当天的23点时间
	 * 
	 * @return
	 */
	public static long getEndOfDay(long time) {
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TimeZone.getDefault());
		_calendar.setTimeInMillis(time);
		_calendar.set(Calendar.HOUR_OF_DAY, 23);
		_calendar.set(Calendar.MINUTE, 59);
		_calendar.set(Calendar.SECOND, 59);
		_calendar.set(Calendar.MILLISECOND, 0);
		return _calendar.getTimeInMillis();
	}
	
	/**
	 * 获取时间戳字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getUrlTimeStamp(Date date) {
		DateFormat _format = new SimpleDateFormat("yyyyMMddHHmmss");
		return _format.format(date);
	}

	/**
	 * 是否是同一天
	 * 
	 * @param src
	 * @param target
	 * @return true:同一天
	 */
	public static boolean isSameDay(long src, long target) {
		
		int offset = TIME_ZONE.getRawOffset(); // 只考虑了时区，没考虑夏令时
		return (src + offset) / DAY == (target + offset) / DAY;
	}

	/**
	 * 将分钟数转换为小时数和分钟数的数组 如80分钟转换为1小时20分
	 * 
	 * @param mins
	 * @return
	 */
	public static int[] toTimeArray(int mins) {
		int[] _result = new int[2];
		_result[0] = (int) (mins * MIN / HOUR);
		_result[1] = (int) (mins - _result[0] * HOUR / MIN);
		return _result;
	}

	/**
	 * 以格式{@link TimeUtils#hmFormat}解析数据，返回其表示的毫秒数
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static long getHMTime(String source) throws ParseException {
		Date date = hmFormat.parse(source);
		calendar.setTime(date);
		calendar.setTimeZone(TimeZone.getDefault());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return hour * TimeUtils.HOUR + minute * TimeUtils.MIN;
	}

	/**
	 * 返回小时：分钟格式的时间
	 * 
	 * @param time
	 * @return
	 */
	public static String formatHMTime(long time) {
		return hmFormat.format(new Date(time));
	}
	/**
	 * 返回小时：分钟：秒格式的时间
	 * @param time
	 * @return
	 */
	public static String formatHMSTime(long time){
		return hmsFormat.format(new Date(time));
	}
	/**
	 * 以格式{@link TimeUtils#ymdhmsFormat}解析数据，返回其表示的毫秒数
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static long getYMDHMSTime(String source) throws ParseException {
		Date date = ymdhmsFormat.parse(source);
		return date.getTime();
	}
	public static Date getYMDHMSDate(String source) {
		try{
			return ymdhmsFormat.parse(source);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取排行奖励结算最后结算时间(字符串的时间格式)
	 */
	public static long getYMDHMSTimeWithDefault(String source)
	{
		long parseTime=0L;
		try
		{
			parseTime=TimeUtils.getYMDHMSTime(source);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return parseTime;
	}
	/**
	 * 以格式{@link TimeUtils#ymdFormat}解析数据，返回其表示的毫秒数
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static long getYMDTime(String source) throws ParseException {
		Date date = ymdFormat.parse(source);
		return date.getTime();
	}
	public static Date getYMDTimeFromStr(String source){
		try{
			return ymdFormat.parse(source);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回 <b>年份-月份-日期</b> 格式的时间. 例如: 2012-12-24
	 * 
	 * @param time
	 * @return
	 */
	public static String formatYMDTime(long time) {
		return ymdFormat.format(time);
	}

	/**
	 * 以格式{@link TimeUtils#ymdhmFormat}解析数据，返回其表示的毫秒数
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static long getYMDHMTime(String source) throws ParseException {
		Date date = ymdhmFormat.parse(source);
		return date.getTime();
	}
	
	/**
	 * 以格式{@link TimeUtils#ymdhmFormat}解析数据，返回其对应的Calendar的实例
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getCalendarByYMDHM(String source) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getDefault());
		Date date = ymdhmFormat.parse(source);
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 返回 <b>年份-月份-日期 小时:分钟</b> 格式的时间. 例如: 2012-12-24 15:01
	 * 
	 * @param time
	 * @return
	 */
	public static String formatYMDHMTime(long time) {
		return ymdhmFormat.format(time);
	}
	
	/**
	 * 返回 <b>年份-月份-日期 小时:分钟:秒</b> 格式的时间. 例如: 2012-12-24 15:01:01
	 * 
	 * @param time
	 * @return
	 */
	public static String formatYMDHMSTime(long time) {
		return ymdhmsFormat.format(time);
	}
	
	/**
	 * 返回 <b>年份-月份-日期 小时:分钟:秒</b> 格式的时间. 例如: 2012-12-24 15:01:01
	 * 
	 * @param time
	 * @return
	 */
	public static Date formatYMDHMSTimeToDate(long time) {
		Date day=null;
		try {
			day= ymdhmsFormat.parse(ymdhmsFormat.format(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	
	public static void main(String[] args) {
		String str = formatYMDHMSTime(1479981895660l);
		String[] ss = str.split(" ");
		System.out.println(ss[0]+"T"+ss[1]);
	}
	

	/**
	 * 返回按时间单位计算后的ms时间，该时间必须足够小以致可用整型表示
	 * 
	 * @param time
	 * @param fromTimeUnit
	 * @return
	 */
	public static long translateTime(int time, long fromTimeUnit) {
		return TimeUtils.translateTime(time, fromTimeUnit, MILLI_SECOND);
	}

	/**
	 * 将指定的时间值转化为期望单位的时间值
	 * 
	 * @param time
	 * @param fromTimeUnit
	 * @param toTimeUnit
	 * @return
	 */
	public static long translateTime(long time, long fromTimeUnit,
			long toTimeUnit) {
		Assert.isTrue(time >= 0);
		long milliTime = time * fromTimeUnit / toTimeUnit;
		Assert.isTrue(milliTime <= Long.MAX_VALUE, String.format(
				"The time value %d is too big!", time));
		return milliTime;
	}

	/**
	 * 获得指定时间的小时数
	 * 
	 * @param time
	 * @return
	 */
	public static int getHourTime(long time) {
		calendar.setTimeInMillis(time);
		calendar.setTimeZone(TimeZone.getDefault());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 设置指定时间的设置为给定的时间数(不改变的时间数可填-1)
	 * 
	 * @param time
	 * @param year
	 * @param month
	 * @param day
	 *            (月中的天数)
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static long getTime(long time, int year, int month, int day,
			int hour, int minute, int second) {
		calendar.setTimeInMillis(time);
		calendar.setTimeZone(TimeZone.getDefault());
		int _unChange = -1;
		if (year != _unChange) {
			calendar.set(Calendar.YEAR, year);
		}
		if (month != _unChange) {
			calendar.set(Calendar.MONTH, month);
		}
		if (day != _unChange) {
			calendar.set(Calendar.DAY_OF_MONTH, day);
		}
		if (hour != _unChange) {
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		}
		if (minute != _unChange) {
			calendar.set(Calendar.MINUTE, minute);
		}
		if (second != _unChange) {
			calendar.set(Calendar.SECOND, second);
		}
		return calendar.getTimeInMillis();
	}

	/**
	 * 获得修正后的时间
	 * 
	 * @param originTime
	 * @param changeYear
	 * @param changeMonth
	 * @param changeDay
	 * @param changeHour
	 * @param changeMinute
	 * @param changeSecond
	 * @return
	 */
	public static long getChangeTime(long originTime, int changeYear,
			int changeMonth, int changeDay, int changeHour, int changeMinute,
			int changeSecond) {
		calendar.setTimeInMillis(originTime);
		calendar.setTimeZone(TimeZone.getDefault());
		int _unChange = 0;
		if (changeYear != _unChange) {
			calendar.add(Calendar.YEAR, changeYear);
		}
		if (changeMonth != _unChange) {
			calendar.add(Calendar.MONTH, changeMonth);
		}
		if (changeDay != _unChange) {
			calendar.add(Calendar.DAY_OF_MONTH, changeDay);
		}
		if (changeHour != _unChange) {
			calendar.add(Calendar.HOUR_OF_DAY, changeHour);
		}
		if (changeMinute != _unChange) {
			calendar.add(Calendar.MINUTE, changeMinute);
		}
		if (changeSecond != _unChange) {
			calendar.add(Calendar.SECOND, changeSecond);
		}
		return calendar.getTimeInMillis();
	}

	/**
	 * 判断start和end是否在同一个星期内(周一为一周开始)
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @author haijiang.jin
	 * @date 2009-02-04
	 */
	public static boolean isInSameWeek(long start, long end) {
		Calendar st = Calendar.getInstance();
		st.setTimeZone(TimeZone.getDefault());
		st.setTimeZone(TIME_ZONE);
		st.setTimeInMillis(start);
		Calendar et = Calendar.getInstance();
		et.setTimeZone(TIME_ZONE);
		et.setTimeInMillis(end);
		int days = Math.abs(TimeUtils.getSoFarWentDays(st, et));
		if (days < TimeUtils.DAYOFWEEK_CARDINALITY) {
			// 设置Monday为一周的开始
			st.setFirstDayOfWeek(Calendar.MONDAY);
			et.setFirstDayOfWeek(Calendar.MONDAY);
			if (st.get(Calendar.WEEK_OF_YEAR) == et.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		}
		return false;
	}
	

	 /**
    * 获取当前日期是星期几(周一为第一天)
    * 
    * @param dt
    * @return 当前日期是星期几
    */
   public static int getWeekDayOfTime(long now) {	        
       Calendar cal = Calendar.getInstance();
       cal.setTimeZone(TIME_ZONE);
       cal.setFirstDayOfWeek(Calendar.MONDAY);
       cal.setTimeInMillis(now);
       return cal.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : cal.get(Calendar.DAY_OF_WEEK) - 1;
   }

	/**
	 * 以日期中的日为实际计算单位，计算两个时间点实际日的差距 比如 12-1 23:00 和12-2 01:00，相差1天，而不是小于24小时就算做0天
	 * 如果(now - st)为正，则表示now在st之后
	 * 
	 * @param st
	 * @param now
	 * @return
	 */
	public static int getSoFarWentDays(Calendar st, Calendar now) {

		int sign = st.before(now) ? 1 : -1;
		if (now.before(st)) {
			Calendar tmp = now;
			now = st;
			st = tmp;
		}
		int days = now.get(Calendar.DAY_OF_YEAR) - st.get(Calendar.DAY_OF_YEAR);
		if (st.get(Calendar.YEAR) != now.get(Calendar.YEAR)) {
			Calendar cloneSt = (Calendar) st.clone();
			cloneSt.setTimeZone(TIME_ZONE);
			while (cloneSt.get(Calendar.YEAR) != now.get(Calendar.YEAR)) {
				days += cloneSt.getActualMaximum(Calendar.DAY_OF_YEAR);
				cloneSt.add(Calendar.YEAR, 1);
			}
		}

		return days * sign;
	}
	
	
	public static int getSoFarWentHours(long time1, long time2) {
		Calendar st = Calendar.getInstance();
		st.setTimeZone(TIME_ZONE);
		st.setTimeInMillis(time1);
		
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(time2);
		
		
		if (now.before(st)) {
			Calendar tmp = now;
			now = st;
			st = tmp;
		}
		
		st.clear(Calendar.MILLISECOND);
		st.clear(Calendar.SECOND);
		st.clear(Calendar.MINUTE);
		
		int diffHour = 0;
		Calendar cloneSt = (Calendar) st.clone();
		cloneSt.setTimeZone(TIME_ZONE);
		while(cloneSt.before(now))
		{
			cloneSt.add(Calendar.HOUR, 1);
			diffHour++;
		}
		
		if(diffHour != 0)
		{
			return diffHour - 1;
		}
		else
		{
			return diffHour;
		}
	}
	
	/**
	 * 获取两个时间的小时相差数, 经修改getSoFarWentHours(long time1, long time2)而来.
	 * 例：8:59:59与9:00:00相差1小时
	 * 
	 * @param time1
	 *            时间1
	 * @param time2
	 *            时间2
	 * @return 两个时间的小时相差数
	 */
	public static int getSoFarWentHours2(long time1, long time2) {
		Calendar st = Calendar.getInstance();
		st.setTimeZone(TIME_ZONE);
		st.setTimeInMillis(time1);

		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(time2);

		if (now.before(st)) {
			Calendar tmp = now;
			now = st;
			st = tmp;
		}

		st.clear(Calendar.MILLISECOND);
		st.clear(Calendar.SECOND);
		st.clear(Calendar.MINUTE);

		now.clear(Calendar.MILLISECOND);
		now.clear(Calendar.SECOND);
		now.clear(Calendar.MINUTE);

		int diffHour = 0;
		Calendar cloneSt = (Calendar) st.clone();
		while (cloneSt.before(now)) {
			cloneSt.add(Calendar.HOUR, 1);
			diffHour++;
		}

		return diffHour;
	}
	
	/**
	 * specTime is in [st,now] or not?
	 * @param st
	 * @param now
	 * @param specTime
	 * @return
	 */
	private static boolean hasSpecTimeBetween(long st, long now, long specTime)
	{
		if(st <= specTime && specTime <= now)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 得到从time1 到time2 中,specTime所指定的时分秒的时刻,有几次
	 * @param time1
	 * @param time2
	 * @param specTime
	 * @return
	 */
	public static int getSpecTimeCountBetween(long time1, long time2, long specTime)
	{
		Calendar st = Calendar.getInstance();
		st.setTimeZone(TIME_ZONE);
		st.setTimeInMillis(time1);
		
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(time2);
		
		Calendar spec = Calendar.getInstance();
		spec.setTimeInMillis(specTime);
		
		if (now.before(st)) {
			Calendar tmp = now;
			now = st;
			st = tmp;
		}
		
		//第一个时间的年月日和被比较时间的时间部分合成
		Calendar st_spec = mergeDateAndTime(st,spec);
		
		if(isSameDay(time1, time2))
		{
			if(hasSpecTimeBetween(time1,time2,st_spec.getTimeInMillis()))
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
			
		int diffDay = 0;
		Calendar cloneSt = (Calendar) st_spec.clone();
		cloneSt.setTimeZone(TIME_ZONE);
		while(cloneSt.before(now))
		{
			cloneSt.add(Calendar.DATE, 1);
			diffDay++;
		}
		
		if(st.after(st_spec))
		{
			diffDay--;
		}
		
		return diffDay;
	}
	
	
	/**
	 * 把日期和时间合并
	 * 
	 * @param date
	 *            代表一个日期，方法其只取日期部分
	 * @param time
	 *            代表一个时间，方法其只取时间部分
	 * @return
	 */
	public static Calendar mergeDateAndTime(Calendar date, Calendar time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TIME_ZONE);
		cal.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date
				.get(Calendar.DATE), time.get(Calendar.HOUR_OF_DAY), time
				.get(Calendar.MINUTE), time.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	
	/**
	 * 获取几天后的当前时间点
	 * @param day
	 * @return
	 */
	public static Date getAfterToday(int day)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TIME_ZONE);
		c.add(Calendar.DATE, day);
		
		return c.getTime();
	}

	
	/**
	 * 设置几分钟之后的时间点
	 * @param minutes
	 * @return
	 */
	public static Date getAfterMinutes(int minutes)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TIME_ZONE);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+minutes);
		
		return c.getTime();
	}
	
	/**
	 * 获取当天的某个时间点
	 * 
	 * @param now 当前时间
	 * @param someTimeStr 时间字符串
	 * @return 
	 * 
	 */
	public static long getTodaySomeTime(long now, String someTimeStr)
	{
		if (now <= 0 || 
			someTimeStr == null ||
			someTimeStr.isEmpty()) {
			// 如果当前时间 <= 0, 
			// 或者时间字符串为空, 
			// 则直接退出!
			return 0;
		}

		// 获取今天 0 点时间
		long time0 = getBeginOfDay(now);
		// 获取偏移时间
		long offsetTime = getHMSTime(someTimeStr);

		// 返回时间点
		return time0 + offsetTime;
	}

	/**
	 * 判断当前时间是否可以执行重置操作, 例如在凌晨 02:00 重置玩家征收次数
	 * 
	 * @param now 当前时间戳
	 * @param lastOpTime 上次操作时间
	 * @param resetTimeStr 重置时间字符串, 例如: 02:00
	 * @return
	 * 
	 */
	public static boolean canDoResetOp(long now, long lastOpTime, String resetTimeStr) {
		if (resetTimeStr == null || 
			resetTimeStr.isEmpty()) {
			return false;
		}

		if (now - lastOpTime > DAY) {
			// 如果时间间隔已相差 1 天, 
			// 则直接返回 true!
			return true;
		}
		
		// 根据上一次操作时间获取重置时间戳
		long lr = getTodaySomeTime(lastOpTime, resetTimeStr);

		if (canDoResetOp(now, lastOpTime, lr)) {
			return true;
		}

		// 根据当前时间获取重置时间戳
		long cr = getTodaySomeTime(now, resetTimeStr);
		
		if (canDoResetOp(now, lastOpTime, cr)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断当前时间是否可以执行重置操作, 例如在凌晨 02:00 重置玩家征收次数
	 * 
	 * @param now 当前时间戳
	 * @param lastOpTime 上次操作时间
	 * @param resetTime 重置时间戳
	 * @return
	 * 
	 */
	public static boolean canDoResetOp(long now, long lastOpTime, long resetTime) {
		return (lastOpTime < resetTime) && (now > resetTime);
	}

	/**
	 * 获取 "小时:分钟:秒" 所表示的时间戳
	 * 
	 * @param s
	 * @return
	 */
	public static long getHMSTime(String str) {
		try {
			Date date = hmsFormat.parse(str);
			
			calendar.setTime(date);
			calendar.setTimeZone(TIME_ZONE);
			int h = calendar.get(Calendar.HOUR_OF_DAY);
			int m = calendar.get(Calendar.MINUTE);
			int s = calendar.get(Calendar.SECOND);
			
			return h * TimeUtils.HOUR + m * TimeUtils.MIN + s * TimeUtils.SECOND;
		} catch (Exception ex) {
			return 0L;
		}
	}
	
	public static int getNowHour(TimeService timeService) {
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TIME_ZONE);
		_calendar.setTimeInMillis(timeService.now());
		return _calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getNowDay(TimeService timeService) {
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TIME_ZONE);
		if (timeService != null)
			_calendar.setTimeInMillis(timeService.now());
		else
			_calendar.setTimeInMillis(System.currentTimeMillis());
		int day = _calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	public static short getNowMonth(TimeService timeService) {
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TIME_ZONE);
		if (timeService != null)
			_calendar.setTimeInMillis(timeService.now());
		else
			_calendar.setTimeInMillis(System.currentTimeMillis());
		short month = (short)_calendar.get(Calendar.MONTH);
		return month;
	}
	/**
	 * 当月最大天数
	 * @param timeService
	 * @return
	 */
	public static short getMaxDayOfMonth(TimeService timeService) {
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TIME_ZONE);
		if (timeService != null)
			_calendar.setTimeInMillis(timeService.now());
		else
			_calendar.setTimeInMillis(System.currentTimeMillis());
		short days = (short)_calendar.getMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}
	
	/**
	 * 判断时间是否在同一个月内
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean IsSameMonth(long time1, long time2){
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TIME_ZONE);
		_calendar.setTimeInMillis(time1);
		short month1 = (short)_calendar.get(Calendar.MONTH);
		_calendar.setTimeInMillis(time2);
		short month2 = (short)_calendar.get(Calendar.MONTH);
		
		return month1 == month2;
	}
	
	 /**
     * 计算Cd时间
     * @param cdTime
     * @param nowTime
     * @param coefficient
     * @return
	 */
	 public static int getIntCdtime(Date cdtime,long nowTime,long coefficient){
		 if(coefficient<0) return 0;
		//计算玩家挑战的cd时间，当前系统时间减去数据库中上次存储的cd时间，再换算成秒
		 int cdTime=0;
		 if(cdtime==null){
			 return cdTime;
	 	 }else{
	 		long tmpTime= (cdtime.getTime()+coefficient)-nowTime;
	 		if(tmpTime>0)
	 			cdTime=(int)(tmpTime/TimeUtils.SECOND);
		 }
		 return cdTime;
	 }
	 
	 /**
	     * 计算Cd时间
	     * @param cdTime
	     * @param nowTime
	     * @param coefficient
	     * @return
		 */
		 public static int getIntCdtime(long cdtime,long nowTime,long coefficient){
			 if(coefficient<0) return 0;
			//计算玩家挑战的cd时间，当前系统时间减去数据库中上次存储的cd时间，再换算成秒
			 int cdTime=0;
		 	long tmpTime= (cdtime+coefficient)-nowTime;
		 	if(tmpTime>0)
		 		cdTime=(int)(tmpTime/TimeUtils.SECOND);
			 
			 return cdTime;
		 }
	 
	 	/**
	     * 计算Cd时间 ,无系数,返回毫秒
	     * @param cdTime
	     * @param nowTime
	     * @return
		 */
		 public static long getIntCdtime(Date cdtime,long nowTime){
			//计算玩家挑战的cd时间，当前系统时间减去数据库中上次存储的cd时间，再换算成秒
			 long cdTime=0;
			 if(cdtime==null){
				 return cdTime;
		 	 }else{
		 		long tmpTime= cdtime.getTime()-nowTime;
		 		if(tmpTime>0)
		 			cdTime=tmpTime;
			 }
			 return cdTime;
		 }
		 
		 /**
		     * 计算Cd时间 ,无系数,返回秒
		     * @param cdTime
		     * @param nowTime
		     * @return
			 */
		 public static int getIntCdtime(long cdtime,long nowTime){
			//计算玩家挑战的cd时间，当前系统时间减去数据库中上次存储的cd时间，再换算成秒
			int cdTime=0;
		 	long tmpTime= cdtime-nowTime;
		 	if(tmpTime>0)
	 			cdTime=(int)(tmpTime/TimeUtils.SECOND);
		    return cdTime;
		 }
		 
		 /**
		     * 计算Cd时间 ,无系数,返回秒
		     * @param cdTime
		     * @param nowTime
		     * @return
			 */
		 public static long getLongCdtime(long cdtime,long nowTime){
			//计算玩家挑战的cd时间，当前系统时间减去数据库中上次存储的cd时间，再换算成秒
			long cdTime=0;
		 	long tmpTime= cdtime-nowTime;
		 	if(tmpTime>0)
		 		cdTime=tmpTime;
		    return cdTime;
		 }
 
	 /**
	     * 计算排行榜奖励结算时间
	     * @param rankBonusTime //玩家上次领取时间
	     * @param coefficient //奖励领取倒计时系数
	     * @param rankBonusTimeLast //系统中建立最后结算时间
	     * @return
	 */
	 public static int getRankBonustime(Date rankBonusTime,long coefficient,long rankBonusTimeLast){
		 if(rankBonusTime==null) return 0;
		 if(coefficient<0) return 0;
		//计算玩家挑战的cd时间，当前系统时间减去数据库中上次存储的cd时间，再换算成秒
		 int tmpRankBonusTime=0;
		 long tmpTime= (rankBonusTimeLast+coefficient)-rankBonusTime.getTime();
		 if(tmpTime>0)
			tmpRankBonusTime=(int)(tmpTime/TimeUtils.SECOND);
		 return tmpRankBonusTime;
	 }
	 
	 /**
	     * 长整形时间转Date时间类型
	     * @param time //玩家上次领取时间
	     * @return
	 */
	 public static Date longToDate(long time){
		 Date d=new Date();
		 d.setTime(time);
		 return d;
	 }
		/**
		 * 以格式{@link TimeUtils#hmsFormat}解析数据，返回其表示的毫秒数
		 * 
		 * @param source
		 * @param cday 指定添加多少天
		 * @return
		 * @throws ParseException
		 */
		public static long getHMSTime2(String source,int cday){
			String[] time=source.split(":");
			Calendar _calendar = Calendar.getInstance();
			_calendar.setTimeZone(TIME_ZONE);
			_calendar.add(Calendar.DAY_OF_MONTH, cday);
			_calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
			_calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
			_calendar.set(Calendar.SECOND, Integer.parseInt(time[2]));
			return _calendar.getTimeInMillis();
		}
	 /**
	     * 判断两个时间间隔相差多长时间
	     * @param starttime //玩家上次领取时间
	     * @param endtime //玩家上次领取时间
	     * @param interval //时间间隔
	     * @return
	 */
	 public static int compareTime(long starttime,long endtime,long Interval){
		 int time=0;
		 time=(int) ((endtime-starttime)/Interval);
		 return time;		 
	 }
	 
	 /**
	  * 日期
	  */
	 public static int daysBetween(long startTime,long endTime){
	       long between_days=(getBeginOfDay(endTime)-getBeginOfDay(startTime))/TimeUtils.DAY;          
	       return (int)between_days;    
	 }
	 

	     
     /** 
 	 *字符串的日期格式的计算 
 	 */  
 	     public static int daysBetweenWithFormat(String smdate,String bdate,String format) throws ParseException{  
 	         SimpleDateFormat sdf=new SimpleDateFormat(format);  
 	         Calendar cal = Calendar.getInstance();    
 	        cal.setTimeZone(TIME_ZONE);
 	         cal.setTime(sdf.parse(smdate));    
 	         long time1 = cal.getTimeInMillis();                 
 	         cal.setTime(sdf.parse(bdate));    
 	         long time2 = cal.getTimeInMillis();         
 	         long between_days=(time2-time1)/(1000*3600*24);  
 	             
 	        return Integer.parseInt(String.valueOf(between_days));     
 	     } 
	     
	     
	 /** 
	     * 计算两个日期之间相差的天数 
	     * @param smdate 较小的时间
	     * @param bdate  较大的时间
	     * @return 相差天数
		 * @throws ParseException 
	     */  
	    public static int daysBetween(Date smdate,Date bdate) throws ParseException  
	    {  
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    	smdate=sdf.parse(sdf.format(smdate));
	    	bdate=sdf.parse(sdf.format(bdate));
	        Calendar cal = Calendar.getInstance(); 
	        cal.setTimeZone(TIME_ZONE);
	        cal.setTime(smdate);  
	        long time1 = cal.getTimeInMillis();               
	        cal.setTime(bdate);  
	        long time2 = cal.getTimeInMillis();       
	        long between_days=(time2-time1)/(1000*3600*24);
	          
	       return Integer.parseInt(String.valueOf(between_days));         
	    }  
	    
	    /** 
	     * 计算两个日期之间相差的天数 
	     * @param smdate 较小的时间
	     * @param bdate  较大的时间
	     * @return 相差天数
		 * @throws ParseException 
	     */  
	    public static int daysBetweenForStr(String smdate,String bdate) throws ParseException  
	    {  
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance();  
	        cal.setTimeZone(TIME_ZONE);
	        cal.setTime(sdf.parse(smdate));
	        long time1 = cal.getTimeInMillis();               
	        cal.setTime(sdf.parse(bdate));  
	        long time2 = cal.getTimeInMillis();       
	        long between_days=(time2-time1)/(1000*3600*24);
	          
	       return Integer.parseInt(String.valueOf(between_days));         
	    }  
	    /** 
	     * 计算两个日期之间相差的天数 
	     * @param smdate 较小的时间
	     * @param bdate  较大的时间
	     * @return 相差天数
	     * @throws ParseException 
	     */  
	    public static int daysBetweenFor(long smdate,long bdate) throws ParseException  
	    {  
	    	  
	    	long between_days=(bdate-smdate)/(1000*3600*24);
	    	
	    	return (int)between_days;         
	    }  
	    
	    
	    /***
	     * 时间戳转换为date 型
	     * @param unixDate String 时间戳
	     * 
	     * */
	    public String getDate(String unixDate) {
	    	  
	    	   SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	  // SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    	   long unixLong = 0;
	    	   String date = "";
	    	   try {
	    	   unixLong = Long.parseLong(unixDate);
	    	   } catch(Exception ex) {
	    	   System.out.println("String转换Long错误，请确认数据可以转换！");
	    	   }
	    	   try {
	    	   date = fm1.format(unixLong);
	    	//   date = fm2.format(new Date(date));
	    	   } catch(Exception ex) {
	    	   System.out.println("String转换Date错误，请确认数据可以转换！");
	    	   }
	    	 return date;
	    	   }
	    
		public static Date stringToTime(String date) {
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		        return sdf.parse(date);
			}
			catch(Exception err) {
				return null;
			}
		}
		
		/***
		 * 获取当前时间String
		 * */
		public String dateToTimeString() {
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simpledate.format(new java.util.Date());
		}
		
		/***
		 * 获取当前时间String
		 * */
		public static String dateToTimeString(Date d) {
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simpledate.format(d);
		}
		
		/***
		 * 获取当前时间String
		 * */
		public static String dateToTimeyyyyMMddString(String str) {
			SimpleDateFormat pramdate = new SimpleDateFormat("yyyy-MM-dd");
			Date d=null;
			try {
				d=pramdate.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
				return "";
			}
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMdd");
			return simpledate.format(d);
		}
		
		/***
		 * 获取毫秒时间格式转换
		 * */
		public static int dateToTimeyyyyMMddInteger(long time) {
			Date d=new Date(time);
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMdd");
			return Integer.parseInt(simpledate.format(d));
		}
		
		
		/**
		 * 将timeFlag（yyyyMMdd）时间类型增加指定
		 * @param timeFlag
		 * @return
		 */
		public static int getTimeAddNum(int day){
			Calendar calendar=Calendar.getInstance();
			calendar.setTimeZone(TIME_ZONE);
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMdd");
			try {
				calendar.add(Calendar.DAY_OF_MONTH, day);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Integer.parseInt(simpledate.format(calendar.getTime()));
		}
		
		/**
		 * 将timeFlag（yyyyMMdd）时间类型增加指定
		 * @param timeFlag
		 * @return
		 */
		public static int getTimeAddNum(int timeFlag,int day){
			Calendar calendar=Calendar.getInstance();
			calendar.setTimeZone(TIME_ZONE);
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMdd");
			try {
				Date time=simpledate.parse(String.valueOf(timeFlag));
				calendar.setTime(time);
				calendar.add(Calendar.DAY_OF_MONTH, day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Integer.parseInt(simpledate.format(calendar.getTime()));
		}
		
		/**
		 * 将long时间类型增加指定
		 * @param time
		 * @return
		 */
		public static long getTimeAddNum(long time,int day){
			Calendar calendar=Calendar.getInstance();
			calendar.setTimeZone(TIME_ZONE);
			calendar.setTimeInMillis(time);
			calendar.add(Calendar.DAY_OF_MONTH, day);
			return calendar.getTimeInMillis();
		}
		
		/**
		 * 将timeFlag（yyyyMMddHHmm）时间类型增加指定分钟
		 * @param timeFlag
		 * @return
		 */
		public static long getTimeAddMinute(long timeFlag,int m){
			Calendar calendar=Calendar.getInstance();
			calendar.setTimeZone(TIME_ZONE);
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMddHHmm");
			try {
				Date time=simpledate.parse(String.valueOf(timeFlag));
				calendar.setTime(time);
				calendar.add(Calendar.MINUTE, m);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Long.parseLong(simpledate.format(calendar.getTime()));
		}
		
		/**
		 * 获得时间格式，根据当前时间四舍五入分钟进位
		 * @return
		 */
		public static long getTimeRoundingFormat(boolean isMerge){
			long timeFlag=0;
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMddHHmm");
			Calendar calendar=Calendar.getInstance();
			calendar.setTimeZone(TIME_ZONE);
			int minute=calendar.get(Calendar.MINUTE);
			if(isMerge){
				//计算数据的余数
				int remainder=minute%10;
				if(remainder>=5){
					calendar.add(Calendar.MINUTE, 10-remainder);
				}else if(remainder>0&&remainder<5){
					calendar.add(Calendar.MINUTE, -remainder);
				}
			}else{
				int remainder=minute%5;
				if(remainder>=0){
					calendar.add(Calendar.MINUTE, -remainder);
				}
			}
			
			timeFlag=Long.parseLong(simpledate.format(calendar.getTime()));
			return timeFlag;
		}
		
		
		/**
		 * 时间格式转换，将数字类型的时间转换为日期类型
		 * @return
		 */
		public static String getIntTimetoStr(int timeFlag){
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat simpledateStr = new SimpleDateFormat("yyyy_MM_dd");
			Date d=null;
			try {
				d = simpledate.parse(String.valueOf(timeFlag));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return simpledateStr.format(d);
		}
		
		/**
		 * 时间格式转换，将数字类型的时间转换为日期类型
		 * @return
		 */
		public static String getIntTimetoStr(int timeFlag,String simpleFormat,String simpleFormatStr){
			SimpleDateFormat simpledate = new SimpleDateFormat(simpleFormat);
			SimpleDateFormat simpledateStr = new SimpleDateFormat(simpleFormatStr);
			Date d=null;
			try {
				d = simpledate.parse(String.valueOf(timeFlag));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return simpledateStr.format(d);
		}
		
		/**
		 * 时间格式转换，将数字类型的时间转换为日期类型
		 * @return
		 */
		public static String getIntTimetoStr(String timeFlag,String simpleFormat,String simpleFormatStr){
			SimpleDateFormat simpledate = new SimpleDateFormat(simpleFormat);
			SimpleDateFormat simpledateStr = new SimpleDateFormat(simpleFormatStr);
			Date d=null;
			try {
				d = simpledate.parse(timeFlag);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return simpledateStr.format(d);
		}
		
		/**
		 * 时间格式转换，将数字类型的时间转换为日期类型
		 * @return
		 */
		public static Date getIntTimetoDate(boolean flag,int timeFlag){
			SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMdd");
			Date d=null;
			try {
				d = simpledate.parse(String.valueOf(timeFlag));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(flag)
				d.setTime(getBeginOfDay(d.getTime()));
			else
				d.setTime(getEndOfDay(d.getTime()));
			return d;
		}
		
		/**
		* 取得当前日期所在周的第一天(周一)的0点
		* 
		* @param date
		* @return
		*/
		public static Date getFirstDayOfWeek(Date date) { 
			Calendar c = new GregorianCalendar();
			c.setTimeZone(TIME_ZONE);
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setTime(date);
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
			return new Date(getBeginOfDay(c.getTimeInMillis()));
		}
		
		
		 /**
	     * 获取当前日期是星期几(周一为第一天)
	     * 
	     * @param dt
	     * @return 当前日期是星期几
	     */
	    public static int getWeekDayOfDate(Date dt) {	        
	        Calendar cal = new GregorianCalendar();
	        cal.setTimeZone(TIME_ZONE);
	        cal.setFirstDayOfWeek(Calendar.MONDAY);
	        cal.setTime(dt);
	        return cal.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : cal.get(Calendar.DAY_OF_WEEK) - 1;
	    }
	    
	   
		
		
}
