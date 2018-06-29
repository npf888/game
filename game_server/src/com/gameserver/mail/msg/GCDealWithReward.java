package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 响应客户端请求全部奖品领取奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDealWithReward extends GCMessage{
	
	/** 邮件id */
	private long mailId;

	public GCDealWithReward (){
	}
	
	public GCDealWithReward (
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
		return MessageType.GC_DEAL_WITH_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DEAL_WITH_REWARD";
	}

	public long getMailId(){
		return mailId;
	}
		
	public void setMailId(long mailId){
		this.mailId = mailId;
	}
}