package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DiceClassicalGuessLog extends BaseLogMessage{
       private int bet;
       private String roomNum;
       private int bigSmall;
       private int isRight;
       private String endCount;
    
    public DiceClassicalGuessLog() {    	
    }

    public DiceClassicalGuessLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int bet,			String roomNum,			int bigSmall,			int isRight,			String endCount            ) {
        super(MessageType.LOG_DICECLASSICALGUESS_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.bet =  bet;
            this.roomNum =  roomNum;
            this.bigSmall =  bigSmall;
            this.isRight =  isRight;
            this.endCount =  endCount;
    }

       public int getBet() {
	       return bet;
       }
       public String getRoomNum() {
	       return roomNum;
       }
       public int getBigSmall() {
	       return bigSmall;
       }
       public int getIsRight() {
	       return isRight;
       }
       public String getEndCount() {
	       return endCount;
       }
        
       public void setBet(int bet) {
	       this.bet = bet;
       }
       public void setRoomNum(String roomNum) {
	       this.roomNum = roomNum;
       }
       public void setBigSmall(int bigSmall) {
	       this.bigSmall = bigSmall;
       }
       public void setIsRight(int isRight) {
	       this.isRight = isRight;
       }
       public void setEndCount(String endCount) {
	       this.endCount = endCount;
       }
    
    @Override
    protected boolean readLogContent() {
	        bet =  readInt();
	        roomNum =  readString();
	        bigSmall =  readInt();
	        isRight =  readInt();
	        endCount =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bet);
	        writeString(roomNum);
	        writeInt(bigSmall);
	        writeInt(isRight);
	        writeString(endCount);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DICECLASSICALGUESS_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DICECLASSICALGUESS_RECORD";
    }
}