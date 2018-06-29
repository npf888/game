package com.gameserver.recharge.data;

import com.gameserver.recharge.HumanRechargeOrder;

/**
 * 充值
 * @author wayne
 *
 */
public class HumanRechargeOrderInfoData {
	private long orderId;
	private int productId;
	private int orderStatus;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public static HumanRechargeOrderInfoData convertFromHumanRechargeOrder(HumanRechargeOrder order){
		HumanRechargeOrderInfoData humanRechargeOrderInfoData = new HumanRechargeOrderInfoData();
		humanRechargeOrderInfoData.setOrderId(order.getId());
		humanRechargeOrderInfoData.setOrderStatus(order.getOrderStatus().getIndex());
		humanRechargeOrderInfoData.setProductId(order.getProductId());
		return humanRechargeOrderInfoData;
	}
}
