package com.gameserver.slot.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 横向的
 * 
 * 只装 0,1,2 这三个位置的
 * @author JavaServer
 *
 */
public class OneTwoThreePosition {

	private List<Integer> oneTwoThreePosition = new ArrayList<Integer>();

	public List<Integer> getOneTwoThreePosition() {
		return oneTwoThreePosition;
	}

	public void setOneTwoThreePosition(List<Integer> oneTwoThreePosition) {
		this.oneTwoThreePosition = oneTwoThreePosition;
	}
	
	
}
