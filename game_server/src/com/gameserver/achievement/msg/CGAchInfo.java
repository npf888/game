package com.gameserver.achievement.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.achievement.handler.AchievementHandlerFactory;

/**
 * 获取成就数据
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGAchInfo extends CGMessage{
	
	/** 角色ID */
	private long roleId;
	
	public CGAchInfo (){
	}
	
	public CGAchInfo (
			long roleId ){
			this.roleId = roleId;
	}
	
	@Override
	protected boolean readImpl() {
		roleId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(roleId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ACH_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ACH_INFO";
	}

	public long getRoleId(){
		return roleId;
	}
		
	public void setRoleId(long roleId){
		this.roleId = roleId;
	}
	


	@Override
	public void execute() {
		AchievementHandlerFactory.getHandler().handleAchInfo(this.getSession().getPlayer(), this);
	}
}