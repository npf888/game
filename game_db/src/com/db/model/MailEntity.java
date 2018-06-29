package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 邮件
 * @author wayne
 *
 */
@Entity
@Table(name="t_mail_info")
public class MailEntity implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7440626898423363420L;
	
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
	private int mailType;
	/** 邮件状态记录邮件是否已经读取*/
	private int mailStatus;
	/** 判断邮件是否有附件*/
	private int hasAttachment;
	/** 邮件附件内容*/
	private String attachmentPack;
	/** 邮件修改时间*/
	private long updateTime;
	/** 邮件创建时间*/
	private long createTime;
	/** 邮件删除标志位*/
	private int deleted;
	/*邮件头像*/
	private String head;
	@Id
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Column
	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	@Column
	public long getSendId() {
		return sendId;
	}

	public void setSendId(long sendId) {
		this.sendId = sendId;
	}

	@Column
	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	@Column
	public long getRecId() {
		return recId;
	}

	public void setRecId(long recId) {
		this.recId = recId;
	}

	@Column
	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	@Column
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column
	public int getMailType() {
		return mailType;
	}

	public void setMailType(int mailType) {
		this.mailType = mailType;
	}

	@Column
	public int getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(int mailStatus) {
		this.mailStatus = mailStatus;
	}



	@Column
	public int getHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(int hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	@Column
	public String getAttachmentPack() {
		return attachmentPack;
	}

	public void setAttachmentPack(String attachmentPack) {
		this.attachmentPack = attachmentPack;
	}

	@Column
	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Column
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	@Column
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

}
