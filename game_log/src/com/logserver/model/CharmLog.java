package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class CharmLog extends BaseLogMessage{
       private long charmDelta;
       private long charmLeft;
    
    public CharmLog() {    	
    }

    public CharmLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long charmDelta,			long charmLeft            ) {
        super(MessageType.LOG_CHARM_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.charmDelta =  charmDelta;
            this.charmLeft =  charmLeft;
    }

       public long getCharmDelta() {
	       return charmDelta;
       }
       public long getCharmLeft() {
	       return charmLeft;
       }
        
       public void setCharmDelta(long charmDelta) {
	       this.charmDelta = charmDelta;
       }
       public void setCharmLeft(long charmLeft) {
	       this.charmLeft = charmLeft;
       }
    
    @Override
    protected boolean readLogContent() {
	        charmDelta =  readLong();
	        charmLeft =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(charmDelta);
	        writeLong(charmLeft);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_CHARM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_CHARM_RECORD";
    }
}