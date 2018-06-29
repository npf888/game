package com.game.webserver.login;

/**
 * 检测登录回复
 * @author wayne
 *
 */
public class CheckLoginRes {

	private long userId;
	private String accountId;
	private String facebookId;
	private int role;
	private String name;
	private String img;
	private boolean updateFbInfo;
	private String deviceMac;
	
	public CheckLoginRes(){
		
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	public boolean isUpdateFbInfo() {
		return updateFbInfo;
	}

	public void setUpdateFbInfo(boolean updateFbInfo) {
		this.updateFbInfo = updateFbInfo;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

}
