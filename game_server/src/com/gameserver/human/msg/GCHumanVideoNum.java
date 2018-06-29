package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 观看视频返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanVideoNum extends GCMessage{
	

	public GCHumanVideoNum (){
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
		return MessageType.GC_HUMAN_VIDEO_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_VIDEO_NUM";
	}
}