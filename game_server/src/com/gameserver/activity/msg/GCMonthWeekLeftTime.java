package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回    周、月特惠充值活动的时间
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCMonthWeekLeftTime extends GCMessage{
	
	/** 秒 */
	private long weekLeftTime;
	/** 秒 */
	private long monthLeftTime;

	public GCMonthWeekLeftTime (){
	}
	
	public GCMonthWeekLeftTime (
			long weekLeftTime,
			long monthLeftTime ){
			this.weekLeftTime = weekLeftTime;
			this.monthLeftTime = monthLeftTime;
	}

	@Override
	protected boolean readImpl() {
		weekLeftTime = readLong();
		monthLeftTime = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(weekLeftTime);
		writeLong(monthLeftTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MONTH_WEEK_LEFT_TIME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MONTH_WEEK_LEFT_TIME";
	}

	public long getWeekLeftTime(){
		return weekLeftTime;
	}
		
	public void setWeekLeftTime(long weekLeftTime){
		this.weekLeftTime = weekLeftTime;
	}

	public long getMonthLeftTime(){
		return monthLeftTime;
	}
		
	public void setMonthLeftTime(long monthLeftTime){
		this.monthLeftTime = monthLeftTime;
	}
}