package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DiceCowLog extends BaseLogMessage{
       private int bet;
       private String roomNum;
       private String diceValues;
       private String diceName;
    
    public DiceCowLog() {    	
    }

    public DiceCowLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int bet,			String roomNum,			String diceValues,			String diceName            ) {
        super(MessageType.LOG_DICECOW_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.bet =  bet;
            this.roomNum =  roomNum;
            this.diceValues =  diceValues;
            this.diceName =  diceName;
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
       public String getDiceName() {
	       return diceName;
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
       public void setDiceName(String diceName) {
	       this.diceName = diceName;
       }
    
    @Override
    protected boolean readLogContent() {
	        bet =  readInt();
	        roomNum =  readString();
	        diceValues =  readString();
	        diceName =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bet);
	        writeString(roomNum);
	        writeString(diceValues);
	        writeString(diceName);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DICECOW_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DICECOW_RECORD";
    }
}