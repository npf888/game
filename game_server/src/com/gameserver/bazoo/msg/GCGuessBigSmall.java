package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 抢开 之后 其他人 竞猜大小 推送所有人
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGuessBigSmall extends GCMessage{
	
	/** 当前是哪个用户(他的ID) */
	private long passportId;
	/** 当前这个用户 同意了 哪个用户(他的ID) */
	private long agreePassportId;

	public GCGuessBigSmall (){
	}
	
	public GCGuessBigSmall (
			long passportId,
			long agreePassportId ){
			this.passportId = passportId;
			this.agreePassportId = agreePassportId;
	}

	@Override
	protected boolean readImpl() {
		passportId = readLong();
		agreePassportId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(passportId);
		writeLong(agreePassportId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GUESS_BIG_SMALL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GUESS_BIG_SMALL";
	}

	public long getPassportId(){
		return passportId;
	}
		
	public void setPassportId(long passportId){
		this.passportId = passportId;
	}

	public long getAgreePassportId(){
		return agreePassportId;
	}
		
	public void setAgreePassportId(long agreePassportId){
		this.agreePassportId = agreePassportId;
	}
}