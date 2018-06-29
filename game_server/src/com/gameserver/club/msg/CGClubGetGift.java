package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐部留言礼物
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubGetGift extends CGMessage{
	
	/** 留言id */
	private String msgId;
	
	public CGClubGetGift (){
	}
	
	public CGClubGetGift (
			String msgId ){
			this.msgId = msgId;
	}
	
	@Override
	protected boolean readImpl() {
		msgId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(msgId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_GET_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_GET_GIFT";
	}

	public String getMsgId(){
		return msgId;
	}
		
	public void setMsgId(String msgId){
		this.msgId = msgId;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubGetGift(this.getSession().getPlayer(), this);
	}
}