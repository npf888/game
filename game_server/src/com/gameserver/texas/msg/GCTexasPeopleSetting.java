package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州人数
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasPeopleSetting extends GCMessage{
	
	/** 人数 */
	private int people;

	public GCTexasPeopleSetting (){
	}
	
	public GCTexasPeopleSetting (
			int people ){
			this.people = people;
	}

	@Override
	protected boolean readImpl() {
		people = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(people);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_PEOPLE_SETTING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_PEOPLE_SETTING";
	}

	public int getPeople(){
		return people;
	}
		
	public void setPeople(int people){
		this.people = people;
	}
}