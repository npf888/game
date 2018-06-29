package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 单独摇色子
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDiceSingleSwing extends CGMessage{
	
	
	public CGDiceSingleSwing (){
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
		return MessageType.CG_DICE_SINGLE_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DICE_SINGLE_SWING";
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleDiceSingleSwing(this.getSession().getPlayer(), this);
	}
}