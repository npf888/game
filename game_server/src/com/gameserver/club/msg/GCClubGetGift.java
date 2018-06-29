package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐部留言礼物
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubGetGift extends GCMessage{
	
	/** 类型 0 成功 1 失败 */
	private int ret;

	public GCClubGetGift (){
	}
	
	public GCClubGetGift (
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
		return MessageType.GC_CLUB_GET_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_GET_GIFT";
	}

	public int getRet(){
		return ret;
	}
		
	public void setRet(int ret){
		this.ret = ret;
	}
}