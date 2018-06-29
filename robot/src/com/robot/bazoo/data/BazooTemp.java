package com.robot.bazoo.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.bazoorpc.remoteData.TriggerRobotData;
import com.robot.Robot;
import com.robot.bazoo.Util.ClassicalLogicUtil;
import com.robot.bazoo.Util.CountNumUtil;

public class BazooTemp {
	protected Logger logger = Loggers.BAZOO;
	//当前处于哪个房间内
	private RoomNumber roomNumber;
	
	private TriggerRobotData data;
	
	//是哪里的main调用过来的  
	private String wayOfType;
	
	private int indexNum=-1;
	private int bet;
	
	private List<Integer> diceValues = new ArrayList<Integer>();
	
	
	private int cowNameInt;
	
	
	//所有人的 点数
	private List<DiceInfo> diceInfoList = new ArrayList<DiceInfo>();
	private DiceNum diceNum = new DiceNum();
	
	//是否是顺子
	private boolean isStraight = false;
	//自己的点数
	private DiceInfo selfDiceInfo = null;
	
	//第一次（每一局纯粹 第一次）
	private int firstTime = 0;
	
	
	
	public int getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public List<Integer> getDiceValues() {
		return diceValues;
	}
	public void setDiceValues(List<Integer> diceValues) {
		this.diceValues = diceValues;
	}
	public int getCowNameInt() {
		return cowNameInt;
	}
	public void setCowNameInt(int cowNameInt) {
		this.cowNameInt = cowNameInt;
	}
	public List<DiceInfo> getDiceInfoList() {
		return diceInfoList;
	}
	public void setDiceInfoList(List<DiceInfo> diceInfoList) {
		this.diceInfoList = diceInfoList;
	}
	
	public DiceNum getDiceNum() {
		return diceNum;
	}
	public void setDiceNum(DiceNum diceNum) {
		this.diceNum = diceNum;
	}
	
	
	public TriggerRobotData getData() {
		return data;
	}
	public void setData(TriggerRobotData data) {
		this.data = data;
	}
	
	
	public String getWayOfType() {
		return wayOfType;
	}
	public void setWayOfType(String wayOfType) {
		this.wayOfType = wayOfType;
	}
	
	
	
	public DiceInfo getSelfDiceInfo() {
		return selfDiceInfo;
	}
	public void setSelfDiceInfo(DiceInfo selfDiceInfo) {
		this.selfDiceInfo = selfDiceInfo;
	}
	public boolean isStraight() {
		return isStraight;
	}
	public void setStraight(boolean isStraight) {
		this.isStraight = isStraight;
	}
	
	
	
	public RoomNumber getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(RoomNumber roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(int firstTime) {
		this.firstTime = firstTime;
	}
	//统计以下色子
	public void  countDice(boolean onePoint){
		this.getDiceNum().init();
		for(DiceInfo diceInfo:diceInfoList){
			int[] diceArr = diceInfo.getDiceValues();
			
			this.getDiceNum().getOnePoint().setNumber(this.getDiceNum().getOnePoint().getNumber()
					+ClassicalLogicUtil.getDice(DiceUtils.getListFromArr(diceArr), 1, onePoint));
			
			this.getDiceNum().getTwoPoint().setNumber(this.getDiceNum().getTwoPoint().getNumber()
					+ClassicalLogicUtil.getDice(DiceUtils.getListFromArr(diceArr), 2, onePoint));
			
			this.getDiceNum().getThreePoint().setNumber(this.getDiceNum().getThreePoint().getNumber()
					+ClassicalLogicUtil.getDice(DiceUtils.getListFromArr(diceArr), 3, onePoint));
			
			this.getDiceNum().getFourPoint().setNumber(this.getDiceNum().getFourPoint().getNumber()
					+ClassicalLogicUtil.getDice(DiceUtils.getListFromArr(diceArr), 4, onePoint));
			
			this.getDiceNum().getFivePoint().setNumber(this.getDiceNum().getFivePoint().getNumber()
					+ClassicalLogicUtil.getDice(DiceUtils.getListFromArr(diceArr), 5, onePoint));
			
			this.getDiceNum().getSixPoint().setNumber(this.getDiceNum().getSixPoint().getNumber()
					+ClassicalLogicUtil.getDice(DiceUtils.getListFromArr(diceArr), 6, onePoint));
		}
	}
	
	public List<PointNumber> pointList(){
		return this.getDiceNum().pointList();
	}

	
	public List<PointNumber> getSelfDice(Robot robot){
		if(selfDiceInfo == null){
			logger.info("[无双吹牛]---[当前用户:"+robot.getPassportId()+"]---[色子值 为空]");
			return null;
		}
		List<PointNumber> pnList = CountNumUtil.getPointNumber(DiceUtils.getListFromArr(selfDiceInfo.getDiceValues()));
		Collections.sort(pnList, new Comparator<PointNumber>() {
			@Override
			public int compare(PointNumber o1, PointNumber o2) {
				if(o1.getNumber()>o2.getNumber()){
					return -1;
				}else if(o1.getNumber()<o2.getNumber()){
					return 1;
				}else{
					if(o1.getPoint() > o2.getPoint()){
						return -1;
					}else if(o1.getPoint() < o2.getPoint()){
						return 1;
					}else{
						return 0;
					}
				}
			}
		});
		return pnList;
	}
}
