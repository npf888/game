package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州房间信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasRoomInfo extends GCMessage{
	
	/** 房间状态 */
	private int roomState;
	/** 公共牌 */
	private int[] publicCardList;
	/** 边池 */
	private long[] sidePoolList;
	/** 庄家位置 */
	private int bankPos;
	/** 当前下注位置 */
	private int currentBetterPos;

	public GCTexasRoomInfo (){
	}
	
	public GCTexasRoomInfo (
			int roomState,
			int[] publicCardList,
			long[] sidePoolList,
			int bankPos,
			int currentBetterPos ){
			this.roomState = roomState;
			this.publicCardList = publicCardList;
			this.sidePoolList = sidePoolList;
			this.bankPos = bankPos;
			this.currentBetterPos = currentBetterPos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		roomState = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		publicCardList = new int[count];
		for(int i=0; i<count; i++){
			publicCardList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		sidePoolList = new long[count];
		for(int i=0; i<count; i++){
			sidePoolList[i] = readLong();
		}
		bankPos = readInteger();
		currentBetterPos = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(roomState);
		writeShort(publicCardList.length);
		for(int i=0; i<publicCardList.length; i++){
			writeInteger(publicCardList[i]);
		}
		writeShort(sidePoolList.length);
		for(int i=0; i<sidePoolList.length; i++){
			writeLong(sidePoolList[i]);
		}
		writeInteger(bankPos);
		writeInteger(currentBetterPos);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_ROOM_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_ROOM_INFO";
	}

	public int getRoomState(){
		return roomState;
	}
		
	public void setRoomState(int roomState){
		this.roomState = roomState;
	}

	public int[] getPublicCardList(){
		return publicCardList;
	}

	public void setPublicCardList(int[] publicCardList){
		this.publicCardList = publicCardList;
	}	

	public long[] getSidePoolList(){
		return sidePoolList;
	}

	public void setSidePoolList(long[] sidePoolList){
		this.sidePoolList = sidePoolList;
	}	

	public int getBankPos(){
		return bankPos;
	}
		
	public void setBankPos(int bankPos){
		this.bankPos = bankPos;
	}

	public int getCurrentBetterPos(){
		return currentBetterPos;
	}
		
	public void setCurrentBetterPos(int currentBetterPos){
		this.currentBetterPos = currentBetterPos;
	}
}