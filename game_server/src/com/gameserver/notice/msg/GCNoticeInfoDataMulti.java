package com.gameserver.notice.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 跑马灯
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCNoticeInfoDataMulti extends GCMessage{
	
	/** 多语言key */
	private int langId;
	/** 参数 */
	private String[] paramsList;

	public GCNoticeInfoDataMulti (){
	}
	
	public GCNoticeInfoDataMulti (
			int langId,
			String[] paramsList ){
			this.langId = langId;
			this.paramsList = paramsList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
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
		writeInteger(langId);
		writeShort(paramsList.length);
		for(int i=0; i<paramsList.length; i++){
			writeString(paramsList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_NOTICE_INFO_DATA_MULTI;
	}
	
	@Override
	public String getTypeName() {
		return "GC_NOTICE_INFO_DATA_MULTI";
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