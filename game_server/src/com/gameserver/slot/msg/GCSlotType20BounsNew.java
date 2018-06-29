package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 泰国大象老虎机Bouns小游戏(新的小游戏-替换了老的小游戏) 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType20BounsNew extends GCMessage{
	
	/** 每次获取的reward */
	private long[] reward;
	/** 是否是 相片 0 不是，1 是 */
	private int[] isPicture;
	/** 相同图片获取的金币 */
	private long[] samePictureGold;
	/** 获得的总金币 */
	private long totalGold;

	public GCSlotType20BounsNew (){
	}
	
	public GCSlotType20BounsNew (
			long[] reward,
			int[] isPicture,
			long[] samePictureGold,
			long totalGold ){
			this.reward = reward;
			this.isPicture = isPicture;
			this.samePictureGold = samePictureGold;
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		reward = new long[count];
		for(int i=0; i<count; i++){
			reward[i] = readLong();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		isPicture = new int[count];
		for(int i=0; i<count; i++){
			isPicture[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		samePictureGold = new long[count];
		for(int i=0; i<count; i++){
			samePictureGold[i] = readLong();
		}
		totalGold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(reward.length);
		for(int i=0; i<reward.length; i++){
			writeLong(reward[i]);
		}
		writeShort(isPicture.length);
		for(int i=0; i<isPicture.length; i++){
			writeInteger(isPicture[i]);
		}
		writeShort(samePictureGold.length);
		for(int i=0; i<samePictureGold.length; i++){
			writeLong(samePictureGold[i]);
		}
		writeLong(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE20_BOUNS_NEW;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE20_BOUNS_NEW";
	}

	public long[] getReward(){
		return reward;
	}

	public void setReward(long[] reward){
		this.reward = reward;
	}	

	public int[] getIsPicture(){
		return isPicture;
	}

	public void setIsPicture(int[] isPicture){
		this.isPicture = isPicture;
	}	

	public long[] getSamePictureGold(){
		return samePictureGold;
	}

	public void setSamePictureGold(long[] samePictureGold){
		this.samePictureGold = samePictureGold;
	}	

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}
}