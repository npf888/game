package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.worldboss.handler.WorldbossHandlerFactory;

/**
 * 开启或者关闭面板
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGOpenPanel extends CGMessage{
	
	/** 0:开启，1:关闭 */
	private int panelType;
	
	public CGOpenPanel (){
	}
	
	public CGOpenPanel (
			int panelType ){
			this.panelType = panelType;
	}
	
	@Override
	protected boolean readImpl() {
		panelType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(panelType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_PANEL";
	}

	public int getPanelType(){
		return panelType;
	}
		
	public void setPanelType(int panelType){
		this.panelType = panelType;
	}
	


	@Override
	public void execute() {
		WorldbossHandlerFactory.getHandler().handleOpenPanel(this.getSession().getPlayer(), this);
	}
}