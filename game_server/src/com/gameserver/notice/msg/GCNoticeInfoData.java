package com.gameserver.notice.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 跑马灯
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCNoticeInfoData extends GCMessage{
	
	/** 内容 */
	private String content;

	public GCNoticeInfoData (){
	}
	
	public GCNoticeInfoData (
			String content ){
			this.content = content;
	}

	@Override
	protected boolean readImpl() {
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_NOTICE_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_NOTICE_INFO_DATA";
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}
}