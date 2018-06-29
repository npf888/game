package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class GoldLog extends BaseLogMessage{
       private long goldDelta;
       private long goldLeft;
    
    public GoldLog() {    	
    }

    public GoldLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long goldDelta,			long goldLeft            ) {
        super(MessageType.LOG_GOLD_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.goldDelta =  goldDelta;
            this.goldLeft =  goldLeft;
    }

       public long getGoldDelta() {
	       return goldDelta;
       }
       public long getGoldLeft() {
	       return goldLeft;
       }
        
       public void setGoldDelta(long goldDelta) {
	       this.goldDelta = goldDelta;
       }
       public void setGoldLeft(long goldLeft) {
	       this.goldLeft = goldLeft;
       }
    
    @Override
    protected boolean readLogContent() {
	        goldDelta =  readLong();
	        goldLeft =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(goldDelta);
	        writeLong(goldLeft);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_GOLD_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_GOLD_RECORD";
    }
}