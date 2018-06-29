package com.gameserver.monthcard.data;

import com.gameserver.monthcard.HumanMonthCard;

/**
 * 月卡数据
 * @author wayne
 *
 */
public class HumanMonthCardInfoData {
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
	public static HumanMonthCardInfoData convertFromHumanMonthCard(
			HumanMonthCard humanMonthCard) {
		// TODO Auto-generated method stub
		HumanMonthCardInfoData humanMonthCardInfoData= new HumanMonthCardInfoData();
		humanMonthCardInfoData.setBeginTime(humanMonthCard.getBeginTime());
		humanMonthCardInfoData.setDuration(humanMonthCard.getDuration());
		humanMonthCardInfoData.setGetTime(humanMonthCard.getGetTime());
		return humanMonthCardInfoData;
	}

}
