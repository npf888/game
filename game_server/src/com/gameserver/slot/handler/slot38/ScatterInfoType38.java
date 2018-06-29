package com.gameserver.slot.handler.slot38;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.slot.data.ScatterInfo;

public class ScatterInfoType38 extends ScatterInfo{
	private List<Integer> bonusList = new ArrayList<Integer>();
	private int bnus;
	private List<Integer> wildList = new ArrayList<Integer>();
	private int wild;
	//南瓜头的位置
	private List<Integer> pumpkinList = new ArrayList<Integer>();
	//南瓜头的数量
	private int pumpkin;
	//jackpot的位置
	private List<Integer> jackpotList = new ArrayList<Integer>();
	//jackpot的数量
	private int jackpotNum;
	
	public List<Integer> getBonusList() {
		return bonusList;
	}

	public void setBonusList(List<Integer> bonusList) {
		this.bonusList = bonusList;
	}

	public int getBnus() {
		return bnus;
	}

	public void setBnus(int bnus) {
		this.bnus = bnus;
	}

	public List<Integer> getWildList() {
		return wildList;
	}

	public void setWildList(List<Integer> wildList) {
		this.wildList = wildList;
	}

	public int getWild() {
		return wild;
	}

	public void setWild(int wild) {
		this.wild = wild;
	}

	public List<Integer> getPumpkinList() {
		return pumpkinList;
	}

	public void setPumpkinList(List<Integer> pumpkinList) {
		this.pumpkinList = pumpkinList;
	}

	public int getPumpkin() {
		return pumpkin;
	}

	public void setPumpkin(int pumpkin) {
		this.pumpkin = pumpkin;
	}

	public List<Integer> getJackpotList() {
		return jackpotList;
	}

	public void setJackpotList(List<Integer> jackpotList) {
		this.jackpotList = jackpotList;
	}

	public int getJackpotNum() {
		return jackpotNum;
	}

	public void setJackpotNum(int jackpotNum) {
		this.jackpotNum = jackpotNum;
	}

}
