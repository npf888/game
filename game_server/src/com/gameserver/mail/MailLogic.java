package com.gameserver.mail;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.uuid.UUIDType;
import com.db.model.MailEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanMailManager;
import com.gameserver.human.manager.HumanRelationManager;
import com.gameserver.mail.data.MailInfoData;
import com.gameserver.mail.enums.MailHasAttachment;
import com.gameserver.mail.enums.MailStatus;
import com.gameserver.mail.enums.MailTypeEnum;
import com.gameserver.mail.msg.GCUpdateMailList;
import com.gameserver.mail.redismsg.MailAddRedisMessage;
import com.gameserver.player.Player;
import com.gameserver.player.cache.PlayerCacheInfo;

/**
 * 邮件逻辑
 * @author wayne
 *
 */
public class MailLogic {

	private Logger logger = Loggers.mailLogger;
	private static String SYSTEM_MAIL = "System";
	private static String SYSTEM_MAIL_HEAD_IMAGE = "icon_acl.png";
	
	
	private static MailLogic instance = new MailLogic();
	public static MailLogic getInstance()
	{
		return instance;
	}
	
	/**
	 * 邮件列表数据
	 * @param human
	 * @param mailList
	 * @return
	 */
	public List<MailInfoData> buildMailInfoDataList(Human human,
			List<Mail> mailList) {
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();
		// TODO Auto-generated method stub
		List<MailInfoData> mailInfoDataList=new ArrayList<MailInfoData>();
		for(Mail mail:mailList)
		{
			MailInfoData mailInfoData=MailInfoData.convertFromMail(mail);
			mailInfoData.setHeadName(mail.getHead());
			
			
			PlayerCacheInfo playerCacheInfo= Globals.getPlayerCacheService().getPlayerCacheById(mail.getSendId());
			if(playerCacheInfo != null){
				mailInfoData.setVipLevel(playerCacheInfo.getVipLevel());
			}
			//判断邮件目标是否为好友
			if(humanRelationManager.checkIfAddFriend(mail.getSendId())){
				mailInfoData.setIsFriendSend(0);//是好友发送
			}else{
				mailInfoData.setIsFriendSend(1);//不是好友发送
			}
			mailInfoDataList.add(mailInfoData);
		}
		return mailInfoDataList;
	}

	/**
	 * 删除邮件
	 * @param player
	 * @param mailIds
	 */
	public void deleteMails(Player player, long[] mailIds) {
		// TODO Auto-generated method stub
		for(long mailId:mailIds)
		{
			Mail mail=player.getHuman().getHumanMailManager().getMailDataByMailId(mailId);
			mail.setDeleted(1);//删除，数据库 只是改个标志位
			mail.setModified();
			player.getHuman().getHumanMailManager().removeMailById(mailId);
		}
	}

	/**
	 * 检查邮件是否可以删除
	 * @param player
	 * @param mailIds
	 * @return
	 */
	public boolean checkMailDelete(Player player, long[] mailIds) {
		// TODO Auto-generated method stub
		for(long mailId:mailIds)
		{
			Mail mail=player.getHuman().getHumanMailManager().getMailDataByMailId(mailId);
			if(mail==null)
			{
				player.getHuman().sendSystemMessage(LangConstants.MAIL_ISNOTEXIST);
				return false;
			}
			if(mail.getHasAttachment()==MailHasAttachment.ANYTHING && mail.getMailStatus().getIndex() < 2)
			{
				player.getHuman().sendSystemMessage(LangConstants.MAIL_DELETE_ERR);
				return false;
			}
		}
		return true;
	}
	

	/**
     * 系统发送统一接口邮件
     * @param human 可以为空 发送邮件的人
     * @param recvIds 接收人的id列表
     * @param sysMailKind
     * @param randRewardDataList 附件列表
     * @return
     */
	public void systemSendMail(Human human,String title,String content,List<Long> recvIds,List<RandRewardData> randRewardDataList)
	{
		//String mailTitle= Globals.getLangService().readSysLang(LangConstants.MAIL_SYSTEM_NAME); 
		sendMailToRoles(human, recvIds,title,MailTypeEnum.SYSTEM, content, randRewardDataList);
	}
	public void systemSendMailCompensation(Human human,String title,String content,List<Long> recvIds,List<RandRewardData> randRewardDataList)
	{
		//String mailTitle= Globals.getLangService().readSysLang(LangConstants.MAIL_SYSTEM_NAME); 
		sendMailToRoles(human, recvIds,title,MailTypeEnum.COMPENSATION, content, randRewardDataList);
	}
	public void systemSendMailHuman(Human human,String title,String content,List<Long> recvIds,List<RandRewardData> randRewardDataList)
	{
		//String mailTitle= Globals.getLangService().readSysLang(LangConstants.MAIL_SYSTEM_NAME); 
		sendMailToRoles(human, recvIds,title,MailTypeEnum.PLAYER, content, randRewardDataList);
	}
	public void systemSendMailHumanGfit(Human human,String title,String content,List<Long> recvIds,List<RandRewardData> randRewardDataList)
	{
		//String mailTitle= Globals.getLangService().readSysLang(LangConstants.MAIL_SYSTEM_NAME); 
		sendMailToRoles(human, recvIds,title,MailTypeEnum.PLAYER_GIFT, content, randRewardDataList);
	}
	
	/**
     * 多人群发送邮件
     * @param roleIds 目标人id列表
     * @param mailKind 邮件类型，0：系统；1：奖励；2：好友
     * @param mailDealWithStatus 好友处理状态
     * @param content 邮件内容
     * @param randRewardDataList 奖励列表
     * @return
     */
	public void sendMailToRoles(Human human,List<Long> roleIds,String mailTitle,MailTypeEnum mailKind,String content,List<RandRewardData> randRewardDataList)
	{
		for(long roleId:roleIds)
		{
			sendMailToRole(human,roleId,mailTitle,mailKind,content,randRewardDataList);
		}
	}
	
	
	/**
     * 发送邮件
     * @param roleIds 目标人id
     * @param mailKind 邮件类型，0：系统；1：好友
     * @param mailDealWithStatus 好友处理状态
     * @param content 邮件内容
     * @param randRewardDataList 奖励列表
     * @return
     */
	public boolean sendMailToRole(Human human,long friendId,String mailTitle,MailTypeEnum mailKind,String content,List<RandRewardData> randRewardDataList )
	{
		PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(friendId);
		if(playerCacheInfo==null) return false;
		
		long sendId=0L;
		String sendName= SYSTEM_MAIL;
		String head = SYSTEM_MAIL_HEAD_IMAGE;
		if(human!=null)
		{
			sendId=human.getPassportId();
			sendName=human.getName();
			head = human.getImg();
		}
		
		
		//MailEntity mailEntity=creatMailEntity(sendId,sendName,friendId,playerCacheInfo.getName(),mailTitle,mailKind,content,randRewardDataList);
		MailEntity mailEntity=creatMailEntity(sendId,sendName,friendId,"",mailTitle,mailKind,content,randRewardDataList, head);
		Globals.getDaoService().getMailDao().save(mailEntity);
		sendMail(human,playerCacheInfo,mailEntity);
		if(human!=null)
			logger.debug("玩家id["+human.getPassportId()+"]发送邮件");
		else
			logger.debug("系统发送邮件");
		return true;
	}
	
	/**
	 * 构建邮件信息
	 * @param sendId 发送者id 如果是系统邮件或者是奖励有奖发送者id为0
	 * @param sendName 发送者姓名
	 * @param roleId 接受者id
	 * @param recvName 接受者名称
	 * @param mailTitle 邮件标题
	 * @param mailKind  邮件类型
	 * @param refMap 关联内容
	 * @param mailDealWithStatus 处理状态
	 * @param content 主要内容
	 * @param randRewardDataList 奖励
	 * @param head 头像
	 */
	public MailEntity creatMailEntity(long sendId,String sendName,long roleId,String recvName,String mailTitle,MailTypeEnum mailKind,String content,List<RandRewardData> randRewardDataList, String head)
	{
		MailEntity mailEntity=new MailEntity();
		mailEntity.setCharId(roleId);
		long now = Globals.getTimeService().now();
		long mailId=Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANMAILID);
		mailEntity.setId(mailId);
		mailEntity.setSendId(sendId);
		mailEntity.setSendName(sendName);
		mailEntity.setRecId(roleId);
		mailEntity.setRecName(recvName);
		mailEntity.setMailType(mailKind.getIndex());
		mailEntity.setMailStatus(MailStatus.UNREAD.getIndex());
		mailEntity.setDeleted(0);//未删除
		if(randRewardDataList!=null&&randRewardDataList.size()>0)
		{
			mailEntity.setHasAttachment(MailHasAttachment.ANYTHING.getIndex());
		}else
		{
			mailEntity.setHasAttachment(MailHasAttachment.NOTHING.getIndex());
		}
		if(randRewardDataList == null)
			randRewardDataList = new ArrayList<RandRewardData>();
		mailEntity.setAttachmentPack(JSON.toJSONString(randRewardDataList));
		mailEntity.setTitle(mailTitle);
		mailEntity.setContent(content);
		mailEntity.setCreateTime(Globals.getTimeService().now());
		mailEntity.setHead(head);
		return mailEntity;
	}
	
	/**
	 * 发送邮件
	 * @param human
	 * @param playerCacheInfo
	 * @param mailEntity
	 */
	public void sendMail(Human human,PlayerCacheInfo playerCacheInfo, MailEntity mailEntity)
	{
		MailAddRedisMessage mailAddRedisMessage = new MailAddRedisMessage();
		mailAddRedisMessage.setMailEntity(mailEntity); 
		Globals.getRedisService().sendRedisMsgToServer(playerCacheInfo.getServerId(), mailAddRedisMessage);

	}
	
	/**
	 * 接收邮件
	 * @param human
	 * @param mail
	 */
	public void receiveMail(Human human, MailEntity mailEntity) {
		logger.debug("玩家id["+human.getPassportId()+"]接收邮件");
		Mail mail=new Mail();
		mail.setOwner(human);
		mail.fromEntity(mailEntity);
	
		HumanMailManager humanMailManager = human.getHumanMailManager();
		humanMailManager.getMailList().add(0, mail);
		////send msg 
		GCUpdateMailList gcUpdateMailList = new GCUpdateMailList();
		gcUpdateMailList.setMailId(mail.getDbId());
		gcUpdateMailList.setMailKind(mail.getMailType().getIndex());
		gcUpdateMailList.setMailInfoData(MailInfoData.convertFromMail(mail));
		human.sendMessage(gcUpdateMailList);
	}
	
}
