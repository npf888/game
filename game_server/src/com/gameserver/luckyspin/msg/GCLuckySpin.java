package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 转盘
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLuckySpin extends GCMessage{
	
	/** 位置 */
	private int pos;

	public GCLuckySpin (){
	}
	
	public GCLuckySpin (
			int pos ){
			this.pos = pos;
	}

	@Override
	protected boolean readImpl() {
		pos = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LUCKY_SPIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LUCKY_SPIN";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
}