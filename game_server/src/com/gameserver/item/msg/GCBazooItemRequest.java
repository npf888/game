package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 请求背包 返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooItemRequest extends GCMessage{
	
	/** 新的背包信息 */
	private com.gameserver.item.data.ItemInfoDataNew[] itemInfoDataNew;

	public GCBazooItemRequest (){
	}
	
	public GCBazooItemRequest (
			com.gameserver.item.data.ItemInfoDataNew[] itemInfoDataNew ){
			this.itemInfoDataNew = itemInfoDataNew;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		itemInfoDataNew = new com.gameserver.item.data.ItemInfoDataNew[count];
		for(int i=0; i<count; i++){
			com.gameserver.item.data.ItemInfoDataNew obj = new com.gameserver.item.data.ItemInfoDataNew();
			obj.setFromPlayerId(readLong());
			obj.setItemId(readInteger());
			obj.setItemType(readInteger());
			obj.setItemName(readString());
			obj.setPrice(readLong());
			obj.setImg(readString());
			obj.setUsingClockItemId(readInteger());
			obj.setIsExist(readInteger());
			itemInfoDataNew[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(itemInfoDataNew.length);
		for(int i=0; i<itemInfoDataNew.length; i++){
			writeLong(itemInfoDataNew[i].getFromPlayerId());
			writeInteger(itemInfoDataNew[i].getItemId());
			writeInteger(itemInfoDataNew[i].getItemType());
			writeString(itemInfoDataNew[i].getItemName());
			writeLong(itemInfoDataNew[i].getPrice());
			writeString(itemInfoDataNew[i].getImg());
			writeInteger(itemInfoDataNew[i].getUsingClockItemId());
			writeInteger(itemInfoDataNew[i].getIsExist());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_ITEM_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_ITEM_REQUEST";
	}

	public com.gameserver.item.data.ItemInfoDataNew[] getItemInfoDataNew(){
		return itemInfoDataNew;
	}

	public void setItemInfoDataNew(com.gameserver.item.data.ItemInfoDataNew[] itemInfoDataNew){
		this.itemInfoDataNew = itemInfoDataNew;
	}	
}