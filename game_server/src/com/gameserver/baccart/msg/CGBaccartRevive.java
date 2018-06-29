package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 百家乐复活
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartRevive extends CGMessage{
	
	/** 复活 */
	private int revive;
	
	public CGBaccartRevive (){
	}
	
	public CGBaccartRevive (
			int revive ){
			this.revive = revive;
	}
	
	@Override
	protected boolean readImpl() {
		revive = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(revive);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BACCART_REVIVE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_REVIVE";
	}

	public int getRevive(){
		return revive;
	}
		
	public void setRevive(int revive){
		this.revive = revive;
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartRevive(this.getSession().getPlayer(), this);
	}
}