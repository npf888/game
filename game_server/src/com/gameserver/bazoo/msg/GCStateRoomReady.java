package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 2
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomReady extends GCMessage{
	
	/** 倒计时  还有多长时间 开始 统一摇色子 */
	private int status;
	/** 倒计时  还剩几秒 */
	private int leftSecond;

	public GCStateRoomReady (){
	}
	
	public GCStateRoomReady (
			int status,
			int leftSecond ){
			this.status = status;
			this.leftSecond = leftSecond;
	}

	@Override
	protected boolean readImpl() {
		status = readInteger();
		leftSecond = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(status);
		writeInteger(leftSecond);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_READY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_READY";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}

	public int getLeftSecond(){
		return leftSecond;
	}
		
	public void setLeftSecond(int leftSecond){
		this.leftSecond = leftSecond;
	}
}