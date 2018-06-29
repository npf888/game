package com.gameserver.recharge.async;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.game.webserver.login.AbstractParams;

/**
 * 订单验证
 * @author 郭君伟
 *
 */
public class OrderValidationParam extends AbstractParams<OrderValidationRes> {

	
	private long userId;
	
	private List<Long> orderList;
	
	public OrderValidationParam(){}
	public OrderValidationParam(String host, String page, boolean post) {
		super(host, page, post);
	}

	public List<Long> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Long> orderList) {
		this.orderList = orderList;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Override
	protected String content() {
		return JSON.toJSONString(this);
	}

	@Override
	public OrderValidationRes getRes() {
		if(this.errorCode!=0){
			return null;
		}else{
			return JSON.parseObject(this.result,OrderValidationRes.class);
		}
	}

}
