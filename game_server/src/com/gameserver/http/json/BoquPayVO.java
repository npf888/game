package com.gameserver.http.json;

import com.gameserver.recharge.data.BoquData;

/**
 * 博趣传过来的json
 * @author JavaServer
 *
 */
public class BoquPayVO {

	private String action;
	private BoquData data;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public BoquData getData() {
		return data;
	}
	public void setData(BoquData data) {
		this.data = data;
	}
	
	
}
