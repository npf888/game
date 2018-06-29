package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 群体发送互动道具
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGroupSendInteractiveItem extends GCMessage{
	
	/** 发送玩家id */
	private long fromId;
	/** 互动道具id */
	private int itemId;

	public GCGroupSendInteractiveItem (){
	}
	
	public GCGroupSendInteractiveItem (
			long fromId,
			int itemId ){
			this.fromId = fromId;
			this.itemId = itemId;
	}

	@Override
	protected boolean readImpl() {
		fromId = readLong();
		itemId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(fromId);
		writeInteger(itemId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GROUP_SEND_INTERACTIVE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GROUP_SEND_INTERACTIVE_ITEM";
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
}