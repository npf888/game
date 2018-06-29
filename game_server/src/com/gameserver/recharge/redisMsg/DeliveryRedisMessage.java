package com.gameserver.recharge.redisMsg;

import com.gameserver.recharge.handler.RechargeHandlerFactory;
import com.gameserver.redis.IRedisMessage;
/**
 * GameView 发货
 * @author 郭君伟
 *
 */
public class DeliveryRedisMessage implements IRedisMessage {
	
	private long roleID;
	
	/**实际美元 **/
	private long amount;
	/**订单 **/
	private long extraparam1;
	/**真正发放的元宝数 **/
	private long gold;
	
	public long getRoleID() {
		return roleID;
	}




	public void setRoleID(long roleID) {
		this.roleID = roleID;
	}




	public long getAmount() {
		return amount;
	}




	public void setAmount(long amount) {
		this.amount = amount;
	}




	public long getExtraparam1() {
		return extraparam1;
	}




	public void setExtraparam1(long extraparam1) {
		this.extraparam1 = extraparam1;
	}




	public long getGold() {
		return gold;
	}




	public void setGold(long gold) {
		this.gold = gold;
	}




	@Override
	public void execute() {
		RechargeHandlerFactory.getRedisHandler().handleDeliyerRedisMessage(this);      
	}

}
