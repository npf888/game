package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class DataOverviewLog extends BaseLogMessage{
       private long value;
       private int age;
       private String ipCountries;
       private int girl;
    
    public DataOverviewLog() {    	
    }

    public DataOverviewLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long value,			int age,			String ipCountries,			int girl            ) {
        super(MessageType.LOG_DATAOVERVIEW_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.value =  value;
            this.age =  age;
            this.ipCountries =  ipCountries;
            this.girl =  girl;
    }

       public long getValue() {
	       return value;
       }
       public int getAge() {
	       return age;
       }
       public String getIpCountries() {
	       return ipCountries;
       }
       public int getGirl() {
	       return girl;
       }
        
       public void setValue(long value) {
	       this.value = value;
       }
       public void setAge(int age) {
	       this.age = age;
       }
       public void setIpCountries(String ipCountries) {
	       this.ipCountries = ipCountries;
       }
       public void setGirl(int girl) {
	       this.girl = girl;
       }
    
    @Override
    protected boolean readLogContent() {
	        value =  readLong();
	        age =  readInt();
	        ipCountries =  readString();
	        girl =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(value);
	        writeInt(age);
	        writeString(ipCountries);
	        writeInt(girl);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_DATAOVERVIEW_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_DATAOVERVIEW_RECORD";
    }
}