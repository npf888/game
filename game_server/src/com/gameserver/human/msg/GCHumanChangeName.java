package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 更改名字
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanChangeName extends GCMessage{
	
	/** 玩家名字 */
	private String name;
	/** (如果不是重复，此字段值为null 或者0)重复多语言的ID */
	private int duplicateNum;

	public GCHumanChangeName (){
	}
	
	public GCHumanChangeName (
			String name,
			int duplicateNum ){
			this.name = name;
			this.duplicateNum = duplicateNum;
	}

	@Override
	protected boolean readImpl() {
		name = readString();
		duplicateNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(name);
		writeInteger(duplicateNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_CHANGE_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_CHANGE_NAME";
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	public int getDuplicateNum(){
		return duplicateNum;
	}
		
	public void setDuplicateNum(int duplicateNum){
		this.duplicateNum = duplicateNum;
	}
}