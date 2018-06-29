package com.gameserver.lobby.data;

/**
 * 返回客户端最高20位彩金
 * @author 郭君伟
 *
 */
public class JackpotList implements Comparable<JackpotList>{
	
	/** 角色唯一ID**/
	private long userId;
	/** 头像图片 **/
	private String img;
	/** 名字**/
	private String name;
	/** 彩金 **/
	private long jackpot;
    /** 类型  百家乐 1 德州扑克 2 老虎机 classic：3  老虎机 四大美人：4 老虎机 水果：5  **/
	private int gameType;
    

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getJackpot() {
		return jackpot;
	}

	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}
	
	

	

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	@Override
	public int compareTo(JackpotList o) {
		if(this.jackpot > o.getJackpot()){
			return -1;
		}else if(this.jackpot == o.getJackpot()){
			return 0;
		}
		return 1;
	}

	@Override
	public String toString() {
		return this.userId+":::"+this.jackpot;
	}
	
	
	

}
