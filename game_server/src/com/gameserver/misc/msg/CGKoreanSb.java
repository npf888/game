package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.misc.handler.MiscHandlerFactory;

/**
 * 韩国推广奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGKoreanSb extends CGMessage{
	
	/** 奖励 */
	private int rewardNum;
	
	public CGKoreanSb (){
	}
	
	public CGKoreanSb (
			int rewardNum ){
			this.rewardNum = rewardNum;
	}
	
	@Override
	protected boolean readImpl() {
		rewardNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_KOREAN_SB;
	}
	
	@Override
	public String getTypeName() {
		return "CG_KOREAN_SB";
	}

	public int getRewardNum(){
		return rewardNum;
	}
		
	public void setRewardNum(int rewardNum){
		this.rewardNum = rewardNum;
	}
	


	@Override
	public void execute() {
		MiscHandlerFactory.getHandler().handleKoreanSb(this.getSession().getPlayer(), this);
	}
}