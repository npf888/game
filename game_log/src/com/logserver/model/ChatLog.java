package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class ChatLog extends BaseLogMessage{
       private int channel;
       private String recCharName;
       private String content;
    
    public ChatLog() {    	
    }

    public ChatLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int channel,			String recCharName,			String content            ) {
        super(MessageType.LOG_CHAT_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.channel =  channel;
            this.recCharName =  recCharName;
            this.content =  content;
    }

       public int getChannel() {
	       return channel;
       }
       public String getRecCharName() {
	       return recCharName;
       }
       public String getContent() {
	       return content;
       }
        
       public void setChannel(int channel) {
	       this.channel = channel;
       }
       public void setRecCharName(String recCharName) {
	       this.recCharName = recCharName;
       }
       public void setContent(String content) {
	       this.content = content;
       }
    
    @Override
    protected boolean readLogContent() {
	        channel =  readInt();
	        recCharName =  readString();
	        content =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(channel);
	        writeString(recCharName);
	        writeString(content);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_CHAT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_CHAT_RECORD";
    }
}