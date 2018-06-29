package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 牛牛 模式:重摇开始
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCowSingleSwingBegin extends GCMessage{
	

	public GCCowSingleSwingBegin (){
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
		return MessageType.GC_COW_SINGLE_SWING_BEGIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COW_SINGLE_SWING_BEGIN";
	}
}