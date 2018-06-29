package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 自己获奖记录
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSngRankInfo extends CGMessage{
	
	
	public CGSngRankInfo (){
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
		return MessageType.CG_SNG_RANK_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SNG_RANK_INFO";
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSngRankInfo(this.getSession().getPlayer(), this);
	}
}