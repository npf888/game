package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 抽奖返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRaffle extends GCMessage{
	
	/** 1 铜 2 银 3 金 */
	private int cardType;
	/** 1 成功 0失败 */
	private int returnRes;
	/** 奖池ID 成功的前提下有效 */
	private int Id;

	public GCRaffle (){
	}
	
	public GCRaffle (
			int cardType,
			int returnRes,
			int Id ){
			this.cardType = cardType;
			this.returnRes = returnRes;
			this.Id = Id;
	}

	@Override
	protected boolean readImpl() {
		cardType = readInteger();
		returnRes = readInteger();
		Id = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(cardType);
		writeInteger(returnRes);
		writeInteger(Id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RAFFLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RAFFLE";
	}

	public int getCardType(){
		return cardType;
	}
		
	public void setCardType(int cardType){
		this.cardType = cardType;
	}

	public int getReturnRes(){
		return returnRes;
	}
		
	public void setReturnRes(int returnRes){
		this.returnRes = returnRes;
	}

	public int getId(){
		return Id;
	}
		
	public void setId(int Id){
		this.Id = Id;
	}
}