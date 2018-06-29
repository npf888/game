package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 获取俱乐留排行榜
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubRankingList extends CGMessage{
	
	/** 1 活跃帮   2贡献榜 */
	private int opType;
	
	public CGClubRankingList (){
	}
	
	public CGClubRankingList (
			int opType ){
			this.opType = opType;
	}
	
	@Override
	protected boolean readImpl() {
		opType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(opType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_RANKING_lIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_RANKING_lIST";
	}

	public int getOpType(){
		return opType;
	}
		
	public void setOpType(int opType){
		this.opType = opType;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubRankingList(this.getSession().getPlayer(), this);
	}
}