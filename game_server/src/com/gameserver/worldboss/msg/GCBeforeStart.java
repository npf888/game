package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 比赛之前 多少分钟
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBeforeStart extends GCMessage{
	
	/** boss的类型 */
	private int bossType;
	/** 剩余时间 */
	private int leftTime;

	public GCBeforeStart (){
	}
	
	public GCBeforeStart (
			int bossType,
			int leftTime ){
			this.bossType = bossType;
			this.leftTime = leftTime;
	}

	@Override
	protected boolean readImpl() {
		bossType = readInteger();
		leftTime = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bossType);
		writeInteger(leftTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BEFORE_START;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BEFORE_START";
	}

	public int getBossType(){
		return bossType;
	}
		
	public void setBossType(int bossType){
		this.bossType = bossType;
	}

	public int getLeftTime(){
		return leftTime;
	}
		
	public void setLeftTime(int leftTime){
		this.leftTime = leftTime;
	}
}