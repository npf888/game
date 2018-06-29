package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州结算
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasSettleGiveup extends GCMessage{
	
	/** 获胜玩家  */
	private int winnerPos;
	/** 边池列表  */
	private int[] settlePoolList;

	public GCTexasSettleGiveup (){
	}
	
	public GCTexasSettleGiveup (
			int winnerPos,
			int[] settlePoolList ){
			this.winnerPos = winnerPos;
			this.settlePoolList = settlePoolList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		winnerPos = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		settlePoolList = new int[count];
		for(int i=0; i<count; i++){
			settlePoolList[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(winnerPos);
		writeShort(settlePoolList.length);
		for(int i=0; i<settlePoolList.length; i++){
			writeInteger(settlePoolList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_SETTLE_GIVEUP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_SETTLE_GIVEUP";
	}

	public int getWinnerPos(){
		return winnerPos;
	}
		
	public void setWinnerPos(int winnerPos){
		this.winnerPos = winnerPos;
	}

	public int[] getSettlePoolList(){
		return settlePoolList;
	}

	public void setSettlePoolList(int[] settlePoolList){
		this.settlePoolList = settlePoolList;
	}	
}