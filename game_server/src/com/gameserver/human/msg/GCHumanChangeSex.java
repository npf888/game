package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 更改性别
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanChangeSex extends GCMessage{
	
	/** 性别 */
	private int sex;

	public GCHumanChangeSex (){
	}
	
	public GCHumanChangeSex (
			int sex ){
			this.sex = sex;
	}

	@Override
	protected boolean readImpl() {
		sex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(sex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_CHANGE_SEX;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_CHANGE_SEX";
	}

	public int getSex(){
		return sex;
	}
		
	public void setSex(int sex){
		this.sex = sex;
	}
}