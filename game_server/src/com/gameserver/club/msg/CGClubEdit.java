package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 设置俱乐部
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubEdit extends CGMessage{
	
	/** 公告 */
	private String notice;
	/** 类型 1 公开 2 需要申请  3 不可加入 */
	private int _type;
	/** 图标 */
	private int ico;
	/** 段位限制 */
	private int limit;
	
	public CGClubEdit (){
	}
	
	public CGClubEdit (
			String notice,
			int _type,
			int ico,
			int limit ){
			this.notice = notice;
			this._type = _type;
			this.ico = ico;
			this.limit = limit;
	}
	
	@Override
	protected boolean readImpl() {
		notice = readString();
		_type = readInteger();
		ico = readInteger();
		limit = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(notice);
		writeInteger(_type);
		writeInteger(ico);
		writeInteger(limit);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_EDIT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_EDIT";
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
		ClubHandlerFactory.getHandler().handleClubEdit(this.getSession().getPlayer(), this);
	}
}