package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class TexasRoomLog extends BaseLogMessage{
       private long roomId;
       private long modeId;
       private long typeId;
    
    public TexasRoomLog() {    	
    }

    public TexasRoomLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long roomId,			long modeId,			long typeId            ) {
        super(MessageType.LOG_TEXASROOM_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.roomId =  roomId;
            this.modeId =  modeId;
            this.typeId =  typeId;
    }

       public long getRoomId() {
	       return roomId;
       }
       public long getModeId() {
	       return modeId;
       }
       public long getTypeId() {
	       return typeId;
       }
        
       public void setRoomId(long roomId) {
	       this.roomId = roomId;
       }
       public void setModeId(long modeId) {
	       this.modeId = modeId;
       }
       public void setTypeId(long typeId) {
	       this.typeId = typeId;
       }
    
    @Override
    protected boolean readLogContent() {
	        roomId =  readLong();
	        modeId =  readLong();
	        typeId =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(roomId);
	        writeLong(modeId);
	        writeLong(typeId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_TEXASROOM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_TEXASROOM_RECORD";
    }
}