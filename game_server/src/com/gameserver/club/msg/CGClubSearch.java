package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐搜索
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubSearch extends CGMessage{
	
	/** 俱乐部名字 */
	private String name;
	
	public CGClubSearch (){
	}
	
	public CGClubSearch (
			String name ){
			this.name = name;
	}
	
	@Override
	protected boolean readImpl() {
		name = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(name);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_SEARCH;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_SEARCH";
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubSearch(this.getSession().getPlayer(), this);
	}
}