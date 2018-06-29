package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 16
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomBlackWhiteWaitSomeOne extends GCMessage{
	
	/** 用户ID */
	private long whoTurnPassportId;
	/** 剩余时间 */
	private long leftSecond;

	public GCStateRoomBlackWhiteWaitSomeOne (){
	}
	
	public GCStateRoomBlackWhiteWaitSomeOne (
			long whoTurnPassportId,
			long leftSecond ){
			this.whoTurnPassportId = whoTurnPassportId;
			this.leftSecond = leftSecond;
	}

	@Override
	protected boolean readImpl() {
		whoTurnPassportId = readLong();
		leftSecond = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(whoTurnPassportId);
		writeLong(leftSecond);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE";
	}

	public long getWhoTurnPassportId(){
		return whoTurnPassportId;
	}
		
	public void setWhoTurnPassportId(long whoTurnPassportId){
		this.whoTurnPassportId = whoTurnPassportId;
	}

	public long getLeftSecond(){
		return leftSecond;
	}
		
	public void setLeftSecond(long leftSecond){
		this.leftSecond = leftSecond;
	}
}