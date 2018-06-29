package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DiceClassicalRoomLog extends BaseLogMessage{
       private int bet;
       private String roomNum;
       private int roomStatus;
       private int totalNum;
       private String everyIds;
    
    public DiceClassicalRoomLog() {    	
    }

    public DiceClassicalRoomLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int bet,			String roomNum,			int roomStatus,			int totalNum,			String everyIds            ) {
        super(MessageType.LOG_DICECLASSICALROOM_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.bet =  bet;
            this.roomNum =  roomNum;
            this.roomStatus =  roomStatus;
            this.totalNum =  totalNum;
            this.everyIds =  everyIds;
    }

       public int getBet() {
	       return bet;
       }
       public String getRoomNum() {
	       return roomNum;
       }
       public int getRoomStatus() {
	       return roomStatus;
       }
       public int getTotalNum() {
	       return totalNum;
       }
       public String getEveryIds() {
	       return everyIds;
       }
        
       public void setBet(int bet) {
	       this.bet = bet;
       }
       public void setRoomNum(String roomNum) {
	       this.roomNum = roomNum;
       }
       public void setRoomStatus(int roomStatus) {
	       this.roomStatus = roomStatus;
       }
       public void setTotalNum(int totalNum) {
	       this.totalNum = totalNum;
       }
       public void setEveryIds(String everyIds) {
	       this.everyIds = everyIds;
       }
    
    @Override
    protected boolean readLogContent() {
	        bet =  readInt();
	        roomNum =  readString();
	        roomStatus =  readInt();
	        totalNum =  readInt();
	        everyIds =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bet);
	        writeString(roomNum);
	        writeInt(roomStatus);
	        writeInt(totalNum);
	        writeString(everyIds);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DICECLASSICALROOM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DICECLASSICALROOM_RECORD";
    }
}