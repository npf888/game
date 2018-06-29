package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州sng报名
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGJoinTexasSng extends CGMessage{
	
	/** sng id */
	private int sngId;
	
	public CGJoinTexasSng (){
	}
	
	public CGJoinTexasSng (
			int sngId ){
			this.sngId = sngId;
	}
	
	@Override
	protected boolean readImpl() {
		sngId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(sngId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_JOIN_TEXAS_SNG;
	}
	
	@Override
	public String getTypeName() {
		return "CG_JOIN_TEXAS_SNG";
	}

	public int getSngId(){
		return sngId;
	}
		
	public void setSngId(int sngId){
		this.sngId = sngId;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleJoinTexasSng(this.getSession().getPlayer(), this);
	}
}