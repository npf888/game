package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 更改名字
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanChangeName extends CGMessage{
	
	/** 玩家名字 */
	private String name;
	
	public CGHumanChangeName (){
	}
	
	public CGHumanChangeName (
			String name ){
			this.name = name;
	}
	
	@Override
	protected boolean readImpl() {
		name = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(name);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HUMAN_CHANGE_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_CHANGE_NAME";
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleHumanChangeName(this.getSession().getPlayer(), this);
	}
}