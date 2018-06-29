package com.gameserver.http.boqu;

public class BoquBillData {

	//用户的平台 ID
	private String user_id;
	//该游戏ID(区分合作方发布多款游戏)
	private String game_id;
	//该游戏内用户ID
	private String game_user_id;
	
	//平台币改变数量正数加 负数减
	private int coin;
	//游戏币数量正数加 负数减,根据定义好的比例计算所得
	private int game_coin;
	//交易标题
	private String title;
	//交易ID
	private String bill_id;
	
	
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
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public int getGame_coin() {
		return game_coin;
	}
	public void setGame_coin(int game_coin) {
		this.game_coin = game_coin;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBill_id() {
		return bill_id;
	}
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}

	
	
}
