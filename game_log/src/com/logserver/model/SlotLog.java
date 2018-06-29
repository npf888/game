package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class SlotLog extends BaseLogMessage{
       private long slotId;
       private int slotType;
       private int bet;
    
    public SlotLog() {    	
    }

    public SlotLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long slotId,			int slotType,			int bet            ) {
        super(MessageType.LOG_SLOT_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.slotId =  slotId;
            this.slotType =  slotType;
            this.bet =  bet;
    }

       public long getSlotId() {
	       return slotId;
       }
       public int getSlotType() {
	       return slotType;
       }
       public int getBet() {
	       return bet;
       }
        
       public void setSlotId(long slotId) {
	       this.slotId = slotId;
       }
       public void setSlotType(int slotType) {
	       this.slotType = slotType;
       }
       public void setBet(int bet) {
	       this.bet = bet;
       }
    
    @Override
    protected boolean readLogContent() {
	        slotId =  readLong();
	        slotType =  readInt();
	        bet =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(slotId);
	        writeInt(slotType);
	        writeInt(bet);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_SLOT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_SLOT_RECORD";
    }
}