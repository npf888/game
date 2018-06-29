package com.gameserver.bazoo.service.cow;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.player.Player;

// 房间--用户--玩耍 信息
public class CowUserInfo {
	
	public static final int isBanker=1;
	public static final int notBanker=0;
	//要摇的哪几个色子 的index的值
	private List<Integer> shakeIndexs;
	//是赢(true)  还是输(false)
	private boolean isRight;
	//赢了自己的人的 集合（一局）
	private List<Player> winPlayers=new ArrayList<Player>();
	//赢了自己的人的 集合（一局）
	private List<Player> lostPlayers=new ArrayList<Player>();
	
	//当前摇到的是啥
	private String cowName;
	//当前摇到的是几 对应上边的 cowName 和它 同进退
	private int cowNum;
	//需要标红的色子
	private List<Integer> redDiceValues;
	//是否是庄家 1:是，0:否
	private int banker;
	
	//每一局 摇了几次 用户 每一局 最多能摇一次
	private int shakeNum;
	
	public CowUserInfo(){
		init();
	}
	/**
	 * 每次重摇 都初始化一些信息
	 */
	public void init(){
		shakeNum=0;
		winPlayers.clear();
		lostPlayers.clear();
		banker=0;
		cowNum=-1;
		cowName="";
		shakeIndexs=new ArrayList<Integer>();
		redDiceValues=new ArrayList<Integer>();
		isRight=false;
	}
	
	
	public List<Player> getWinPlayers() {
		return winPlayers;
	}
	public void setWinPlayers(List<Player> winPlayers) {
		this.winPlayers = winPlayers;
	}
	public List<Player> getLostPlayers() {
		return lostPlayers;
	}
	public void setLostPlayers(List<Player> lostPlayers) {
		this.lostPlayers = lostPlayers;
	}
	public List<Integer> getShakeIndexs() {
		return shakeIndexs;
	}
	public void setShakeIndexs(List<Integer> shakeIndexs) {
		this.shakeIndexs = shakeIndexs;
	}
	public boolean isRight() {
		return isRight;
	}
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}
	public String getCowName() {
		return cowName;
	}
	public void setCowName(String cowName) {
		this.cowName = cowName;
	}
	public int getCowNum() {
		return cowNum;
	}
	public void setCowNum(int cowNum) {
		this.cowNum = cowNum;
	}
	public int getBanker() {
		return banker;
	}
	public void setBanker(int banker) {
		this.banker = banker;
	}
	public int getShakeNum() {
		return shakeNum;
	}
	public void setShakeNum(int shakeNum) {
		this.shakeNum = shakeNum;
	}
	public List<Integer> getRedDiceValues() {
		return redDiceValues;
	}
	public void setRedDiceValues(List<Integer> redDiceValues) {
		this.redDiceValues = redDiceValues;
	}
	
	
}
