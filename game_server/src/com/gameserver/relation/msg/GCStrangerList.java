package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回陌生人列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStrangerList extends GCMessage{
	
	/** 服务器返回数据 */
	private com.gameserver.relation.data.StrangerData[] strangerData;

	public GCStrangerList (){
	}
	
	public GCStrangerList (
			com.gameserver.relation.data.StrangerData[] strangerData ){
			this.strangerData = strangerData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		strangerData = new com.gameserver.relation.data.StrangerData[count];
		for(int i=0; i<count; i++){
			com.gameserver.relation.data.StrangerData obj = new com.gameserver.relation.data.StrangerData();
			obj.setUserId(readLong());
			obj.setImg(readString());
			obj.setName(readString());
			obj.setLevel(readInteger());
			obj.setSex(readInteger());
			obj.setCountries(readString());
			obj.setVipLevel(readInteger());
			obj.setIsRequest(readInteger());
			strangerData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(strangerData.length);
		for(int i=0; i<strangerData.length; i++){
			writeLong(strangerData[i].getUserId());
			writeString(strangerData[i].getImg());
			writeString(strangerData[i].getName());
			writeInteger(strangerData[i].getLevel());
			writeInteger(strangerData[i].getSex());
			writeString(strangerData[i].getCountries());
			writeInteger(strangerData[i].getVipLevel());
			writeInteger(strangerData[i].getIsRequest());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STRANGER_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STRANGER_LIST";
	}

	public com.gameserver.relation.data.StrangerData[] getStrangerData(){
		return strangerData;
	}

	public void setStrangerData(com.gameserver.relation.data.StrangerData[] strangerData){
		this.strangerData = strangerData;
	}	
}