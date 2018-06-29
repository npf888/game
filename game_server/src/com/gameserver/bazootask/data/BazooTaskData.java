package com.gameserver.bazootask.data;

public class BazooTaskData {
	public static final int  TASK_TYPE=1;
	public static final int  ACHIEVE_TYPE=2;
	public static final int  FINISH_YES=1;
	public static final int  FINISH_NO=0;
	
	private int taskId;
	private int type;//1：是任务，2：是成就
	private int finished;
	private int finishTimes;//完成次数
	private int getNums;//领取次数
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
	public int getFinishTimes() {
		return finishTimes;
	}
	public void setFinishTimes(int finishTimes) {
		this.finishTimes = finishTimes;
	}
	public int getGetNums() {
		return getNums;
	}
	public void setGetNums(int getNums) {
		this.getNums = getNums;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
