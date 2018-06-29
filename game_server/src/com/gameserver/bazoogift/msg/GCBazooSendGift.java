package com.gameserver.bazoogift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooSendGift extends GCMessage{
	
	/** 发送者的金币数量 */
	private long fromPlayerGold;
	/** 谁发的 */
	private long fromPlayerId;
	/** 发给谁 */
	private long toPlayerId;
	/** 礼物ID */
	private int itemId;
	/** 礼物数量 */
	private int number;
	/** 礼物类型(2:色钟，3：红包，4：礼物) */
	private int itemType;

	public GCBazooSendGift (){
	}
	
	public GCBazooSendGift (
			long fromPlayerGold,
			long fromPlayerId,
			long toPlayerId,
			int itemId,
			int number,
			int itemType ){
			this.fromPlayerGold = fromPlayerGold;
			this.fromPlayerId = fromPlayerId;
			this.toPlayerId = toPlayerId;
			this.itemId = itemId;
			this.number = number;
			this.itemType = itemType;
	}

	@Override
	protected boolean readImpl() {
		fromPlayerGold = readLong();
		fromPlayerId = readLong();
		toPlayerId = readLong();
		itemId = readInteger();
		number = readInteger();
		itemType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(fromPlayerGold);
		writeLong(fromPlayerId);
		writeLong(toPlayerId);
		writeInteger(itemId);
		writeInteger(number);
		writeInteger(itemType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_SEND_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_SEND_GIFT";
	}

	public long getFromPlayerGold(){
		return fromPlayerGold;
	}
		
	public void setFromPlayerGold(long fromPlayerGold){
		this.fromPlayerGold = fromPlayerGold;
	}

	public long getFromPlayerId(){
		return fromPlayerId;
	}
		
	public void setFromPlayerId(long fromPlayerId){
		this.fromPlayerId = fromPlayerId;
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

	public int getItemType(){
		return itemType;
	}
		
	public void setItemType(int itemType){
		this.itemType = itemType;
	}
}