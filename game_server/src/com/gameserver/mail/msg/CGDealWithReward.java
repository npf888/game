package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.mail.handler.MailHandlerFactory;

/**
 * 客户端请求全部奖品领取奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDealWithReward extends CGMessage{
	
	/** 邮件id */
	private long mailId;
	
	public CGDealWithReward (){
	}
	
	public CGDealWithReward (
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
		return MessageType.CG_DEAL_WITH_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DEAL_WITH_REWARD";
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
		MailHandlerFactory.getHandler().handleDealWithReward(this.getSession().getPlayer(), this);
	}
}