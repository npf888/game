package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐留言板发送礼包
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubNoteSendGift extends CGMessage{
	
	/** 奖励id */
	private int pid;
	
	public CGClubNoteSendGift (){
	}
	
	public CGClubNoteSendGift (
			int pid ){
			this.pid = pid;
	}
	
	@Override
	protected boolean readImpl() {
		pid = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_NOTE_SEND_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_NOTE_SEND_GIFT";
	}

	public int getPid(){
		return pid;
	}
		
	public void setPid(int pid){
		this.pid = pid;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubNoteSendGift(this.getSession().getPlayer(), this);
	}
}