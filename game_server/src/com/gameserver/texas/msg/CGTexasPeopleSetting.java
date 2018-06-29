package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州人数
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasPeopleSetting extends CGMessage{
	
	/** 人数 */
	private int people;
	
	public CGTexasPeopleSetting (){
	}
	
	public CGTexasPeopleSetting (
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
		return MessageType.CG_TEXAS_PEOPLE_SETTING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_PEOPLE_SETTING";
	}

	public int getPeople(){
		return people;
	}
		
	public void setPeople(int people){
		this.people = people;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasPeopleSetting(this.getSession().getPlayer(), this);
	}
}