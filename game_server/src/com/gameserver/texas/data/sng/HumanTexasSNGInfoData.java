package com.gameserver.texas.data.sng;

import com.db.model.HumanTexasSNGEntity;
import com.gameserver.texas.sng.HumanTexasSNG;

/**
 * 德州sng
 * @author wayne
 *
 */
public class HumanTexasSNGInfoData {
	private int joinTimes;
	private int goldNum;
	private int silverNum;
	private int copperNum;
	private int weekScore;
	public int getJoinTimes() {
		return joinTimes;
	}
	public void setJoinTimes(int joinTimes) {
		this.joinTimes = joinTimes;
	}
	public int getGoldNum() {
		return goldNum;
	}
	public void setGoldNum(int goldNum) {
		this.goldNum = goldNum;
	}
	public int getSilverNum() {
		return silverNum;
	}
	public void setSilverNum(int silverNum) {
		this.silverNum = silverNum;
	}
	public int getCopperNum() {
		return copperNum;
	}
	public void setCopperNum(int copperNum) {
		this.copperNum = copperNum;
	}
	public int getWeekScore() {
		return weekScore;
	}
	public void setWeekScore(int weekScore) {
		this.weekScore = weekScore;
	}
	
	public static HumanTexasSNGInfoData convertFromHumanTexasSNG(HumanTexasSNG humanTexasSNG){
		HumanTexasSNGInfoData tempHumanTexasSNGInfoData = new HumanTexasSNGInfoData();
		tempHumanTexasSNGInfoData.setCopperNum(humanTexasSNG.getCopperNum());
		tempHumanTexasSNGInfoData.setGoldNum(humanTexasSNG.getGoldNum());
		tempHumanTexasSNGInfoData.setJoinTimes(humanTexasSNG.getJoinTimes());
		tempHumanTexasSNGInfoData.setSilverNum(humanTexasSNG.getSilverNum());
		tempHumanTexasSNGInfoData.setWeekScore(humanTexasSNG.getWeekScore());
		return tempHumanTexasSNGInfoData;
	}
	
	public static HumanTexasSNGInfoData convertFromHumanTexasSNGEntity(
			HumanTexasSNGEntity tempHumanTexasSNGEntity) {
		// TODO Auto-generated method stub
		HumanTexasSNGInfoData tempHumanTexasSNGInfoData = new HumanTexasSNGInfoData();
		tempHumanTexasSNGInfoData.setCopperNum(tempHumanTexasSNGEntity.getCopperNum());
		tempHumanTexasSNGInfoData.setGoldNum(tempHumanTexasSNGEntity.getGoldNum());
		tempHumanTexasSNGInfoData.setJoinTimes(tempHumanTexasSNGEntity.getJoinTimes());
		tempHumanTexasSNGInfoData.setSilverNum(tempHumanTexasSNGEntity.getSilverNum());
		tempHumanTexasSNGInfoData.setWeekScore(tempHumanTexasSNGEntity.getWeekScore());
		return tempHumanTexasSNGInfoData;
	}
}
