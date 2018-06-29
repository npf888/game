package com.gameserver.http.vo;

import java.util.List;

import com.gameserver.common.data.RandRewardData;

//{"charId":10017,"content":"2121","id":0,"itemList":[{"rewardCount":12121,"rewardId":3}],"title":"1212"}
public class RewardVO {

	private long id;
	private long charId;
	private String content;
	private String title;
	private List<RandRewardData> itemList;
	public long getCharId() {
		return charId;
	}
	public void setCharId(long charId) {
		this.charId = charId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<RandRewardData> getItemList() {
		return itemList;
	}
	public void setItemList(List<RandRewardData> itemList) {
		this.itemList = itemList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
