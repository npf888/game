package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class TournamentLog extends BaseLogMessage{
       private long tournamentId;
       private int tournamentType;
       private int slotType;
       private long totalReward;
       private long userId;
       private long rewards;
       private long reward;
       private long obtainedReward;
    
    public TournamentLog() {    	
    }

    public TournamentLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long tournamentId,			int tournamentType,			int slotType,			long totalReward,			long userId,			long rewards,			long reward,			long obtainedReward            ) {
        super(MessageType.LOG_TOURNAMENT_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.tournamentId =  tournamentId;
            this.tournamentType =  tournamentType;
            this.slotType =  slotType;
            this.totalReward =  totalReward;
            this.userId =  userId;
            this.rewards =  rewards;
            this.reward =  reward;
            this.obtainedReward =  obtainedReward;
    }

       public long getTournamentId() {
	       return tournamentId;
       }
       public int getTournamentType() {
	       return tournamentType;
       }
       public int getSlotType() {
	       return slotType;
       }
       public long getTotalReward() {
	       return totalReward;
       }
       public long getUserId() {
	       return userId;
       }
       public long getRewards() {
	       return rewards;
       }
       public long getReward() {
	       return reward;
       }
       public long getObtainedReward() {
	       return obtainedReward;
       }
        
       public void setTournamentId(long tournamentId) {
	       this.tournamentId = tournamentId;
       }
       public void setTournamentType(int tournamentType) {
	       this.tournamentType = tournamentType;
       }
       public void setSlotType(int slotType) {
	       this.slotType = slotType;
       }
       public void setTotalReward(long totalReward) {
	       this.totalReward = totalReward;
       }
       public void setUserId(long userId) {
	       this.userId = userId;
       }
       public void setRewards(long rewards) {
	       this.rewards = rewards;
       }
       public void setReward(long reward) {
	       this.reward = reward;
       }
       public void setObtainedReward(long obtainedReward) {
	       this.obtainedReward = obtainedReward;
       }
    
    @Override
    protected boolean readLogContent() {
	        tournamentId =  readLong();
	        tournamentType =  readInt();
	        slotType =  readInt();
	        totalReward =  readLong();
	        userId =  readLong();
	        rewards =  readLong();
	        reward =  readLong();
	        obtainedReward =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(tournamentId);
	        writeInt(tournamentType);
	        writeInt(slotType);
	        writeLong(totalReward);
	        writeLong(userId);
	        writeLong(rewards);
	        writeLong(reward);
	        writeLong(obtainedReward);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_TOURNAMENT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_TOURNAMENT_RECORD";
    }
}