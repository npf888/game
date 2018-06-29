package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 巴西风情老虎机触发 bonus 游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType24BounsGameStart extends GCMessage{
	
	/** 随机出来要玩 哪个游戏小游戏类型 1：喝酒小游戏 2：桑巴小游戏 */
	private int gameType;
	/** 桑巴小游戏 会用到这个，作者:力度条颜色（前端上传） 1：黄色 2：黄绿色 3：绿色 */
	private int[] color;
	/** 桑巴小游戏 会用到这个，用户有几次机会，默认是 1 次 */
	private int chance;
	/** 总共有几轮  喝酒小游戏会用到这个 */
	private int round;

	public GCSlotType24BounsGameStart (){
	}
	
	public GCSlotType24BounsGameStart (
			int gameType,
			int[] color,
			int chance,
			int round ){
			this.gameType = gameType;
			this.color = color;
			this.chance = chance;
			this.round = round;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		gameType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		color = new int[count];
		for(int i=0; i<count; i++){
			color[i] = readInteger();
		}
		chance = readInteger();
		round = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(gameType);
		writeShort(color.length);
		for(int i=0; i<color.length; i++){
			writeInteger(color[i]);
		}
		writeInteger(chance);
		writeInteger(round);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE24_BOUNS_GAME_START;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE24_BOUNS_GAME_START";
	}

	public int getGameType(){
		return gameType;
	}
		
	public void setGameType(int gameType){
		this.gameType = gameType;
	}

	public int[] getColor(){
		return color;
	}

	public void setColor(int[] color){
		this.color = color;
	}	

	public int getChance(){
		return chance;
	}
		
	public void setChance(int chance){
		this.chance = chance;
	}

	public int getRound(){
		return round;
	}
		
	public void setRound(int round){
		this.round = round;
	}
}