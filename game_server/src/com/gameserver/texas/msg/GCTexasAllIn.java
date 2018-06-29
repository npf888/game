package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州allin
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasAllIn extends GCMessage{
	
	/** allin位置 */
	private int pos;
	/** allin额度 */
	private long allInBet;

	public GCTexasAllIn (){
	}
	
	public GCTexasAllIn (
			int pos,
			long allInBet ){
			this.pos = pos;
			this.allInBet = allInBet;
	}

	@Override
	protected boolean readImpl() {
		pos = readInteger();
		allInBet = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		writeLong(allInBet);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_ALL_IN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_ALL_IN";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}

	public long getAllInBet(){
		return allInBet;
	}
		
	public void setAllInBet(long allInBet){
		this.allInBet = allInBet;
	}
}