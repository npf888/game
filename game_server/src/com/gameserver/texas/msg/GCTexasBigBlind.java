package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 庄家位置
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasBigBlind extends GCMessage{
	
	/** 庄家位置 */
	private int pos;
	/** 大盲注 */
	private long bigBlind;

	public GCTexasBigBlind (){
	}
	
	public GCTexasBigBlind (
			int pos,
			long bigBlind ){
			this.pos = pos;
			this.bigBlind = bigBlind;
	}

	@Override
	protected boolean readImpl() {
		pos = readInteger();
		bigBlind = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		writeLong(bigBlind);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_BIG_BLIND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_BIG_BLIND";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}

	public long getBigBlind(){
		return bigBlind;
	}
		
	public void setBigBlind(long bigBlind){
		this.bigBlind = bigBlind;
	}
}