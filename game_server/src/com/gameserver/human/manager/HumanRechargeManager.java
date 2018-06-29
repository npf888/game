package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.db.model.HumanRechargeOrderEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.player.enums.ChannelTypeEnum;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.RechargeLogic;
import com.gameserver.recharge.data.HumanRechargeOrderInfoData;
import com.gameserver.recharge.data.MolValidationOrder;
import com.gameserver.recharge.enums.OrderStatus;
import com.gameserver.recharge.enums.TopUpType;
import com.gameserver.recharge.msg.GCMolOrder;
import com.gameserver.recharge.msg.GCOrderInfoDataList;

/**
 * 充值管理器
 * @author wayne
 *
 */
public class HumanRechargeManager implements RoleDataHolder, InitializeRequired{
	
	private Logger logger = Loggers.rechargeLogger;
	/** 主人 */
	private Human owner;
	
	/**未处理订单列表 **/
	private List<HumanRechargeOrder> unHandlerHumanRechargeOrderList = new ArrayList<HumanRechargeOrder>();
	
	/**亚马逊验证订单标记 **/
	private Set<Long> ids = new HashSet<Long>();
	
	/**已经处理的订单 **/
	private List<HumanRechargeOrder> humanRechargeOrderList = new ArrayList<HumanRechargeOrder>();
	
	/**临时记录 **/
	private long diamondAdd;
	
	/**翻倍转盘 **/
	private int multipleDouble;
	
	public HumanRechargeManager(Human owner){
		
		this.owner=owner;
	}
	
	public Human getOwner() {
		return owner;
	}

	
	public void load()
	{
		List<HumanRechargeOrderEntity> humanRechargeOrderEntityList = Globals.getDaoService().getRechargeOrderDao().getRechargeOrderListByCharId(owner.getPassportId());
		if(humanRechargeOrderEntityList!=null&&humanRechargeOrderEntityList.size()>0){
			for(HumanRechargeOrderEntity humanRechargeOrderEntity:humanRechargeOrderEntityList){
				HumanRechargeOrder humanRechargeOrder=new HumanRechargeOrder(owner);
				humanRechargeOrder.fromEntity(humanRechargeOrderEntity);
				
				if(humanRechargeOrder.getOrderStatus() == OrderStatus.VALIDATE || humanRechargeOrder.getOrderStatus() == OrderStatus.CANCEL)
				{
					humanRechargeOrderList.add(humanRechargeOrder);
				}
				else
				{
					unHandlerHumanRechargeOrderList.add(humanRechargeOrder);
				}
			
			}
		}
		
	}


	
	@Override
	public void init() {
		

		List<HumanRechargeOrder> tempList = new ArrayList<HumanRechargeOrder>();
		tempList.addAll(unHandlerHumanRechargeOrderList);
		logger.info("------用户登录-订单这边-ChannelType=="+owner.getPlayer().getChannelType());
		if(owner.getPlayer().getChannelType() == ChannelTypeEnum.GAME_VIEW){
			
			List<Long> list = new ArrayList<Long>();
			//获取未处理订单的订单号 发往登陆服校验
			 for(HumanRechargeOrder tRO:tempList){
				 if(tRO.getOrderStatus() == OrderStatus.PENDING){
						logger.info("human recharge manager init pending "+tRO.getId());
						list.add(tRO.getDbId());
				}
			 }
			 if(!list.isEmpty()){
				/* OrderValidationParam param = new OrderValidationParam(Globals.getLocalConfig().getRequestDomain(),"/api/validation",true);
				 param.setUserId(owner.getPassportId());
				 param.setOrderList(list);
				 AsyncHttpOperation<OrderValidationRes> asyncHttpOper = new AsyncHttpOperation<OrderValidationRes>(owner.getPlayer(),param, new OrderValidateCallBack());
				 //发起异步请求 
				 Globals.getAsyncService().createOperationAndExecuteAtOnce(asyncHttpOper);*/
			 }
		}else{
			List<MolValidationOrder> list = new ArrayList<MolValidationOrder>();
			for(HumanRechargeOrder hro:tempList){
				int topUpType = hro.getTopUpType();
				if(topUpType == TopUpType.COMMON.getIndex()){
					logger.info("---普通订单---用户登录-发现未被处理的订单-topUpType==="+topUpType);
					if(hro.getOrderStatus() == OrderStatus.PENDING){
						logger.info("------用户登录-发现未被处理的订单-去处理==="+hro.getOrderStatus());
						RechargeLogic.onRecharge(owner.getPlayer(), hro.getId(),hro.getCost());
					}
				}else if(topUpType == TopUpType.MYCARD.getIndex()){
					logger.info("---mycard---用户登录-发现未被处理的订单-topUpType==="+topUpType);
					long orderID = hro.getId();
					if(hro.getOrderStatus() == OrderStatus.NON_VALIDATE){
						logger.info("------用户登录-发现未被处理的订单-去处理==="+hro.getOrderStatus());
						long createTime = hro.getCreateTime();
						if(TimeUtils.daysBetween(createTime, Globals.getTimeService().now()) <= 31){
					       // Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(new SDKTradeQuery(owner,String.valueOf(orderID)));
						}
					}else if(hro.getOrderStatus() == OrderStatus.PENDING){
						RechargeLogic.onRechargeMyCard(owner.getPlayer(),orderID,0);
					}
				}else if(topUpType == TopUpType.MOL.getIndex()){
					logger.info("---MOL---用户登录-发现未被处理的订单-topUpType==="+topUpType);
					if(hro.getOrderStatus() == OrderStatus.PENDING){
						logger.info("------用户登录-发现未被处理的订单-去处理==="+hro.getOrderStatus());
						long orderID = hro.getId();
						String paymentIdmol = hro.getPaymentIdmol();
						MolValidationOrder mv = new MolValidationOrder();
						mv.setReferenceId(String.valueOf(orderID));
						mv.setPaymentId(paymentIdmol);
						list.add(mv);
					}
				}
			}
				if(!list.isEmpty()){
					GCMolOrder  message = new GCMolOrder();
					message.setMolValidationOrder(list.toArray(new MolValidationOrder[list.size()]));
					this.owner.sendMessage(message);
				}
		  }
		
	}


	@Override
	public void checkBeforeRoleEnter() {
		
	}
	
	@Override
	public void checkAfterRoleLoad() {
	}
	
	
	public long getDiamondAdd() {
		return diamondAdd;
	}

	public void setDiamondAdd(long diamondAdd) {
		this.diamondAdd = diamondAdd;
	}

	/**
	 * 添加订单
	 * @param order
	 */
	public void addOrder(HumanRechargeOrder order){
		this.unHandlerHumanRechargeOrderList.add(0,order);
	}
	
	/**
	 * 查找订单
	 * @param order
	 */
	public HumanRechargeOrder getOrderById(long orderId){
		for(HumanRechargeOrder order : unHandlerHumanRechargeOrderList){
			if(order.getId() == orderId)
				return order;
		}
		return null;
	}
	
	/**
	 * 查找订单
	 * @param order
	 */
	public HumanRechargeOrder getOrderByChannelOrderId(String channelOrderId){
		for(HumanRechargeOrder order : unHandlerHumanRechargeOrderList){
			if(order.getChannelOrderId()==null)
				continue;
			if(order.getChannelOrderId().equalsIgnoreCase(channelOrderId))
				return order;
		}
		return null;
	}
	
	/**
	 * 验证订单
	 * @param order
	 */
	public  void verifyOrder(HumanRechargeOrder order){
		order.setOrderStatus(OrderStatus.VALIDATE);
		order.setModified();
		humanRechargeOrderList.add(order);
		unHandlerHumanRechargeOrderList.remove(order);
	}
	
	/**
	 * 取消订单
	 * @param order
	 */
	public void cancelOrder(HumanRechargeOrder order){
		order.setOrderStatus(OrderStatus.CANCEL);
		order.setModified();
		humanRechargeOrderList.add(order);
		unHandlerHumanRechargeOrderList.remove(order);
	}
	
	public HumanRechargeOrder getOrderByProductId(int productId,int channelId){
		
		for(HumanRechargeOrder hro : unHandlerHumanRechargeOrderList){
			int userProductId = hro.getProductId();
			int userChannelId = hro.getChannelId();
			long id = hro.getId();
			if(userProductId == productId && userChannelId == channelId && !ids.contains(id)){
				ids.add(id);
				return hro;
			}
		}
		return null;
	}
	
	
	public GCOrderInfoDataList buildOrderInfoDataList(){
		GCOrderInfoDataList gcOrderInfoDataList = new GCOrderInfoDataList();
		HumanRechargeOrderInfoData[] orderInfoDataList= new HumanRechargeOrderInfoData[unHandlerHumanRechargeOrderList.size()];
		gcOrderInfoDataList.setOrderList(orderInfoDataList);
		return gcOrderInfoDataList;
	}
	/**
	 * 判断是不是首次充值
	 */
	
	public boolean judgeFirstRecharge(){
		if((unHandlerHumanRechargeOrderList == null || unHandlerHumanRechargeOrderList.size() == 0) 
				&& (humanRechargeOrderList == null || humanRechargeOrderList.size()==0)){
			return true;
		}
		return false;
	}

	public int getMultipleDouble() {
		return multipleDouble;
	}

	public void setMultipleDouble(int multipleDouble) {
		this.multipleDouble = multipleDouble;
	}
	
	
	
}
