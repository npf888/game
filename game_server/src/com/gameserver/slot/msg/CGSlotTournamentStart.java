package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 竞赛开始
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotTournamentStart extends CGMessage{
	
	/** 竞赛类型 */
	private int tournamentType;
	
	public CGSlotTournamentStart (){
	}
	
	public CGSlotTournamentStart (
			int tournamentType ){
			this.tournamentType = tournamentType;
	}
	
	@Override
	protected boolean readImpl() {
		tournamentType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(tournamentType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TOURNAMENT_START;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TOURNAMENT_START";
	}

	public int getTournamentType(){
		return tournamentType;
	}
		
	public void setTournamentType(int tournamentType){
		this.tournamentType = tournamentType;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotTournamentStart(this.getSession().getPlayer(), this);
	}
}