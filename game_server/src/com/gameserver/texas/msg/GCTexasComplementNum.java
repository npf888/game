package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州补充筹码数
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasComplementNum extends GCMessage{
	
	/** 补充数 */
	private long complement;

	public GCTexasComplementNum (){
	}
	
	public GCTexasComplementNum (
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
		return MessageType.GC_TEXAS_COMPLEMENT_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_COMPLEMENT_NUM";
	}

	public long getComplement(){
		return complement;
	}
		
	public void setComplement(long complement){
		this.complement = complement;
	}
}