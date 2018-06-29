package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class FriendLog extends BaseLogMessage{
       private long friendId;
    
    public FriendLog() {    	
    }

    public FriendLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long friendId            ) {
        super(MessageType.LOG_FRIEND_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.friendId =  friendId;
    }

       public long getFriendId() {
	       return friendId;
       }
        
       public void setFriendId(long friendId) {
	       this.friendId = friendId;
       }
    
    @Override
    protected boolean readLogContent() {
	        friendId =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(friendId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_FRIEND_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_FRIEND_RECORD";
    }
}