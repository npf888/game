package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 百家乐下注
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartBet extends CGMessage{
	
	/** 下注列表 */
	private com.gameserver.baccart.data.BaccartBetData[] betDataList;
	
	public CGBaccartBet (){
	}
	
	public CGBaccartBet (
			com.gameserver.baccart.data.BaccartBetData[] betDataList ){
			this.betDataList = betDataList;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
				betDataList = new com.gameserver.baccart.data.BaccartBetData[count];
				for(int i=0; i<count; i++){
			com.gameserver.baccart.data.BaccartBetData obj  =new com.gameserver.baccart.data.BaccartBetData();
			obj.setBetType(readInteger());
			obj.setBetNum(readLong());
			betDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(betDataList.length);
		for(int i=0; i<betDataList.length; i++){
			writeInteger(betDataList[i].getBetType());
			writeLong(betDataList[i].getBetNum());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BACCART_BET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_BET";
	}

	public com.gameserver.baccart.data.BaccartBetData[] getBetDataList(){
		return betDataList;
	}

	public void setBetDataList(com.gameserver.baccart.data.BaccartBetData[] betDataList){
		this.betDataList = betDataList;
	}	
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartBet(this.getSession().getPlayer(), this);
	}
}