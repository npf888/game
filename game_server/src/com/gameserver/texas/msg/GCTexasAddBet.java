package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州加注
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasAddBet extends GCMessage{
	
	/** 加注位置 */
	private int pos;
	/** 加注额度 */
	private long addBet;

	public GCTexasAddBet (){
	}
	
	public GCTexasAddBet (
			int pos,
			long addBet ){
			this.pos = pos;
			this.addBet = addBet;
	}

	@Override
	protected boolean readImpl() {
		pos = readInteger();
		addBet = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		writeLong(addBet);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_ADD_BET;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_ADD_BET";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}

	public long getAddBet(){
		return addBet;
	}
		
	public void setAddBet(long addBet){
		this.addBet = addBet;
	}
}