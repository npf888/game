package com.gameserver.monthcard.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.monthcard.handler.MonthcardHandlerFactory;

/**
 * 领取月卡
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGMonthCardGet extends CGMessage{
	
	
	public CGMonthCardGet (){
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
		return MessageType.CG_MONTH_CARD_GET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MONTH_CARD_GET";
	}
	


	@Override
	public void execute() {
		MonthcardHandlerFactory.getHandler().handleMonthCardGet(this.getSession().getPlayer(), this);
	}
}