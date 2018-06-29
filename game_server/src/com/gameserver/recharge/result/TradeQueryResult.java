package com.gameserver.recharge.result;

/**
 * 验证交易结果返回数据
 * @author 郭君伟
 *
 */
public class TradeQueryResult {
	
    private String ReturnCode;
    private String ReturnMsg;
    private String PayResult;
    private String FacTradeSeq;
    private String PaymentType;
    private String Amount;
    private String Currency;
    private String MyCardTradeNo;
    private String MyCardType;
    private String PromoCode;
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
	public String getPayResult() {
		return PayResult;
	}
	public void setPayResult(String payResult) {
		PayResult = payResult;
	}
	public String getFacTradeSeq() {
		return FacTradeSeq;
	}
	public void setFacTradeSeq(String facTradeSeq) {
		FacTradeSeq = facTradeSeq;
	}
	public String getPaymentType() {
		return PaymentType;
	}
	public void setPaymentType(String paymentType) {
		PaymentType = paymentType;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	public String getMyCardTradeNo() {
		return MyCardTradeNo;
	}
	public void setMyCardTradeNo(String myCardTradeNo) {
		MyCardTradeNo = myCardTradeNo;
	}
	public String getMyCardType() {
		return MyCardType;
	}
	public void setMyCardType(String myCardType) {
		MyCardType = myCardType;
	}
	public String getPromoCode() {
		return PromoCode;
	}
	public void setPromoCode(String promoCode) {
		PromoCode = promoCode;
	}
	public String getSerialId() {
		return SerialId;
	}
	public void setSerialId(String serialId) {
		SerialId = serialId;
	}
    
    
}
