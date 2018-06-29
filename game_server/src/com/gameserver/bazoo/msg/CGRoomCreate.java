package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 创建房间（创建的房间都是私人房间）
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoomCreate extends CGMessage{
	
	/** 模式 */
	private int modeType;
	/** 倍数 */
	private int bet;
	/** 创建房间的 密码 */
	private String password;
	
	public CGRoomCreate (){
	}
	
	public CGRoomCreate (
			int modeType,
			int bet,
			String password ){
			this.modeType = modeType;
			this.bet = bet;
			this.password = password;
	}
	
	@Override
	protected boolean readImpl() {
		modeType = readInteger();
		bet = readInteger();
		password = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(modeType);
		writeInteger(bet);
		writeString(password);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ROOM_CREATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROOM_CREATE";
	}

	public int getModeType(){
		return modeType;
	}
		
	public void setModeType(int modeType){
		this.modeType = modeType;
	}

	public int getBet(){
		return bet;
	}
		
	public void setBet(int bet){
		this.bet = bet;
	}

	public String getPassword(){
		return password;
	}
		
	public void setPassword(String password){
		this.password = password;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleRoomCreate(this.getSession().getPlayer(), this);
	}
}