package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 抢开 之后 , 竞猜大小之前  前端 要等待一小段时间(动画时间)  然后在发送这个接口 通知前端 打开 竞猜的 窗口 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGuessOpen extends GCMessage{
	

	public GCGuessOpen (){
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
		return MessageType.GC_GUESS_OPEN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GUESS_OPEN";
	}
}