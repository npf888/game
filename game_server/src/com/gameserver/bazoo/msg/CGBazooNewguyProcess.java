package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 存储新手的进度(每调用一次 -->某个模式进度完毕)
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooNewguyProcess extends CGMessage{
	
	/** 模式(1:经典, 2:牛牛, 3:梭哈, 4:红黑) */
	private int ModeType;
	
	public CGBazooNewguyProcess (){
	}
	
	public CGBazooNewguyProcess (
			int ModeType ){
			this.ModeType = ModeType;
	}
	
	@Override
	protected boolean readImpl() {
		ModeType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(ModeType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_NEWGUY_PROCESS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_NEWGUY_PROCESS";
	}

	public int getModeType(){
		return ModeType;
	}
		
	public void setModeType(int ModeType){
		this.ModeType = ModeType;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleBazooNewguyProcess(this.getSession().getPlayer(), this);
	}
}