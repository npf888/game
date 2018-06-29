package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州加注
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasAddBet extends CGMessage{
	
	/** 加注额度 */
	private long addBet;
	
	public CGTexasAddBet (){
	}
	
	public CGTexasAddBet (
			long addBet ){
			this.addBet = addBet;
	}
	
	@Override
	protected boolean readImpl() {
		addBet = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(addBet);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TEXAS_ADD_BET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_ADD_BET";
	}

	public long getAddBet(){
		return addBet;
	}
		
	public void setAddBet(long addBet){
		this.addBet = addBet;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasAddBet(this.getSession().getPlayer(), this);
	}
}