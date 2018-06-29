package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 打开收集系统初始数据请求返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCollectInit extends GCMessage{
	
	/** 抽奖券  */
	private com.gameserver.collect.data.ItemData1[] itemData1;
	/** 碎片数量  */
	private com.gameserver.collect.data.ItemData2[] itemData2;

	public GCCollectInit (){
	}
	
	public GCCollectInit (
			com.gameserver.collect.data.ItemData1[] itemData1,
			com.gameserver.collect.data.ItemData2[] itemData2 ){
			this.itemData1 = itemData1;
			this.itemData2 = itemData2;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		itemData1 = new com.gameserver.collect.data.ItemData1[count];
		for(int i=0; i<count; i++){
			com.gameserver.collect.data.ItemData1 obj = new com.gameserver.collect.data.ItemData1();
			obj.setCardType(readInteger());
			obj.setNum(readInteger());
			itemData1[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		itemData2 = new com.gameserver.collect.data.ItemData2[count];
		for(int i=0; i<count; i++){
			com.gameserver.collect.data.ItemData2 obj = new com.gameserver.collect.data.ItemData2();
			obj.setItemid(readInteger());
			obj.setNum(readInteger());
			itemData2[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(itemData1.length);
		for(int i=0; i<itemData1.length; i++){
			writeInteger(itemData1[i].getCardType());
			writeInteger(itemData1[i].getNum());
		}
		writeShort(itemData2.length);
		for(int i=0; i<itemData2.length; i++){
			writeInteger(itemData2[i].getItemid());
			writeInteger(itemData2[i].getNum());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COLLECT_INIT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COLLECT_INIT";
	}

	public com.gameserver.collect.data.ItemData1[] getItemData1(){
		return itemData1;
	}

	public void setItemData1(com.gameserver.collect.data.ItemData1[] itemData1){
		this.itemData1 = itemData1;
	}	

	public com.gameserver.collect.data.ItemData2[] getItemData2(){
		return itemData2;
	}

	public void setItemData2(com.gameserver.collect.data.ItemData2[] itemData2){
		this.itemData2 = itemData2;
	}	
}