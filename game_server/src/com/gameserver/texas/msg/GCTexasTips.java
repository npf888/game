package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州打赏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasTips extends GCMessage{
	
	/** 打赏位置 */
	private int pos;

	public GCTexasTips (){
	}
	
	public GCTexasTips (
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
		return MessageType.GC_TEXAS_TIPS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_TIPS";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
}