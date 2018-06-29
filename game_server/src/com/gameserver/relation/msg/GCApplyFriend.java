package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端处理好友请求
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCApplyFriend extends GCMessage{
	
	/** 玩家id */
	private long playId;
	/** 处理结果 */
	private int result;

	public GCApplyFriend (){
	}
	
	public GCApplyFriend (
			long playId,
			int result ){
			this.playId = playId;
			this.result = result;
	}

	@Override
	protected boolean readImpl() {
		playId = readLong();
		result = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playId);
		writeInteger(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_APPLY_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "GC_APPLY_FRIEND";
	}

	public long getPlayId(){
		return playId;
	}
		
	public void setPlayId(long playId){
		this.playId = playId;
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}
}