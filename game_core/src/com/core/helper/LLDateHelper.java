package com.core.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.common.constants.LLCommonConstant;
import com.core.util.StringUtils;



/**
 * 日期操作类
 * @author Thinker
 */
public class LLDateHelper
{
	/** 单例 */
	private static final LLDateHelper instance=new LLDateHelper();
	
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_DAY_TIME_FORMAT="yyyy-MM-dd HH:mm";
    
    public static final long S=1000;
    public static final long M=1000*60;
    public static final long H=1000*60*60;
    public static final long D=1000*60*60*24;
    
    //时间相关参数
    private static final long HOURS_PER_DAY = 24;
    private static final long MINUTES_PER_HOUR = 60;
    private static final long SECONDS_PER_MINUTE = 60;
    private static final long MILLIONSECONDS_PER_SECOND = 1000;
    private static final long MILLIONSECONDS_PER_MINUTE = MILLIONSECONDS_PER_SECOND * SECONDS_PER_MINUTE;
    private static final long MILLIONSECONDS_PER_HOUR = MILLIONSECONDS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    private static final long MILLION_SECOND_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLIONSECONDS_PER_SECOND;

	private LLDateHelper() {}
	
	/**
	 * 取得该类唯一实例
	 * @return 该类唯实例
	 */
	public static LLDateHelper instance()
	{
		return instance;
	}
	
 	
/**
 * 将yyyy-MM-dd hh:mm格式的字符串转换为日期对象
 * @param date 待转换字符串
 * @return 转换后日期对象
 * 
 * 
 */
	
	public static Date stringToSinutes(String date) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(DEFAULT_DAY_TIME_FORMAT);  
	        return sdf.parse(date);
		}
		catch(Exception err) {
			return null;
		}
	}	
	/**
	 * 将yyyy-MM-dd格式的字符串转换为日期对象
	 * @param date 待转换字符串
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 * @see com.core.constants.LLCommonConstant.socialgame.commons.CommonCst#DEFAULT_DATE_FORMAT
	 */
	public static Date getDate(String date)
	{
		return getDate(date,LLCommonConstant.DEFAULT_DATE_FORMAT,null);
	}
	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的字符串转换为日期对象
	 * @param date 待转换字符串
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 * @see com.core.constants.LLCommonConstant.socialgame.commons.CommonCst#DEFAULT_DATETIME_FORMAT
	 */
	public static Date getDateTime(String date) 
	{
		return getDate(date,LLCommonConstant.DEFAULT_DATETIME_FORMAT,null);
	}
	/**
	 * 将指定格式的字符串转换为日期对象
	 * @param date 待转换字符串
	 * @param format 日期格式
	 * @return 转换后日期对象
	 * @see #getDate(String, String, Date)
	 */
	public static Date getDate(String date,String format)
	{
		return getDate(date,format,null);
	}
	/**
	 * 将指定格式的字符串转换为日期对象
	 * @param date 日期对象
	 * @param format 日期格式
	 * @param defVal 转换失败时的默认返回值
	 * @return 转换后的日期对象
	 */
	public static Date getDate(String date,String format,Date defVal)
	{
		if(StringUtils.isEmpty(date)||StringUtils.isEmpty(format)) return null;
		
		Date d;
		try 
		{
			d=new SimpleDateFormat(format).parse(date);
		}catch(ParseException e)
		{
			d=defVal;
		}
		return d;
	}
	
	/**
	 * 将日期对象格式化成yyyy-MM-dd格式的字符串
	 * @param date 待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 * @see com.core.constants.LLCommonConstant.socialgame.commons.CommonCst#DEFAULT_DATE_FORMAT
	 */
	public static String formatDate(Date date)
	{
		return formatDate(date,LLCommonConstant.DEFAULT_DATE_FORMAT,null);
	}
	/**
	 * 将日期对象格式化成yyyy-MM-dd HH:mm:ss格式的字符串
	 * @param date 待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 * @see com.core.constants.LLCommonConstant.socialgame.commons.CommonCst#DEFAULT_DATETIME_FORMAT
	 */
	public static String forDatetime(Date date)
	{
		return formatDate(date,LLCommonConstant.DEFAULT_DATETIME_FORMAT,null);
	}
	/**
	 * 将日期对象格式化成HH:mm:ss格式的字符串
	 * @param date 待格式化日期对象
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 * @see com.core.constants.LLCommonConstant.socialgame.commons.CommonCst#DEFAULT_TIME_FORMAT
	 */
	public static String formatTime(Date date)
	{
		return formatDate(date,LLCommonConstant.DEFAULT_TIME_FORMAT,null);
	}
	/**
	 * 将日期对象格式化成指定类型的字符串
	 * @param date 待格式化日期对象
	 * @param format 格式化格式
	 * @return 格式化后的字符串
	 * @see #formatDate(Date, String, String)
	 */
	public static String formatDate(Date date,String format) 
	{
		return formatDate(date,format,null);
	}
	/**
	 * 将日期对象格式化成指定类型的字符串
	 * @param date 待格式化日期对象
	 * @param format 格式化格式
	 * @param defVal 格式化失败时的默认返回值
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date,String format,String defVal)
	{
		if (date == null||StringUtils.isEmpty(format)) return defVal;
		String ret;		
		try 
		{
			ret=new SimpleDateFormat(format).format(date);
		}catch(Exception e) 
		{
			ret=defVal;
		}
		return ret;
	}
	
    /**
     * 获得两个日期之间相差的天数(返回值去掉了小数部分，即只返回)。（date1 - date2）
     * @param date1
     * @param date2
     * @return 返回两个日期之间的天数差，如果date1晚于date2，则返回正数，否则返回负数或者0
     */
    public static int intervalDays(Date date1, Date date2)
    {
        long intervalMillSecond = setToDayStartTime(date1).getTime() - setToDayStartTime(date2).getTime();
        //相差的天数 = 相差的毫秒数 / 每天的毫秒数 (小数位采用去尾制)
        return (int) (intervalMillSecond / LLCommonConstant.MILLION_SECOND_PER_DAY);
    }

    /**
     * 将时间调整到当天的0：0：0
     * @param date
     * @return
     */
    public static Date setToDayStartTime(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得两个日期之间相差的分钟数。（date1 - date2）
     * @param date1
     * @param date2
     * @return 返回两个日期之间相差的分钟数。
     */
    public static int intervalMinutes(Date date1, Date date2)
    {
        long intervalMillSecond = date1.getTime() - date2.getTime();
        //相差的分钟数 = 相差的毫秒数 / 每分钟的毫秒数 (小数位采用进位制处理，即大于0则加1)
        return (int) (intervalMillSecond / LLCommonConstant.MILLIONSECONDS_PER_MINUTE
                + (intervalMillSecond % LLCommonConstant.MILLIONSECONDS_PER_MINUTE > 0 ? 1 : 0));
    }

    /**
     * 获得两个日期之间相差的秒数。（date1 - date2）
     * @param date1
     * @param date2
     * @return
     */
    public static int intervalSeconds(Date date1, Date date2)
    {
        long intervalMillSecond = date1.getTime() - date2.getTime();
        return (int) (intervalMillSecond / LLCommonConstant.MILLIONSECONDS_PER_SECOND
                + (intervalMillSecond % LLCommonConstant.MILLIONSECONDS_PER_SECOND > 0 ? 1 : 0));
    }
    

    /**
     * 将指定日期对象格式化成指定生日日期对象
     *  生日日期范围 [1000-01-01 00:00:00, 9999-12-31 23:59:59]
     * @param origalBirthday
     * @return
     */
    public static Date formatBirthday(Date origalBirthday)
    {
        if(origalBirthday == null)
        {
            return LLDateHelper.getDate("1000-01-01 00:00:00");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(origalBirthday);
        int year = calendar.get(Calendar.YEAR);
        if (year > 9999 || year < 1000)
        {
            calendar.set(Calendar.YEAR, Math.max(1000, year % 10000));
        }
        return calendar.getTime();
    }
    
    /**
     * 从Calendar类中获得“年”。
     * @param c Calendar实例
     * @return 返回指定Calendar的年
     */
    public static int getYear(Calendar c) 
    {
        return c.get(Calendar.YEAR);
    }
    
    /**
     * 从Calendar中获得当年的月数，从1到12。
     * 注意：这里的结果比Calendar实际值大一，因为Calendar的月从0开始。
     * @param c Calendar实例
     * @return 返回指定的Calendar的月
     */
    public static int getMonth(Calendar c)
    {
        return c.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 从Calendar中获得当月的日期。
     * @param c Calendar实例
     * @return 返回指定Calendar的当月的日期
     */
    public static int getDay(Calendar c) 
    {
        return c.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 从Calendar中获得时间，24小时制表示。
     * @param c Calendar的实例
     * @return 返回指定的Calendar的时间
     */
    public static int getHour(Calendar c)
    {
        return c.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * 从Calendar中获得分钟数。
     * @param c Calendar实例
     * @return 返回指定的Calendar的分钟数
     */
    public static int getMinute(Calendar c) 
    {
        return c.get(Calendar.MINUTE);
    }
    
    /**
     * 从Calendar中获得秒数。
     * @param c Calendar实例
     * @return 返回指定的Calendar的秒数
     */
    public static int getSecond(Calendar c)
    {
        return c.get(Calendar.SECOND);
    }
    
    /**
     * 判断两个日期是不是同一天。
     * @param c1 第一个日期
     * @param c2 第二个日期
     * @return 如果是同一天则返回true；否则返回false
     */
    public static boolean isSameDay(Calendar c1, Calendar c2) 
    {
        if (getYear(c1) == getYear(c2)&& getMonth(c1) == getMonth(c2) && getDay(c1) == getDay(c2))
        {
            return true;
        }
        return false;
    }
}

