package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.misc.handler.MiscHandlerFactory;

/**
 * fb邀请奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFbInviteGetReward extends CGMessage{
	
	/** 奖励项  */
	private int rewardId;
	
	public CGFbInviteGetReward (){
	}
	
	public CGFbInviteGetReward (
			int rewardId ){
			this.rewardId = rewardId;
	}
	
	@Override
	protected boolean readImpl() {
		rewardId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_FB_INVITE_GET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FB_INVITE_GET_REWARD";
	}

	public int getRewardId(){
		return rewardId;
	}
		
	public void setRewardId(int rewardId){
		this.rewardId = rewardId;
	}
	


	@Override
	public void execute() {
		MiscHandlerFactory.getHandler().handleFbInviteGetReward(this.getSession().getPlayer(), this);
	}
}