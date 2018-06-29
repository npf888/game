package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐部改名字
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubChangeName extends CGMessage{
	
	/** 俱乐部名字 */
	private String name;
	
	public CGClubChangeName (){
	}
	
	public CGClubChangeName (
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
		return MessageType.CG_CLUB_CHANGE_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_CHANGE_NAME";
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubChangeName(this.getSession().getPlayer(), this);
	}
}