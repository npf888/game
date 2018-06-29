package com.gameserver.task.data;

/**
 * 任务数据
 * @author wayne
 *
 */
public class TaskData {
	private int taskId;
	private int finished;
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
	public int getGetNums() {
		return getNums;
	}
	public void setGetNums(int getNums) {
		this.getNums = getNums;
	}

}
