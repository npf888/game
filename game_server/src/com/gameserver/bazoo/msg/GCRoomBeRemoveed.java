package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 被踢出房间
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomBeRemoveed extends GCMessage{
	

	public GCRoomBeRemoveed (){
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
		return MessageType.GC_ROOM_BE_REMOVEED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_BE_REMOVEED";
	}
}