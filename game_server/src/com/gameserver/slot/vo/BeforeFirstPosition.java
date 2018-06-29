package com.gameserver.slot.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 横向 的：
 * 每次所选元素 的上一条
 * @author JavaServer
 *
 */
public class BeforeFirstPosition {

	private List<Long> beforFirstPosition = new ArrayList<Long>();

	public List<Long> getBeforFirstPosition() {
		return beforFirstPosition;
	}

	public void setBeforFirstPosition(List<Long> beforFirstPosition) {
		this.beforFirstPosition = beforFirstPosition;
	}
	
	
}
