package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 商城的道具列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooMallRequest extends GCMessage{
	
	/** 商城的道具列表 */
	private com.gameserver.item.data.ItemInfoDataNew[] itemInfoDataNew;

	public GCBazooMallRequest (){
	}
	
	public GCBazooMallRequest (
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
		return MessageType.GC_BAZOO_MALL_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_MALL_REQUEST";
	}

	public com.gameserver.item.data.ItemInfoDataNew[] getItemInfoDataNew(){
		return itemInfoDataNew;
	}

	public void setItemInfoDataNew(com.gameserver.item.data.ItemInfoDataNew[] itemInfoDataNew){
		this.itemInfoDataNew = itemInfoDataNew;
	}	
}