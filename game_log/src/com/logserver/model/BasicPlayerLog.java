package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class BasicPlayerLog extends BaseLogMessage{
       private String ip;
       private int exp;
       private int minute;
       private int totalMinute;
    
    public BasicPlayerLog() {    	
    }

    public BasicPlayerLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			String ip,			int exp,			int minute,			int totalMinute            ) {
        super(MessageType.LOG_BASICPLAYER_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.ip =  ip;
            this.exp =  exp;
            this.minute =  minute;
            this.totalMinute =  totalMinute;
    }

       public String getIp() {
	       return ip;
       }
       public int getExp() {
	       return exp;
       }
       public int getMinute() {
	       return minute;
       }
       public int getTotalMinute() {
	       return totalMinute;
       }
        
       public void setIp(String ip) {
	       this.ip = ip;
       }
       public void setExp(int exp) {
	       this.exp = exp;
       }
       public void setMinute(int minute) {
	       this.minute = minute;
       }
       public void setTotalMinute(int totalMinute) {
	       this.totalMinute = totalMinute;
       }
    
    @Override
    protected boolean readLogContent() {
	        ip =  readString();
	        exp =  readInt();
	        minute =  readInt();
	        totalMinute =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(ip);
	        writeInt(exp);
	        writeInt(minute);
	        writeInt(totalMinute);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_BASICPLAYER_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_BASICPLAYER_RECORD";
    }
}