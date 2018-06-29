package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 优惠券是否存在
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCouponExist extends GCMessage{
	
	/** 是否存在 1 是 有优惠券    0 是没有优惠券 */
	private int couponExist;

	public GCCouponExist (){
	}
	
	public GCCouponExist (
			int couponExist ){
			this.couponExist = couponExist;
	}

	@Override
	protected boolean readImpl() {
		couponExist = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(couponExist);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COUPON_EXIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COUPON_EXIST";
	}

	public int getCouponExist(){
		return couponExist;
	}
		
	public void setCouponExist(int couponExist){
		this.couponExist = couponExist;
	}
}