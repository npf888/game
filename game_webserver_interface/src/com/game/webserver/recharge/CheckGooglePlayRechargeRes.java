package com.game.webserver.recharge;

/**
 * 充值
 * @author wayne
 *
 */
public class CheckGooglePlayRechargeRes {
	
	private String kind;
	private String purchaseTimeMillis;
	private int purchaseState;
	private int consumptionState;
	private String developerPayload;
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getPurchaseTimeMillis() {
		return purchaseTimeMillis;
	}
	public void setPurchaseTimeMillis(String purchaseTimeMillis) {
		this.purchaseTimeMillis = purchaseTimeMillis;
	}
	public int getPurchaseState() {
		return purchaseState;
	}
	public void setPurchaseState(int purchaseState) {
		this.purchaseState = purchaseState;
	}
	public int getConsumptionState() {
		return consumptionState;
	}
	public void setConsumptionState(int consumptionState) {
		this.consumptionState = consumptionState;
	}
	public String getDeveloperPayload() {
		return developerPayload;
	}
	public void setDeveloperPayload(String developerPayload) {
		this.developerPayload = developerPayload;
	}
}
