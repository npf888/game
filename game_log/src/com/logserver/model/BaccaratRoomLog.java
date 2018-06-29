package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class BaccaratRoomLog extends BaseLogMessage{
       private long roomId;
    
    public BaccaratRoomLog() {    	
    }

    public BaccaratRoomLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long roomId            ) {
        super(MessageType.LOG_BACCARATROOM_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.roomId =  roomId;
    }

       public long getRoomId() {
	       return roomId;
       }
        
       public void setRoomId(long roomId) {
	       this.roomId = roomId;
       }
    
    @Override
    protected boolean readLogContent() {
	        roomId =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(roomId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_BACCARATROOM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_BACCARATROOM_RECORD";
    }
}