package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 验证订MOL
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRequestOrderMol extends CGMessage{
	
	/** PID-1表示点卡支付 */
	private int productId;
	
	public CGRequestOrderMol (){
	}
	
	public CGRequestOrderMol (
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
		return MessageType.CG_REQUEST_ORDER_MOL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQUEST_ORDER_MOL";
	}

	public int getProductId(){
		return productId;
	}
		
	public void setProductId(int productId){
		this.productId = productId;
	}
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleRequestOrderMol(this.getSession().getPlayer(), this);
	}
}