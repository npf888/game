package com.gameserver.bazoo.data;


/**
 * 摇出来的色子的信息
 * @author JavaServer
 *
 */
public class DiceInfo {

	//用户ID
	private long passportId;
	
	private int[] diceValues;
	
	//牛牛模式专用字段 几小牛
	private int cowNameInt;
	//需要被标红的色子
	private int[] redDiceValues;

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public int[] getDiceValues() {
		return diceValues;
	}

	public void setDiceValues(int[] diceValues) {
		this.diceValues = diceValues;
	}

	public int getCowNameInt() {
		return cowNameInt;
	}

	public void setCowNameInt(int cowNameInt) {
		this.cowNameInt = cowNameInt;
	}

	public int[] getRedDiceValues() {
		return redDiceValues;
	}

	public void setRedDiceValues(int[] redDiceValues) {
		this.redDiceValues = redDiceValues;
	}

	
	
	
	
	
}
