package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 巴西风情老虎机数据推送
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType24Bouns extends GCMessage{
	
	/** 是否成功 1 成功 0 失败 ,2开启小游戏 */
	private int isSuccess;
	/** 可以抽奖次数 */
	private int bounsNum;
	/** 抽奖获得的金钱总数 */
	private long money;
	/** 点击获得免费次数 */
	private long singleBounsNum;
	/** 点击获得的金钱总数 */
	private long singleMoney;

	public GCSlotType24Bouns (){
	}
	
	public GCSlotType24Bouns (
			int isSuccess,
			int bounsNum,
			long money,
			long singleBounsNum,
			long singleMoney ){
			this.isSuccess = isSuccess;
			this.bounsNum = bounsNum;
			this.money = money;
			this.singleBounsNum = singleBounsNum;
			this.singleMoney = singleMoney;
	}

	@Override
	protected boolean readImpl() {
		isSuccess = readInteger();
		bounsNum = readInteger();
		money = readLong();
		singleBounsNum = readLong();
		singleMoney = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isSuccess);
		writeInteger(bounsNum);
		writeLong(money);
		writeLong(singleBounsNum);
		writeLong(singleMoney);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE24_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE24_BOUNS";
	}

	public int getIsSuccess(){
		return isSuccess;
	}
		
	public void setIsSuccess(int isSuccess){
		this.isSuccess = isSuccess;
	}

	public int getBounsNum(){
		return bounsNum;
	}
		
	public void setBounsNum(int bounsNum){
		this.bounsNum = bounsNum;
	}

	public long getMoney(){
		return money;
	}
		
	public void setMoney(long money){
		this.money = money;
	}

	public long getSingleBounsNum(){
		return singleBounsNum;
	}
		
	public void setSingleBounsNum(long singleBounsNum){
		this.singleBounsNum = singleBounsNum;
	}

	public long getSingleMoney(){
		return singleMoney;
	}
		
	public void setSingleMoney(long singleMoney){
		this.singleMoney = singleMoney;
	}
}