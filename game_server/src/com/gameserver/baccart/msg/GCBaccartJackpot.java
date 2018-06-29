package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 彩金池
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartJackpot extends GCMessage{
	
	/** 筹码 */
	private long jackpot;

	public GCBaccartJackpot (){
	}
	
	public GCBaccartJackpot (
			long jackpot ){
			this.jackpot = jackpot;
	}

	@Override
	protected boolean readImpl() {
		jackpot = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(jackpot);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_JACKPOT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_JACKPOT";
	}

	public long getJackpot(){
		return jackpot;
	}
		
	public void setJackpot(long jackpot){
		this.jackpot = jackpot;
	}
}