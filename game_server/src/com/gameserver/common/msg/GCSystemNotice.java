package com.gameserver.common.msg;

/**
 * 系统公告
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSystemNotice extends GCMessage{
	
	/** 消息内容 */
	private String content;
	/** 消息字体颜色 */
	private int color;
	/** 速度 */
	private short speed;

	public GCSystemNotice (){
	}
	
	public GCSystemNotice (
			String content,
			int color,
			short speed ){
			this.content = content;
			this.color = color;
			this.speed = speed;
	}

	@Override
	protected boolean readImpl() {
		content = readString();
		color = readInteger();
		speed = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(content);
		writeInteger(color);
		writeShort(speed);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SYSTEM_NOTICE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SYSTEM_NOTICE";
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public int getColor(){
		return color;
	}
		
	public void setColor(int color){
		this.color = color;
	}

	public short getSpeed(){
		return speed;
	}
		
	public void setSpeed(short speed){
		this.speed = speed;
	}
}