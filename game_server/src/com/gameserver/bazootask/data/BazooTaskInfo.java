package com.gameserver.bazootask.data;
/**
 * 无双吹牛 的task的任务
 * @author JavaServer
 *
 */
public class BazooTaskInfo {

	private int taskId;
	private int refreshtype;
	private int modeType;
	private int bigType;
	private int wayOfPlay;
	private int condition;
	private int rewardNum;
	//完成次数
	private int finishTimes;
	//是否完成 0:未完成，1:已完成
	private int isFinish;
	private int nameId;
	private int descrip;
	private String icon;
	
	
	
	
	public int getRefreshtype() {
		return refreshtype;
	}
	public void setRefreshtype(int refreshtype) {
		this.refreshtype = refreshtype;
	}
	public int getModeType() {
		return modeType;
	}
	public void setModeType(int modeType) {
		this.modeType = modeType;
	}
	public int getWayOfPlay() {
		return wayOfPlay;
	}
	public void setWayOfPlay(int wayOfPlay) {
		this.wayOfPlay = wayOfPlay;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public int getRewardNum() {
		return rewardNum;
	}
	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}
	public int getFinishTimes() {
		return finishTimes;
	}
	public void setFinishTimes(int finishTimes) {
		this.finishTimes = finishTimes;
	}
	public int getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(int isFinish) {
		this.isFinish = isFinish;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getBigType() {
		return bigType;
	}
	public void setBigType(int bigType) {
		this.bigType = bigType;
	}
	
	
	
	
	
}
