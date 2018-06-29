package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.recharge.handler.RechargeHandlerFactory;

/**
 * 优惠券是否存在
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGCouponExist extends CGMessage{
	
	
	public CGCouponExist (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_COUPON_EXIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_COUPON_EXIST";
	}
	


	@Override
	public void execute() {
		RechargeHandlerFactory.getHandler().handleCouponExist(this.getSession().getPlayer(), this);
	}
}