package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州补充筹码
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasComplement extends GCMessage{
	
	/** 补充数 */
	private long complement;

	public GCTexasComplement (){
	}
	
	public GCTexasComplement (
			long complement ){
			this.complement = complement;
	}

	@Override
	protected boolean readImpl() {
		complement = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(complement);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_COMPLEMENT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_COMPLEMENT";
	}

	public long getComplement(){
		return complement;
	}
		
	public void setComplement(long complement){
		this.complement = complement;
	}
}