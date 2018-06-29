package com.gameserver.recharge;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.uuid.UUIDType;
import com.db.model.HumanEntity;
import com.db.model.HumanRechargeOrderEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.enums.ChannelTypeEnum;
import com.gameserver.recharge.enums.OrderStatus;
import com.gameserver.recharge.interfaces.IRechargeListener;
import com.gameserver.recharge.template.RechargeTemplate;

/**
 * 充值服务
 * @author wayne
 *
 */
public class RechargeService  implements InitializeRequired,AfterInitializeRequired{
	
	private Logger logger = Loggers.rechargeLogger;
	
	private List<IRechargeListener> rechargeListnerList = new ArrayList<IRechargeListener>();

	/** 渠道ID(channelId) 对应的产品 **/
	private Map<Integer,List<RechargeTemplate>> rechargeTemplateMap = new HashMap<Integer,List<RechargeTemplate>>();
	
	/** key:渠道 value:(key:PID value:模板数据)**/
	private Map<Integer,Map<Integer,RechargeTemplate>> channelPidMap = new HashMap<Integer,Map<Integer,RechargeTemplate>>();

	@Override
	public void init() {
		logger.info("recharge service init");
	}
	
	@Override
	public void afterInit() {
		logger.info("recharge service after init");
		for(RechargeTemplate tempRechargeTemplate:Globals.getTemplateService().getAll(RechargeTemplate.class).values()){
			
            int channel =  tempRechargeTemplate.getChannelId();
			
			int pid = tempRechargeTemplate.getPid();
			
			List<RechargeTemplate> tempList = rechargeTemplateMap.get(channel);
			if(tempList == null){
				tempList = new ArrayList<RechargeTemplate>();
				rechargeTemplateMap.put(channel, tempList);
			}
			tempList.add(tempRechargeTemplate);
			
			if(channelPidMap.containsKey(channel)){
				channelPidMap.get(channel).put(pid, tempRechargeTemplate);
			}else{
				Map<Integer,RechargeTemplate> map = new HashMap<Integer,RechargeTemplate>();
				map.put(pid, tempRechargeTemplate);
				channelPidMap.put(channel, map);
			}
		}
	}

	public RechargeTemplate getTemplate(int channel,int pid){
		//取指定的渠道
		if(channelPidMap.containsKey(channel) && channelPidMap.get(channel).containsKey(pid)){
			return channelPidMap.get(channel).get(pid);
		}
		//默认渠道
		if(channelPidMap.containsKey(0) && channelPidMap.get(0).containsKey(pid)){
			return channelPidMap.get(0).get(pid);
		}
		
		return null;
	}

	
	/**
	 * 产生订单
	 * @param human
	 * @param productId  recharge.xls : pid
	 * @param cost
	 * @return
	 */
	public HumanRechargeOrder generateOrder(Human human,int productId,int cost){
		HumanRechargeOrder order = new HumanRechargeOrder(human);
		long now = Globals.getTimeService().now();
		order.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANRECHARGEID));
		order.setCharId(human.getPassportId());
		order.setProductId(productId);
		order.setOrderStatus(OrderStatus.NON_VALIDATE);
		order.setChannelId(human.getPlayer().getChannelType().getIndex());
		order.setCost(cost);
		order.setCreateTime(now);
		order.setAuthCode("");
		order.setPaymentType("");
		order.setTradeSeq("");
		order.setMyCardTradeNo("");
		order.setUserId("");
		order.setReceiptId("");
		order.setCurrencyCode("");
		order.setAmountmol(0);
		order.setLevel(human.getLevel());
		order.setGold(human.getGold());
		order.setInDb(false);
		order.active();
		return order;
	}
	/**
	 * 第三方平台 充值 产生订单
	 * @param human
	 * @param productId  recharge.xls : pid
	 * @param cost
	 * @return
	 */
	public HumanRechargeOrder thirdPartyGenerateOrder(Human human,int productId,int cost,long giveGold){
		HumanRechargeOrder order = new HumanRechargeOrder(human);
		long now = Globals.getTimeService().now();
		order.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANRECHARGEID));
		order.setCharId(human.getPassportId());
		order.setProductId(productId);
		order.setOrderStatus(OrderStatus.NON_VALIDATE);
		order.setChannelId(human.getPlayer().getChannelType().getIndex());
		order.setCost(cost);
		order.setCreateTime(now);
		order.setAuthCode("");
		order.setPaymentType("");
		order.setTradeSeq("");
		order.setMyCardTradeNo("");
		order.setUserId("");
		order.setReceiptId("");
		order.setCurrencyCode("");
		order.setAmountmol(0);
		order.setLevel(human.getLevel());
		order.setGold(giveGold);
		order.setInDb(false);
		order.active();
		return order;
	}
	/**
	 * 第三方平台 充值 产生订单
	 * @param human
	 * @param productId  recharge.xls : pid
	 * @param cost
	 * @return
	 */
	public void thirdPartyProduceOrder(HumanEntity entity,int productId,int cost,long giveGold){
		HumanRechargeOrderEntity order = new HumanRechargeOrderEntity();
		long now = Globals.getTimeService().now();
		order.setId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANRECHARGEID));
		order.setCharId(entity.getPassportId());
		order.setProductId(productId);
		order.setOrderStatus(OrderStatus.NON_VALIDATE.getIndex());
		order.setChannelId(0);
		order.setCost(cost);
		order.setCreateTime(now);
		order.setAuthCode("");
		order.setPaymentType("");
		order.setTradeSeq("");
		order.setMyCardTradeNo("");
		order.setUserId("");
		order.setReceiptId("");
		order.setCurrencyCode("");
		order.setAmountmol(0);
		order.setLevel(entity.getLevel());
		order.setGold(giveGold);
		Globals.getDaoService().getRechargeOrderDao().save(order);
	}
	
	/**
	 * 增加监听器
	 * @param rechargeListner
	 */
	public void addListner(IRechargeListener rechargeListner){
		
		rechargeListnerList.add(rechargeListner);
	}
	
	/**
	 * 移除监听器
	 * @param rechargeListner
	 */
	public void removeListner(IRechargeListener rechargeListner){
		rechargeListnerList.remove(rechargeListner);
	}
	
	/**
	 * 充值通知
	 * @param human
	 * @param productId
	 */
	public void onRecharge(Human human ,int productId){
		for(IRechargeListener rechargeListner : rechargeListnerList){
			rechargeListner.onRecharge(human, productId);
		}
	}
	
	/**
	 * product
	 */
	public RechargeTemplate rechargeTemplateBySkuId(ChannelTypeEnum channelType,String skuId){
		List<RechargeTemplate> rechargeTemplateList= rechargeTemplateMap.get(channelType.getIndex());
		if(rechargeTemplateList==null){
			rechargeTemplateList = rechargeTemplateMap.get(0);
		}
		if(rechargeTemplateList==null)
			return null;
		for(RechargeTemplate tempRechargeTemplate :rechargeTemplateList){
			if(tempRechargeTemplate.getProductId().equalsIgnoreCase(skuId)){
				return tempRechargeTemplate;
			}
		}
		return null;
	}
	

	/**
	 * product
	 */
	public RechargeTemplate rechargeIdrTemplateByCost(ChannelTypeEnum channelType,int cost){
		List<RechargeTemplate> rechargeTemplateList= rechargeTemplateMap.get(channelType.getIndex());
		if(rechargeTemplateList==null){
			logger.info("no found recharge "+channelType.toString() );
			rechargeTemplateList = rechargeTemplateMap.get(0);
			if(rechargeTemplateList==null){
				logger.info("no found recharge for 0" );
				return null;
			}
		}
	
		for(RechargeTemplate tempRechargeTemplate :rechargeTemplateList){
			logger.info("recharege template ["+tempRechargeTemplate.getNum()+"]");
			if(tempRechargeTemplate.getNum() == cost){
				return tempRechargeTemplate;
			}
		}
		return null;
	}
}
