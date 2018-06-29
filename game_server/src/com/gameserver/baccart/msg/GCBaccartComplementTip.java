package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐补充筹码
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartComplementTip extends GCMessage{
	
	/** 补充 */
	private long complement;

	public GCBaccartComplementTip (){
	}
	
	public GCBaccartComplementTip (
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
		return MessageType.GC_BACCART_COMPLEMENT_TIP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_COMPLEMENT_TIP";
	}

	public long getComplement(){
		return complement;
	}
		
	public void setComplement(long complement){
		this.complement = complement;
	}
}