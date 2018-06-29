package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐部邀请/拒绝结果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubJoinResult extends GCMessage{
	
	/** 要删除的俱乐部id */
	private String clubId;

	public GCClubJoinResult (){
	}
	
	public GCClubJoinResult (
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
		return MessageType.GC_CLUB_JOIN_RESULT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_JOIN_RESULT";
	}

	public String getClubId(){
		return clubId;
	}
		
	public void setClubId(String clubId){
		this.clubId = clubId;
	}
}