package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 观看视频
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanVideoNum extends CGMessage{
	
	
	public CGHumanVideoNum (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HUMAN_VIDEO_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_VIDEO_NUM";
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleHumanVideoNum(this.getSession().getPlayer(), this);
	}
}