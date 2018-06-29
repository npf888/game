package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获得券通知
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetVouchers extends GCMessage{
	
	/** 物品ID */
	private int itemId;
	/** 数量 */
	private int num;

	public GCGetVouchers (){
	}
	
	public GCGetVouchers (
			int itemId,
			int num ){
			this.itemId = itemId;
			this.num = num;
	}

	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		num = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		writeInteger(num);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_VOUCHERS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_VOUCHERS";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getNum(){
		return num;
	}
		
	public void setNum(int num){
		this.num = num;
	}
}