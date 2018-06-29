package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.mail.handler.MailHandlerFactory;

/**
 * 客户端请求读取邮件
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGReadMail extends CGMessage{
	
	/** 邮件id */
	private long mailId;
	
	public CGReadMail (){
	}
	
	public CGReadMail (
			long mailId ){
			this.mailId = mailId;
	}
	
	@Override
	protected boolean readImpl() {
		mailId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(mailId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_READ_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_READ_MAIL";
	}

	public long getMailId(){
		return mailId;
	}
		
	public void setMailId(long mailId){
		this.mailId = mailId;
	}
		@Override
	public boolean isCollect()
	{
		return true;
	}
	


	@Override
	public void execute() {
		MailHandlerFactory.getHandler().handleReadMail(this.getSession().getPlayer(), this);
	}
}