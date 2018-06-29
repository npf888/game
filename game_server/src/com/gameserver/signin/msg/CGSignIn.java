package com.gameserver.signin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.signin.handler.SigninHandlerFactory;

/**
 * 签到
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSignIn extends CGMessage{
	
	/** 签到天数 */
	private int day;
	
	public CGSignIn (){
	}
	
	public CGSignIn (
			int day ){
			this.day = day;
	}
	
	@Override
	protected boolean readImpl() {
		day = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(day);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SIGN_IN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SIGN_IN";
	}

	public int getDay(){
		return day;
	}
		
	public void setDay(int day){
		this.day = day;
	}
	


	@Override
	public void execute() {
		SigninHandlerFactory.getHandler().handleSignIn(this.getSession().getPlayer(), this);
	}
}