package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州跟注
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasFollow extends GCMessage{
	
	/** 跟注位置 */
	private int pos;

	public GCTexasFollow (){
	}
	
	public GCTexasFollow (
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
		return MessageType.GC_TEXAS_FOLLOW;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_FOLLOW";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
}