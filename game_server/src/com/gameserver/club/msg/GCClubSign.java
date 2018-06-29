package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐签到
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubSign extends GCMessage{
	
	/** 0 成功 1失败 */
	private int ret;

	public GCClubSign (){
	}
	
	public GCClubSign (
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
		return MessageType.GC_CLUB_SIGN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_SIGN";
	}

	public int getRet(){
		return ret;
	}
		
	public void setRet(int ret){
		this.ret = ret;
	}
}