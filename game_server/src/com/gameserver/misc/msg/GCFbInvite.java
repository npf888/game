package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * fb邀请
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFbInvite extends GCMessage{
	
	/** 好友列表 */
	private String[] fbInviteIdList;

	public GCFbInvite (){
	}
	
	public GCFbInvite (
			String[] fbInviteIdList ){
			this.fbInviteIdList = fbInviteIdList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		fbInviteIdList = new String[count];
		for(int i=0; i<count; i++){
			fbInviteIdList[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(fbInviteIdList.length);
		for(int i=0; i<fbInviteIdList.length; i++){
			writeString(fbInviteIdList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FB_INVITE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FB_INVITE";
	}

	public String[] getFbInviteIdList(){
		return fbInviteIdList;
	}

	public void setFbInviteIdList(String[] fbInviteIdList){
		this.fbInviteIdList = fbInviteIdList;
	}	
}