package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DiamondLog extends BaseLogMessage{
       private long diamondDelta;
       private long diamondLeft;
    
    public DiamondLog() {    	
    }

    public DiamondLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long diamondDelta,			long diamondLeft            ) {
        super(MessageType.LOG_DIAMOND_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.diamondDelta =  diamondDelta;
            this.diamondLeft =  diamondLeft;
    }

       public long getDiamondDelta() {
	       return diamondDelta;
       }
       public long getDiamondLeft() {
	       return diamondLeft;
       }
        
       public void setDiamondDelta(long diamondDelta) {
	       this.diamondDelta = diamondDelta;
       }
       public void setDiamondLeft(long diamondLeft) {
	       this.diamondLeft = diamondLeft;
       }
    
    @Override
    protected boolean readLogContent() {
	        diamondDelta =  readLong();
	        diamondLeft =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(diamondDelta);
	        writeLong(diamondLeft);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DIAMOND_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DIAMOND_RECORD";
    }
}