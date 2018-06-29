package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 牛牛 模式:单独摇号
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGCowSingleSwing extends CGMessage{
	
	/** 将要被 重摇的  色子的值的集合 */
	private int[] diceValues;
	
	public CGCowSingleSwing (){
	}
	
	public CGCowSingleSwing (
			int[] diceValues ){
			this.diceValues = diceValues;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
				diceValues = new int[count];
				for(int i=0; i<count; i++){
			diceValues[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(diceValues.length);
		for(int i=0; i<diceValues.length; i++){
			writeInteger(diceValues[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_COW_SINGLE_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_COW_SINGLE_SWING";
	}

	public int[] getDiceValues(){
		return diceValues;
	}

	public void setDiceValues(int[] diceValues){
		this.diceValues = diceValues;
	}	
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleCowSingleSwing(this.getSession().getPlayer(), this);
	}
}