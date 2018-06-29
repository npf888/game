package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 选择模式
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGModeChose extends CGMessage{
	
	/** 模式类型：1：经典吹牛模式，2：牛牛模式，3：梭哈模式，4：单挑模式，5：单机模式 */
	private int modeType;
	
	public CGModeChose (){
	}
	
	public CGModeChose (
			int modeType ){
			this.modeType = modeType;
	}
	
	@Override
	protected boolean readImpl() {
		modeType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(modeType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MODE_CHOSE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MODE_CHOSE";
	}

	public int getModeType(){
		return modeType;
	}
		
	public void setModeType(int modeType){
		this.modeType = modeType;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleModeChose(this.getSession().getPlayer(), this);
	}
}