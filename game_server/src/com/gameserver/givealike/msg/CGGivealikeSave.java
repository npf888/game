package com.gameserver.givealike.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.givealike.handler.GivealikeHandlerFactory;

/**
 * 保存用户点评
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGivealikeSave extends CGMessage{
	
	/** 老虎机类型 */
	private int slotType;
	/** 美术点评 */
	private int paintAssess;
	/** 玩法点评 */
	private int playAssess;
	/** 综合点评 */
	private int totalAssess;
	
	public CGGivealikeSave (){
	}
	
	public CGGivealikeSave (
			int slotType,
			int paintAssess,
			int playAssess,
			int totalAssess ){
			this.slotType = slotType;
			this.paintAssess = paintAssess;
			this.playAssess = playAssess;
			this.totalAssess = totalAssess;
	}
	
	@Override
	protected boolean readImpl() {
		slotType = readInteger();
		paintAssess = readInteger();
		playAssess = readInteger();
		totalAssess = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotType);
		writeInteger(paintAssess);
		writeInteger(playAssess);
		writeInteger(totalAssess);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GIVEALIKE_SAVE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GIVEALIKE_SAVE";
	}

	public int getSlotType(){
		return slotType;
	}
		
	public void setSlotType(int slotType){
		this.slotType = slotType;
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
	


	@Override
	public void execute() {
		GivealikeHandlerFactory.getHandler().handleGivealikeSave(this.getSession().getPlayer(), this);
	}
}