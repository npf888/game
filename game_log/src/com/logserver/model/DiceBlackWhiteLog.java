package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DiceBlackWhiteLog extends BaseLogMessage{
       private int bet;
       private String roomNum;
       private String diceValues;
       private int isOut;
       private long totalGold;
    
    public DiceBlackWhiteLog() {    	
    }

    public DiceBlackWhiteLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int bet,			String roomNum,			String diceValues,			int isOut,			long totalGold            ) {
        super(MessageType.LOG_DICEBLACKWHITE_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.bet =  bet;
            this.roomNum =  roomNum;
            this.diceValues =  diceValues;
            this.isOut =  isOut;
            this.totalGold =  totalGold;
    }

       public int getBet() {
	       return bet;
       }
       public String getRoomNum() {
	       return roomNum;
       }
       public String getDiceValues() {
	       return diceValues;
       }
       public int getIsOut() {
	       return isOut;
       }
       public long getTotalGold() {
	       return totalGold;
       }
        
       public void setBet(int bet) {
	       this.bet = bet;
       }
       public void setRoomNum(String roomNum) {
	       this.roomNum = roomNum;
       }
       public void setDiceValues(String diceValues) {
	       this.diceValues = diceValues;
       }
       public void setIsOut(int isOut) {
	       this.isOut = isOut;
       }
       public void setTotalGold(long totalGold) {
	       this.totalGold = totalGold;
       }
    
    @Override
    protected boolean readLogContent() {
	        bet =  readInt();
	        roomNum =  readString();
	        diceValues =  readString();
	        isOut =  readInt();
	        totalGold =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bet);
	        writeString(roomNum);
	        writeString(diceValues);
	        writeInt(isOut);
	        writeLong(totalGold);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DICEBLACKWHITE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DICEBLACKWHITE_RECORD";
    }
}