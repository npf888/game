package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * facebook 的广告 每看一次 加多少金币
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooFacebookAddGold extends CGMessage{
	
	/** 增加的金币数量 */
	private int gold;
	
	public CGBazooFacebookAddGold (){
	}
	
	public CGBazooFacebookAddGold (
			int gold ){
			this.gold = gold;
	}
	
	@Override
	protected boolean readImpl() {
		gold = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(gold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_FACEBOOK_ADD_GOLD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_FACEBOOK_ADD_GOLD";
	}

	public int getGold(){
		return gold;
	}
		
	public void setGold(int gold){
		this.gold = gold;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleBazooFacebookAddGold(this.getSession().getPlayer(), this);
	}
}