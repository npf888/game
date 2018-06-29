package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州补充筹码
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasComplement extends CGMessage{
	
	/** 补充数 */
	private long complement;
	
	public CGTexasComplement (){
	}
	
	public CGTexasComplement (
			long complement ){
			this.complement = complement;
	}
	
	@Override
	protected boolean readImpl() {
		complement = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(complement);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TEXAS_COMPLEMENT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_COMPLEMENT";
	}

	public long getComplement(){
		return complement;
	}
		
	public void setComplement(long complement){
		this.complement = complement;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasComplement(this.getSession().getPlayer(), this);
	}
}