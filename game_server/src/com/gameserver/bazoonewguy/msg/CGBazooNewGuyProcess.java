package com.gameserver.bazoonewguy.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoonewguy.handler.BazoonewguyHandlerFactory;

/**
 * 无双吹牛新手打点
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooNewGuyProcess extends CGMessage{
	
	/** 模式类型：1：经典吹牛模式，2：牛牛模式，3：梭哈模式，4：单挑模式 */
	private int modeType;
	/** 新手进程：到第1步传1，到第二步传2，3...依次类推 */
	private int process;
	
	public CGBazooNewGuyProcess (){
	}
	
	public CGBazooNewGuyProcess (
			int modeType,
			int process ){
			this.modeType = modeType;
			this.process = process;
	}
	
	@Override
	protected boolean readImpl() {
		modeType = readInteger();
		process = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(modeType);
		writeInteger(process);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_NEW_GUY_PROCESS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_NEW_GUY_PROCESS";
	}

	public int getModeType(){
		return modeType;
	}
		
	public void setModeType(int modeType){
		this.modeType = modeType;
	}

	public int getProcess(){
		return process;
	}
		
	public void setProcess(int process){
		this.process = process;
	}
	


	@Override
	public void execute() {
		BazoonewguyHandlerFactory.getHandler().handleBazooNewGuyProcess(this.getSession().getPlayer(), this);
	}
}