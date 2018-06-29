package com.gameserver.recharge.data;
/**
 * 博趣 支付 完成之后的通知
 * @author JavaServer
 *
 */
public class BoquData {

	//平台订单 id
	private String order_id;
	//平台用户 id
	private String user_id;
	//支付总额(单位/分)
	private int amount;
	//提交订单时透传字段
	private String info;
	/**
	 * 状态(见下面备注)  
	 * init 待支付    
	 * sync付款成功,通知给服务端异常,等待再次同步  
	 * complete成功  
	 * refund已退款  
	 * timeout 超时
	 */
	private String status;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
