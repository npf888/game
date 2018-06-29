package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 更改图像 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanChangeImg extends CGMessage{
	
	/** 玩家图像  */
	private String img;
	
	public CGHumanChangeImg (){
	}
	
	public CGHumanChangeImg (
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
		return MessageType.CG_HUMAN_CHANGE_IMG;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_CHANGE_IMG";
	}

	public String getImg(){
		return img;
	}
		
	public void setImg(String img){
		this.img = img;
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleHumanChangeImg(this.getSession().getPlayer(), this);
	}
}