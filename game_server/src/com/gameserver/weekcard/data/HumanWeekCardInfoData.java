package com.gameserver.weekcard.data;

import com.gameserver.weekcard.HumanWeekCard;

/**
 * 周卡 信息
 * @author wayne
 *
 */
public class HumanWeekCardInfoData {
	/**开始时间*/
	private long beginTime;
	/**上次领取时间*/
	private long getTime;
	/**持续时间*/
	private long duration;
	
	public long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	public long getGetTime() {
		return getTime;
	}
	public void setGetTime(long getTime) {
		this.getTime = getTime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public static HumanWeekCardInfoData convertFromHumanWeekCard( HumanWeekCard humanWeekCard)
	{
		HumanWeekCardInfoData humanWeekCardInfoData= new HumanWeekCardInfoData();
		humanWeekCardInfoData.setBeginTime(humanWeekCard.getBeginTime());
		humanWeekCardInfoData.setDuration(humanWeekCard.getDuration());
		humanWeekCardInfoData.setGetTime(humanWeekCard.getGetTime());
		return humanWeekCardInfoData;
	}
}
