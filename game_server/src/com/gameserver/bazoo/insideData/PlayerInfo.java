package com.gameserver.bazoo.insideData;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.player.Player;

public class PlayerInfo {
	private Logger logger = Loggers.BAZOO;
	
	/**
	 * 经典模式
	 */
	//默认为 不抢开
	private boolean robOpen;
	//抢开的人
	private Player robMan;
	//抢开的人 是否 猜对
	private boolean isRight;
	//最后一个 叫点的 人(也是  上一个叫号的用户)
	private Player lastMan;
	//当前等待执行的人
	private Player waitingMan;
	//每一局刚开始的时候 第一个叫号的人
	private Player everyRoundFirstCallMan;
	//本轮是否已经开始叫号 true 是   false:没有（主要是开始叫号之后可就不可以 重摇了）
	private boolean epicycleStatus;
	//万能符是否叫过  默认为没有叫过(一点 就是万能符)
	private boolean onePoint;
	//万能符是否叫过  默认为没有叫过(一点 就是万能符)
	private int finalDiceNum;
	
	/**
	 * 牛牛模式
	 */
	//-----------------
	/**
	 * 梭哈模式
	 */
	//梭哈模式 每个 房间 摇了多少次（每个房间最多可以要的次数=  人数 * 7）
	private int leftTimes;
	//最小的需要叫点的人
	private List<Player> lastMans;
	//每次可能有两个以上叫点的人 ，当一个叫完以后  ，还不能 去 选 下一个该谁叫点的人，必须等所有叫点的人  全部叫完
	private int callTimes;
	
	
	/**
	 * 黑白红黑模式
	 */
	//叫一次 这个数字 就加一
	private int times;
	//没摇一次 所有参与的人
	private List<Player> allInPlayers = new ArrayList<Player>();
	//几杀
	private int killNum;
	
	/**
	 * 公共
	 */
	
	//当前房间的状态      0:绝对第一次未开始     1:正在进行中    
	private int status;
	//翻的倍数
	private int len;
	
	public PlayerInfo(){
		
		init(true);
	}
	
	
	public void init(boolean refresh){
		if(refresh){
			status=0;
		}
		finalDiceNum=0;
		epicycleStatus=false;
		onePoint=false;
		len=0;
		lastMan=null;
		isRight=false;
		robMan=null;
		robOpen=false;
		leftTimes=0;
		callTimes=0;
		times=0;
		killNum=0;
	}
	
	
	public void countLen(Room room, List<Player> roomPlayerList){
		Player lastPlayer = this.getLastMan();
		RoomEveryUserInfo lastRoomEveryUserInfo = lastPlayer.getHuman().getBazooRoomEveryUserInfo();
		int lastSeat = lastRoomEveryUserInfo.getSeat();
		//抢开的人
		//抢开的人  的判断 一定 是和 最后一个叫号的 人相反 
		Player robPlayer = this.getRobMan();
		RoomEveryUserInfo robRoomEveryUserInfo = robPlayer.getHuman().getBazooRoomEveryUserInfo();
		int robSeat = robRoomEveryUserInfo.getSeat();
		int cha = robSeat-lastSeat;
		//算一下 抢开的人 和  最后一个交点的人之间的距离
		if(cha < 0){
			len = cha+room.getPlayersPartIn().size();
		}else{
			len = cha;
		}
		logger.info("[无双吹牛]---[计算倍数]---[len::"+len+"]---[cha::"+cha+"]---[lastSeat::"+lastSeat+"]---[robSeat::"+robSeat+"]");
		//去掉观看 的人
//		for(Player p:roomPlayerList){
//			RoomEveryUserInfo roomEveryUserInfo = p.getHuman().getBazooRoomEveryUserInfo();
//			if(p.getHuman().getBazooRoomEveryUserInfo().getUserStatus() == RoomEveryUserInfo.USER_STATUS_PURE_WATCH
//					||p.getHuman().getBazooRoomEveryUserInfo().getUserStatus() == RoomEveryUserInfo.USER_STATUS_WATCH){
//				
//				int pSeat = roomEveryUserInfo.getSeat();
//				if(robSeat < lastSeat){
//					if(pSeat>robSeat  && pSeat<lastSeat){
//							len--;
//					}
//				}else if(robSeat > lastSeat){
//					if(pSeat>lastSeat  && pSeat<robSeat){
//							len--;
//					}
//				}
//				
//			}
//		}
	}
	
	
	
	/**
	 * 黑红单双  模式 判断几杀
	 * @return
	 */
	
	public int howManyKillNum(){
		int killNum = 0;
		List<Player> all = this.getAllInPlayers();
		for(Player p:all){
			if(p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getIsOut() == 1){
				killNum++;
			}
		}
		return killNum;
	}
	
	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Player getLastMan() {
		return lastMan;
	}

	public void setLastMan(Player lastMan) {
		this.lastMan = lastMan;
	}

	public boolean isRobOpen() {
		return robOpen;
	}

	public void setRobOpen(boolean robOpen) {
		this.robOpen = robOpen;
	}

	public Player getRobMan() {
		return robMan;
	}

	public void setRobMan(Player robMan) {
		this.robMan = robMan;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public boolean isOnePoint() {
		return onePoint;
	}

	public void setOnePoint(boolean onePoint) {
		this.onePoint = onePoint;
	}

	public boolean isEpicycleStatus() {
		return epicycleStatus;
	}

	public void setEpicycleStatus(boolean epicycleStatus) {
		this.epicycleStatus = epicycleStatus;
	}

	public Player getWaitingMan() {
		return waitingMan;
	}

	public void setWaitingMan(Player waitingMan) {
		this.waitingMan = waitingMan;
	}
	public int getFinalDiceNum() {
		return finalDiceNum;
	}
	public void setFinalDiceNum(int finalDiceNum) {
		this.finalDiceNum = finalDiceNum;
	}

	public Player getEveryRoundFirstCallMan() {
		return everyRoundFirstCallMan;
	}

	public void setEveryRoundFirstCallMan(Player everyRoundFirstCallMan) {
		this.everyRoundFirstCallMan = everyRoundFirstCallMan;
	}
	
	public int getLeftTimes() {
		return leftTimes;
	}

	public void setLeftTimes(int leftTimes) {
		this.leftTimes = leftTimes;
	}

	public List<Player> getLastMans() {
		return lastMans;
	}
	public void setLastMans(List<Player> lastMans) {
		this.lastMans = lastMans;
	}

	public int getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(int callTimes) {
		this.callTimes = callTimes;
	}
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	public void changeTimes(){
		if(this.times<10){
			this.times=this.times+1;
		}
	}
	public List<Player> getAllInPlayers() {
		return allInPlayers;
	}


	public void setAllInPlayers(List<Player> allInPlayers) {
		this.allInPlayers = allInPlayers;
	}


	public int getKillNum() {
		return killNum;
	}


	public void setKillNum(int killNum) {
		this.killNum = killNum;
	}


	
	
	
}
