package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DiceClassicalCallNumLog extends BaseLogMessage{
       private int bet;
       private String roomNum;
       private String numValue;
       private String endCount;
    
    public DiceClassicalCallNumLog() {    	
    }

    public DiceClassicalCallNumLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int bet,			String roomNum,			String numValue,			String endCount            ) {
        super(MessageType.LOG_DICECLASSICALCALLNUM_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.bet =  bet;
            this.roomNum =  roomNum;
            this.numValue =  numValue;
            this.endCount =  endCount;
    }

       public int getBet() {
	       return bet;
       }
       public String getRoomNum() {
	       return roomNum;
       }
       public String getNumValue() {
	       return numValue;
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
       public void setNumValue(String numValue) {
	       this.numValue = numValue;
       }
       public void setEndCount(String endCount) {
	       this.endCount = endCount;
       }
    
    @Override
    protected boolean readLogContent() {
	        bet =  readInt();
	        roomNum =  readString();
	        numValue =  readString();
	        endCount =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bet);
	        writeString(roomNum);
	        writeString(numValue);
	        writeString(endCount);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DICECLASSICALCALLNUM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DICECLASSICALCALLNUM_RECORD";
    }
}