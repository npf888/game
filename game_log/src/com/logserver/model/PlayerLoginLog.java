package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class PlayerLoginLog extends BaseLogMessage{
       private int channelType;
       private String deviceMode;
       private String clientVersion;
       private String countries;
       private String ipCountries;
    
    public PlayerLoginLog() {    	
    }

    public PlayerLoginLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int channelType,			String deviceMode,			String clientVersion,			String countries,			String ipCountries            ) {
        super(MessageType.LOG_PLAYERLOGIN_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.channelType =  channelType;
            this.deviceMode =  deviceMode;
            this.clientVersion =  clientVersion;
            this.countries =  countries;
            this.ipCountries =  ipCountries;
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
       public String getCountries() {
	       return countries;
       }
       public String getIpCountries() {
	       return ipCountries;
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
       public void setCountries(String countries) {
	       this.countries = countries;
       }
       public void setIpCountries(String ipCountries) {
	       this.ipCountries = ipCountries;
       }
    
    @Override
    protected boolean readLogContent() {
	        channelType =  readInt();
	        deviceMode =  readString();
	        clientVersion =  readString();
	        countries =  readString();
	        ipCountries =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(channelType);
	        writeString(deviceMode);
	        writeString(clientVersion);
	        writeString(countries);
	        writeString(ipCountries);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_PLAYERLOGIN_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_PLAYERLOGIN_RECORD";
    }
}