package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class OnlineTimeRewardLog extends BaseLogMessage{
       private int reward_id;
    
    public OnlineTimeRewardLog() {    	
    }

    public OnlineTimeRewardLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int reward_id            ) {
        super(MessageType.LOG_ONLINETIMEREWARD_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.reward_id =  reward_id;
    }

       public int getReward_id() {
	       return reward_id;
       }
        
       public void setReward_id(int reward_id) {
	       this.reward_id = reward_id;
       }
    
    @Override
    protected boolean readLogContent() {
	        reward_id =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(reward_id);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_ONLINETIMEREWARD_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_ONLINETIMEREWARD_RECORD";
    }
}