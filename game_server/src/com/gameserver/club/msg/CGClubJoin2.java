package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 从俱乐部列表加入俱乐部
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubJoin2 extends CGMessage{
	
	/** 俱乐部id */
	private String clubId;
	
	public CGClubJoin2 (){
	}
	
	public CGClubJoin2 (
			String clubId ){
			this.clubId = clubId;
	}
	
	@Override
	protected boolean readImpl() {
		clubId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(clubId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_JOIN2;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_JOIN2";
	}

	public String getClubId(){
		return clubId;
	}
		
	public void setClubId(String clubId){
		this.clubId = clubId;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubJoin2(this.getSession().getPlayer(), this);
	}
}