package com.gameserver.givealike.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 是否评论过
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCIsnotGivealike extends GCMessage{
	
	/** 0：否，1：是 */
	private int isNot;

	public GCIsnotGivealike (){
	}
	
	public GCIsnotGivealike (
			int isNot ){
			this.isNot = isNot;
	}

	@Override
	protected boolean readImpl() {
		isNot = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isNot);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ISNOT_GIVEALIKE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ISNOT_GIVEALIKE";
	}

	public int getIsNot(){
		return isNot;
	}
		
	public void setIsNot(int isNot){
		this.isNot = isNot;
	}
}