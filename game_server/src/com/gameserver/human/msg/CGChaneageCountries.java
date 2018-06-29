package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 修改角色年龄国际
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGChaneageCountries extends CGMessage{
	
	/** 国籍 */
	private String countries;
	/** 年龄 */
	private int age;
	
	public CGChaneageCountries (){
	}
	
	public CGChaneageCountries (
			String countries,
			int age ){
			this.countries = countries;
			this.age = age;
	}
	
	@Override
	protected boolean readImpl() {
		countries = readString();
		age = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(countries);
		writeInteger(age);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CHANEAGE_COUNTRIES;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHANEAGE_COUNTRIES";
	}

	public String getCountries(){
		return countries;
	}
		
	public void setCountries(String countries){
		this.countries = countries;
	}

	public int getAge(){
		return age;
	}
		
	public void setAge(int age){
		this.age = age;
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleChaneageCountries(this.getSession().getPlayer(), this);
	}
}