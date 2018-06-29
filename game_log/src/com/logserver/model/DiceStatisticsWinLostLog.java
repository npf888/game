package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DiceStatisticsWinLostLog extends BaseLogMessage{
       private int bet;
       private String roomNum;
       private int isRobot;
       private int modeType;
       private int winOrLost;
    
    public DiceStatisticsWinLostLog() {    	
    }

    public DiceStatisticsWinLostLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int bet,			String roomNum,			int isRobot,			int modeType,			int winOrLost            ) {
        super(MessageType.LOG_DICESTATISTICSWINLOST_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.bet =  bet;
            this.roomNum =  roomNum;
            this.isRobot =  isRobot;
            this.modeType =  modeType;
            this.winOrLost =  winOrLost;
    }

       public int getBet() {
	       return bet;
       }
       public String getRoomNum() {
	       return roomNum;
       }
       public int getIsRobot() {
	       return isRobot;
       }
       public int getModeType() {
	       return modeType;
       }
       public int getWinOrLost() {
	       return winOrLost;
       }
        
       public void setBet(int bet) {
	       this.bet = bet;
       }
       public void setRoomNum(String roomNum) {
	       this.roomNum = roomNum;
       }
       public void setIsRobot(int isRobot) {
	       this.isRobot = isRobot;
       }
       public void setModeType(int modeType) {
	       this.modeType = modeType;
       }
       public void setWinOrLost(int winOrLost) {
	       this.winOrLost = winOrLost;
       }
    
    @Override
    protected boolean readLogContent() {
	        bet =  readInt();
	        roomNum =  readString();
	        isRobot =  readInt();
	        modeType =  readInt();
	        winOrLost =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bet);
	        writeString(roomNum);
	        writeInt(isRobot);
	        writeInt(modeType);
	        writeInt(winOrLost);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DICESTATISTICSWINLOST_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DICESTATISTICSWINLOST_RECORD";
    }
}