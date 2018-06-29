package com.gameserver.slot.handler.slot17;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.slot.data.ScatterInfo;

public class ScatterInfoType17 extends ScatterInfo {
	private List<Integer> bonusList = new ArrayList<Integer>();
	
	private int bnus;

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
}
