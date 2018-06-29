package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DailyTaskLog extends BaseLogMessage{
       private int tId;
       private int finished;
       private int getNums;
    
    public DailyTaskLog() {    	
    }

    public DailyTaskLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int tId,			int finished,			int getNums            ) {
        super(MessageType.LOG_DAILYTASK_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.tId =  tId;
            this.finished =  finished;
            this.getNums =  getNums;
    }

       public int getTId() {
	       return tId;
       }
       public int getFinished() {
	       return finished;
       }
       public int getGetNums() {
	       return getNums;
       }
        
       public void setTId(int tId) {
	       this.tId = tId;
       }
       public void setFinished(int finished) {
	       this.finished = finished;
       }
       public void setGetNums(int getNums) {
	       this.getNums = getNums;
       }
    
    @Override
    protected boolean readLogContent() {
	        tId =  readInt();
	        finished =  readInt();
	        getNums =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(tId);
	        writeInt(finished);
	        writeInt(getNums);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DAILYTASK_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DAILYTASK_RECORD";
    }
}