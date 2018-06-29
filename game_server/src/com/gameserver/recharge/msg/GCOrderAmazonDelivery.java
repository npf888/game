package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 验证订单亚马逊订单返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCOrderAmazonDelivery extends GCMessage{
	
	/** 成功的收据ID */
	private String receiptId;

	public GCOrderAmazonDelivery (){
	}
	
	public GCOrderAmazonDelivery (
			String receiptId ){
			this.receiptId = receiptId;
	}

	@Override
	protected boolean readImpl() {
		receiptId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(receiptId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ORDER_AMAZON_DELIVERY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ORDER_AMAZON_DELIVERY";
	}

	public String getReceiptId(){
		return receiptId;
	}
		
	public void setReceiptId(String receiptId){
		this.receiptId = receiptId;
	}
}