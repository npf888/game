package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 请求老虎机前3名排行数据
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotGetRank extends CGMessage{
	
	/** 比赛类型 */
	private int tournamentType;
	
	public CGSlotGetRank (){
	}
	
	public CGSlotGetRank (
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
		return MessageType.CG_SLOT_GET_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_GET_RANK";
	}

	public int getTournamentType(){
		return tournamentType;
	}
		
	public void setTournamentType(int tournamentType){
		this.tournamentType = tournamentType;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotGetRank(this.getSession().getPlayer(), this);
	}
}