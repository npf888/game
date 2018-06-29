package com.gameserver.slot.data;

import java.util.ArrayList;
import java.util.List;

public class ScatterRotaryInfo {
	
	/**1：GOLD BONUS;2:FREE SPIN **/
	private int type;
	
	/**第二个盘子停留的位置 表里面的 ID **/
	private int secondIndex;
	
	/**第三个盘子停留的位置 表里面的 ID **/
	private int thirdIndex;
	
	/**第四个盘子停留的位置 表里面的 ID **/
	private int bigWheelIndex;
	
	/**大转盘玩法停留位置 **/
	private List<Integer> posList = new ArrayList<Integer>();
	
	/**Bouns 小游戏ID  **/
	private List<Integer> bounsList = new ArrayList<Integer>();
	
	/**Bouns 小游戏连线停留位置  **/
	private List<Integer> bounsListPosition = new ArrayList<Integer>();

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSecondIndex() {
		return secondIndex;
	}

	public void setSecondIndex(int secondIndex) {
		this.secondIndex = secondIndex;
	}

	public int getThirdIndex() {
		return thirdIndex;
	}

	public void setThirdIndex(int thirdIndex) {
		this.thirdIndex = thirdIndex;
	}

	public int getBigWheelIndex() {
		return bigWheelIndex;
	}

	public void setBigWheelIndex(int bigWheelIndex) {
		this.bigWheelIndex = bigWheelIndex;
	}

	public List<Integer> getPosList() {
		return posList;
	}

	public void setPosList(List<Integer> posList) {
		this.posList = posList;
	}

	public List<Integer> getBounsList() {
		return bounsList;
	}

	public void setBounsList(List<Integer> bounsList) {
		this.bounsList = bounsList;
	}

	public List<Integer> getBounsListPosition() {
		return bounsListPosition;
	}

	public void setBounsListPosition(List<Integer> bounsListPosition) {
		this.bounsListPosition = bounsListPosition;
	}

	
	
	
}
