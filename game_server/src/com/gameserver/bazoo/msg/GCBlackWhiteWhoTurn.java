package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 轮到谁叫号了
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBlackWhiteWhoTurn extends GCMessage{
	
	/** 用户ID */
	private long whoTurnPassportId;
	/** 剩余时间 */
	private long leftSecond;

	public GCBlackWhiteWhoTurn (){
	}
	
	public GCBlackWhiteWhoTurn (
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
		return MessageType.GC_BLACK_WHITE_WHO_TURN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BLACK_WHITE_WHO_TURN";
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