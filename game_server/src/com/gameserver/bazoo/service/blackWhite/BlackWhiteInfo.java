package com.gameserver.bazoo.service.blackWhite;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.player.Player;

/**
 * 黑白单双的信息
 * @author JavaServer
 *
 */
public class BlackWhiteInfo {
	//是不是当前用户在叫号
	private boolean isCurUser;

	//每次变化所有的值
	private List<Integer> perdiceValues = new ArrayList<Integer>();
	private List<Integer> removeDice = new ArrayList<Integer>();
	private List<Integer> removeDiceIndex = new ArrayList<Integer>();
	
	
	
	//每一次 的输赢 金币和倍数
	private long gold;
	private int multiple;
	//是否出局
	private int isOut;
	//每一局 个人 的几杀
	private int killNum;
	
	public boolean isCurUser() {
		return isCurUser;
	}

	public void setCurUser(boolean isCurUser) {
		this.isCurUser = isCurUser;
	}

	public List<Integer> getRemoveDice() {
		return removeDice;
	}

	public void setRemoveDice(List<Integer> removeDice) {
		this.removeDice = removeDice;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getIsOut() {
		return isOut;
	}

	public void setIsOut(int isOut) {
		this.isOut = isOut;
	}

	public List<Integer> getPerdiceValues() {
		return perdiceValues;
	}

	public void setPerdiceValues(List<Integer> perdiceValues) {
		this.perdiceValues = perdiceValues;
	}

	public List<Integer> getRemoveDiceIndex() {
		return removeDiceIndex;
	}

	public void setRemoveDiceIndex(List<Integer> removeDiceIndex) {
		this.removeDiceIndex = removeDiceIndex;
	}

	public int getKillNum() {
		return killNum;
	}

	public void setKillNum(int killNum) {
		this.killNum = killNum;
	}



	
	
	
}
