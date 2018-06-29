package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.mail.handler.MailHandlerFactory;

/**
 * 一键领取邮件
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGReceiveAll extends CGMessage{
	
	
	public CGReceiveAll (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_RECEIVE_ALL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RECEIVE_ALL";
	}
	


	@Override
	public void execute() {
		MailHandlerFactory.getHandler().handleReceiveAll(this.getSession().getPlayer(), this);
	}
}