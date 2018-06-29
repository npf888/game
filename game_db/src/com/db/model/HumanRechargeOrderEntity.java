package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 用户充值订单
 * @author wayne
 *
 */
@Entity
@Table(name = "t_human_recharge_order")
public class HumanRechargeOrderEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1348399237234106532L;
	private long id;
	private long charId;
	private int orderStatus;
	private int channelId;
	private int productId;
	private String channelOrderId;
	private int cost;
	private long updateTime;
	private long createTime;
	private int topUpType;
	private String authCode;
	private String tradeSeq;
	private String paymentType;
	private String myCardTradeNo;
	private String userId;
	private String receiptId;
	private String currencyCode;
	private int amountmol;
	private String paymentIdmol;
	/**
	 * 下单的时候  用户的等级
	 */
	private long level;
	/**
	 * 下单的时候  用户的金币
	 */
	private long gold;
	@Id
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Column
	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	@Column
	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	@Column
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	
	/**
	 * @return the channelOrderId
	 */
	@Column
	public String getChannelOrderId() {
		return channelOrderId;
	}

	/**
	 * @param channelOrderId the channelOrderId to set
	 */
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	@Column
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Column
	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Column
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Column
	public int getTopUpType() {
		return topUpType;
	}

	public void setTopUpType(int topUpType) {
		this.topUpType = topUpType;
	}

	@Column
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@Column
	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	@Column
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Column
	public String getMyCardTradeNo() {
		return myCardTradeNo;
	}

	public void setMyCardTradeNo(String myCardTradeNo) {
		this.myCardTradeNo = myCardTradeNo;
	}

	@Column
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column
	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	@Column
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column
	public int getAmountmol() {
		return amountmol;
	}

	public void setAmountmol(int amountmol) {
		this.amountmol = amountmol;
	}

	@Column
	public String getPaymentIdmol() {
		return paymentIdmol;
	}

	public void setPaymentIdmol(String paymentIdmol) {
		this.paymentIdmol = paymentIdmol;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	
    
	
}
