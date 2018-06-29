package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州分池
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasSidePool extends GCMessage{
	
	/** 边池列表  */
	private int[] sidePoolList;

	public GCTexasSidePool (){
	}
	
	public GCTexasSidePool (
			int[] sidePoolList ){
			this.sidePoolList = sidePoolList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		sidePoolList = new int[count];
		for(int i=0; i<count; i++){
			sidePoolList[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(sidePoolList.length);
		for(int i=0; i<sidePoolList.length; i++){
			writeInteger(sidePoolList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_SIDE_POOL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_SIDE_POOL";
	}

	public int[] getSidePoolList(){
		return sidePoolList;
	}

	public void setSidePoolList(int[] sidePoolList){
		this.sidePoolList = sidePoolList;
	}	
}