package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州看牌
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasLook extends GCMessage{
	
	/** 跟注位置 */
	private int pos;

	public GCTexasLook (){
	}
	
	public GCTexasLook (
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
		return MessageType.GC_TEXAS_LOOK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_LOOK";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
}