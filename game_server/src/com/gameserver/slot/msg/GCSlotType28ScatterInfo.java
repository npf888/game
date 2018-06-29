package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海底世界老虎机 新添加龟兔赛跑游戏，用户的 名次永远是数组中的第一个
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType28ScatterInfo extends GCMessage{
	
	/** 奖励集合 */
	private long[] rewards;
	/** 名次集合 */
	private int[] rands;
	/** 特殊scatter位置 */
	private int[] specilScatter;

	public GCSlotType28ScatterInfo (){
	}
	
	public GCSlotType28ScatterInfo (
			long[] rewards,
			int[] rands,
			int[] specilScatter ){
			this.rewards = rewards;
			this.rands = rands;
			this.specilScatter = specilScatter;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		rewards = new long[count];
		for(int i=0; i<count; i++){
			rewards[i] = readLong();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		rands = new int[count];
		for(int i=0; i<count; i++){
			rands[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		specilScatter = new int[count];
		for(int i=0; i<count; i++){
			specilScatter[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(rewards.length);
		for(int i=0; i<rewards.length; i++){
			writeLong(rewards[i]);
		}
		writeShort(rands.length);
		for(int i=0; i<rands.length; i++){
			writeInteger(rands[i]);
		}
		writeShort(specilScatter.length);
		for(int i=0; i<specilScatter.length; i++){
			writeInteger(specilScatter[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE28_SCATTER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE28_SCATTER_INFO";
	}

	public long[] getRewards(){
		return rewards;
	}

	public void setRewards(long[] rewards){
		this.rewards = rewards;
	}	

	public int[] getRands(){
		return rands;
	}

	public void setRands(int[] rands){
		this.rands = rands;
	}	

	public int[] getSpecilScatter(){
		return specilScatter;
	}

	public void setSpecilScatter(int[] specilScatter){
		this.specilScatter = specilScatter;
	}	
}