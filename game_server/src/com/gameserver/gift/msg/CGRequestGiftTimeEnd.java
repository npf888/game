package com.gameserver.gift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.gift.handler.GiftHandlerFactory;

/**
 * 当时间为0时，调用这个接口 ,目的是返回提示信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRequestGiftTimeEnd extends CGMessage{
	
	
	public CGRequestGiftTimeEnd (){
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
		return MessageType.CG_REQUEST_GIFT_TIME_END;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQUEST_GIFT_TIME_END";
	}
	


	@Override
	public void execute() {
		GiftHandlerFactory.getHandler().handleRequestGiftTimeEnd(this.getSession().getPlayer(), this);
	}
}