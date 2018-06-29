package com.gameserver.gift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.gift.handler.GiftHandlerFactory;

/**
 * 请求多种礼包
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRequestGift extends CGMessage{
	
	
	public CGRequestGift (){
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
		return MessageType.CG_REQUEST_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQUEST_GIFT";
	}
	


	@Override
	public void execute() {
		GiftHandlerFactory.getHandler().handleRequestGift(this.getSession().getPlayer(), this);
	}
}