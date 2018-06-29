package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 创建俱乐部
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubCreate extends CGMessage{
	
	/** 俱乐部名字 */
	private String name;
	/** 公告 */
	private String notice;
	/** 类型 1 公开 2 需要申请  3 不可加入 */
	private int _type;
	/** 图标 */
	private int ico;
	/** 段位限制 */
	private int limit;
	
	public CGClubCreate (){
	}
	
	public CGClubCreate (
			String name,
			String notice,
			int _type,
			int ico,
			int limit ){
			this.name = name;
			this.notice = notice;
			this._type = _type;
			this.ico = ico;
			this.limit = limit;
	}
	
	@Override
	protected boolean readImpl() {
		name = readString();
		notice = readString();
		_type = readInteger();
		ico = readInteger();
		limit = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(name);
		writeString(notice);
		writeInteger(_type);
		writeInteger(ico);
		writeInteger(limit);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_CREATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_CREATE";
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	public String getNotice(){
		return notice;
	}
		
	public void setNotice(String notice){
		this.notice = notice;
	}

	public int get_type(){
		return _type;
	}
		
	public void set_type(int _type){
		this._type = _type;
	}

	public int getIco(){
		return ico;
	}
		
	public void setIco(int ico){
		this.ico = ico;
	}

	public int getLimit(){
		return limit;
	}
		
	public void setLimit(int limit){
		this.limit = limit;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubCreate(this.getSession().getPlayer(), this);
	}
}