package com.gameserver.scene.data;

/**
 * 场景NPC数据
 * @author Thinker
 *
 */
public class SceneNpcData
{
	/** NPC模板Id*/
	private int npcId;
	/** NPC坐标X*/
	private int posX;
	/** NPC坐标Y*/
	private int posY;
	/** NPC坐标Z*/
	private int posZ;
	
	public SceneNpcData(){}
	
	public int getNpcId() {
		return npcId;
	}
	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getPosZ() {
		return posZ;
	}
	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}
	
}