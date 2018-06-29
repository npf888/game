package com.gameserver.slot.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 每次转出来的 15个元素，3*5
 * 3*3的是 9个元素
 * @author JavaServer
 *
 */
public class ChoseElement {

	private List<Integer> choseElements = new ArrayList<Integer>();

	public List<Integer> getChoseElements() {
		return choseElements;
	}

	public void setChoseElements(List<Integer> choseElements) {
		this.choseElements = choseElements;
	}
	
	
}
