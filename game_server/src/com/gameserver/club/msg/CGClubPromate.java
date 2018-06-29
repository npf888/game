package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 授权
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubPromate extends CGMessage{
	
	/** 玩家id */
	private long playerId;
	/** 类型 0 降职 1 升职 */
	private int opType;
	
	public CGClubPromate (){
	}
	
	public CGClubPromate (
			long playerId,
			int opType ){
			this.playerId = playerId;
			this.opType = opType;
	}
	
	@Override
	protected boolean readImpl() {
		playerId = readLong();
		opType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeInteger(opType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_PROMATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_PROMATE";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public int getOpType(){
		return opType;
	}
		
	public void setOpType(int opType){
		this.opType = opType;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubPromate(this.getSession().getPlayer(), this);
	}
}