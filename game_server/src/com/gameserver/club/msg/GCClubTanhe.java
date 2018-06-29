package com.gameserver.club.msg;

import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.msg.MessageType;

/**
 * 俱乐部发起弹劾
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubTanhe extends GCMessage{
	
	/** 类型 0 成功 1 失败 */
	private int ret;

	public GCClubTanhe (){
	}
	
	public GCClubTanhe (
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
		return MessageType.GC_CLUB_TANHE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_TANHE";
	}

	public int getRet(){
		return ret;
	}
		
	public void setRet(int ret){
		this.ret = ret;
	}
}