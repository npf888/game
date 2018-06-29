package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 更改图像 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanChangeImg extends GCMessage{
	
	/** 玩家图像  */
	private String img;

	public GCHumanChangeImg (){
	}
	
	public GCHumanChangeImg (
			String img ){
			this.img = img;
	}

	@Override
	protected boolean readImpl() {
		img = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(img);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_CHANGE_IMG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_CHANGE_IMG";
	}

	public String getImg(){
		return img;
	}
		
	public void setImg(String img){
		this.img = img;
	}
}