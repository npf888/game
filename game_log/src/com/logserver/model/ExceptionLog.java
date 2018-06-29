package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class ExceptionLog extends BaseLogMessage{
       private String shortMessage;
       private String fullMessage;
       private String className;
       private String methodName;
    
    public ExceptionLog() {    	
    }

    public ExceptionLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			String shortMessage,			String fullMessage,			String className,			String methodName            ) {
        super(MessageType.LOG_EXCEPTION_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.shortMessage =  shortMessage;
            this.fullMessage =  fullMessage;
            this.className =  className;
            this.methodName =  methodName;
    }

       public String getShortMessage() {
	       return shortMessage;
       }
       public String getFullMessage() {
	       return fullMessage;
       }
       public String getClassName() {
	       return className;
       }
       public String getMethodName() {
	       return methodName;
       }
        
       public void setShortMessage(String shortMessage) {
	       this.shortMessage = shortMessage;
       }
       public void setFullMessage(String fullMessage) {
	       this.fullMessage = fullMessage;
       }
       public void setClassName(String className) {
	       this.className = className;
       }
       public void setMethodName(String methodName) {
	       this.methodName = methodName;
       }
    
    @Override
    protected boolean readLogContent() {
	        shortMessage =  readString();
	        fullMessage =  readString();
	        className =  readString();
	        methodName =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(shortMessage);
	        writeString(fullMessage);
	        writeString(className);
	        writeString(methodName);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_EXCEPTION_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_EXCEPTION_RECORD";
    }
}