package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐捐献
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubDonate extends GCMessage{
	
	/** 0 成功 1失败 */
	private int ret;

	public GCClubDonate (){
	}
	
	public GCClubDonate (
			int ret ){
			this.ret = ret;
	}

	@Override
	protected boolean readImpl() {
		ret = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(ret);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_DONATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_DONATE";
	}

	public int getRet(){
		return ret;
	}
		
	public void setRet(int ret){
		this.ret = ret;
	}
}