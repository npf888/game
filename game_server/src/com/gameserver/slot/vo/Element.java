package com.gameserver.slot.vo;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.slot.template.SlotsTemplate;

/**
 * 所有的元素信息
 * 这个对象 表示 某一列 的元素信息
 * @author JavaServer
 *
 */
public class Element {

	private List<SlotsTemplate> allElements = new ArrayList<SlotsTemplate>();

	public List<SlotsTemplate> getAllElements() {
		return allElements;
	}

	public void setAllElements(List<SlotsTemplate> allElements) {
		this.allElements = allElements;
	}
	
	
}
