package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 自己攻击的血量 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSelfAttackBloodInfo extends GCMessage{
	
	/** 自己攻击的血量 */
	private int selfAttackBlood;
	/** 当前是否 bigWin、megaWin等等 :   0:普通     1:bigwin  2:megawin   3:superwin  4:epicwin */
	private int winType;

	public GCSelfAttackBloodInfo (){
	}
	
	public GCSelfAttackBloodInfo (
			int selfAttackBlood,
			int winType ){
			this.selfAttackBlood = selfAttackBlood;
			this.winType = winType;
	}

	@Override
	protected boolean readImpl() {
		selfAttackBlood = readInteger();
		winType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(selfAttackBlood);
		writeInteger(winType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SELF_ATTACK_BLOOD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SELF_ATTACK_BLOOD_INFO";
	}

	public int getSelfAttackBlood(){
		return selfAttackBlood;
	}
		
	public void setSelfAttackBlood(int selfAttackBlood){
		this.selfAttackBlood = selfAttackBlood;
	}

	public int getWinType(){
		return winType;
	}
		
	public void setWinType(int winType){
		this.winType = winType;
	}
}