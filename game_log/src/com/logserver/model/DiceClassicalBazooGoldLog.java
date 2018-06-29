package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DiceClassicalBazooGoldLog extends BaseLogMessage{
       private int bet;
       private String roomNum;
       private int goldType;
       private String goldReason;
       private long goldChangeBefore;
       private long goldChange;
       private long goldChangeAfter;
       private String endCount;
    
    public DiceClassicalBazooGoldLog() {    	
    }

    public DiceClassicalBazooGoldLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int bet,			String roomNum,			int goldType,			String goldReason,			long goldChangeBefore,			long goldChange,			long goldChangeAfter,			String endCount            ) {
        super(MessageType.LOG_DICECLASSICALBAZOOGOLD_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.bet =  bet;
            this.roomNum =  roomNum;
            this.goldType =  goldType;
            this.goldReason =  goldReason;
            this.goldChangeBefore =  goldChangeBefore;
            this.goldChange =  goldChange;
            this.goldChangeAfter =  goldChangeAfter;
            this.endCount =  endCount;
    }

       public int getBet() {
	       return bet;
       }
       public String getRoomNum() {
	       return roomNum;
       }
       public int getGoldType() {
	       return goldType;
       }
       public String getGoldReason() {
	       return goldReason;
       }
       public long getGoldChangeBefore() {
	       return goldChangeBefore;
       }
       public long getGoldChange() {
	       return goldChange;
       }
       public long getGoldChangeAfter() {
	       return goldChangeAfter;
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
       public void setGoldType(int goldType) {
	       this.goldType = goldType;
       }
       public void setGoldReason(String goldReason) {
	       this.goldReason = goldReason;
       }
       public void setGoldChangeBefore(long goldChangeBefore) {
	       this.goldChangeBefore = goldChangeBefore;
       }
       public void setGoldChange(long goldChange) {
	       this.goldChange = goldChange;
       }
       public void setGoldChangeAfter(long goldChangeAfter) {
	       this.goldChangeAfter = goldChangeAfter;
       }
       public void setEndCount(String endCount) {
	       this.endCount = endCount;
       }
    
    @Override
    protected boolean readLogContent() {
	        bet =  readInt();
	        roomNum =  readString();
	        goldType =  readInt();
	        goldReason =  readString();
	        goldChangeBefore =  readLong();
	        goldChange =  readLong();
	        goldChangeAfter =  readLong();
	        endCount =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bet);
	        writeString(roomNum);
	        writeInt(goldType);
	        writeString(goldReason);
	        writeLong(goldChangeBefore);
	        writeLong(goldChange);
	        writeLong(goldChangeAfter);
	        writeString(endCount);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DICECLASSICALBAZOOGOLD_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DICECLASSICALBAZOOGOLD_RECORD";
    }
}