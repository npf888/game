package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐留言列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubNoteList extends GCMessage{
	
	/** 操作类型 1 全部  2 增加  3 删除   4 更新 （可能用不上） */
	private int opType;
	/** 俱乐部留言板 */
	private com.gameserver.club.protocol.ClubNoteUnit[] clubNote;

	public GCClubNoteList (){
	}
	
	public GCClubNoteList (
			int opType,
			com.gameserver.club.protocol.ClubNoteUnit[] clubNote ){
			this.opType = opType;
			this.clubNote = clubNote;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		opType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		clubNote = new com.gameserver.club.protocol.ClubNoteUnit[count];
		for(int i=0; i<count; i++){
			com.gameserver.club.protocol.ClubNoteUnit obj = new com.gameserver.club.protocol.ClubNoteUnit();
			obj.setNoteId(readString());
			obj.setPlayerId(readLong());
			obj.setPlayerName(readString());
			obj.setImg(readString());
			obj.setGuoji(readString());
			obj.setLevel(readLong());
			obj.setNoteType(readInteger());
			obj.setContent(readString());
			obj.setGiftId(readInteger());
			obj.setAlreadyGet(readInteger());
			obj.setZhiwu(readInteger());
			obj.setTime(readLong());
			clubNote[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(opType);
		writeShort(clubNote.length);
		for(int i=0; i<clubNote.length; i++){
			writeString(clubNote[i].getNoteId());
			writeLong(clubNote[i].getPlayerId());
			writeString(clubNote[i].getPlayerName());
			writeString(clubNote[i].getImg());
			writeString(clubNote[i].getGuoji());
			writeLong(clubNote[i].getLevel());
			writeInteger(clubNote[i].getNoteType());
			writeString(clubNote[i].getContent());
			writeInteger(clubNote[i].getGiftId());
			writeInteger(clubNote[i].getAlreadyGet());
			writeInteger(clubNote[i].getZhiwu());
			writeLong(clubNote[i].getTime());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_NOTE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_NOTE_LIST";
	}

	public int getOpType(){
		return opType;
	}
		
	public void setOpType(int opType){
		this.opType = opType;
	}

	public com.gameserver.club.protocol.ClubNoteUnit[] getClubNote(){
		return clubNote;
	}

	public void setClubNote(com.gameserver.club.protocol.ClubNoteUnit[] clubNote){
		this.clubNote = clubNote;
	}	
}