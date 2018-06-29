package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 未处理订单列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCOrderInfoDataList extends GCMessage{
	
	/** 订单信息 */
	private com.gameserver.recharge.data.HumanRechargeOrderInfoData[] orderList;

	public GCOrderInfoDataList (){
	}
	
	public GCOrderInfoDataList (
			com.gameserver.recharge.data.HumanRechargeOrderInfoData[] orderList ){
			this.orderList = orderList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		orderList = new com.gameserver.recharge.data.HumanRechargeOrderInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.recharge.data.HumanRechargeOrderInfoData obj = new com.gameserver.recharge.data.HumanRechargeOrderInfoData();
			obj.setOrderId(readLong());
			obj.setProductId(readInteger());
			obj.setOrderStatus(readInteger());
			orderList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(orderList.length);
		for(int i=0; i<orderList.length; i++){
			writeLong(orderList[i].getOrderId());
			writeInteger(orderList[i].getProductId());
			writeInteger(orderList[i].getOrderStatus());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ORDER_INFO_DATA_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ORDER_INFO_DATA_LIST";
	}

	public com.gameserver.recharge.data.HumanRechargeOrderInfoData[] getOrderList(){
		return orderList;
	}

	public void setOrderList(com.gameserver.recharge.data.HumanRechargeOrderInfoData[] orderList){
		this.orderList = orderList;
	}	
}