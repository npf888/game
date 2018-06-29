package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 选择模式 返回数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCModeChose extends GCMessage{
	
	/** bet场 对应的人数信息 */
	private com.gameserver.bazoo.data.BetTotalNum[] betTotalNum;

	public GCModeChose (){
	}
	
	public GCModeChose (
			com.gameserver.bazoo.data.BetTotalNum[] betTotalNum ){
			this.betTotalNum = betTotalNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		betTotalNum = new com.gameserver.bazoo.data.BetTotalNum[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.BetTotalNum obj = new com.gameserver.bazoo.data.BetTotalNum();
			obj.setBet(readInteger());
			obj.setTotalNum(readLong());
			betTotalNum[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(betTotalNum.length);
		for(int i=0; i<betTotalNum.length; i++){
			writeInteger(betTotalNum[i].getBet());
			writeLong(betTotalNum[i].getTotalNum());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MODE_CHOSE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MODE_CHOSE";
	}

	public com.gameserver.bazoo.data.BetTotalNum[] getBetTotalNum(){
		return betTotalNum;
	}

	public void setBetTotalNum(com.gameserver.bazoo.data.BetTotalNum[] betTotalNum){
		this.betTotalNum = betTotalNum;
	}	
}