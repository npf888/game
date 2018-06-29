package com.gameserver.bazoo.data;
/**
 * 博趣入口页面的参数
 * @author JavaServer
 *
 */
public class BoQuThroughData {

	private String access_token;
	private String token_type;
	private Integer game_id;
	private Integer game_user_id;
	private Long timestamp;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public Integer getGame_id() {
		return game_id;
	}
	public void setGame_id(Integer game_id) {
		this.game_id = game_id;
	}
	public Integer getGame_user_id() {
		return game_user_id;
	}
	public void setGame_user_id(Integer game_user_id) {
		this.game_user_id = game_user_id;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
