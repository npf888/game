package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 验证订单亚马逊
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGOrderAmazon extends CGMessage{
	
	/** 订单号 */
	private long orderId;
	/** 收据ID */
	private String receiptId;
	/** Amazon客户的 ID */
	private String userId;
	
	public CGOrderAmazon (){
	}
	
	public CGOrderAmazon (
			long orderId,
			String receiptId,
			String userId ){
			this.orderId = orderId;
			this.receiptId = receiptId;
			this.userId = userId;
	}
	
	@Override
	protected boolean readImpl() {
		orderId = readLong();
		receiptId = readString();
		userId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(orderId);
		writeString(receiptId);
		writeString(userId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ORDER_AMAZON;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ORDER_AMAZON";
	}

	public long getOrderId(){
		return orderId;
	}
		
	public void setOrderId(long orderId){
		this.orderId = orderId;
	}

	public String getReceiptId(){
		return receiptId;
	}
		
	public void setReceiptId(String receiptId){
		this.receiptId = receiptId;
	}

	public String getUserId(){
		return userId;
	}
		
	public void setUserId(String userId){
		this.userId = userId;
	}
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleOrderAmazon(this.getSession().getPlayer(), this);
	}
}