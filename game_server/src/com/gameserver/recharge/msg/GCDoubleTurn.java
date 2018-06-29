package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 充值返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDoubleTurn extends GCMessage{
	
	/** 表ID */
	private int multipleId;
	/** 需要翻倍的钱 */
	private long doubleMoney;

	public GCDoubleTurn (){
	}
	
	public GCDoubleTurn (
			int multipleId,
			long doubleMoney ){
			this.multipleId = multipleId;
			this.doubleMoney = doubleMoney;
	}

	@Override
	protected boolean readImpl() {
		multipleId = readInteger();
		doubleMoney = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(multipleId);
		writeLong(doubleMoney);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DOUBLE_TURN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DOUBLE_TURN";
	}

	public int getMultipleId(){
		return multipleId;
	}
		
	public void setMultipleId(int multipleId){
		this.multipleId = multipleId;
	}

	public long getDoubleMoney(){
		return doubleMoney;
	}
		
	public void setDoubleMoney(long doubleMoney){
		this.doubleMoney = doubleMoney;
	}
}