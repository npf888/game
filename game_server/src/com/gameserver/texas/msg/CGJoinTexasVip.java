package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州加入vip房间
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGJoinTexasVip extends CGMessage{
	
	/** vipId */
	private long vipId;
	/** 密码 */
	private String password;
	
	public CGJoinTexasVip (){
	}
	
	public CGJoinTexasVip (
			long vipId,
			String password ){
			this.vipId = vipId;
			this.password = password;
	}
	
	@Override
	protected boolean readImpl() {
		vipId = readLong();
		password = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(vipId);
		writeString(password);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_JOIN_TEXAS_VIP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_JOIN_TEXAS_VIP";
	}

	public long getVipId(){
		return vipId;
	}
		
	public void setVipId(long vipId){
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
		TexasHandlerFactory.getHandler().handleJoinTexasVip(this.getSession().getPlayer(), this);
	}
}