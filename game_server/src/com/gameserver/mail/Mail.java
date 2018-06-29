package com.gameserver.mail;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.db.model.MailEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.mail.enums.MailHasAttachment;
import com.gameserver.mail.enums.MailStatus;
import com.gameserver.mail.enums.MailTypeEnum;

/**
 * 邮件
 * @author wayne
 *
 */
public class Mail implements PersistanceObject<Long, MailEntity>{

	/** 主人 */
	private Human owner;
	/** 生命期 */
	private final LifeCycle lifeCycle;
	/** 是否已经在数据库中 */
	private boolean inDb;
	/** 主键 */
	private long id;
	/** 所属角色ID */
	private long charId;
	/** 发送角色ID*/
	private long sendId;
	/** 发送角色名称*/
	private String sendName;
	/** 接收角色ID*/
	private long recId;
	/** 接收角色名称*/
	private String recName;
	/** 邮件标题*/
	private String title;
	/** 邮件内容*/
	private String content;
	/** 邮件类型*/
	private MailTypeEnum mailType;
	/** 邮件状态记录邮件是否已经读取*/
	private MailStatus mailStatus;
	/** 邮件接收时间*/
	private long createTime;
	/** 邮件修改时间*/
	private long updateTime;
	/** 邮件删除标志位 0 未删除  ，1 已删除*/
	private int deleted;
	/** 邮件删除时间*/
	private long deleteTime;
	/** 判断邮件是否有附件*/
	private MailHasAttachment hasAttachment;
	/** 邮件附件内容*/
	private List<RandRewardData> randRewardDataList=new ArrayList<RandRewardData>();
	/*邮件头像*/
	private String head;
	
	public Mail() 
	{
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	/**
	 * 设置主人
	 * 
	 * @param owner
	 * @throws IllegalArgumentException
	 *             当owner为空时抛出
	 */
	public void setOwner(Human owner)
	{
		Assert.notNull(owner);
		this.owner = owner;
	}
	
	public Human getOwner() {
		return this.owner;
	}
	
	@Override
	public void setDbId(Long id) {
		this.id=id;
	}

	@Override
	public Long getDbId() {
		return id;
	}


	@Override
	public String getGUID()
	{
		return "mail#" + this.id;
	}

	@Override
	public boolean isInDb() {
		return inDb;
	}
	
	@Override
	public void setInDb(boolean inDb) {
		this.inDb=inDb;
	}
	
	public void setCharId(long charId)
	{
		this.charId = charId;
	}
	
	@Override
	public long getCharId() {
		return charId;
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

	public long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MailTypeEnum getMailType() {
		return mailType;
	}

	public void setMailType(MailTypeEnum mailType) {
		this.mailType = mailType;
	}

	public MailStatus getMailStatus() {
		return mailStatus;
	}
	
	public void setMailStatus(MailStatus mailStatus) {
		this.mailStatus = mailStatus;
	}

	
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public long getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(long deleteTime) {
		this.deleteTime = deleteTime;
	}

	public MailHasAttachment getHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(MailHasAttachment hasAttachment) {
		this.hasAttachment = hasAttachment;
	}


	public List<RandRewardData> getRandRewardDataList() {
		return randRewardDataList;
	}

	



	@Override
	public MailEntity toEntity() {
		MailEntity mailEntity=new MailEntity();
		mailEntity.setId(this.getDbId());
		mailEntity.setCharId(this.getCharId());
		mailEntity.setSendId(this.getSendId());
		mailEntity.setSendName(this.getSendName());
		mailEntity.setRecId(this.getRecId());
		mailEntity.setRecName(this.getRecName());
		mailEntity.setTitle(this.getTitle());
		mailEntity.setContent(this.getContent());
		mailEntity.setMailType(this.getMailType().getIndex());
		mailEntity.setMailStatus(this.getMailStatus().getIndex());
		mailEntity.setDeleted(this.getDeleted());
		mailEntity.setCreateTime(this.getCreateTime());
		mailEntity.setUpdateTime(this.getUpdateTime());
	
		mailEntity.setHasAttachment(this.getHasAttachment().getIndex());
		mailEntity.setAttachmentPack(JSON.toJSONString(this.randRewardDataList));
		
		mailEntity.setHead(this.getHead());
		return mailEntity;
	}

	@Override
	public void fromEntity(MailEntity entity) {
		this.setDbId(entity.getId());
		this.setCharId(entity.getCharId());
		this.setSendId(entity.getSendId());
		this.setSendName(entity.getSendName());
		this.setRecId(entity.getRecId());
		this.setRecName(entity.getRecName());
		this.setTitle(entity.getTitle());
		this.setContent(entity.getContent());
		this.setMailType(MailTypeEnum.valueOf(entity.getMailType()));
		this.setMailStatus(MailStatus.valueOf(entity.getMailStatus()));
		this.setDeleted(entity.getDeleted());
		this.setCreateTime(entity.getCreateTime());
		this.setUpdateTime(entity.getUpdateTime());
		this.setHead(entity.getHead());
		this.setHasAttachment(MailHasAttachment.valueOf(entity.getHasAttachment()));
		if(entity.getAttachmentPack()!=null)
		{
			this.randRewardDataList.addAll(JSON.parseArray(entity.getAttachmentPack(), RandRewardData.class));
		}
		this.setInDb(true);
		this.active();
	}

	
	/**
	 * 激活此关系
	 */
	public void active() 
	{
		getLifeCycle().activate();
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public void setModified() {
		onUpdate();
	}
	
	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate()
	{
		if (owner != null) 
		{
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	
	/**
	 * 物品被删除时调用,恢复默认值,并触发删除机制
	 * 
	 */
	public void onDelete()
	{
		this.lifeCycle.destroy();
		this.getOwner().getPlayer().getDataUpdater().addDelete(this.getLifeCycle());
	}
	
	
	/**
	 * 获取CD倒计时
	 * @return
	 */
	public int getMailCdTime()
	{
		long nowTime=Globals.getTimeService().now();//获取系统当前时间
		long cdTimeCoefficient=Globals.getConfigTempl().getMailTime()*TimeUtils.DAY;
		int deleteMailCdTime=TimeUtils.getIntCdtime(getCreateTime(), nowTime, cdTimeCoefficient);
		return deleteMailCdTime;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
}
