package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 验证订单mycard
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGValidateOrderMycard extends CGMessage{
	
	/** 订单号 */
	private long orderId;
	
	public CGValidateOrderMycard (){
	}
	
	public CGValidateOrderMycard (
			long orderId ){
			this.orderId = orderId;
	}
	
	@Override
	protected boolean readImpl() {
		orderId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(orderId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_VALIDATE_ORDER_MYCARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_VALIDATE_ORDER_MYCARD";
	}

	public long getOrderId(){
		return orderId;
	}
		
	public void setOrderId(long orderId){
		this.orderId = orderId;
	}
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleValidateOrderMycard(this.getSession().getPlayer(), this);
	}
}