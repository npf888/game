package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 百家乐玩家信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanBaccart extends CGMessage{
	
	/** 玩家id */
	private long playerId;
	
	public CGHumanBaccart (){
	}
	
	public CGHumanBaccart (
			long playerId ){
			this.playerId = playerId;
	}
	
	@Override
	protected boolean readImpl() {
		playerId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HUMAN_BACCART;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_BACCART";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleHumanBaccart(this.getSession().getPlayer(), this);
	}
}