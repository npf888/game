package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 用于发送符号数值改变消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoleSymbolChangedLong extends GCMessage{
	
	/** 角色类型 */
	private short roleType;
	/** 角色UUID */
	private long roleUUID;
	/** 所有变化的符号值 */
	private com.core.util.KeyValuePair<Integer,Long>[] properties;

	public GCRoleSymbolChangedLong (){
	}
	
	public GCRoleSymbolChangedLong (
			short roleType,
			long roleUUID,
			com.core.util.KeyValuePair<Integer,Long>[] properties ){
			this.roleType = roleType;
			this.roleUUID = roleUUID;
			this.properties = properties;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		roleType = readShort();
		roleUUID = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		properties =(com.core.util.KeyValuePair<Integer,Long>[]) new com.core.util.KeyValuePair<?,?>[count];
		for(int i=0; i<count; i++){
			com.core.util.KeyValuePair<Integer,Long> obj = new com.core.util.KeyValuePair<Integer,Long>();
			obj.setKey(readInteger());
			obj.setValue(readLong());
			properties[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(roleType);
		writeLong(roleUUID);
		writeShort(properties.length);
		for(int i=0; i<properties.length; i++){
			writeInteger(properties[i].getKey());
			writeLong(properties[i].getValue());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROLE_SYMBOL_CHANGED_LONG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROLE_SYMBOL_CHANGED_LONG";
	}

	public short getRoleType(){
		return roleType;
	}
		
	public void setRoleType(short roleType){
		this.roleType = roleType;
	}

	public long getRoleUUID(){
		return roleUUID;
	}
		
	public void setRoleUUID(long roleUUID){
		this.roleUUID = roleUUID;
	}

	public com.core.util.KeyValuePair<Integer,Long>[] getProperties(){
		return properties;
	}

	public void setProperties(com.core.util.KeyValuePair<Integer,Long>[] properties){
		this.properties = properties;
	}	
}