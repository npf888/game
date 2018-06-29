package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 一键领取邮件返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCReceiveAll extends GCMessage{
	
	/** 邮件Id列表 */
	private long[] mailIdList;

	public GCReceiveAll (){
	}
	
	public GCReceiveAll (
			long[] mailIdList ){
			this.mailIdList = mailIdList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		mailIdList = new long[count];
		for(int i=0; i<count; i++){
			mailIdList[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(mailIdList.length);
		for(int i=0; i<mailIdList.length; i++){
			writeLong(mailIdList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RECEIVE_ALL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECEIVE_ALL";
	}

	public long[] getMailIdList(){
		return mailIdList;
	}

	public void setMailIdList(long[] mailIdList){
		this.mailIdList = mailIdList;
	}	
}