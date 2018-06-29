package com.gameserver.bazoosignin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 签到返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooSignin extends GCMessage{
	
	/** 签到获得的金币 */
	private long gold;
	/** 色子 int类型 的代号  豹子:7, 顺子:6, 炸弹:5, 葫芦:4, 三条:3, 两对:2, 两对:1, 散点:0 */
	private int nameInt;
	/** 色子的值 */
	private int[] diceValues;

	public GCBazooSignin (){
	}
	
	public GCBazooSignin (
			long gold,
			int nameInt,
			int[] diceValues ){
			this.gold = gold;
			this.nameInt = nameInt;
			this.diceValues = diceValues;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		gold = readLong();
		nameInt = readInteger();
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
		writeLong(gold);
		writeInteger(nameInt);
		writeShort(diceValues.length);
		for(int i=0; i<diceValues.length; i++){
			writeInteger(diceValues[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_SIGNIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_SIGNIN";
	}

	public long getGold(){
		return gold;
	}
		
	public void setGold(long gold){
		this.gold = gold;
	}

	public int getNameInt(){
		return nameInt;
	}
		
	public void setNameInt(int nameInt){
		this.nameInt = nameInt;
	}

	public int[] getDiceValues(){
		return diceValues;
	}

	public void setDiceValues(int[] diceValues){
		this.diceValues = diceValues;
	}	
}