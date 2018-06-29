package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐复活
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartRevive extends GCMessage{
	
	/** 复活 */
	private int revive;

	public GCBaccartRevive (){
	}
	
	public GCBaccartRevive (
			int revive ){
			this.revive = revive;
	}

	@Override
	protected boolean readImpl() {
		revive = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(revive);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_REVIVE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_REVIVE";
	}

	public int getRevive(){
		return revive;
	}
		
	public void setRevive(int revive){
		this.revive = revive;
	}
}