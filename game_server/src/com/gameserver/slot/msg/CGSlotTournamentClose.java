package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 关闭竞赛面板
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotTournamentClose extends CGMessage{
	
	/** 竞赛类型 */
	private int tournamentType;
	
	public CGSlotTournamentClose (){
	}
	
	public CGSlotTournamentClose (
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
		return MessageType.CG_SLOT_TOURNAMENT_CLOSE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TOURNAMENT_CLOSE";
	}

	public int getTournamentType(){
		return tournamentType;
	}
		
	public void setTournamentType(int tournamentType){
		this.tournamentType = tournamentType;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotTournamentClose(this.getSession().getPlayer(), this);
	}
}