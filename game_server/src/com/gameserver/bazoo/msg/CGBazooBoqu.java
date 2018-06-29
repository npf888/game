package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 博趣平台入口
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooBoqu extends CGMessage{
	
	/** 是pc端还是手机端，PC端传入'pc',手机端传入'mobile' */
	private String pcOrMobile;
	
	public CGBazooBoqu (){
	}
	
	public CGBazooBoqu (
			String pcOrMobile ){
			this.pcOrMobile = pcOrMobile;
	}
	
	@Override
	protected boolean readImpl() {
		pcOrMobile = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(pcOrMobile);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_BOQU;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_BOQU";
	}

	public String getPcOrMobile(){
		return pcOrMobile;
	}
		
	public void setPcOrMobile(String pcOrMobile){
		this.pcOrMobile = pcOrMobile;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleBazooBoqu(this.getSession().getPlayer(), this);
	}
}