package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 双倍经验加成
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGExpDouble extends CGMessage{
	
	
	public CGExpDouble (){
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
		return MessageType.CG_EXP_DOUBLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EXP_DOUBLE";
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleExpDouble(this.getSession().getPlayer(), this);
	}
}