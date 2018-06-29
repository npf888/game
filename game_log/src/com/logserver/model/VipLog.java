package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class VipLog extends BaseLogMessage{
       private int vipLevel;
    
    public VipLog() {    	
    }

    public VipLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int vipLevel            ) {
        super(MessageType.LOG_VIP_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.vipLevel =  vipLevel;
    }

       public int getVipLevel() {
	       return vipLevel;
       }
        
       public void setVipLevel(int vipLevel) {
	       this.vipLevel = vipLevel;
       }
    
    @Override
    protected boolean readLogContent() {
	        vipLevel =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(vipLevel);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_VIP_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_VIP_RECORD";
    }
}