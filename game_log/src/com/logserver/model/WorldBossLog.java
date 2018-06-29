package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class WorldBossLog extends BaseLogMessage{
       private int bossType;
       private String starttime;
       private int beginEnd;
       private int bloodBeginEnd;
       private long curAttackBlood;
       private long bossId;
    
    public WorldBossLog() {    	
    }

    public WorldBossLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int bossType,			String starttime,			int beginEnd,			int bloodBeginEnd,			long curAttackBlood,			long bossId            ) {
        super(MessageType.LOG_WORLDBOSS_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.bossType =  bossType;
            this.starttime =  starttime;
            this.beginEnd =  beginEnd;
            this.bloodBeginEnd =  bloodBeginEnd;
            this.curAttackBlood =  curAttackBlood;
            this.bossId =  bossId;
    }

       public int getBossType() {
	       return bossType;
       }
       public String getStarttime() {
	       return starttime;
       }
       public int getBeginEnd() {
	       return beginEnd;
       }
       public int getBloodBeginEnd() {
	       return bloodBeginEnd;
       }
       public long getCurAttackBlood() {
	       return curAttackBlood;
       }
       public long getBossId() {
	       return bossId;
       }
        
       public void setBossType(int bossType) {
	       this.bossType = bossType;
       }
       public void setStarttime(String starttime) {
	       this.starttime = starttime;
       }
       public void setBeginEnd(int beginEnd) {
	       this.beginEnd = beginEnd;
       }
       public void setBloodBeginEnd(int bloodBeginEnd) {
	       this.bloodBeginEnd = bloodBeginEnd;
       }
       public void setCurAttackBlood(long curAttackBlood) {
	       this.curAttackBlood = curAttackBlood;
       }
       public void setBossId(long bossId) {
	       this.bossId = bossId;
       }
    
    @Override
    protected boolean readLogContent() {
	        bossType =  readInt();
	        starttime =  readString();
	        beginEnd =  readInt();
	        bloodBeginEnd =  readInt();
	        curAttackBlood =  readLong();
	        bossId =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bossType);
	        writeString(starttime);
	        writeInt(beginEnd);
	        writeInt(bloodBeginEnd);
	        writeLong(curAttackBlood);
	        writeLong(bossId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_WORLDBOSS_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_WORLDBOSS_RECORD";
    }
}