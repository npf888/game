package com.gameserver.recharge.redisMsg;


import com.gameserver.recharge.handler.RechargeHandlerFactory;
import com.gameserver.redis.IRedisMessage;
/**
 * redis 回调接口
 * @author 郭君伟
 *
 */
public class PaymentRedisMessage implements IRedisMessage{
	
	private long userId;
	private long orderId;
	private int cost;
	
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}


	/**
	 * @return the orderId
	 */
	public long getOrderId() {
		return orderId;
	}


	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}


	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}


	/**
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		RechargeHandlerFactory.getRedisHandler().handlePaymentRedisMessage(this);
	}



}
