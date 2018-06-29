package com.gameserver.bazoo.service.TimeTask;
/**
 * 无双吹牛的任务
 * @author JavaServer
 *
 */
public class BazooScheduleImpl{
	
	private BazooTask bazooTask;
	//房间号
	private String roomNubmer;
	//是否被停止 true是，false否
	private boolean stop;
	//几点将要被执行
	private long time;

	
	
	
	
	
	
	
	
	public BazooTask getBazooTask() {
		return bazooTask;
	}

	public void setBazooTask(BazooTask bazooTask) {
		this.bazooTask = bazooTask;
	}

	public String getRoomNubmer() {
		return roomNubmer;
	}

	public void setRoomNubmer(String roomNubmer) {
		this.roomNubmer = roomNubmer;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
	
	
	
	
}
