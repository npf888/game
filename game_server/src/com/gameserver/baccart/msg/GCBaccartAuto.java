package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐自动补充
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartAuto extends GCMessage{
	
	/** 自动补充 */
	private int auto;

	public GCBaccartAuto (){
	}
	
	public GCBaccartAuto (
			int auto ){
			this.auto = auto;
	}

	@Override
	protected boolean readImpl() {
		auto = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(auto);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_AUTO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_AUTO";
	}

	public int getAuto(){
		return auto;
	}
		
	public void setAuto(int auto){
		this.auto = auto;
	}
}