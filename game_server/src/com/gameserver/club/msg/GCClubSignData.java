package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐签到信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubSignData extends GCMessage{
	
	/** 是否可以签到 */
	private int canSign;
	/** 若不能签到这个是知道下次可签到需要持续的时间毫秒 */
	private long nextSignTime;

	public GCClubSignData (){
	}
	
	public GCClubSignData (
			int canSign,
			long nextSignTime ){
			this.canSign = canSign;
			this.nextSignTime = nextSignTime;
	}

	@Override
	protected boolean readImpl() {
		canSign = readInteger();
		nextSignTime = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(canSign);
		writeLong(nextSignTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_SIGN_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_SIGN_DATA";
	}

	public int getCanSign(){
		return canSign;
	}
		
	public void setCanSign(int canSign){
		this.canSign = canSign;
	}

	public long getNextSignTime(){
		return nextSignTime;
	}
		
	public void setNextSignTime(long nextSignTime){
		this.nextSignTime = nextSignTime;
	}
}