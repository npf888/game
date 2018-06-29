package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.activity.handler.ActivityHandlerFactory;

/**
 * 返回   周、月特惠充值活动的时间
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGMonthWeekLeftTime extends CGMessage{
	
	
	public CGMonthWeekLeftTime (){
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
		return MessageType.CG_MONTH_WEEK_LEFT_TIME;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MONTH_WEEK_LEFT_TIME";
	}
	


	@Override
	public void execute() {
		ActivityHandlerFactory.getHandler().handleMonthWeekLeftTime(this.getSession().getPlayer(), this);
	}
}