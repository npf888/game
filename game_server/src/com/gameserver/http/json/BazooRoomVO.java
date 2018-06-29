package com.gameserver.http.json;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.bazoo.service.room.RoomNumber;

public class BazooRoomVO {
	//当前房间的状态
		private int state;
		//房间状态  true 满了，false 没满
		private boolean fullOrNot;
		//房间号
		private RoomNumber roomNumber;
		//房间里的人
		private List<PlayerVO> playerVOS;
		//参与游戏的总人数（不包括观光者）,创建 房间的时候 由配置文件 决定
		private int maxNum;
		//定时 轮流转的任务
		private List<BazooTaskVO> bazooTaskVOS = new ArrayList<BazooTaskVO>();
		
		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public boolean isFullOrNot() {
			return fullOrNot;
		}

		public void setFullOrNot(boolean fullOrNot) {
			this.fullOrNot = fullOrNot;
		}

		public RoomNumber getRoomNumber() {
			return roomNumber;
		}

		public void setRoomNumber(RoomNumber roomNumber) {
			this.roomNumber = roomNumber;
		}

		public List<PlayerVO> getPlayerVOS() {
			return playerVOS;
		}

		public void setPlayerVOS(List<PlayerVO> playerVOS) {
			this.playerVOS = playerVOS;
		}

		public int getMaxNum() {
			return maxNum;
		}

		public void setMaxNum(int maxNum) {
			this.maxNum = maxNum;
		}

		public List<BazooTaskVO> getBazooTaskVOS() {
			return bazooTaskVOS;
		}

		public void setBazooTaskVOS(List<BazooTaskVO> bazooTaskVOS) {
			this.bazooTaskVOS = bazooTaskVOS;
		}

		
		
		
		
}
