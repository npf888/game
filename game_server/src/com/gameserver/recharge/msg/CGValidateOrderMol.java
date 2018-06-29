package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 验证订单MOL
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGValidateOrderMol extends CGMessage{
	
	/** 订单号 */
	private long orderId;
	/** 币种 */
	private String currencyCode;
	/** 单位是分 */
	private int amount;
	/** MOL订单 */
	private String paymentId;
	/** 发放的游戏币PID为-1的时候有效 */
	private String virtualCurrencyAmount;
	
	public CGValidateOrderMol (){
	}
	
	public CGValidateOrderMol (
			long orderId,
			String currencyCode,
			int amount,
			String paymentId,
			String virtualCurrencyAmount ){
			this.orderId = orderId;
			this.currencyCode = currencyCode;
			this.amount = amount;
			this.paymentId = paymentId;
			this.virtualCurrencyAmount = virtualCurrencyAmount;
	}
	
	@Override
	protected boolean readImpl() {
		orderId = readLong();
		currencyCode = readString();
		amount = readInteger();
		paymentId = readString();
		virtualCurrencyAmount = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(orderId);
		writeString(currencyCode);
		writeInteger(amount);
		writeString(paymentId);
		writeString(virtualCurrencyAmount);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_VALIDATE_ORDER_MOL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_VALIDATE_ORDER_MOL";
	}

	public long getOrderId(){
		return orderId;
	}
		
	public void setOrderId(long orderId){
		this.orderId = orderId;
	}

	public String getCurrencyCode(){
		return currencyCode;
	}
		
	public void setCurrencyCode(String currencyCode){
		this.currencyCode = currencyCode;
	}

	public int getAmount(){
		return amount;
	}
		
	public void setAmount(int amount){
		this.amount = amount;
	}

	public String getPaymentId(){
		return paymentId;
	}
		
	public void setPaymentId(String paymentId){
		this.paymentId = paymentId;
	}

	public String getVirtualCurrencyAmount(){
		return virtualCurrencyAmount;
	}
		
	public void setVirtualCurrencyAmount(String virtualCurrencyAmount){
		this.virtualCurrencyAmount = virtualCurrencyAmount;
	}
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleValidateOrderMol(this.getSession().getPlayer(), this);
	}
}