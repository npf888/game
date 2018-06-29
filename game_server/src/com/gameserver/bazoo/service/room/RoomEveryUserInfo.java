package com.gameserver.bazoo.service.room;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.service.blackWhite.BlackWhiteInfo;
import com.gameserver.bazoo.service.classical.ClassicalUserInfo;
import com.gameserver.bazoo.service.cow.CowUserInfo;
import com.gameserver.bazoo.service.showHand.ShowHandUserInfo;

// 房间--用户--玩耍 信息
public class RoomEveryUserInfo {
	
	protected Logger logger = Loggers.BAZOO;
	public static final int USER_STATUS_WATCH=0;
	public static final int USER_STATUS_PARTIN=1;
	public static final int USER_STATUS_PURE_WATCH=2;
	
	
	public static final int USER_NOT_IN_ROOM=0;
	public static final int USER_IN_ROOM=1;
	
	//用户在房间里的位置
	private int seat;
	//用户当前的状态 0：观战   未参与(因为刚进入房间 别的用户 正在玩，所以此时只能观战)    1：参与      2:纯粹的观战模式 （进去就是看看 什么其他的也不干）
	private int userStatus;
	//输赢
	private Long money;
	//几连胜
	private int winTimes;
	//最终用户色子 摇的啥
	private List<Integer> diceValues;
	//每一局 输赢多少倍
	private int multiple;
	/**
	 * 删除标志位 (当游戏正在进行时 ，用户退出了房间 ，那么 他也得 玩完这一局（此时此刻他不能再进入其他房间），然后由服务器给他删除掉)
	 * 0:用户已经退出房间，1:用户没有退出房间
	 */
	private int isInRoom;
	
	
	
	
	
	/**
	 * 经典吹牛的 用户信息
	 */
	private ClassicalUserInfo classicalUserInfo;
	/**
	 * 牛牛 用户信息
	 */
	private CowUserInfo cowUserInfo;
	
	/**
	 * 梭哈 用户信息
	 */
	private ShowHandUserInfo showHandUserInfo;
	
	/**
	 * 黑白单双 用户信息
	 */
	private BlackWhiteInfo blackWhiteInfo;
	
	
	
	
	
	/**
	 * 每次重摇 都初始化一些信息
	 */
	public RoomEveryUserInfo(){
		init();
	}
	public void init(){
		isInRoom=0;
		seat=0;
		money=0l;
		userStatus=1;
		multiple=0;
		classicalUserInfo=new ClassicalUserInfo();
		cowUserInfo=new CowUserInfo();
		showHandUserInfo=new ShowHandUserInfo();
		blackWhiteInfo=new BlackWhiteInfo();
		diceValues=new ArrayList<Integer>();
	}
	
	
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public ClassicalUserInfo getClassicalUserInfo() {
		return classicalUserInfo;
	}
	public void setClassicalUserInfo(ClassicalUserInfo classicalUserInfo) {
		this.classicalUserInfo = classicalUserInfo;
	}
	public CowUserInfo getCowUserInfo() {
		return cowUserInfo;
	}
	public void setCowUserInfo(CowUserInfo cowUserInfo) {
		this.cowUserInfo = cowUserInfo;
	}
	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}
	public int getWinTimes() {
		return winTimes;
	}
	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}
	public ShowHandUserInfo getShowHandUserInfo() {
		return showHandUserInfo;
	}
	public void setShowHandUserInfo(ShowHandUserInfo showHandUserInfo) {
		this.showHandUserInfo = showHandUserInfo;
	}
	public List<Integer> getDiceValues() {
		return diceValues;
	}
	public void setDiceValues(List<Integer> diceValues) {
		this.diceValues = diceValues;
	}
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	public int getIsInRoom() {
		return isInRoom;
	}
	public void setIsInRoom(int isInRoom,long passportId) {
		logger.info("[无双吹牛]---["+passportId+"isInRoom::"+isInRoom+"]");
		this.isInRoom = isInRoom;
	}
	public BlackWhiteInfo getBlackWhiteInfo() {
		return blackWhiteInfo;
	}
	public void setBlackWhiteInfo(BlackWhiteInfo blackWhiteInfo) {
		this.blackWhiteInfo = blackWhiteInfo;
	}
	
	
	
	
}
