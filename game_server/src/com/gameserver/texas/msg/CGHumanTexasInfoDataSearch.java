package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州补充筹码数
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanTexasInfoDataSearch extends CGMessage{
	
	/** 玩家id */
	private long playerId;
	
	public CGHumanTexasInfoDataSearch (){
	}
	
	public CGHumanTexasInfoDataSearch (
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
		return MessageType.CG_HUMAN_TEXAS_INFO_DATA_SEARCH;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_TEXAS_INFO_DATA_SEARCH";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleHumanTexasInfoDataSearch(this.getSession().getPlayer(), this);
	}
}