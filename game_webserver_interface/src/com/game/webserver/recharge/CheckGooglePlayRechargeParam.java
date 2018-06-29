package com.game.webserver.recharge;

import com.alibaba.fastjson.JSON;
import com.game.webserver.login.AbstractParams;


public class CheckGooglePlayRechargeParam extends AbstractParams<CheckGooglePlayRechargeRes>{

	private int googleId;
	private String packageName;
	private String productId;
	private String token;
	
	public CheckGooglePlayRechargeParam(){}
	public CheckGooglePlayRechargeParam(String host, String page, boolean post) {
		super(host, page, post);
		// TODO Auto-generated constructor stub
	}

	public int getGoogleId() {
		return googleId;
	}
	public void setGoogleId(int googleId) {
		this.googleId = googleId;
	}
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	protected String content() {
		return JSON.toJSONString(this);
	}

	@Override
	public CheckGooglePlayRechargeRes getRes() {
		if(this.errorCode!=0)
		{
			return null;
		}
		else
		{
			return JSON.parseObject(this.result,CheckGooglePlayRechargeRes.class);
		}
	}

}
