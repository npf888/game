package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class StatisticsTimeLog extends BaseLogMessage{
       private long userId;
       private long slotType;
       private long slotListPosition;
       private long bet;
       private long slotStartBeforeTime;
       private long slotStartBeforeAfter;
       private long achievementTournamentStartEnd;
       private long humanJackpotBroadcastNoticeStartEnd;
       private long activityBeforeEnd;
       private long taskCollectBeforeEnd;
       private long treasuryStartEnd;
       private long HuoYueForLaohujiWinEnd;
       private long WorldBossNewBeforeEnd;
       private long totalTime;
    
    public StatisticsTimeLog() {    	
    }

    public StatisticsTimeLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long userId,			long slotType,			long slotListPosition,			long bet,			long slotStartBeforeTime,			long slotStartBeforeAfter,			long achievementTournamentStartEnd,			long humanJackpotBroadcastNoticeStartEnd,			long activityBeforeEnd,			long taskCollectBeforeEnd,			long treasuryStartEnd,			long HuoYueForLaohujiWinEnd,			long WorldBossNewBeforeEnd,			long totalTime            ) {
        super(MessageType.LOG_STATISTICSTIME_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.userId =  userId;
            this.slotType =  slotType;
            this.slotListPosition =  slotListPosition;
            this.bet =  bet;
            this.slotStartBeforeTime =  slotStartBeforeTime;
            this.slotStartBeforeAfter =  slotStartBeforeAfter;
            this.achievementTournamentStartEnd =  achievementTournamentStartEnd;
            this.humanJackpotBroadcastNoticeStartEnd =  humanJackpotBroadcastNoticeStartEnd;
            this.activityBeforeEnd =  activityBeforeEnd;
            this.taskCollectBeforeEnd =  taskCollectBeforeEnd;
            this.treasuryStartEnd =  treasuryStartEnd;
            this.HuoYueForLaohujiWinEnd =  HuoYueForLaohujiWinEnd;
            this.WorldBossNewBeforeEnd =  WorldBossNewBeforeEnd;
            this.totalTime =  totalTime;
    }

       public long getUserId() {
	       return userId;
       }
       public long getSlotType() {
	       return slotType;
       }
       public long getSlotListPosition() {
	       return slotListPosition;
       }
       public long getBet() {
	       return bet;
       }
       public long getSlotStartBeforeTime() {
	       return slotStartBeforeTime;
       }
       public long getSlotStartBeforeAfter() {
	       return slotStartBeforeAfter;
       }
       public long getAchievementTournamentStartEnd() {
	       return achievementTournamentStartEnd;
       }
       public long getHumanJackpotBroadcastNoticeStartEnd() {
	       return humanJackpotBroadcastNoticeStartEnd;
       }
       public long getActivityBeforeEnd() {
	       return activityBeforeEnd;
       }
       public long getTaskCollectBeforeEnd() {
	       return taskCollectBeforeEnd;
       }
       public long getTreasuryStartEnd() {
	       return treasuryStartEnd;
       }
       public long getHuoYueForLaohujiWinEnd() {
	       return HuoYueForLaohujiWinEnd;
       }
       public long getWorldBossNewBeforeEnd() {
	       return WorldBossNewBeforeEnd;
       }
       public long getTotalTime() {
	       return totalTime;
       }
        
       public void setUserId(long userId) {
	       this.userId = userId;
       }
       public void setSlotType(long slotType) {
	       this.slotType = slotType;
       }
       public void setSlotListPosition(long slotListPosition) {
	       this.slotListPosition = slotListPosition;
       }
       public void setBet(long bet) {
	       this.bet = bet;
       }
       public void setSlotStartBeforeTime(long slotStartBeforeTime) {
	       this.slotStartBeforeTime = slotStartBeforeTime;
       }
       public void setSlotStartBeforeAfter(long slotStartBeforeAfter) {
	       this.slotStartBeforeAfter = slotStartBeforeAfter;
       }
       public void setAchievementTournamentStartEnd(long achievementTournamentStartEnd) {
	       this.achievementTournamentStartEnd = achievementTournamentStartEnd;
       }
       public void setHumanJackpotBroadcastNoticeStartEnd(long humanJackpotBroadcastNoticeStartEnd) {
	       this.humanJackpotBroadcastNoticeStartEnd = humanJackpotBroadcastNoticeStartEnd;
       }
       public void setActivityBeforeEnd(long activityBeforeEnd) {
	       this.activityBeforeEnd = activityBeforeEnd;
       }
       public void setTaskCollectBeforeEnd(long taskCollectBeforeEnd) {
	       this.taskCollectBeforeEnd = taskCollectBeforeEnd;
       }
       public void setTreasuryStartEnd(long treasuryStartEnd) {
	       this.treasuryStartEnd = treasuryStartEnd;
       }
       public void setHuoYueForLaohujiWinEnd(long HuoYueForLaohujiWinEnd) {
	       this.HuoYueForLaohujiWinEnd = HuoYueForLaohujiWinEnd;
       }
       public void setWorldBossNewBeforeEnd(long WorldBossNewBeforeEnd) {
	       this.WorldBossNewBeforeEnd = WorldBossNewBeforeEnd;
       }
       public void setTotalTime(long totalTime) {
	       this.totalTime = totalTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        userId =  readLong();
	        slotType =  readLong();
	        slotListPosition =  readLong();
	        bet =  readLong();
	        slotStartBeforeTime =  readLong();
	        slotStartBeforeAfter =  readLong();
	        achievementTournamentStartEnd =  readLong();
	        humanJackpotBroadcastNoticeStartEnd =  readLong();
	        activityBeforeEnd =  readLong();
	        taskCollectBeforeEnd =  readLong();
	        treasuryStartEnd =  readLong();
	        HuoYueForLaohujiWinEnd =  readLong();
	        WorldBossNewBeforeEnd =  readLong();
	        totalTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(userId);
	        writeLong(slotType);
	        writeLong(slotListPosition);
	        writeLong(bet);
	        writeLong(slotStartBeforeTime);
	        writeLong(slotStartBeforeAfter);
	        writeLong(achievementTournamentStartEnd);
	        writeLong(humanJackpotBroadcastNoticeStartEnd);
	        writeLong(activityBeforeEnd);
	        writeLong(taskCollectBeforeEnd);
	        writeLong(treasuryStartEnd);
	        writeLong(HuoYueForLaohujiWinEnd);
	        writeLong(WorldBossNewBeforeEnd);
	        writeLong(totalTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_STATISTICSTIME_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_STATISTICSTIME_RECORD";
    }
}