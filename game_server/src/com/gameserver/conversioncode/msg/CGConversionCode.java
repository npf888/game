package com.gameserver.conversioncode.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.conversioncode.handler.ConversioncodeHandlerFactory;

/**
 * 兑换码兑换礼包
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGConversionCode extends CGMessage{
	
	/** 兑换码 */
	private String code;
	
	public CGConversionCode (){
	}
	
	public CGConversionCode (
			String code ){
			this.code = code;
	}
	
	@Override
	protected boolean readImpl() {
		code = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(code);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CONVERSION_CODE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CONVERSION_CODE";
	}

	public String getCode(){
		return code;
	}
		
	public void setCode(String code){
		this.code = code;
	}
	


	@Override
	public void execute() {
		ConversioncodeHandlerFactory.getHandler().handleConversionCode(this.getSession().getPlayer(), this);
	}
}