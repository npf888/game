package com.gameserver.activity;

import java.lang.reflect.Constructor;
import java.util.List;

import com.common.constants.Loggers;
import com.gameserver.activity.enums.ActivityTypeEnum;

/**
 * 活动数据工厂
 * @author wayne
 *
 */
public class HumanActivityDataFactory {
	public static HumanActivityData createHumanActivityData(ActivityTypeEnum activityTypeEnum,List<ActivityRewardRule> ruleList){
		Class<? extends HumanActivityData> clazz = activityTypeEnum.getClazz();
		try {
			Constructor<? extends HumanActivityData> con = clazz.getConstructor(List.class);
			HumanActivityData tempData= con.newInstance(ruleList);
			return tempData;
		} catch (Exception e) {
			Loggers.activityLogger.error("", e);
		}
		return null;
	}

}
