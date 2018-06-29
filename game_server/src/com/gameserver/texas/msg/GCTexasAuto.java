package com.gameserver.texas.msg;

import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.msg.MessageType;

/**
 * 德州改变自动补充筹码
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasAuto extends GCMessage{
	
	/** 补充 */
	private int isAuto;

	public GCTexasAuto (){
	}
	
	public GCTexasAuto (
			int isAuto ){
			this.isAuto = isAuto;
	}

	@Override
	protected boolean readImpl() {
		isAuto = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isAuto);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_AUTO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_AUTO";
	}

	public int getIsAuto(){
		return isAuto;
	}
		
	public void setIsAuto(int isAuto){
		this.isAuto = isAuto;
	}
}