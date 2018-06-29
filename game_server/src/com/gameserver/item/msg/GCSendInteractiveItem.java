package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 发送互动道具
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSendInteractiveItem extends GCMessage{
	
	/** 发送玩家id */
	private long fromId;
	/** 互动道具id */
	private int itemId;
	/** 玩家id */
	private long toId;

	public GCSendInteractiveItem (){
	}
	
	public GCSendInteractiveItem (
			long fromId,
			int itemId,
			long toId ){
			this.fromId = fromId;
			this.itemId = itemId;
			this.toId = toId;
	}

	@Override
	protected boolean readImpl() {
		fromId = readLong();
		itemId = readInteger();
		toId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(fromId);
		writeInteger(itemId);
		writeLong(toId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SEND_INTERACTIVE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SEND_INTERACTIVE_ITEM";
	}

	public long getFromId(){
		return fromId;
	}
		
	public void setFromId(long fromId){
		this.fromId = fromId;
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public long getToId(){
		return toId;
	}
		
	public void setToId(long toId){
		this.toId = toId;
	}
}