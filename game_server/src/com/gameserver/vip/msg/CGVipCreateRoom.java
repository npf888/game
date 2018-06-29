package com.gameserver.vip.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.vip.handler.VipHandlerFactory;

/**
 * vip开房
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGVipCreateRoom extends CGMessage{
	
	/** vip房间类型d */
	private int vipId;
	/** 密码 */
	private String password;
	
	public CGVipCreateRoom (){
	}
	
	public CGVipCreateRoom (
			int vipId,
			String password ){
			this.vipId = vipId;
			this.password = password;
	}
	
	@Override
	protected boolean readImpl() {
		vipId = readInteger();
		password = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vipId);
		writeString(password);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_VIP_CREATE_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_VIP_CREATE_ROOM";
	}

	public int getVipId(){
		return vipId;
	}
		
	public void setVipId(int vipId){
		this.vipId = vipId;
	}

	public String getPassword(){
		return password;
	}
		
	public void setPassword(String password){
		this.password = password;
	}
	


	@Override
	public void execute() {
		VipHandlerFactory.getHandler().handleVipCreateRoom(this.getSession().getPlayer(), this);
	}
}