package com.game.webserver.recharge;

public class CheckIOSRechargeRes {
	private String kind;
	private String purchaseTimeMillis;
	private String productId;
	private String developerPayload;
	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	/**
	 * @return the purchaseTimeMillis
	 */
	public String getPurchaseTimeMillis() {
		return purchaseTimeMillis;
	}
	/**
	 * @param purchaseTimeMillis the purchaseTimeMillis to set
	 */
	public void setPurchaseTimeMillis(String purchaseTimeMillis) {
		this.purchaseTimeMillis = purchaseTimeMillis;
	}
	/**
	 * @return the developerPayload
	 */
	public String getDeveloperPayload() {
		return developerPayload;
	}
	/**
	 * @param developerPayload the developerPayload to set
	 */
	public void setDeveloperPayload(String developerPayload) {
		this.developerPayload = developerPayload;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
