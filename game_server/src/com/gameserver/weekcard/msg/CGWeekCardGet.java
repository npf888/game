package com.gameserver.weekcard.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.weekcard.handler.WeekcardHandlerFactory;

/**
 * 领取周卡
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGWeekCardGet extends CGMessage{
	
	
	public CGWeekCardGet (){
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
		return MessageType.CG_WEEK_CARD_GET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_WEEK_CARD_GET";
	}
	


	@Override
	public void execute() {
		WeekcardHandlerFactory.getHandler().handleWeekCardGet(this.getSession().getPlayer(), this);
	}
}