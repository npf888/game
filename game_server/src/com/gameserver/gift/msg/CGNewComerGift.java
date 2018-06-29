package com.gameserver.gift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.gift.handler.GiftHandlerFactory;

/**
 * 新手礼包
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGNewComerGift extends CGMessage{
	
	
	public CGNewComerGift (){
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
		return MessageType.CG_NEW_COMER_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_NEW_COMER_GIFT";
	}
	


	@Override
	public void execute() {
		GiftHandlerFactory.getHandler().handleNewComerGift(this.getSession().getPlayer(), this);
	}
}