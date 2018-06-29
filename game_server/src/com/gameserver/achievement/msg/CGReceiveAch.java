package com.gameserver.achievement.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.achievement.handler.AchievementHandlerFactory;

/**
 * 领取成就奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGReceiveAch extends CGMessage{
	
	/** 成就ID */
	private int id;
	
	public CGReceiveAch (){
	}
	
	public CGReceiveAch (
			int id ){
			this.id = id;
	}
	
	@Override
	protected boolean readImpl() {
		id = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_RECEIVE_ACH;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RECEIVE_ACH";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}
	


	@Override
	public void execute() {
		AchievementHandlerFactory.getHandler().handleReceiveAch(this.getSession().getPlayer(), this);
	}
}