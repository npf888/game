package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 百家乐补充筹码
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartComplement extends CGMessage{
	
	/** 补充数 */
	private long complement;
	
	public CGBaccartComplement (){
	}
	
	public CGBaccartComplement (
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
		return MessageType.CG_BACCART_COMPLEMENT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_COMPLEMENT";
	}

	public long getComplement(){
		return complement;
	}
		
	public void setComplement(long complement){
		this.complement = complement;
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartComplement(this.getSession().getPlayer(), this);
	}
}