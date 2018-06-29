package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 更换色钟 通知房间内所有人 自己更换了色钟
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooItemClockChangeToAll extends GCMessage{
	
	/** 更换色钟图片的玩家ID */
	private long playerId;
	/** 目标色钟的itemId */
	private int itemId;
	/** 道具图片 */
	private String img;

	public GCBazooItemClockChangeToAll (){
	}
	
	public GCBazooItemClockChangeToAll (
			long playerId,
			int itemId,
			String img ){
			this.playerId = playerId;
			this.itemId = itemId;
			this.img = img;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		itemId = readInteger();
		img = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeInteger(itemId);
		writeString(img);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public String getImg(){
		return img;
	}
		
	public void setImg(String img){
		this.img = img;
	}
}