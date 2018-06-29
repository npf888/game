package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class NewRechargeLog extends BaseLogMessage{
       private long money;
       private int timeDian;
       private int channelType;
       private String deviceMode;
       private String clientVersion;
       private String gameOrderId;
       private String countries;
       private int age;
       private String ipCountries;
       private int girl;
    
    public NewRechargeLog() {    	
    }

    public NewRechargeLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long money,			int timeDian,			int channelType,			String deviceMode,			String clientVersion,			String gameOrderId,			String countries,			int age,			String ipCountries,			int girl            ) {
        super(MessageType.LOG_NEWRECHARGE_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.money =  money;
            this.timeDian =  timeDian;
            this.channelType =  channelType;
            this.deviceMode =  deviceMode;
            this.clientVersion =  clientVersion;
            this.gameOrderId =  gameOrderId;
            this.countries =  countries;
            this.age =  age;
            this.ipCountries =  ipCountries;
            this.girl =  girl;
    }

       public long getMoney() {
	       return money;
       }
       public int getTimeDian() {
	       return timeDian;
       }
       public int getChannelType() {
	       return channelType;
       }
       public String getDeviceMode() {
	       return deviceMode;
       }
       public String getClientVersion() {
	       return clientVersion;
       }
       public String getGameOrderId() {
	       return gameOrderId;
       }
       public String getCountries() {
	       return countries;
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
        
       public void setMoney(long money) {
	       this.money = money;
       }
       public void setTimeDian(int timeDian) {
	       this.timeDian = timeDian;
       }
       public void setChannelType(int channelType) {
	       this.channelType = channelType;
       }
       public void setDeviceMode(String deviceMode) {
	       this.deviceMode = deviceMode;
       }
       public void setClientVersion(String clientVersion) {
	       this.clientVersion = clientVersion;
       }
       public void setGameOrderId(String gameOrderId) {
	       this.gameOrderId = gameOrderId;
       }
       public void setCountries(String countries) {
	       this.countries = countries;
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
	        money =  readLong();
	        timeDian =  readInt();
	        channelType =  readInt();
	        deviceMode =  readString();
	        clientVersion =  readString();
	        gameOrderId =  readString();
	        countries =  readString();
	        age =  readInt();
	        ipCountries =  readString();
	        girl =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(money);
	        writeInt(timeDian);
	        writeInt(channelType);
	        writeString(deviceMode);
	        writeString(clientVersion);
	        writeString(gameOrderId);
	        writeString(countries);
	        writeInt(age);
	        writeString(ipCountries);
	        writeInt(girl);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_NEWRECHARGE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_NEWRECHARGE_RECORD";
    }
}