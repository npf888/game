package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 验证订单
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCValidateOrder extends GCMessage{
	
	/** 结果 */
	private int result;
	/** 订单号 */
	private long orderId;
	/** 产品 */
	private long pId;
	/** 道具奖励 */
	private com.gameserver.common.data.RandRewardData[] randRewardList;

	public GCValidateOrder (){
	}
	
	public GCValidateOrder (
			int result,
			long orderId,
			long pId,
			com.gameserver.common.data.RandRewardData[] randRewardList ){
			this.result = result;
			this.orderId = orderId;
			this.pId = pId;
			this.randRewardList = randRewardList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		result = readInteger();
		orderId = readLong();
		pId = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		randRewardList = new com.gameserver.common.data.RandRewardData[count];
		for(int i=0; i<count; i++){
			com.gameserver.common.data.RandRewardData obj = new com.gameserver.common.data.RandRewardData();
			obj.setRewardId(readInteger());
			obj.setRewardCount(readInteger());
			randRewardList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
		writeLong(orderId);
		writeLong(pId);
		writeShort(randRewardList.length);
		for(int i=0; i<randRewardList.length; i++){
			writeInteger(randRewardList[i].getRewardId());
			writeInteger(randRewardList[i].getRewardCount());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_VALIDATE_ORDER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_VALIDATE_ORDER";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}

	public long getOrderId(){
		return orderId;
	}
		
	public void setOrderId(long orderId){
		this.orderId = orderId;
	}

	public long getPId(){
		return pId;
	}
		
	public void setPId(long pId){
		this.pId = pId;
	}

	public com.gameserver.common.data.RandRewardData[] getRandRewardList(){
		return randRewardList;
	}

	public void setRandRewardList(com.gameserver.common.data.RandRewardData[] randRewardList){
		this.randRewardList = randRewardList;
	}	
}