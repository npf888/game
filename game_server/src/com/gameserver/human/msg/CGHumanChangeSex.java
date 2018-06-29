package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 更改性别
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanChangeSex extends CGMessage{
	
	/** 性别 */
	private int sex;
	
	public CGHumanChangeSex (){
	}
	
	public CGHumanChangeSex (
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
		return MessageType.CG_HUMAN_CHANGE_SEX;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_CHANGE_SEX";
	}

	public int getSex(){
		return sex;
	}
		
	public void setSex(int sex){
		this.sex = sex;
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleHumanChangeSex(this.getSession().getPlayer(), this);
	}
}