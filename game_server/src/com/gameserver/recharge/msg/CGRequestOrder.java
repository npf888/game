package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 请求订单
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRequestOrder extends CGMessage{
	
	/** 产品d */
	private int productId;
	
	public CGRequestOrder (){
	}
	
	public CGRequestOrder (
			int productId ){
			this.productId = productId;
	}
	
	@Override
	protected boolean readImpl() {
		productId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(productId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REQUEST_ORDER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQUEST_ORDER";
	}

	public int getProductId(){
		return productId;
	}
		
	public void setProductId(int productId){
		this.productId = productId;
	}
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleRequestOrder(this.getSession().getPlayer(), this);
	}
}