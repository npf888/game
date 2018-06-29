package com.gameserver.slot.data;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.slot.template.ScatterTemplate;

public class ScatterInfo {
	protected ScatterTemplate scatterTemplate;
	protected List<Integer> posList = new ArrayList<Integer>();

	/**
	 * @return the scatterTemplate
	 */
	public ScatterTemplate getScatterTemplate() {
		return scatterTemplate;
	}

	/**
	 * @param scatterTemplate the scatterTemplate to set
	 */
	public void setScatterTemplate(ScatterTemplate scatterTemplate) {
		this.scatterTemplate = scatterTemplate;
	}

	public List<Integer> getPosList(){
		return this.posList;
	}

	
	
	
}
