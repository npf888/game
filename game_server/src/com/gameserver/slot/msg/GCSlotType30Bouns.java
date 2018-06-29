package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 福尔摩斯老虎机bouns小游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType30Bouns extends GCMessage{
	
	/** 1：单个中，0：单个不中 */
	private int isSingleWin;
	/** 1：是全中，0：不全中 */
	private int isAllWin;
	/** 单次金币 */
	private int gold;
	/** 所有金币 */
	private int totalGold;

	public GCSlotType30Bouns (){
	}
	
	public GCSlotType30Bouns (
			int isSingleWin,
			int isAllWin,
			int gold,
			int totalGold ){
			this.isSingleWin = isSingleWin;
			this.isAllWin = isAllWin;
			this.gold = gold;
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		isSingleWin = readInteger();
		isAllWin = readInteger();
		gold = readInteger();
		totalGold = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isSingleWin);
		writeInteger(isAllWin);
		writeInteger(gold);
		writeInteger(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE30_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE30_BOUNS";
	}

	public int getIsSingleWin(){
		return isSingleWin;
	}
		
	public void setIsSingleWin(int isSingleWin){
		this.isSingleWin = isSingleWin;
	}

	public int getIsAllWin(){
		return isAllWin;
	}
		
	public void setIsAllWin(int isAllWin){
		this.isAllWin = isAllWin;
	}

	public int getGold(){
		return gold;
	}
		
	public void setGold(int gold){
		this.gold = gold;
	}

	public int getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(int totalGold){
		this.totalGold = totalGold;
	}
}