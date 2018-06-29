package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐留言板发送
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubNoteSend extends CGMessage{
	
	/** 内容 */
	private String msg;
	
	public CGClubNoteSend (){
	}
	
	public CGClubNoteSend (
			String msg ){
			this.msg = msg;
	}
	
	@Override
	protected boolean readImpl() {
		msg = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(msg);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_NOTE_SEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_NOTE_SEND";
	}

	public String getMsg(){
		return msg;
	}
		
	public void setMsg(String msg){
		this.msg = msg;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubNoteSend(this.getSession().getPlayer(), this);
	}
}