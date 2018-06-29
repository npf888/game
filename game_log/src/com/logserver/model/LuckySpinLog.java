package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class LuckySpinLog extends BaseLogMessage{
       private int free;
       private int spinId;
    
    public LuckySpinLog() {    	
    }

    public LuckySpinLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int free,			int spinId            ) {
        super(MessageType.LOG_LUCKYSPIN_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.free =  free;
            this.spinId =  spinId;
    }

       public int getFree() {
	       return free;
       }
       public int getSpinId() {
	       return spinId;
       }
        
       public void setFree(int free) {
	       this.free = free;
       }
       public void setSpinId(int spinId) {
	       this.spinId = spinId;
       }
    
    @Override
    protected boolean readLogContent() {
	        free =  readInt();
	        spinId =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(free);
	        writeInt(spinId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_LUCKYSPIN_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_LUCKYSPIN_RECORD";
    }
}