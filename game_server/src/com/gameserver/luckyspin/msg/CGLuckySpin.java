package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.luckyspin.handler.LuckyspinHandlerFactory;

/**
 * 转盘
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGLuckySpin extends CGMessage{
	
	/** 免费 */
	private int free;
	
	public CGLuckySpin (){
	}
	
	public CGLuckySpin (
			int free ){
			this.free = free;
	}
	
	@Override
	protected boolean readImpl() {
		free = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(free);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_LUCKY_SPIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LUCKY_SPIN";
	}

	public int getFree(){
		return free;
	}
		
	public void setFree(int free){
		this.free = free;
	}
	


	@Override
	public void execute() {
		LuckyspinHandlerFactory.getHandler().handleLuckySpin(this.getSession().getPlayer(), this);
	}
}