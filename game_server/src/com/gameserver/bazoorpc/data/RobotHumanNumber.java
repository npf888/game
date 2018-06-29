package com.gameserver.bazoorpc.data;

import java.util.List;

import com.gameserver.player.Player;

public class RobotHumanNumber {
	private int robotNumber = 0;
	private int humanNumber = 0;
	private List<Player> players;
	public int getRobotNumber() {
		return robotNumber;
	}
	public void setRobotNumber(int robotNumber) {
		this.robotNumber = robotNumber;
	}
	public int getHumanNumber() {
		return humanNumber;
	}
	public void setHumanNumber(int humanNumber) {
		this.humanNumber = humanNumber;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	
	
}
