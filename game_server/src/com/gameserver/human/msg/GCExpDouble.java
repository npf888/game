package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 双倍经验加成
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCExpDouble extends GCMessage{
	
	/** 双倍经验的剩余时间 */
	private long leftTime;

	public GCExpDouble (){
	}
	
	public GCExpDouble (
			long leftTime ){
			this.leftTime = leftTime;
	}

	@Override
	protected boolean readImpl() {
		leftTime = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(leftTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EXP_DOUBLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EXP_DOUBLE";
	}

	public long getLeftTime(){
		return leftTime;
	}
		
	public void setLeftTime(long leftTime){
		this.leftTime = leftTime;
	}
}