package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端请求商品信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanBagInfoData extends GCMessage{
	
	/** 商品信息 */
	private com.gameserver.item.data.ItemInfoData[] itemList;

	public GCHumanBagInfoData (){
	}
	
	public GCHumanBagInfoData (
			com.gameserver.item.data.ItemInfoData[] itemList ){
			this.itemList = itemList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		itemList = new com.gameserver.item.data.ItemInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.item.data.ItemInfoData obj = new com.gameserver.item.data.ItemInfoData();
			obj.setUuid(readString());
			obj.setTemplateId(readInteger());
			obj.setOverlap(readInteger());
			obj.setBeginTime(readLong());
			obj.setDuration(readLong());
			itemList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(itemList.length);
		for(int i=0; i<itemList.length; i++){
			writeString(itemList[i].getUuid());
			writeInteger(itemList[i].getTemplateId());
			writeInteger(itemList[i].getOverlap());
			writeLong(itemList[i].getBeginTime());
			writeLong(itemList[i].getDuration());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_BAG_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_BAG_INFO_DATA";
	}

	public com.gameserver.item.data.ItemInfoData[] getItemList(){
		return itemList;
	}

	public void setItemList(com.gameserver.item.data.ItemInfoData[] itemList){
		this.itemList = itemList;
	}	
}