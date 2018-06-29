package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class RenameLog extends BaseLogMessage{
       private String beforeName;
       private String afterName;
    
    public RenameLog() {    	
    }

    public RenameLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			String beforeName,			String afterName            ) {
        super(MessageType.LOG_RENAME_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.beforeName =  beforeName;
            this.afterName =  afterName;
    }

       public String getBeforeName() {
	       return beforeName;
       }
       public String getAfterName() {
	       return afterName;
       }
        
       public void setBeforeName(String beforeName) {
	       this.beforeName = beforeName;
       }
       public void setAfterName(String afterName) {
	       this.afterName = afterName;
       }
    
    @Override
    protected boolean readLogContent() {
	        beforeName =  readString();
	        afterName =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(beforeName);
	        writeString(afterName);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_RENAME_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_RENAME_RECORD";
    }
}