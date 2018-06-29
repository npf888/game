package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州allin
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasGiveUp extends GCMessage{
	
	/** allin位置 */
	private int pos;

	public GCTexasGiveUp (){
	}
	
	public GCTexasGiveUp (
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
		return MessageType.GC_TEXAS_GIVE_UP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_GIVE_UP";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
}