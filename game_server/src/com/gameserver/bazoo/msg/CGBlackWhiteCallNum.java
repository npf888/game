package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 每个人轮流 叫号 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBlackWhiteCallNum extends CGMessage{
	
	/** 1:红，2：黑，3：单，4：双 */
	private int diceType;
	
	public CGBlackWhiteCallNum (){
	}
	
	public CGBlackWhiteCallNum (
			int diceType ){
			this.diceType = diceType;
	}
	
	@Override
	protected boolean readImpl() {
		diceType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(diceType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BLACK_WHITE_CALL_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BLACK_WHITE_CALL_NUM";
	}

	public int getDiceType(){
		return diceType;
	}
		
	public void setDiceType(int diceType){
		this.diceType = diceType;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleBlackWhiteCallNum(this.getSession().getPlayer(), this);
	}
}