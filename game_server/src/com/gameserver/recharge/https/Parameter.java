package com.gameserver.recharge.https;

/**
 * mycard 充值参数
 * @author 郭君伟
 *
 */
public class Parameter {

	/**3.1 向 MyCard 要求交易授權碼 **//*
	public static String url1 = "https://test.b2b.mycard520.com.tw/MyBillingPay/api/AuthGlobal";
	*//**3.3 驗證 MyCard 交易結果 **//*
	public static String url2 = "https://test.b2b.mycard520.com.tw/MyBillingPay/api/TradeQuery";
	*//**3.4 確認 MyCard 交易，並進行請款 **//*
	public static String url3 = "https://test.b2b.mycard520.com.tw/MyBillingPay/api/PaymentConfirm";
	*//**4.3 查詢交易狀態 （4.2失败 廠商自行呼叫此 API) **//*
	public static String url4 = "https://test.b2b.mycard520.com.tw/MyBillingPay/api/SDKTradeQuery";*/
	
	/**3.1 向 MyCard 要求交易授權碼 **/
	public static String url1 = "https://b2b.mycard520.com.tw/MyBillingPay/api/AuthGlobal";
	/**3.3 驗證 MyCard 交易結果 **/
	public static String url2 = "https://b2b.mycard520.com.tw/MyBillingPay/api/TradeQuery";
	/**3.4 確認 MyCard 交易，並進行請款 **/
	public static String url3 = "https://b2b.mycard520.com.tw/MyBillingPay/api/PaymentConfirm";
	/**4.3 查詢交易狀態 （4.2失败 廠商自行呼叫此 API) **/
	public static String url4 = "https://b2b.mycard520.com.tw/MyBillingPay/api/SDKTradeQuery";
	
	/** 廠商服務代碼  **/
	public static String facServiceId = "nfeiGC";
	
	
	public static String key = "8afc5ebb58A643aa9155a88B2225ec82";
	
	
	
}
