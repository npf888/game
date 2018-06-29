package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 庄家位置
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasSmallBlind extends GCMessage{
	
	/** 位置 */
	private int pos;
	/** 小盲注 */
	private long smallBlind;

	public GCTexasSmallBlind (){
	}
	
	public GCTexasSmallBlind (
			int pos,
			long smallBlind ){
			this.pos = pos;
			this.smallBlind = smallBlind;
	}

	@Override
	protected boolean readImpl() {
		pos = readInteger();
		smallBlind = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		writeLong(smallBlind);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_SMALL_BLIND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_SMALL_BLIND";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}

	public long getSmallBlind(){
		return smallBlind;
	}
		
	public void setSmallBlind(long smallBlind){
		this.smallBlind = smallBlind;
	}
}