package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class WeekcardLog extends BaseLogMessage{
       private long duration;
    
    public WeekcardLog() {    	
    }

    public WeekcardLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long duration            ) {
        super(MessageType.LOG_WEEKCARD_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.duration =  duration;
    }

       public long getDuration() {
	       return duration;
       }
        
       public void setDuration(long duration) {
	       this.duration = duration;
       }
    
    @Override
    protected boolean readLogContent() {
	        duration =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(duration);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_WEEKCARD_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_WEEKCARD_RECORD";
    }
}