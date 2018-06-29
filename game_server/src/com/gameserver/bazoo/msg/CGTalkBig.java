package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 每个人轮流 叫号 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTalkBig extends CGMessage{
	
	/** 色子的数量 */
	private int diceNum;
	/** 色子的值 */
	private int diceValue;
	
	public CGTalkBig (){
	}
	
	public CGTalkBig (
			int diceNum,
			int diceValue ){
			this.diceNum = diceNum;
			this.diceValue = diceValue;
	}
	
	@Override
	protected boolean readImpl() {
		diceNum = readInteger();
		diceValue = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(diceNum);
		writeInteger(diceValue);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TALK_BIG;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TALK_BIG";
	}

	public int getDiceNum(){
		return diceNum;
	}
		
	public void setDiceNum(int diceNum){
		this.diceNum = diceNum;
	}

	public int getDiceValue(){
		return diceValue;
	}
		
	public void setDiceValue(int diceValue){
		this.diceValue = diceValue;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleTalkBig(this.getSession().getPlayer(), this);
	}
}