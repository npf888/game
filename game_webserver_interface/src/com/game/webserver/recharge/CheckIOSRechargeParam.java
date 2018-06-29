package com.game.webserver.recharge;

import com.alibaba.fastjson.JSON;
import com.game.webserver.login.AbstractParams;

public class CheckIOSRechargeParam extends AbstractParams<CheckIOSRechargeRes>{

	private long orderId;
	private String receiptData;
	private boolean sandbox;
	
	public CheckIOSRechargeParam(){}
	public CheckIOSRechargeParam(String host, String page, boolean post) {
		super(host, page, post);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected String content() {
		// TODO Auto-generated method stub
		return JSON.toJSONString(this);
	}

	@Override
	public CheckIOSRechargeRes getRes() {
		// TODO Auto-generated method stub
		if(this.errorCode!=0)
		{
			return null;
		}
		else
		{
			return JSON.parseObject(this.result,CheckIOSRechargeRes.class);
		}
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
	 * @return the receiptData
	 */
	public String getReceiptData() {
		return receiptData;
	}
	/**
	 * @param receiptData the receiptData to set
	 */
	public void setReceiptData(String receiptData) {
		this.receiptData = receiptData;
	}
	/**
	 * @return the sandbox
	 */
	public boolean isSandbox() {
		return sandbox;
	}
	/**
	 * @param sandbox the sandbox to set
	 */
	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}


}
