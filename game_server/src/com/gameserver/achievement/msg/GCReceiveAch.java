package com.gameserver.achievement.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 领取成就奖励返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCReceiveAch extends GCMessage{
	
	/** 成就ID */
	private int id;
	/** 结果 0 失败 1 成功 */
	private int result;

	public GCReceiveAch (){
	}
	
	public GCReceiveAch (
			int id,
			int result ){
			this.id = id;
			this.result = result;
	}

	@Override
	protected boolean readImpl() {
		id = readInteger();
		result = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		writeInteger(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RECEIVE_ACH;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RECEIVE_ACH";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}
}