package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获得优惠券
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCObtainCoupon extends GCMessage{
	
	/** 优惠券 模板的ID */
	private int couponId;

	public GCObtainCoupon (){
	}
	
	public GCObtainCoupon (
			int couponId ){
			this.couponId = couponId;
	}

	@Override
	protected boolean readImpl() {
		couponId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(couponId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OBTAIN_COUPON;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OBTAIN_COUPON";
	}

	public int getCouponId(){
		return couponId;
	}
		
	public void setCouponId(int couponId){
		this.couponId = couponId;
	}
}