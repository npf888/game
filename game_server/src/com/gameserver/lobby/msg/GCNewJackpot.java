package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 游戏彩金数
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCNewJackpot extends GCMessage{
	
	/** 根据bet数 获取的相应的彩金值 */
	private long jackpot;

	public GCNewJackpot (){
	}
	
	public GCNewJackpot (
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
		return MessageType.GC_NEW_JACKPOT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_NEW_JACKPOT";
	}

	public long getJackpot(){
		return jackpot;
	}
		
	public void setJackpot(long jackpot){
		this.jackpot = jackpot;
	}
}