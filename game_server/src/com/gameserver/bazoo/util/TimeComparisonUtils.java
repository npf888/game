package com.gameserver.bazoo.util;

import java.text.ParseException;
import java.util.Date;

import com.core.util.TimeUtils;
import com.gameserver.common.Globals;

/**
 * 时间比对的工具类
 * @author JavaServer
 *
 */
public class TimeComparisonUtils {

	public static int compareNowWithPassTime(Date passTime){
		try {
			long nowLong = Globals.getTimeService().now();
			String now = TimeUtils.formatYMDTime(nowLong);
			String pass = TimeUtils.formatYMDTime(passTime.getTime());
			long finalNowTime = TimeUtils.getYMDTime(now);
			long finalPassTime = TimeUtils.getYMDTime(pass);
			
			//可以领取
			if(finalPassTime<finalNowTime){
				return -1;
			}else if(finalPassTime>finalNowTime){
				return 1;
			}else{
				return 0;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 签到 10次的比较
	 * @param passTime
	 * @return
	 */
	public static int compareNowWithPassTimeTwo(Date passTime){
		try {
			long nowLong = Globals.getTimeService().now();
			String now = TimeUtils.formatYMDTime(nowLong);
			String pass = TimeUtils.formatYMDTime(passTime.getTime());
			long finalNowTime = TimeUtils.getYMDTime(now);
			long finalPassTime = TimeUtils.getYMDTime(pass);
			
			//可以领取
			if(finalPassTime<finalNowTime){
				return 1;
			}else if(finalPassTime>finalNowTime){
				return -1;
			}else{
				return 0;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
