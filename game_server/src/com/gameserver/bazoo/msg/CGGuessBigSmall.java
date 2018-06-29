package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 抢开 之后 其他人 竞猜大小 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGuessBigSmall extends CGMessage{
	
	/** 1:大于         0:小于 */
	private int bigSmall;
	
	public CGGuessBigSmall (){
	}
	
	public CGGuessBigSmall (
			int bigSmall ){
			this.bigSmall = bigSmall;
	}
	
	@Override
	protected boolean readImpl() {
		bigSmall = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bigSmall);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GUESS_BIG_SMALL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GUESS_BIG_SMALL";
	}

	public int getBigSmall(){
		return bigSmall;
	}
		
	public void setBigSmall(int bigSmall){
		this.bigSmall = bigSmall;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleGuessBigSmall(this.getSession().getPlayer(), this);
	}
}