package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 请求订单
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRequestOrderMycard extends CGMessage{
	
	/** 产品d */
	private int productId;
	
	public CGRequestOrderMycard (){
	}
	
	public CGRequestOrderMycard (
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
		return MessageType.CG_REQUEST_ORDER_MYCARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQUEST_ORDER_MYCARD";
	}

	public int getProductId(){
		return productId;
	}
		
	public void setProductId(int productId){
		this.productId = productId;
	}
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleRequestOrderMycard(this.getSession().getPlayer(), this);
	}
}