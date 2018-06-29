package com.gameserver.recharge;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanRechargeOrderEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.recharge.enums.OrderStatus;

/**
 * 充值订单
 * @author wayne
 *
 */
public class HumanRechargeOrder  implements PersistanceObject<Long, HumanRechargeOrderEntity>{
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private boolean inDb;
	/**开始时间*/
	private OrderStatus orderStatus;
	private int channelId;
	/**产品id*/
	private int productId;
	private String channelOrderId;
	/**充值*/
	private int cost;
	/** 更新时间 */
	private long updateTime;
	/** 创建时间 */
	private long createTime;
	
	/**支付类型 0 普通 1 mycard **/
	private int topUpType;
	/**mycard 授权码 **/
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
	public HumanRechargeOrder(Human owner){
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner = owner;
	}
	
	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setCharId(long charId) {
		this.charId = charId;
	}
	
	@Override
	public long getCharId() {
		return charId;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the channelOrderId
	 */
	public String getChannelOrderId() {
		return channelOrderId;
	}

	/**
	 * @param channelOrderId the channelOrderId to set
	 */
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public void setDbId(Long id) {
		this.id = id;
	}
	@Override
	public Long getDbId() {
		return this.id;
	}

	@Override
	public String getGUID() {
		return "rechargeorder#"+this.id;
	}

	@Override
	public boolean isInDb() {
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}


	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	

	public int getTopUpType() {
		return topUpType;
	}

	public void setTopUpType(int topUpType) {
		this.topUpType = topUpType;
	}

	

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getMyCardTradeNo() {
		return myCardTradeNo;
	}

	public void setMyCardTradeNo(String myCardTradeNo) {
		this.myCardTradeNo = myCardTradeNo;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	
	public int getAmountmol() {
		return amountmol;
	}

	public void setAmountmol(int amountmol) {
		this.amountmol = amountmol;
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

	public String getPaymentIdmol() {
		return paymentIdmol;
	}

	public void setPaymentIdmol(String paymentIdmol) {
		this.paymentIdmol = paymentIdmol;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	@Override
	public LifeCycle getLifeCycle() {
		// TODO Auto-generated method stub
		return lifeCycle;
	}

	@Override
	public void setModified() {
		// TODO Auto-generated method stub
		onUpdate();
	}

	@Override
	public HumanRechargeOrderEntity toEntity() {
		// TODO Auto-generated method stub
		HumanRechargeOrderEntity entity = new HumanRechargeOrderEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setProductId(getProductId());
		entity.setChannelId(getChannelId());
		entity.setCost(getCost());
		entity.setOrderStatus(getOrderStatus().getIndex());
		entity.setChannelOrderId(this.getChannelOrderId());
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		entity.setTopUpType(topUpType);
		entity.setAuthCode(authCode);
		entity.setTradeSeq(tradeSeq);
		entity.setPaymentType(paymentType);
		entity.setMyCardTradeNo(myCardTradeNo);
		entity.setUserId(userId);
		entity.setReceiptId(receiptId);
		entity.setCurrencyCode(currencyCode);
		entity.setAmountmol(amountmol);
		entity.setPaymentIdmol(paymentIdmol);
		entity.setLevel(level);
		entity.setGold(gold);
		return entity;
	}

	@Override
	public void fromEntity(HumanRechargeOrderEntity entity) {
		// TODO Auto-generated method stub
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.channelId = entity.getChannelId();
		this.productId = entity.getProductId();
		this.orderStatus = OrderStatus.valueOf(entity.getOrderStatus());
		this.channelOrderId = entity.getChannelOrderId();
		this.cost = entity.getCost();
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.topUpType = entity.getTopUpType();
		this.authCode = entity.getAuthCode();
		this.tradeSeq = entity.getTradeSeq();
		this.paymentType = entity.getPaymentType();
		this.myCardTradeNo = entity.getMyCardTradeNo();
		this.userId = entity.getUserId();
		this.receiptId = entity.getUserId();
		this.amountmol = entity.getAmountmol();
		this.currencyCode = entity.getCurrencyCode();
		this.paymentIdmol = entity.getPaymentIdmol();
		this.level=entity.getLevel();
		this.gold=entity.getGold();
		this.setInDb(true);
		this.active();
	}

	/**
	 * 激活此2关系
	 */
	public void active(){
		getLifeCycle().activate();
	}
	
	private void onUpdate()
	{
		if (owner != null) 
		{
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}


	
}


