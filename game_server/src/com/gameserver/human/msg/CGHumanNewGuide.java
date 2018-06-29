package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 新手引导记录
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanNewGuide extends CGMessage{
	
	/** 0 大礼包 1 百家乐 2 德州 3 老虎 */
	private int guideId;
	
	public CGHumanNewGuide (){
	}
	
	public CGHumanNewGuide (
			int guideId ){
			this.guideId = guideId;
	}
	
	@Override
	protected boolean readImpl() {
		guideId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(guideId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HUMAN_NEW_GUIDE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_NEW_GUIDE";
	}

	public int getGuideId(){
		return guideId;
	}
		
	public void setGuideId(int guideId){
		this.guideId = guideId;
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleHumanNewGuide(this.getSession().getPlayer(), this);
	}
}