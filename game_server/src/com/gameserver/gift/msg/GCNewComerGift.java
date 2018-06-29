package com.gameserver.gift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 新手礼包
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCNewComerGift extends GCMessage{
	
	/** 是否开启 礼包 （0：开启，1：关闭），如果是 1 就关闭礼包 */
	private int openShut;
	/** 礼包类型 限时0，不限时 1 */
	private int giftType;
	/** 剩余时间 */
	private long leftTime;
	/** 礼包 pid */
	private long pid;

	public GCNewComerGift (){
	}
	
	public GCNewComerGift (
			int openShut,
			int giftType,
			long leftTime,
			long pid ){
			this.openShut = openShut;
			this.giftType = giftType;
			this.leftTime = leftTime;
			this.pid = pid;
	}

	@Override
	protected boolean readImpl() {
		openShut = readInteger();
		giftType = readInteger();
		leftTime = readLong();
		pid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(openShut);
		writeInteger(giftType);
		writeLong(leftTime);
		writeLong(pid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_NEW_COMER_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_NEW_COMER_GIFT";
	}

	public int getOpenShut(){
		return openShut;
	}
		
	public void setOpenShut(int openShut){
		this.openShut = openShut;
	}

	public int getGiftType(){
		return giftType;
	}
		
	public void setGiftType(int giftType){
		this.giftType = giftType;
	}

	public long getLeftTime(){
		return leftTime;
	}
		
	public void setLeftTime(long leftTime){
		this.leftTime = leftTime;
	}

	public long getPid(){
		return pid;
	}
		
	public void setPid(long pid){
		this.pid = pid;
	}
}