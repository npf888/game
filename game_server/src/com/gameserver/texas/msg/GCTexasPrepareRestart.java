package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州清理台面
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasPrepareRestart extends GCMessage{
	
	/** 下一局小盲注  */
	private int smallBlind;
	/** 升盲注剩余时间 */
	private long duration;

	public GCTexasPrepareRestart (){
	}
	
	public GCTexasPrepareRestart (
			int smallBlind,
			long duration ){
			this.smallBlind = smallBlind;
			this.duration = duration;
	}

	@Override
	protected boolean readImpl() {
		smallBlind = readInteger();
		duration = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(smallBlind);
		writeLong(duration);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_PREPARE_RESTART;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_PREPARE_RESTART";
	}

	public int getSmallBlind(){
		return smallBlind;
	}
		
	public void setSmallBlind(int smallBlind){
		this.smallBlind = smallBlind;
	}

	public long getDuration(){
		return duration;
	}
		
	public void setDuration(long duration){
		this.duration = duration;
	}
}