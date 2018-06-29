package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 自己攻击的血量 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCReturnBlood extends GCMessage{
	
	/** 技能类型，0:持续的，1：立即生效的 */
	private int skillType;
	/** 是否开启技能，0:开启，1关闭 */
	private int skill;
	/** 技能持续时间 */
	private int skillTime;

	public GCReturnBlood (){
	}
	
	public GCReturnBlood (
			int skillType,
			int skill,
			int skillTime ){
			this.skillType = skillType;
			this.skill = skill;
			this.skillTime = skillTime;
	}

	@Override
	protected boolean readImpl() {
		skillType = readInteger();
		skill = readInteger();
		skillTime = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(skillType);
		writeInteger(skill);
		writeInteger(skillTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RETURN_BLOOD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RETURN_BLOOD";
	}

	public int getSkillType(){
		return skillType;
	}
		
	public void setSkillType(int skillType){
		this.skillType = skillType;
	}

	public int getSkill(){
		return skill;
	}
		
	public void setSkill(int skill){
		this.skill = skill;
	}

	public int getSkillTime(){
		return skillTime;
	}
		
	public void setSkillTime(int skillTime){
		this.skillTime = skillTime;
	}
}