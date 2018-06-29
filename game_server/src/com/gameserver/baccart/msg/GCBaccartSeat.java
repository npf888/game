package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 玩家坐下
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartSeat extends GCMessage{
	
	/** 玩家d */
	private long playerId;
	/** 坐下位置 */
	private int pos;

	public GCBaccartSeat (){
	}
	
	public GCBaccartSeat (
			long playerId,
			int pos ){
			this.playerId = playerId;
			this.pos = pos;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		pos = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeInteger(pos);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_SEAT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_SEAT";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
}