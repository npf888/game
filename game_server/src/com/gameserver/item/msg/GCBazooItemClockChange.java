package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 更换色钟返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooItemClockChange extends GCMessage{
	
	/** 目标色钟的itemId */
	private int itemId;

	public GCBazooItemClockChange (){
	}
	
	public GCBazooItemClockChange (
			int itemId ){
			this.itemId = itemId;
	}

	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_ITEM_CLOCK_CHANGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_ITEM_CLOCK_CHANGE";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}
}