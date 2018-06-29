package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class PlayerKeepLog extends BaseLogMessage{
       private int loginDay;
       private String countries;
       private String ipCountries;
       private int channelType;
       private String deviceMode;
       private String clientVersion;
    
    public PlayerKeepLog() {    	
    }

    public PlayerKeepLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int loginDay,			String countries,			String ipCountries,			int channelType,			String deviceMode,			String clientVersion            ) {
        super(MessageType.LOG_PLAYERKEEP_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.loginDay =  loginDay;
            this.countries =  countries;
            this.ipCountries =  ipCountries;
            this.channelType =  channelType;
            this.deviceMode =  deviceMode;
            this.clientVersion =  clientVersion;
    }

       public int getLoginDay() {
	       return loginDay;
       }
       public String getCountries() {
	       return countries;
       }
       public String getIpCountries() {
	       return ipCountries;
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
        
       public void setLoginDay(int loginDay) {
	       this.loginDay = loginDay;
       }
       public void setCountries(String countries) {
	       this.countries = countries;
       }
       public void setIpCountries(String ipCountries) {
	       this.ipCountries = ipCountries;
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
    
    @Override
    protected boolean readLogContent() {
	        loginDay =  readInt();
	        countries =  readString();
	        ipCountries =  readString();
	        channelType =  readInt();
	        deviceMode =  readString();
	        clientVersion =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(loginDay);
	        writeString(countries);
	        writeString(ipCountries);
	        writeInt(channelType);
	        writeString(deviceMode);
	        writeString(clientVersion);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_PLAYERKEEP_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_PLAYERKEEP_RECORD";
    }
}