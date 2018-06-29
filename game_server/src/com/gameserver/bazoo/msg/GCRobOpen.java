package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 抢开返回信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRobOpen extends GCMessage{
	
	/** 抢开的人的ID */
	private long robPassportId;
	/** 抢开的倍数 */
	private int multiple;

	public GCRobOpen (){
	}
	
	public GCRobOpen (
			long robPassportId,
			int multiple ){
			this.robPassportId = robPassportId;
			this.multiple = multiple;
	}

	@Override
	protected boolean readImpl() {
		robPassportId = readLong();
		multiple = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(robPassportId);
		writeInteger(multiple);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROB_OPEN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROB_OPEN";
	}

	public long getRobPassportId(){
		return robPassportId;
	}
		
	public void setRobPassportId(long robPassportId){
		this.robPassportId = robPassportId;
	}

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}
}