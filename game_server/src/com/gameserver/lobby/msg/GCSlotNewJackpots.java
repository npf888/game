package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 进入老虎机后的第一个页面展示的 四个阶段的 彩金数
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotNewJackpots extends GCMessage{
	
	/** 《第几个》--不同的等级对应的最大彩金数，有几个传几个，（配置中可以关闭不同等级的彩金，然以就不显示） */
	private int[] jackpotNum;
	/** 不同的等级对应的最大彩金数，有几个传几个，（配置中可以关闭不同等级的彩金，然以就不显示） */
	private long[] jackpot;

	public GCSlotNewJackpots (){
	}
	
	public GCSlotNewJackpots (
			int[] jackpotNum,
			long[] jackpot ){
			this.jackpotNum = jackpotNum;
			this.jackpot = jackpot;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		jackpotNum = new int[count];
		for(int i=0; i<count; i++){
			jackpotNum[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		jackpot = new long[count];
		for(int i=0; i<count; i++){
			jackpot[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(jackpotNum.length);
		for(int i=0; i<jackpotNum.length; i++){
			writeInteger(jackpotNum[i]);
		}
		writeShort(jackpot.length);
		for(int i=0; i<jackpot.length; i++){
			writeLong(jackpot[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_NEW_JACKPOTS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_NEW_JACKPOTS";
	}

	public int[] getJackpotNum(){
		return jackpotNum;
	}

	public void setJackpotNum(int[] jackpotNum){
		this.jackpotNum = jackpotNum;
	}	

	public long[] getJackpot(){
		return jackpot;
	}

	public void setJackpot(long[] jackpot){
		this.jackpot = jackpot;
	}	
}