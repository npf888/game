package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 服务器更新邮件列表,显示新邮件
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCUpdateMailList extends GCMessage{
	
	/** 邮件类型 */
	private int mailKind;
	/** 邮件id */
	private long mailId;
	/** 邮件详细信息 */
	private com.gameserver.mail.data.MailInfoData mailInfoData;

	public GCUpdateMailList (){
	}
	
	public GCUpdateMailList (
			int mailKind,
			long mailId,
			com.gameserver.mail.data.MailInfoData mailInfoData ){
			this.mailKind = mailKind;
			this.mailId = mailId;
			this.mailInfoData = mailInfoData;
	}

	@Override
	protected boolean readImpl() {
		mailKind = readInteger();
		mailId = readLong();
		mailInfoData = new com.gameserver.mail.data.MailInfoData();
					mailInfoData.setMailId(readLong());
							mailInfoData.setSendId(readLong());
							mailInfoData.setSendName(readString());
							mailInfoData.setMailCdTime(readLong());
							mailInfoData.setHasAttachment(readInteger());
							mailInfoData.setMailStatus(readInteger());
							mailInfoData.setMailTitle(readString());
							mailInfoData.setMailCreatTime(readLong());
							mailInfoData.setIsFriendSend(readInteger());
							mailInfoData.setVipLevel(readInteger());
							mailInfoData.setHeadName(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mailKind);
		writeLong(mailId);
		writeLong(mailInfoData.getMailId());
		writeLong(mailInfoData.getSendId());
		writeString(mailInfoData.getSendName());
		writeLong(mailInfoData.getMailCdTime());
		writeInteger(mailInfoData.getHasAttachment());
		writeInteger(mailInfoData.getMailStatus());
		writeString(mailInfoData.getMailTitle());
		writeLong(mailInfoData.getMailCreatTime());
		writeInteger(mailInfoData.getIsFriendSend());
		writeInteger(mailInfoData.getVipLevel());
		writeString(mailInfoData.getHeadName());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_MAIL_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_MAIL_LIST";
	}

	public int getMailKind(){
		return mailKind;
	}
		
	public void setMailKind(int mailKind){
		this.mailKind = mailKind;
	}

	public long getMailId(){
		return mailId;
	}
		
	public void setMailId(long mailId){
		this.mailId = mailId;
	}

	public com.gameserver.mail.data.MailInfoData getMailInfoData(){
		return mailInfoData;
	}
		
	public void setMailInfoData(com.gameserver.mail.data.MailInfoData mailInfoData){
		this.mailInfoData = mailInfoData;
	}
}