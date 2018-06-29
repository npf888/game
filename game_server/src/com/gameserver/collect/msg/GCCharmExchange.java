package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 魅力值兑换圈
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCharmExchange extends GCMessage{
	
	/** 1成功 2 魅力值不够 3 请求物品不存在 */
	private int returnType;

	public GCCharmExchange (){
	}
	
	public GCCharmExchange (
			int returnType ){
			this.returnType = returnType;
	}

	@Override
	protected boolean readImpl() {
		returnType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(returnType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHARM_EXCHANGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARM_EXCHANGE";
	}

	public int getReturnType(){
		return returnType;
	}
		
	public void setReturnType(int returnType){
		this.returnType = returnType;
	}
}