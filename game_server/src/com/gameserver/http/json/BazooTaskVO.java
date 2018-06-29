package com.gameserver.http.json;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮流叫号的任务
 * @author JavaServer
 *
 */
public class BazooTaskVO {
	//房间号
	private String roomNubmer;
	//是否被停止 true是，false否
	private boolean stop;
	//几点将要被执行
	private long time;
	private List<Long> nextPlayerIds=new ArrayList<Long>();
	
	
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
	public List<Long> getNextPlayerIds() {
		return nextPlayerIds;
	}
	public void setNextPlayerIds(List<Long> nextPlayerIds) {
		this.nextPlayerIds = nextPlayerIds;
	}

	
		
		
		
}
