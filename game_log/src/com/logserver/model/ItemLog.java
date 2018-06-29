package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class ItemLog extends BaseLogMessage{
       private int templateId;
       private int delta;
       private int resultCount;
    
    public ItemLog() {    	
    }

    public ItemLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			int templateId,			int delta,			int resultCount            ) {
        super(MessageType.LOG_ITEM_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.templateId =  templateId;
            this.delta =  delta;
            this.resultCount =  resultCount;
    }

       public int getTemplateId() {
	       return templateId;
       }
       public int getDelta() {
	       return delta;
       }
       public int getResultCount() {
	       return resultCount;
       }
        
       public void setTemplateId(int templateId) {
	       this.templateId = templateId;
       }
       public void setDelta(int delta) {
	       this.delta = delta;
       }
       public void setResultCount(int resultCount) {
	       this.resultCount = resultCount;
       }
    
    @Override
    protected boolean readLogContent() {
	        templateId =  readInt();
	        delta =  readInt();
	        resultCount =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(templateId);
	        writeInt(delta);
	        writeInt(resultCount);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_ITEM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_ITEM_RECORD";
    }
}