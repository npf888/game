package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机玩家广播2
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotRoom2 extends GCMessage{
	
	/** 玩家id */
	private long playerId;
	/** 图片 */
	private String img;
	/** 等级 */
	private int level;
	/** 国籍 */
	private String countries;
	/** 名字 */
	private String name;

	public GCSlotRoom2 (){
	}
	
	public GCSlotRoom2 (
			long playerId,
			String img,
			int level,
			String countries,
			String name ){
			this.playerId = playerId;
			this.img = img;
			this.level = level;
			this.countries = countries;
			this.name = name;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		img = readString();
		level = readInteger();
		countries = readString();
		name = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeString(img);
		writeInteger(level);
		writeString(countries);
		writeString(name);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ROOM2;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ROOM2";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public String getImg(){
		return img;
	}
		
	public void setImg(String img){
		this.img = img;
	}

	public int getLevel(){
		return level;
	}
		
	public void setLevel(int level){
		this.level = level;
	}

	public String getCountries(){
		return countries;
	}
		
	public void setCountries(String countries){
		this.countries = countries;
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}
}