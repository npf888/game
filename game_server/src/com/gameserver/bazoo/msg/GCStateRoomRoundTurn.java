package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息4
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomRoundTurn extends GCMessage{
	
	/** 没有叫号 ，处于等待状态 */
	private int status;
	/** 谁正在等待（那个人的ID） */
	private long whoTurnPassportId;
	/** 等待剩余时间 */
	private int leftSecond;

	public GCStateRoomRoundTurn (){
	}
	
	public GCStateRoomRoundTurn (
			int status,
			long whoTurnPassportId,
			int leftSecond ){
			this.status = status;
			this.whoTurnPassportId = whoTurnPassportId;
			this.leftSecond = leftSecond;
	}

	@Override
	protected boolean readImpl() {
		status = readInteger();
		whoTurnPassportId = readLong();
		leftSecond = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(status);
		writeLong(whoTurnPassportId);
		writeInteger(leftSecond);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_ROUND_TURN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_ROUND_TURN";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}

	public long getWhoTurnPassportId(){
		return whoTurnPassportId;
	}
		
	public void setWhoTurnPassportId(long whoTurnPassportId){
		this.whoTurnPassportId = whoTurnPassportId;
	}

	public int getLeftSecond(){
		return leftSecond;
	}
		
	public void setLeftSecond(int leftSecond){
		this.leftSecond = leftSecond;
	}
}