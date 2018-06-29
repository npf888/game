package com.gameserver.mail.redismsg;

import com.db.model.MailEntity;
import com.gameserver.mail.handler.MailHandlerFactory;
import com.gameserver.redis.IRedisMessage;

/**
 * 发送邮件
 * @author wayne
 *
 */
public class MailAddRedisMessage implements IRedisMessage{

	public MailEntity mailEntity;
	public MailAddRedisMessage(){
		
	}
	
	public MailEntity getMailEntity() {
		return mailEntity;
	}

	public void setMailEntity(MailEntity mailEntity) {
		this.mailEntity = mailEntity;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		MailHandlerFactory.getRedisHandler().handleMailAddRedisMessage(this);
	}
	
}
