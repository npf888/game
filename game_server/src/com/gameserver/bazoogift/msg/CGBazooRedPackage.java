package com.gameserver.bazoogift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoogift.handler.BazoogiftHandlerFactory;

/**
 * 领取所有红包
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooRedPackage extends CGMessage{
	
	/** 红包的itemID */
	private int itemId;
	
	public CGBazooRedPackage (){
	}
	
	public CGBazooRedPackage (
			int itemId ){
			this.itemId = itemId;
	}
	
	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_RED_PACKAGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_RED_PACKAGE";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}
	


	@Override
	public void execute() {
		BazoogiftHandlerFactory.getHandler().handleBazooRedPackage(this.getSession().getPlayer(), this);
	}
}