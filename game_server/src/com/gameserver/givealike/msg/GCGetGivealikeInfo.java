package com.gameserver.givealike.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回点评信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetGivealikeInfo extends GCMessage{
	
	/** 美术点评 */
	private int paintAssess;
	/** 玩法点评 */
	private int playAssess;
	/** 综合点评 */
	private int totalAssess;

	public GCGetGivealikeInfo (){
	}
	
	public GCGetGivealikeInfo (
			int paintAssess,
			int playAssess,
			int totalAssess ){
			this.paintAssess = paintAssess;
			this.playAssess = playAssess;
			this.totalAssess = totalAssess;
	}

	@Override
	protected boolean readImpl() {
		paintAssess = readInteger();
		playAssess = readInteger();
		totalAssess = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(paintAssess);
		writeInteger(playAssess);
		writeInteger(totalAssess);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_GIVEALIKE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_GIVEALIKE_INFO";
	}

	public int getPaintAssess(){
		return paintAssess;
	}
		
	public void setPaintAssess(int paintAssess){
		this.paintAssess = paintAssess;
	}

	public int getPlayAssess(){
		return playAssess;
	}
		
	public void setPlayAssess(int playAssess){
		this.playAssess = playAssess;
	}

	public int getTotalAssess(){
		return totalAssess;
	}
		
	public void setTotalAssess(int totalAssess){
		this.totalAssess = totalAssess;
	}
}