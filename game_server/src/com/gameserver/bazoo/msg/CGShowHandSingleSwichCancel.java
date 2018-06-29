package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 梭哈  模式:取消 选择色子（某个）
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGShowHandSingleSwichCancel extends CGMessage{
	
	/** 用户  取消选择的某个色子的索引 */
	private int diceIndex;
	
	public CGShowHandSingleSwichCancel (){
	}
	
	public CGShowHandSingleSwichCancel (
			int diceIndex ){
			this.diceIndex = diceIndex;
	}
	
	@Override
	protected boolean readImpl() {
		diceIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(diceIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_HAND_SINGLE_SWICH_CANCEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_HAND_SINGLE_SWICH_CANCEL";
	}

	public int getDiceIndex(){
		return diceIndex;
	}
		
	public void setDiceIndex(int diceIndex){
		this.diceIndex = diceIndex;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleShowHandSingleSwichCancel(this.getSession().getPlayer(), this);
	}
}