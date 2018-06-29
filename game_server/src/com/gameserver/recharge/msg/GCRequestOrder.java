package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 请求订单
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRequestOrder extends GCMessage{
	
	/** 订单信息 */
	private com.gameserver.recharge.data.HumanRechargeOrderInfoData order;

	public GCRequestOrder (){
	}
	
	public GCRequestOrder (
			com.gameserver.recharge.data.HumanRechargeOrderInfoData order ){
			this.order = order;
	}

	@Override
	protected boolean readImpl() {
		order = new com.gameserver.recharge.data.HumanRechargeOrderInfoData();
					order.setOrderId(readLong());
							order.setProductId(readInteger());
							order.setOrderStatus(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(order.getOrderId());
		writeInteger(order.getProductId());
		writeInteger(order.getOrderStatus());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REQUEST_ORDER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REQUEST_ORDER";
	}

	public com.gameserver.recharge.data.HumanRechargeOrderInfoData getOrder(){
		return order;
	}
		
	public void setOrder(com.gameserver.recharge.data.HumanRechargeOrderInfoData order){
		this.order = order;
	}
}