package com.gameserver.worldboss.data;

import java.util.Date;

import com.gameserver.worldboss.pojo.Boss;

public class BossInfoData {
	private int beKilled;
	private int nameId;
	private int descrip;
	private int bossType;
	private long changingBlood;
	private int blood;
	private long increaseBlood;
	private int rewardNum1;
	private int rewardNum2;
	private long startTime;
	private long continueTime;
	private long endTime;
	public int getBeKilled() {
		return beKilled;
	}
	public void setBeKilled(int beKilled) {
		this.beKilled = beKilled;
	}
	
	public int getBossType() {
		return bossType;
	}
	public void setBossType(int bossType) {
		this.bossType = bossType;
	}
	public long getChangingBlood() {
		return changingBlood;
	}
	public void setChangingBlood(long changingBlood) {
		this.changingBlood = changingBlood;
	}
	public int getBlood() {
		return blood;
	}
	public void setBlood(int blood) {
		this.blood = blood;
	}
	public long getIncreaseBlood() {
		return increaseBlood;
	}
	public void setIncreaseBlood(long increaseBlood) {
		this.increaseBlood = increaseBlood;
	}
	
	public int getRewardNum1() {
		return rewardNum1;
	}
	public void setRewardNum1(int rewardNum1) {
		this.rewardNum1 = rewardNum1;
	}
	public int getRewardNum2() {
		return rewardNum2;
	}
	public void setRewardNum2(int rewardNum2) {
		this.rewardNum2 = rewardNum2;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getContinueTime() {
		return continueTime;
	}
	public void setContinueTime(long continueTime) {
		this.continueTime = continueTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
	public int getNameId() {
		return nameId;
	}
	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	
	
	public int getDescrip() {
		return descrip;
	}
	public void setDescrip(int descrip) {
		this.descrip = descrip;
	}
	public  BossInfoData getSelf(Boss theBoss){
		
		int beKilled = theBoss.getBeKilled();
		int type = theBoss.getType();
		long changingBlood = theBoss.getChangingBlood();
		long blood = theBoss.getBlood();
		long increaseBlood = theBoss.getIncreaseBlood();
		int rewardNum1 = theBoss.getRewardNum1();
		int rewardNum2 = theBoss.getRewardNum2();
		
		Date startTime = theBoss.getStartTime();
		long continueTime = theBoss.getContinueTime();
		Date endTime = theBoss.getEndTime();
		int descrip = theBoss.getDescrip();
		this.setBeKilled(beKilled);
		this.setBossType(type);
		this.setChangingBlood(changingBlood);
		this.setIncreaseBlood(increaseBlood);
		this.setBlood(Long.valueOf(blood).intValue());
		this.setRewardNum1(rewardNum1);
		this.setRewardNum2(rewardNum2);
		this.setStartTime(startTime.getTime());
		this.setContinueTime(continueTime);
		this.setEndTime(endTime.getTime());
		this.setNameId(theBoss.getNameId());
		this.setDescrip(descrip);
		return this;
	}
	public BossInfoData getLastSelf(Boss theBoss) {
		if(theBoss == null){
			return this;
		}
		int beKilled = theBoss.getBeKilled();
		int type = theBoss.getType();
		long changingBlood = theBoss.getChangingBlood();
		long blood = theBoss.getBlood();
		long increaseBlood = theBoss.getIncreaseBlood();
		int rewardNum1 = theBoss.getRewardNum1();
		int rewardNum2 = theBoss.getRewardNum2();
		
		Date startTime = theBoss.getStartTime();
		long continueTime = theBoss.getContinueTime();
		Date endTime = theBoss.getEndTime();
		int descrip = theBoss.getDescrip();
		this.setBeKilled(beKilled);
		this.setBossType(type);
		this.setChangingBlood(changingBlood);
		this.setIncreaseBlood(increaseBlood);
		this.setBlood(Long.valueOf(blood).intValue());
		this.setRewardNum1(rewardNum1);
		this.setRewardNum2(rewardNum2);
		this.setStartTime(startTime.getTime());
		this.setContinueTime(continueTime);
		this.setEndTime(endTime.getTime());
		this.setNameId(theBoss.getNameId());
		this.setDescrip(descrip);
		return this;
	}
}
