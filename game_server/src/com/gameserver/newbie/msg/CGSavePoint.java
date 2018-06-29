package com.gameserver.newbie.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.newbie.handler.NewbieHandlerFactory;

/**
 * 新手存盘点
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSavePoint extends CGMessage{
	
	/** 步骤id */
	private int step;
	
	public CGSavePoint (){
	}
	
	public CGSavePoint (
			int step ){
			this.step = step;
	}
	
	@Override
	protected boolean readImpl() {
		step = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(step);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SAVE_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SAVE_POINT";
	}

	public int getStep(){
		return step;
	}
		
	public void setStep(int step){
		this.step = step;
	}
	


	@Override
	public void execute() {
		NewbieHandlerFactory.getHandler().handleSavePoint(this.getSession().getPlayer(), this);
	}
}