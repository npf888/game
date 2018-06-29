package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.luckyspin.handler.LuckyspinHandlerFactory;

/**
 * 转动装盘
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSpinZhuanpan extends CGMessage{
	
	/** 1 免费转动 0 花钱转动 */
	private int isFree;
	
	public CGSpinZhuanpan (){
	}
	
	public CGSpinZhuanpan (
			int isFree ){
			this.isFree = isFree;
	}
	
	@Override
	protected boolean readImpl() {
		isFree = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isFree);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SPIN_ZHUANPAN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SPIN_ZHUANPAN";
	}

	public int getIsFree(){
		return isFree;
	}
		
	public void setIsFree(int isFree){
		this.isFree = isFree;
	}
	


	@Override
	public void execute() {
		LuckyspinHandlerFactory.getHandler().handleSpinZhuanpan(this.getSession().getPlayer(), this);
	}
}