package com.gameserver.mail.data;

import com.gameserver.mail.Mail;

/**
 * 邮件信息
 * @author wayne
 *
 */
public class MailInfoData {
	private long mailId;
	/** 发件角色id*/
	private long sendId;
	/** 发件角色名称*/
	private String sendName;
	/** 邮件还有多长时间过期*/
	private long mailCdTime;
	/** 是否以读*/
	private int isRead;
	/** 是否有奖品未领取*/
	private int hasAttachment;
	/**邮件状态*/
	private int mailStatus;
	/** 邮件标题*/
	private String mailTitle;
	/** 邮件发送时间*/
	private long mailCreatTime;
	/** 是否是好友发送的    0:是，1:否*/
	private int isFriendSend;
	private String headName;
	private int vipLevel;
	
	public long getMailId() {
		return mailId;
	}
	public void setMailId(long mailId) {
		this.mailId = mailId;
	}
	public long getSendId() {
		return sendId;
	}
	public void setSendId(long sendId) {
		this.sendId = sendId;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public long getMailCdTime() {
		return mailCdTime;
	}
	public void setMailCdTime(long mailCdTime) {
		this.mailCdTime = mailCdTime;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public int getHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(int hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	/**
	 * @return the mailStatus
	 */
	public int getMailStatus() {
		return mailStatus;
	}
	/**
	 * @param mailStatus the mailStatus to set
	 */
	public void setMailStatus(int mailStatus) {
		this.mailStatus = mailStatus;
	}
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	public long getMailCreatTime() {
		return mailCreatTime;
	}
	public void setMailCreatTime(long mailCreatTime) {
		this.mailCreatTime = mailCreatTime;
	}
	
	public int getIsFriendSend() {
		return isFriendSend;
	}
	public void setIsFriendSend(int isFriendSend) {
		this.isFriendSend = isFriendSend;
	}
	
	
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	/**
	 * 
	 * @param mail
	 * @return
	 */
	public static MailInfoData convertFromMail(Mail mail)
	{
		MailInfoData mailInfoData=new MailInfoData();
		mailInfoData.setMailId(mail.getDbId());
		mailInfoData.setSendId(mail.getSendId());
		mailInfoData.setSendName(mail.getSendName());
		mailInfoData.setHasAttachment(mail.getHasAttachment().getIndex());
		mailInfoData.setMailTitle(mail.getTitle());
		mailInfoData.setMailCreatTime(mail.getCreateTime());
		mailInfoData.setMailCdTime(mail.getMailCdTime());
		mailInfoData.setMailStatus(mail.getMailStatus().getIndex());
		mailInfoData.setHeadName(mail.getHead());
		return mailInfoData;
	}
}
