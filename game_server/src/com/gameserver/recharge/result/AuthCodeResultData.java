package com.gameserver.recharge.result;

/**
 * 获取授权码返回数据
 * @author 郭君伟
 *
 */
public class AuthCodeResultData {

	private String ReturnCode;
	private String ReturnMsg;
	private String AuthCode;
	private String TradeSeq;
	private String InGameSaveType;
	
	public String getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}
	public String getReturnMsg() {
		return ReturnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		ReturnMsg = returnMsg;
	}
	public String getAuthCode() {
		return AuthCode;
	}
	public void setAuthCode(String authCode) {
		AuthCode = authCode;
	}
	public String getTradeSeq() {
		return TradeSeq;
	}
	public void setTradeSeq(String tradeSeq) {
		TradeSeq = tradeSeq;
	}
	public String getInGameSaveType() {
		return InGameSaveType;
	}
	public void setInGameSaveType(String inGameSaveType) {
		InGameSaveType = inGameSaveType;
	}
	
	
	
	
	
}
