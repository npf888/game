package com.gameserver.slot.handler.slot25;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.slot.data.ScatterInfo;

public class ScatterInfoType25 extends ScatterInfo{
	
	private List<Integer> bonusList = new ArrayList<Integer>();
	private int bnus;
	
	private List<Integer> wildList = new ArrayList<Integer>();
	private int wild;
	
	

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
	
	
}
