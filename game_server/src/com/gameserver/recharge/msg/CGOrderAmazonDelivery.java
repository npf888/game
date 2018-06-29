package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 验证订单亚马逊
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGOrderAmazonDelivery extends CGMessage{
	
	/** 道具奖励 */
	private com.gameserver.recharge.data.AmazonDelivery[] amazonDeliveryList;
	
	public CGOrderAmazonDelivery (){
	}
	
	public CGOrderAmazonDelivery (
			com.gameserver.recharge.data.AmazonDelivery[] amazonDeliveryList ){
			this.amazonDeliveryList = amazonDeliveryList;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
				amazonDeliveryList = new com.gameserver.recharge.data.AmazonDelivery[count];
				for(int i=0; i<count; i++){
			com.gameserver.recharge.data.AmazonDelivery obj  =new com.gameserver.recharge.data.AmazonDelivery();
			obj.setProductId(readInteger());
			obj.setReceiptId(readString());
			obj.setUserId(readString());
			amazonDeliveryList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(amazonDeliveryList.length);
		for(int i=0; i<amazonDeliveryList.length; i++){
			writeInteger(amazonDeliveryList[i].getProductId());
			writeString(amazonDeliveryList[i].getReceiptId());
			writeString(amazonDeliveryList[i].getUserId());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ORDER_AMAZON_DELIVERY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ORDER_AMAZON_DELIVERY";
	}

	public com.gameserver.recharge.data.AmazonDelivery[] getAmazonDeliveryList(){
		return amazonDeliveryList;
	}

	public void setAmazonDeliveryList(com.gameserver.recharge.data.AmazonDelivery[] amazonDeliveryList){
		this.amazonDeliveryList = amazonDeliveryList;
	}	
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleOrderAmazonDelivery(this.getSession().getPlayer(), this);
	}
}