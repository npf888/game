package com.gameserver.vip.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.vip.handler.VipHandlerFactory;

/**
 * 购买vip
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGVipBuy extends CGMessage{
	
	/** 购买档次 */
	private int vipLevel;
	
	public CGVipBuy (){
	}
	
	public CGVipBuy (
			int vipLevel ){
			this.vipLevel = vipLevel;
	}
	
	@Override
	protected boolean readImpl() {
		vipLevel = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vipLevel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_VIP_BUY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_VIP_BUY";
	}

	public int getVipLevel(){
		return vipLevel;
	}
		
	public void setVipLevel(int vipLevel){
		this.vipLevel = vipLevel;
	}
	


	@Override
	public void execute() {
		VipHandlerFactory.getHandler().handleVipBuy(this.getSession().getPlayer(), this);
	}
}