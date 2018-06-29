package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 玩家可以进入场景
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGEnterScene extends CGMessage{
	
	/** 场景Id */
	private int sceneId;
	
	public CGEnterScene (){
	}
	
	public CGEnterScene (
			int sceneId ){
			this.sceneId = sceneId;
	}
	
	@Override
	protected boolean readImpl() {
		sceneId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(sceneId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ENTER_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_SCENE";
	}

	public int getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(int sceneId){
		this.sceneId = sceneId;
	}
		@Override
	public boolean isCollect()
	{
		return true;
	}
	


	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handleEnterScene(this.getSession().getPlayer(), this);
	}
}