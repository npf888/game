package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 响应客户端请求读取邮件
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCReadMail extends GCMessage{
	
	/** 邮件id */
	private long mailId;
	/** 邮件类型 */
	private int mailKind;
	/** 邮件内容 */
	private String content;
	/** 接收时间 */
	private long receiveTime;
	/** 提示信息 */
	private String promptMessage;
	/** 是否已经处理过好友申请 */
	private int isDealWith;
	/** 是否有奖品未领取 */
	private int hasAttachment;
	/** 邮件奖励 */
	private com.gameserver.common.data.RandRewardData[] randReward;

	public GCReadMail (){
	}
	
	public GCReadMail (
			long mailId,
			int mailKind,
			String content,
			long receiveTime,
			String promptMessage,
			int isDealWith,
			int hasAttachment,
			com.gameserver.common.data.RandRewardData[] randReward ){
			this.mailId = mailId;
			this.mailKind = mailKind;
			this.content = content;
			this.receiveTime = receiveTime;
			this.promptMessage = promptMessage;
			this.isDealWith = isDealWith;
			this.hasAttachment = hasAttachment;
			this.randReward = randReward;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		mailId = readLong();
		mailKind = readInteger();
		content = readString();
		receiveTime = readLong();
		promptMessage = readString();
		isDealWith = readInteger();
		hasAttachment = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		randReward = new com.gameserver.common.data.RandRewardData[count];
		for(int i=0; i<count; i++){
			com.gameserver.common.data.RandRewardData obj = new com.gameserver.common.data.RandRewardData();
			obj.setRewardId(readInteger());
			obj.setRewardCount(readInteger());
			randReward[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(mailId);
		writeInteger(mailKind);
		writeString(content);
		writeLong(receiveTime);
		writeString(promptMessage);
		writeInteger(isDealWith);
		writeInteger(hasAttachment);
		writeShort(randReward.length);
		for(int i=0; i<randReward.length; i++){
			writeInteger(randReward[i].getRewardId());
			writeInteger(randReward[i].getRewardCount());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_READ_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_READ_MAIL";
	}

	public long getMailId(){
		return mailId;
	}
		
	public void setMailId(long mailId){
		this.mailId = mailId;
	}

	public int getMailKind(){
		return mailKind;
	}
		
	public void setMailKind(int mailKind){
		this.mailKind = mailKind;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public long getReceiveTime(){
		return receiveTime;
	}
		
	public void setReceiveTime(long receiveTime){
		this.receiveTime = receiveTime;
	}

	public String getPromptMessage(){
		return promptMessage;
	}
		
	public void setPromptMessage(String promptMessage){
		this.promptMessage = promptMessage;
	}

	public int getIsDealWith(){
		return isDealWith;
	}
		
	public void setIsDealWith(int isDealWith){
		this.isDealWith = isDealWith;
	}

	public int getHasAttachment(){
		return hasAttachment;
	}
		
	public void setHasAttachment(int hasAttachment){
		this.hasAttachment = hasAttachment;
	}

	public com.gameserver.common.data.RandRewardData[] getRandReward(){
		return randReward;
	}

	public void setRandReward(com.gameserver.common.data.RandRewardData[] randReward){
		this.randReward = randReward;
	}	
}