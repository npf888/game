package com.gameserver.recharge.result;

/**
 * 请求付款返回数据
 * @author 郭君伟
 *
 */
public class PaymentConfirmResult {

	private String ReturnCode;
	private String ReturnMsg;
	private String FacTradeSeq;
	private String TradeSeq;
	private String SerialId;
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
	public String getFacTradeSeq() {
		return FacTradeSeq;
	}
	public void setFacTradeSeq(String facTradeSeq) {
		FacTradeSeq = facTradeSeq;
	}
	public String getTradeSeq() {
		return TradeSeq;
	}
	public void setTradeSeq(String tradeSeq) {
		TradeSeq = tradeSeq;
	}
	public String getSerialId() {
		return SerialId;
	}
	public void setSerialId(String serialId) {
		SerialId = serialId;
	}
	
	
	
}
