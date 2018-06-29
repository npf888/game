package com.gameserver.bazooachieve.data;

public class BazooAchieveData {
	private int achieveId;
	private int finished;
	private int finishValues;//完成次数 或者钱数  或者 其它的
	private int getNums;// 成就只能领取一次，以后这个成就 就没有了
	
	public int getAchieveId() {
		return achieveId;
	}
	public void setAchieveId(int achieveId) {
		this.achieveId = achieveId;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
	public int getFinishValues() {
		return finishValues;
	}
	public void setFinishValues(int finishValues) {
		this.finishValues = finishValues;
	}
	public int getGetNums() {
		return getNums;
	}
	public void setGetNums(int getNums) {
		this.getNums = getNums;
	}
	
	
}
