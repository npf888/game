package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 庄家位置
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasBankerPos extends GCMessage{
	
	/** 庄家位置 */
	private int pos;

	public GCTexasBankerPos (){
	}
	
	public GCTexasBankerPos (
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
		return MessageType.GC_TEXAS_BANKER_POS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_BANKER_POS";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
}