package com.gameserver.mail.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 响应客户端请求删除邮件
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDeleteMail extends GCMessage{
	

	public GCDeleteMail (){
	}
	

	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DELETE_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DELETE_MAIL";
	}
}