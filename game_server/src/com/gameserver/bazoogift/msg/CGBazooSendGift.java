package com.gameserver.bazoogift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoogift.handler.BazoogiftHandlerFactory;

/**
 * 发送礼物
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooSendGift extends CGMessage{
	
	/** 发送什么类型的道具（2:色钟，3：红包，4：礼物） */
	private int itemType;
	/** 发给谁 */
	private long toPlayerId;
	/** 礼物ID */
	private int itemId;
	/** 礼物数量 */
	private int number;
	
	public CGBazooSendGift (){
	}
	
	public CGBazooSendGift (
			int itemType,
			long toPlayerId,
			int itemId,
			int number ){
			this.itemType = itemType;
			this.toPlayerId = toPlayerId;
			this.itemId = itemId;
			this.number = number;
	}
	
	@Override
	protected boolean readImpl() {
		itemType = readInteger();
		toPlayerId = readLong();
		itemId = readInteger();
		number = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemType);
		writeLong(toPlayerId);
		writeInteger(itemId);
		writeInteger(number);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_SEND_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_SEND_GIFT";
	}

	public int getItemType(){
		return itemType;
	}
		
	public void setItemType(int itemType){
		this.itemType = itemType;
	}

	public long getToPlayerId(){
		return toPlayerId;
	}
		
	public void setToPlayerId(long toPlayerId){
		this.toPlayerId = toPlayerId;
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getNumber(){
		return number;
	}
		
	public void setNumber(int number){
		this.number = number;
	}
	


	@Override
	public void execute() {
		BazoogiftHandlerFactory.getHandler().handleBazooSendGift(this.getSession().getPlayer(), this);
	}
}