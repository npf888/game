package com.gameserver.slot.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 横向的：每一条线的元素
 * @author JavaServer
 *
 */
public class SingelElementLine {

	private List<Integer> singleLine = new ArrayList<Integer>();

	public List<Integer> getSingleLine() {
		return singleLine;
	}

	public void setSingleLine(List<Integer> singleLine) {
		this.singleLine = singleLine;
	}
	
	
}
