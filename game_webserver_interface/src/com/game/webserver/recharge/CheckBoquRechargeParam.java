package com.game.webserver.recharge;

import com.alibaba.fastjson.JSON;
import com.game.webserver.login.AbstractParams;

public class CheckBoquRechargeParam extends AbstractParams<CheckBoquRechargeRes>{
	//博趣订单ID
	private String channelOrderId;
	//咱们的订单ID
	private String orderId;
	//咱们的订单 对应的 产品的ID productId
	private String productId;
	//用户ID
	private String userId;
	
	public CheckBoquRechargeParam(){}
	public CheckBoquRechargeParam(String host, String page, boolean post) {
		super(host, page, post);
	}
	@Override
	protected String content() {
		return JSON.toJSONString(this);
	}

	@Override
	public CheckBoquRechargeRes getRes() {
		if(this.errorCode!=0)
		{
			return null;
		}
		else
		{
			return JSON.parseObject(this.result,CheckBoquRechargeRes.class);
		}
	}
	public String getChannelOrderId() {
		return channelOrderId;
	}
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
