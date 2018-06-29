package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.common.InitializeRequired;
import com.core.util.TimeUtils;
import com.db.model.MailEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.mail.Mail;
import com.gameserver.mail.MailLogic;
import com.gameserver.mail.data.MailInfoData;
import com.gameserver.mail.enums.MailStatus;
import com.gameserver.mail.enums.MailTypeEnum;
import com.gameserver.mail.msg.GCLoadMailList;

public class HumanMailManager implements RoleDataHolder, InitializeRequired{

	/** 主人 */
	private Human owner;
	/** 用户邮件列表信息 */
	private List<Mail> mailList = new ArrayList<Mail>();
	
	public HumanMailManager(Human owner){
		this.owner=owner;
	
	}
	
	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public List<Mail> getMailList() {
		return mailList;
	}

	/**
	 * 加载邮件
	 */
	public void load()
	{
		List<MailEntity> _mailEntityList=Globals.getDaoService().getMailDao().getMailEntityByCharId(owner.getPassportId());
	
		if(_mailEntityList!=null&&_mailEntityList.size()>0){
			for(MailEntity mailEntity:_mailEntityList){
				Mail mail=new Mail();
				mail.setOwner(owner);
				mail.fromEntity(mailEntity);
				this.mailList.add(mail);
			}
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub
		sysDeleteMails();
	}
	
	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * 系统删除邮件
     * @param mailEntityList
     * @return
     */
	private void sysDeleteMails(){
		long now=Globals.getTimeService().now();
		
		Iterator<Mail> iter = mailList.iterator();
		int i=0;
		while(iter.hasNext())
		{
			Mail mail= iter.next();
			if(mail.getMailStatus() == MailStatus.UNREAD)
				continue;
			int time=TimeUtils.compareTime(mail.getCreateTime(), now, TimeUtils.DAY);
			//大于7天删除邮件
			if(time>Globals.getConfigTempl().getMailTime() || i>=Globals.getConfigTempl().getMailNum())
			{
				mail.onDelete();
				iter.remove();
				continue;
			}
			++i;
		}
	}
	
	/**
	 * 获得邮件分类列表
	 * @param mailKind
	 * @return
	 */
	public List<Mail> getMailListByMailKind(MailTypeEnum mailKind) {
		// TODO Auto-generated method stub
		List<Mail> mailListByKind=new ArrayList<Mail>();
		for(Mail mail:mailList){
			if(mail.getMailType()!=mailKind) continue;
			mailListByKind.add(mail);
		}
		return mailListByKind;
	}

	/**
	 * 根据邮件id获取邮件详细信息
	 * @param mailId
	 * @return
	 */
	public Mail getMailDataByMailId(long mailId) {
		// TODO Auto-generated method stub
		Mail mail=null;
		for(Mail tmpmail:mailList){
			if(tmpmail.getDbId()==mailId) {
				mail=tmpmail;
				break;
			}
		}
		return mail;
	}

	/**
	 * 
	 * @param mailId
	 */
	public void removeMailById(long mailId) {
		// TODO Auto-generated method stub
		for(Mail mail:mailList){
			if(mail.getDbId()==mailId){
				mailList.remove(mail);
				break;
			}
		}
	}
	
	public GCLoadMailList buildHumanMailInfoData(MailTypeEnum mailType){
		List<Mail> mailList=getMailListByMailKind(mailType);
		List<MailInfoData> mailInfoDataList=MailLogic.getInstance().buildMailInfoDataList(this.owner, mailList);
		GCLoadMailList gcLoadMailList=new GCLoadMailList();
		gcLoadMailList.setMailKind(mailType.getIndex());
		gcLoadMailList.setMailInfoDataList(mailInfoDataList.toArray(new MailInfoData[mailInfoDataList.size()]));
		return gcLoadMailList;
	}
}
