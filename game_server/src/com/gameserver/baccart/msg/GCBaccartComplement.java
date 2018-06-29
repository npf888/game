package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐补充筹码
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartComplement extends GCMessage{
	
	/** 补充数 */
	private long complement;

	public GCBaccartComplement (){
	}
	
	public GCBaccartComplement (
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
		return MessageType.GC_BACCART_COMPLEMENT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_COMPLEMENT";
	}

	public long getComplement(){
		return complement;
	}
		
	public void setComplement(long complement){
		this.complement = complement;
	}
}