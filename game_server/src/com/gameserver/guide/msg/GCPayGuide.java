package com.gameserver.guide.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回给客户端 付费新手引导
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPayGuide extends GCMessage{
	
	/** 1:treasury-小金猪    2:exp-经验加速卡     3:preference-特惠礼包      4:club-俱乐部      5:monthcard-月卡    6:coupon-优惠券 */
	private int payType;

	public GCPayGuide (){
	}
	
	public GCPayGuide (
			int payType ){
			this.payType = payType;
	}

	@Override
	protected boolean readImpl() {
		payType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(payType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PAY_GUIDE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PAY_GUIDE";
	}

	public int getPayType(){
		return payType;
	}
		
	public void setPayType(int payType){
		this.payType = payType;
	}
}