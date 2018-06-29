package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 6
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomRoundOpen extends GCMessage{
	
	/** 抢开之后的状态 */
	private int status;
	/** 抢开的人的ID */
	private long robPassportId;
	/** 抢开倍数 */
	private int robMultiple;

	public GCStateRoomRoundOpen (){
	}
	
	public GCStateRoomRoundOpen (
			int status,
			long robPassportId,
			int robMultiple ){
			this.status = status;
			this.robPassportId = robPassportId;
			this.robMultiple = robMultiple;
	}

	@Override
	protected boolean readImpl() {
		status = readInteger();
		robPassportId = readLong();
		robMultiple = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(status);
		writeLong(robPassportId);
		writeInteger(robMultiple);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_ROUND_OPEN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_ROUND_OPEN";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}

	public long getRobPassportId(){
		return robPassportId;
	}
		
	public void setRobPassportId(long robPassportId){
		this.robPassportId = robPassportId;
	}

	public int getRobMultiple(){
		return robMultiple;
	}
		
	public void setRobMultiple(int robMultiple){
		this.robMultiple = robMultiple;
	}
}