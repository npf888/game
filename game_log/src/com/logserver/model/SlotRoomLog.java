package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class SlotRoomLog extends BaseLogMessage{
       private long room_id;
    
    public SlotRoomLog() {    	
    }

    public SlotRoomLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long room_id            ) {
        super(MessageType.LOG_SLOTROOM_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.room_id =  room_id;
    }

       public long getRoom_id() {
	       return room_id;
       }
        
       public void setRoom_id(long room_id) {
	       this.room_id = room_id;
       }
    
    @Override
    protected boolean readLogContent() {
	        room_id =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(room_id);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_SLOTROOM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_SLOTROOM_RECORD";
    }
}