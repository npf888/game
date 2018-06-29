package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * VIP等级的修改 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanChangeVip extends GCMessage{
	
	/** VIP等级的修改  */
	private int vip;

	public GCHumanChangeVip (){
	}
	
	public GCHumanChangeVip (
			int vip ){
			this.vip = vip;
	}

	@Override
	protected boolean readImpl() {
		vip = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vip);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_CHANGE_VIP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_CHANGE_VIP";
	}

	public int getVip(){
		return vip;
	}
		
	public void setVip(int vip){
		this.vip = vip;
	}
}