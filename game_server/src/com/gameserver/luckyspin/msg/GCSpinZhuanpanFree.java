package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 免费返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSpinZhuanpanFree extends GCMessage{
	
	/** 转盘停留位置 */
	private int point;
	/** 登陆进度 */
	private int loginPoint;
	/** 每日奖励 */
	private int dailyReward;
	/** VIP奖励 */
	private int vipReward;
	/** 好友奖励 */
	private int friendReward;
	/** 等级奖励 */
	private int levelReward;

	public GCSpinZhuanpanFree (){
	}
	
	public GCSpinZhuanpanFree (
			int point,
			int loginPoint,
			int dailyReward,
			int vipReward,
			int friendReward,
			int levelReward ){
			this.point = point;
			this.loginPoint = loginPoint;
			this.dailyReward = dailyReward;
			this.vipReward = vipReward;
			this.friendReward = friendReward;
			this.levelReward = levelReward;
	}

	@Override
	protected boolean readImpl() {
		point = readInteger();
		loginPoint = readInteger();
		dailyReward = readInteger();
		vipReward = readInteger();
		friendReward = readInteger();
		levelReward = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(point);
		writeInteger(loginPoint);
		writeInteger(dailyReward);
		writeInteger(vipReward);
		writeInteger(friendReward);
		writeInteger(levelReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SPIN_ZHUANPAN_FREE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SPIN_ZHUANPAN_FREE";
	}

	public int getPoint(){
		return point;
	}
		
	public void setPoint(int point){
		this.point = point;
	}

	public int getLoginPoint(){
		return loginPoint;
	}
		
	public void setLoginPoint(int loginPoint){
		this.loginPoint = loginPoint;
	}

	public int getDailyReward(){
		return dailyReward;
	}
		
	public void setDailyReward(int dailyReward){
		this.dailyReward = dailyReward;
	}

	public int getVipReward(){
		return vipReward;
	}
		
	public void setVipReward(int vipReward){
		this.vipReward = vipReward;
	}

	public int getFriendReward(){
		return friendReward;
	}
		
	public void setFriendReward(int friendReward){
		this.friendReward = friendReward;
	}

	public int getLevelReward(){
		return levelReward;
	}
		
	public void setLevelReward(int levelReward){
		this.levelReward = levelReward;
	}
}