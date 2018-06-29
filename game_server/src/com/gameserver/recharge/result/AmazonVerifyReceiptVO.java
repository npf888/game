package com.gameserver.recharge.result;

import com.alibaba.fastjson.JSON;

/**
 * 亚马逊验证收据返回
 * @author 郭君伟
 *
 */
public class AmazonVerifyReceiptVO {
	
	/** 购买日期，用从纪元至今所经过的毫秒数计算。**/
	private long purchaseDate;
	/**购买的唯一标识符。 **/
    private String receiptId;
    /**您为您应用中的此项目定义的 SKU。 **/
    private String productId;
    /** Null。留作将来使用。**/
    private String parentProductId;
    /**所购买的产品的类型。有效的产品类型为 CONSUMABLE、SUBSCRIPTION 和 ENTITLED。 **/
    private String productType;
    /**购买取消或订阅类过期的日期。如果购买未取消，则该字段为 null。 **/
    private String cancelDate;
    /**购买数量。始终为 null 或 1。 **/
    private int quantity;
    /** 指示购买的产品是否是在线应用测试产品的布尔值。**/
    private boolean betaProduct;
    /**指示此购买是否作为 Amazon 的发布和测试流程的一部分进行执行的布尔值。 **/
    private boolean testTransaction;
    
	public long getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getParentProductId() {
		return parentProductId;
	}
	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isBetaProduct() {
		return betaProduct;
	}
	public void setBetaProduct(boolean betaProduct) {
		this.betaProduct = betaProduct;
	}
	public boolean isTestTransaction() {
		return testTransaction;
	}
	public void setTestTransaction(boolean testTransaction) {
		this.testTransaction = testTransaction;
	}
    
	public static void main(String[] args) {
		String str = "{'productId':'goddesscasino.gems.1','cancelDate':null,'receiptId':'q1YqVrJSSs9PSUktLk5OLM7My9dLT80t1jNU0lFKAUoZmlgYmhoZmZoYmxmCxEqBYjnGHj7mEQUFrr4ZRf7uLnmlRZa6pTllhfm5RcHpZoWV_i7euYkZ5mY5XqG2QC0lSlYGtQA','betaProduct':false,'quantity':1,'parentProductId':null,'productType':'CONSUMABLE','testTransaction':true,'purchaseDate':1481522543611}";
		
		AmazonVerifyReceiptVO vo = JSON.parseObject(str,AmazonVerifyReceiptVO.class);
		
		
	}
    
}
