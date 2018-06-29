package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 梭哈  模式:最小的人 摇色子
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCShowHandLittleSwing extends GCMessage{
	
	/** 那个点数 最低的人 去摇色子（这个人的ID） */
	private long[] passportId;

	public GCShowHandLittleSwing (){
	}
	
	public GCShowHandLittleSwing (
			long[] passportId ){
			this.passportId = passportId;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		passportId = new long[count];
		for(int i=0; i<count; i++){
			passportId[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(passportId.length);
		for(int i=0; i<passportId.length; i++){
			writeLong(passportId[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_HAND_LITTLE_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_HAND_LITTLE_SWING";
	}

	public long[] getPassportId(){
		return passportId;
	}

	public void setPassportId(long[] passportId){
		this.passportId = passportId;
	}	
}