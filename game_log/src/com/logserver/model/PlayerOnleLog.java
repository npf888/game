package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class PlayerOnleLog extends BaseLogMessage{
       private int timeDian;
       private int valueRole;
       private String countries;
       private String ipCountries;
    
    public PlayerOnleLog() {    	
    }

    public PlayerOnleLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int timeDian,			int valueRole,			String countries,			String ipCountries            ) {
        super(MessageType.LOG_PLAYERONLE_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.timeDian =  timeDian;
            this.valueRole =  valueRole;
            this.countries =  countries;
            this.ipCountries =  ipCountries;
    }

       public int getTimeDian() {
	       return timeDian;
       }
       public int getValueRole() {
	       return valueRole;
       }
       public String getCountries() {
	       return countries;
       }
       public String getIpCountries() {
	       return ipCountries;
       }
        
       public void setTimeDian(int timeDian) {
	       this.timeDian = timeDian;
       }
       public void setValueRole(int valueRole) {
	       this.valueRole = valueRole;
       }
       public void setCountries(String countries) {
	       this.countries = countries;
       }
       public void setIpCountries(String ipCountries) {
	       this.ipCountries = ipCountries;
       }
    
    @Override
    protected boolean readLogContent() {
	        timeDian =  readInt();
	        valueRole =  readInt();
	        countries =  readString();
	        ipCountries =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(timeDian);
	        writeInt(valueRole);
	        writeString(countries);
	        writeString(ipCountries);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_PLAYERONLE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_PLAYERONLE_RECORD";
    }
}