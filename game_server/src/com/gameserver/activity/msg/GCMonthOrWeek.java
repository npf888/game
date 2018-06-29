package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 直接推送的  周、月特惠充值活动
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCMonthOrWeek extends GCMessage{
	
	/** 周：0，月：1 */
	private int mwtype;
	/** 开始：0，结束：1 */
	private int startOrEnd;
	/** 周或者月的剩余时间 */
	private long leftTime;

	public GCMonthOrWeek (){
	}
	
	public GCMonthOrWeek (
			int mwtype,
			int startOrEnd,
			long leftTime ){
			this.mwtype = mwtype;
			this.startOrEnd = startOrEnd;
			this.leftTime = leftTime;
	}

	@Override
	protected boolean readImpl() {
		mwtype = readInteger();
		startOrEnd = readInteger();
		leftTime = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mwtype);
		writeInteger(startOrEnd);
		writeLong(leftTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MONTH_OR_WEEK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MONTH_OR_WEEK";
	}

	public int getMwtype(){
		return mwtype;
	}
		
	public void setMwtype(int mwtype){
		this.mwtype = mwtype;
	}

	public int getStartOrEnd(){
		return startOrEnd;
	}
		
	public void setStartOrEnd(int startOrEnd){
		this.startOrEnd = startOrEnd;
	}

	public long getLeftTime(){
		return leftTime;
	}
		
	public void setLeftTime(long leftTime){
		this.leftTime = leftTime;
	}
}