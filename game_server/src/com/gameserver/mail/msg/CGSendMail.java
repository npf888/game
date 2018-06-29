package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.mail.handler.MailHandlerFactory;

/**
 * 客户端请求发送邮件
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSendMail extends CGMessage{
	
	/** 目标人id */
	private long roleId;
	/** 邮件内容 */
	private String content;
	/** 邮件标题 */
	private String title;
	/** 邮件奖励 */
	private com.gameserver.common.data.RandRewardData[] randReward;
	
	public CGSendMail (){
	}
	
	public CGSendMail (
			long roleId,
			String content,
			String title,
			com.gameserver.common.data.RandRewardData[] randReward ){
			this.roleId = roleId;
			this.content = content;
			this.title = title;
			this.randReward = randReward;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		roleId = readLong();
		content = readString();
		title = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
				randReward = new com.gameserver.common.data.RandRewardData[count];
				for(int i=0; i<count; i++){
			com.gameserver.common.data.RandRewardData obj  =new com.gameserver.common.data.RandRewardData();
			obj.setRewardId(readInteger());
			obj.setRewardCount(readInteger());
			randReward[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(roleId);
		writeString(content);
		writeString(title);
		writeShort(randReward.length);
		for(int i=0; i<randReward.length; i++){
			writeInteger(randReward[i].getRewardId());
			writeInteger(randReward[i].getRewardCount());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SEND_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SEND_MAIL";
	}

	public long getRoleId(){
		return roleId;
	}
		
	public void setRoleId(long roleId){
		this.roleId = roleId;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public String getTitle(){
		return title;
	}
		
	public void setTitle(String title){
		this.title = title;
	}

	public com.gameserver.common.data.RandRewardData[] getRandReward(){
		return randReward;
	}

	public void setRandReward(com.gameserver.common.data.RandRewardData[] randReward){
		this.randReward = randReward;
	}	
		@Override
	public boolean isCollect()
	{
		return true;
	}
	


	@Override
	public void execute() {
		MailHandlerFactory.getHandler().handleSendMail(this.getSession().getPlayer(), this);
	}
}