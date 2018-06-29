package com.robot.bazoo;

public class RobotBazooCacheData {
	//叫几次 号之后  抢开
	private int robNum;
	private int diceNum;
	private int diceValue;
	
	public int getRobNum() {
		return robNum;
	}

	public void setRobNum(int robNum) {
		this.robNum = robNum;
	}

	public RobotBazooCacheData(){
		this.robNum=0;
		this.diceNum=0;
		this.diceValue=0;
	}

	public int getDiceNum() {
		return diceNum;
	}

	public void setDiceNum(int diceNum) {
		this.diceNum = diceNum;
	}

	public int getDiceValue() {
		return diceValue;
	}

	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
	}

	
}
