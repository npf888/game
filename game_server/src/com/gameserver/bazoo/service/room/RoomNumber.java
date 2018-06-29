package com.gameserver.bazoo.service.room;

import org.apache.commons.lang.StringUtils;

/**
 * 房间编号
 * @author JavaServer
 *
 */
public class RoomNumber {

	public static final int PUB_ROOM=0;
	public static final int PRI_ROOM=1;
	
	public static final int MODE_TYPE_CLASSICAL=1;
	public static final int MODE_TYPE_COW=2;
	public static final int MODE_TYPE_SHOWHAND=3;
	public static final int MODE_TYPE_BLACK_WHITE=4;
	public static final int MODE_TYPE_SINGLEFIGHT=5;
	
	private int modeType;
	private int pubOrPri;//是公共场还是私人场 0:公共场，1:是 私人场
	private int bet;
	private int roomNum;
	
	
	public RoomNumber(){
		modeType=0;
		bet=0;
	}
	
	public String toString(){
		return this.getModeType()+"_"+this.getPubOrPri()+"_"+this.getBet()+"_"+this.getRoomNum();
	}
	public String toStringForNumber(){
		return this.getModeType()+"_"+this.getPubOrPri()+"_"+this.getBet();
	}
	
	public static RoomNumber toRoomNumber(String roomNumber){
		if(StringUtils.isBlank(roomNumber)){
			RoomNumber rn = new RoomNumber();
			rn.setModeType(0);
			rn.setPubOrPri(0);
			rn.setBet(0);
			rn.setRoomNum(0);
			return rn;
		}
		String[] modeType_bet_roomNum = roomNumber.split("_");
		if(modeType_bet_roomNum.length == 1){
			int modeType = Integer.valueOf(modeType_bet_roomNum[0]);
			RoomNumber RoomNumber = new RoomNumber();
			RoomNumber.setModeType(modeType);
			return RoomNumber;
		}else if(modeType_bet_roomNum.length == 2){
			int modeType = Integer.valueOf(modeType_bet_roomNum[0]);
			int pubOrPri = Integer.valueOf(modeType_bet_roomNum[1]);
			RoomNumber RoomNumber = new RoomNumber();
			RoomNumber.setModeType(modeType);
			RoomNumber.setPubOrPri(pubOrPri);
			return RoomNumber;
		}else if(modeType_bet_roomNum.length == 3){
			int modeType = Integer.valueOf(modeType_bet_roomNum[0]);
			int pubOrPri = Integer.valueOf(modeType_bet_roomNum[1]);
			int bet = Integer.valueOf(modeType_bet_roomNum[2]);
			RoomNumber RoomNumber = new RoomNumber();
			RoomNumber.setModeType(modeType);
			RoomNumber.setPubOrPri(pubOrPri);
			RoomNumber.setBet(bet);
			return RoomNumber;
		}else if(modeType_bet_roomNum.length == 4){
			int modeType = Integer.valueOf(modeType_bet_roomNum[0]);
			int pubOrPri = Integer.valueOf(modeType_bet_roomNum[1]);
			int bet = Integer.valueOf(modeType_bet_roomNum[2]);
			int rNum = Integer.valueOf(modeType_bet_roomNum[3]);
			RoomNumber RoomNumber = new RoomNumber();
			RoomNumber.setModeType(modeType);
			RoomNumber.setPubOrPri(pubOrPri);
			RoomNumber.setBet(bet);
			RoomNumber.setRoomNum(rNum);
			return RoomNumber;
		}else{
			RoomNumber RoomNumber = new RoomNumber(); 
			return RoomNumber;
		}
		
	}
	
	
	public String getModeName(){
		if(this.modeType == RoomNumber.MODE_TYPE_CLASSICAL){
			return "经典模式";
		}else if(this.modeType == RoomNumber.MODE_TYPE_COW){
			return "牛牛模式";
		}else if(this.modeType == RoomNumber.MODE_TYPE_SHOWHAND){
			return "梭哈模式";
		}else if(this.modeType == RoomNumber.MODE_TYPE_BLACK_WHITE){
			return "红黑单双模式";
		}else if(this.modeType == RoomNumber.MODE_TYPE_SINGLEFIGHT){
			return "单挑模式";
		}else{
			return "错误模式";
		}
	}
	
	
	public int getModeType() {
		return modeType;
	}

	public void setModeType(int modeType) {
		this.modeType = modeType;
	}

	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getPubOrPri() {
		return pubOrPri;
	}

	public void setPubOrPri(int pubOrPri) {
		this.pubOrPri = pubOrPri;
	}
	
	
	
}
