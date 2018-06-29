package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐部捐献信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubDonateData extends GCMessage{
	
	/** 是否可以捐献 */
	private int canDonate;
	/** 若不能捐献这个是知道下次可捐献需要持续的时间毫秒 */
	private long nextSignTime;

	public GCClubDonateData (){
	}
	
	public GCClubDonateData (
			int canDonate,
			long nextSignTime ){
			this.canDonate = canDonate;
			this.nextSignTime = nextSignTime;
	}

	@Override
	protected boolean readImpl() {
		canDonate = readInteger();
		nextSignTime = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(canDonate);
		writeLong(nextSignTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_DONATE_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_DONATE_DATA";
	}

	public int getCanDonate(){
		return canDonate;
	}
		
	public void setCanDonate(int canDonate){
		this.canDonate = canDonate;
	}

	public long getNextSignTime(){
		return nextSignTime;
	}
		
	public void setNextSignTime(long nextSignTime){
		this.nextSignTime = nextSignTime;
	}
}