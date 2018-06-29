package com.gameserver.gift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 请求多种礼包
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRequestGift extends GCMessage{
	
	/** 开始计时点 */
	private long startTime;
	/** 礼包ID */
	private int giftId;

	public GCRequestGift (){
	}
	
	public GCRequestGift (
			long startTime,
			int giftId ){
			this.startTime = startTime;
			this.giftId = giftId;
	}

	@Override
	protected boolean readImpl() {
		startTime = readLong();
		giftId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(startTime);
		writeInteger(giftId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REQUEST_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REQUEST_GIFT";
	}

	public long getStartTime(){
		return startTime;
	}
		
	public void setStartTime(long startTime){
		this.startTime = startTime;
	}

	public int getGiftId(){
		return giftId;
	}
		
	public void setGiftId(int giftId){
		this.giftId = giftId;
	}
}