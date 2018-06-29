package com.gameserver.slot.pojo;

import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机消息缓存
 * @author 郭君伟
 *
 */
public class SlotMessageCache {
	
	
	
	private volatile long id;
	
	
	private GCMessage message;
	
	public void clear(){
		id = 0;
		message = null;
	}
	
	public long getNum(){
		return id++;
	}
	
	public void setMessage(GCMessage message) {
		this.message = message;
	}

	public GCMessage getMessage(){
		return message;
	}
	
	

}
