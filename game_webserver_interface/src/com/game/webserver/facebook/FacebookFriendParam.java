package com.game.webserver.facebook;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.game.webserver.login.AbstractParams;


public class FacebookFriendParam extends AbstractParams<FacebookFriendRes>{

	private List<String> friendIdList;

	
	public FacebookFriendParam(){}
	public FacebookFriendParam(String host, String page, boolean post) {
		super(host, page, post);
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the friendIdList
	 */
	public List<String> getFriendIdList() {
		return friendIdList;
	}
	/**
	 * @param friendIdList the friendIdList to set
	 */
	public void setFriendIdList(List<String> friendIdList) {
		this.friendIdList = friendIdList;
	}
	
	@Override
	protected String content() {
		// TODO Auto-generated method stub
		return JSON.toJSONString(this);
	}

	@Override
	public FacebookFriendRes getRes() {
		// TODO Auto-generated method stub
		if(this.errorCode!=0)
		{
			return null;
		}
		else
		{
			return JSON.parseObject(this.result,FacebookFriendRes.class);
		}
	}

}
