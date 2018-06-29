package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class InOutTimeLog extends BaseLogMessage{
       private long inTime;
       private int slotType;
       private long outTime;
       private long inOutTotalTime;
       private long charId;
       private long slotId;
    
    public InOutTimeLog() {    	
    }

    public InOutTimeLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long inTime,			int slotType,			long outTime,			long inOutTotalTime,			long charId,			long slotId            ) {
        super(MessageType.LOG_INOUTTIME_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.inTime =  inTime;
            this.slotType =  slotType;
            this.outTime =  outTime;
            this.inOutTotalTime =  inOutTotalTime;
            this.charId =  charId;
            this.slotId =  slotId;
    }

       public long getInTime() {
	       return inTime;
       }
       public int getSlotType() {
	       return slotType;
       }
       public long getOutTime() {
	       return outTime;
       }
       public long getInOutTotalTime() {
	       return inOutTotalTime;
       }
       public long getCharId() {
	       return charId;
       }
       public long getSlotId() {
	       return slotId;
       }
        
       public void setInTime(long inTime) {
	       this.inTime = inTime;
       }
       public void setSlotType(int slotType) {
	       this.slotType = slotType;
       }
       public void setOutTime(long outTime) {
	       this.outTime = outTime;
       }
       public void setInOutTotalTime(long inOutTotalTime) {
	       this.inOutTotalTime = inOutTotalTime;
       }
       public void setCharId(long charId) {
	       this.charId = charId;
       }
       public void setSlotId(long slotId) {
	       this.slotId = slotId;
       }
    
    @Override
    protected boolean readLogContent() {
	        inTime =  readLong();
	        slotType =  readInt();
	        outTime =  readLong();
	        inOutTotalTime =  readLong();
	        charId =  readLong();
	        slotId =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(inTime);
	        writeInt(slotType);
	        writeLong(outTime);
	        writeLong(inOutTotalTime);
	        writeLong(charId);
	        writeLong(slotId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_INOUTTIME_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_INOUTTIME_RECORD";
    }
}