package com.logserver.model;
import com.logserver.MessageType;
import com.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class RechargeLog extends BaseLogMessage{
       private long orderId;
       private int productId;
       private int cost;
    
    public RechargeLog() {    	
    }

    public RechargeLog(
                   long logTime,                   int regionId,                   int serverId,                   long accountId,                   String accountName,                   long level,                   int reason,                   String param,			long orderId,			int productId,			int cost            ) {
        super(MessageType.LOG_RECHARGE_RECORD,logTime,regionId,serverId,accountId,accountName,level,reason,param);
            this.orderId =  orderId;
            this.productId =  productId;
            this.cost =  cost;
    }

       public long getOrderId() {
	       return orderId;
       }
       public int getProductId() {
	       return productId;
       }
       public int getCost() {
	       return cost;
       }
        
       public void setOrderId(long orderId) {
	       this.orderId = orderId;
       }
       public void setProductId(int productId) {
	       this.productId = productId;
       }
       public void setCost(int cost) {
	       this.cost = cost;
       }
    
    @Override
    protected boolean readLogContent() {
	        orderId =  readLong();
	        productId =  readInt();
	        cost =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(orderId);
	        writeInt(productId);
	        writeInt(cost);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_RECHARGE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_RECHARGE_RECORD";
    }
}