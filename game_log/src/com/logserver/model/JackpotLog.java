package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class JackpotLog extends BaseLogMessage{
       private String slotName;
       private int slotType;
       private int slotId;
       private int bet;
       private int jackType;
       private String jackpotNum;
       private String cumuJackpotNum;
       private long jackpot;
       private long cumuJackpot;
       private long afterChangeJackpot;
       private long afterChangeCumuJackpot;
       private long rewardPer;
    
    public JackpotLog() {    	
    }

    public JackpotLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			String slotName,			int slotType,			int slotId,			int bet,			int jackType,			String jackpotNum,			String cumuJackpotNum,			long jackpot,			long cumuJackpot,			long afterChangeJackpot,			long afterChangeCumuJackpot,			long rewardPer            ) {
        super(MessageType.LOG_JACKPOT_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.slotName =  slotName;
            this.slotType =  slotType;
            this.slotId =  slotId;
            this.bet =  bet;
            this.jackType =  jackType;
            this.jackpotNum =  jackpotNum;
            this.cumuJackpotNum =  cumuJackpotNum;
            this.jackpot =  jackpot;
            this.cumuJackpot =  cumuJackpot;
            this.afterChangeJackpot =  afterChangeJackpot;
            this.afterChangeCumuJackpot =  afterChangeCumuJackpot;
            this.rewardPer =  rewardPer;
    }

       public String getSlotName() {
	       return slotName;
       }
       public int getSlotType() {
	       return slotType;
       }
       public int getSlotId() {
	       return slotId;
       }
       public int getBet() {
	       return bet;
       }
       public int getJackType() {
	       return jackType;
       }
       public String getJackpotNum() {
	       return jackpotNum;
       }
       public String getCumuJackpotNum() {
	       return cumuJackpotNum;
       }
       public long getJackpot() {
	       return jackpot;
       }
       public long getCumuJackpot() {
	       return cumuJackpot;
       }
       public long getAfterChangeJackpot() {
	       return afterChangeJackpot;
       }
       public long getAfterChangeCumuJackpot() {
	       return afterChangeCumuJackpot;
       }
       public long getRewardPer() {
	       return rewardPer;
       }
        
       public void setSlotName(String slotName) {
	       this.slotName = slotName;
       }
       public void setSlotType(int slotType) {
	       this.slotType = slotType;
       }
       public void setSlotId(int slotId) {
	       this.slotId = slotId;
       }
       public void setBet(int bet) {
	       this.bet = bet;
       }
       public void setJackType(int jackType) {
	       this.jackType = jackType;
       }
       public void setJackpotNum(String jackpotNum) {
	       this.jackpotNum = jackpotNum;
       }
       public void setCumuJackpotNum(String cumuJackpotNum) {
	       this.cumuJackpotNum = cumuJackpotNum;
       }
       public void setJackpot(long jackpot) {
	       this.jackpot = jackpot;
       }
       public void setCumuJackpot(long cumuJackpot) {
	       this.cumuJackpot = cumuJackpot;
       }
       public void setAfterChangeJackpot(long afterChangeJackpot) {
	       this.afterChangeJackpot = afterChangeJackpot;
       }
       public void setAfterChangeCumuJackpot(long afterChangeCumuJackpot) {
	       this.afterChangeCumuJackpot = afterChangeCumuJackpot;
       }
       public void setRewardPer(long rewardPer) {
	       this.rewardPer = rewardPer;
       }
    
    @Override
    protected boolean readLogContent() {
	        slotName =  readString();
	        slotType =  readInt();
	        slotId =  readInt();
	        bet =  readInt();
	        jackType =  readInt();
	        jackpotNum =  readString();
	        cumuJackpotNum =  readString();
	        jackpot =  readLong();
	        cumuJackpot =  readLong();
	        afterChangeJackpot =  readLong();
	        afterChangeCumuJackpot =  readLong();
	        rewardPer =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(slotName);
	        writeInt(slotType);
	        writeInt(slotId);
	        writeInt(bet);
	        writeInt(jackType);
	        writeString(jackpotNum);
	        writeString(cumuJackpotNum);
	        writeLong(jackpot);
	        writeLong(cumuJackpot);
	        writeLong(afterChangeJackpot);
	        writeLong(afterChangeCumuJackpot);
	        writeLong(rewardPer);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_JACKPOT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_JACKPOT_RECORD";
    }
}