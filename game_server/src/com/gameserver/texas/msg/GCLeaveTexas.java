package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 离开德州房间
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLeaveTexas extends GCMessage{
	
	/** 离开id */
	private long playerId;
	/** 是否没钱 */
	private int ifNoGold;

	public GCLeaveTexas (){
	}
	
	public GCLeaveTexas (
			long playerId,
			int ifNoGold ){
			this.playerId = playerId;
			this.ifNoGold = ifNoGold;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		ifNoGold = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeInteger(ifNoGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LEAVE_TEXAS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEAVE_TEXAS";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public int getIfNoGold(){
		return ifNoGold;
	}
		
	public void setIfNoGold(int ifNoGold){
		this.ifNoGold = ifNoGold;
	}
}