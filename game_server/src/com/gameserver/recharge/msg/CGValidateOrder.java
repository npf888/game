package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 验证订单
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGValidateOrder extends CGMessage{
	
	/** 渠道订单id */
	private String signature;
	/** 产品id */
	private String purchaseData;
	
	public CGValidateOrder (){
	}
	
	public CGValidateOrder (
			String signature,
			String purchaseData ){
			this.signature = signature;
			this.purchaseData = purchaseData;
	}
	
	@Override
	protected boolean readImpl() {
		signature = readString();
		purchaseData = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(signature);
		writeString(purchaseData);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_VALIDATE_ORDER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_VALIDATE_ORDER";
	}

	public String getSignature(){
		return signature;
	}
		
	public void setSignature(String signature){
		this.signature = signature;
	}

	public String getPurchaseData(){
		return purchaseData;
	}
		
	public void setPurchaseData(String purchaseData){
		this.purchaseData = purchaseData;
	}
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleValidateOrder(this.getSession().getPlayer(), this);
	}
}