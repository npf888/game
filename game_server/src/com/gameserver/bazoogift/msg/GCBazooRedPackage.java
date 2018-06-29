package com.gameserver.bazoogift.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 领取所有红包 返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooRedPackage extends GCMessage{
	
	/** 是否成功0：成功，1：失败 */
	private int isSucess;
	/** 失败后 多语言ID */
	private int langId;
	/** 参数列表 */
	private String[] paramsList;

	public GCBazooRedPackage (){
	}
	
	public GCBazooRedPackage (
			int isSucess,
			int langId,
			String[] paramsList ){
			this.isSucess = isSucess;
			this.langId = langId;
			this.paramsList = paramsList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		isSucess = readInteger();
		langId = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		paramsList = new String[count];
		for(int i=0; i<count; i++){
			paramsList[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isSucess);
		writeInteger(langId);
		writeShort(paramsList.length);
		for(int i=0; i<paramsList.length; i++){
			writeString(paramsList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_RED_PACKAGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_RED_PACKAGE";
	}

	public int getIsSucess(){
		return isSucess;
	}
		
	public void setIsSucess(int isSucess){
		this.isSucess = isSucess;
	}

	public int getLangId(){
		return langId;
	}
		
	public void setLangId(int langId){
		this.langId = langId;
	}

	public String[] getParamsList(){
		return paramsList;
	}

	public void setParamsList(String[] paramsList){
		this.paramsList = paramsList;
	}	
}