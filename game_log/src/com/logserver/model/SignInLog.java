package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class SignInLog extends BaseLogMessage{
       private int days;
       private int culumative;
    
    public SignInLog() {    	
    }

    public SignInLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int days,			int culumative            ) {
        super(MessageType.LOG_SIGNIN_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.days =  days;
            this.culumative =  culumative;
    }

       public int getDays() {
	       return days;
       }
       public int getCulumative() {
	       return culumative;
       }
        
       public void setDays(int days) {
	       this.days = days;
       }
       public void setCulumative(int culumative) {
	       this.culumative = culumative;
       }
    
    @Override
    protected boolean readLogContent() {
	        days =  readInt();
	        culumative =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(days);
	        writeInt(culumative);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_SIGNIN_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_SIGNIN_RECORD";
    }
}