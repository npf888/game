package com.gameserver.http.boqu;

public class BoquInfoData {

	//用户的平台 ID
	private String user_id;
	//该游戏ID(区分合作方发布多款游戏)
	private String game_id;
	//该游戏内用户ID
	private String game_user_id;
	//指定需要返回哪些字段多个用逗号分隔
	private String type;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getGame_id() {
		return game_id;
	}
	public void setGame_id(String game_id) {
		this.game_id = game_id;
	}
	public String getGame_user_id() {
		return game_user_id;
	}
	public void setGame_user_id(String game_user_id) {
		this.game_user_id = game_user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
