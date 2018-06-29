package com.gameserver.recharge;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;

import redis.clients.jedis.Jedis;

import com.common.LogReasons;
import com.common.LogReasons.NewRechargeLogReason;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.async.AsyncIoOpeOnly;
import com.core.util.TimeUtils;
import com.db.dao.ClubGiftRecordtDao;
import com.db.model.ClubGiftRecordEntity;
import com.gameserver.club.ClubService;
import com.gameserver.club.handler.ClubMessageHandler;
import com.gameserver.club.msg.utils.ClubMessageUtils;
import com.gameserver.club.redis.BoardMsgData;
import com.gameserver.club.redis.ClubData;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.club.template.ClubRechargeTemplate;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.consts.ClubConsts;
import com.gameserver.currency.Currency;
import com.gameserver.fw.ClubCache;
import com.gameserver.human.Human;
import com.gameserver.human.data.HumanInfoData;
import com.gameserver.human.manager.HumanBagManager;
import com.gameserver.human.manager.HumanMonthCardManager;
import com.gameserver.human.manager.HumanRechargeManager;
import com.gameserver.human.manager.HumanWeekCardManager;
import com.gameserver.human.msg.GCHumanDetailInfo;
import com.gameserver.human.template.CouponTemplate;
import com.gameserver.item.Item;
import com.gameserver.item.template.ItemTemplate;
import com.gameserver.player.Player;
import com.gameserver.player.enums.ChannelTypeEnum;
import com.gameserver.recharge.enums.CategoryEnum;
import com.gameserver.recharge.enums.OrderStatus;
import com.gameserver.recharge.enums.SmallCategoryEnum;
import com.gameserver.recharge.enums.SmallCategoryEnumBackup;
import com.gameserver.recharge.enums.TopUpType;
import com.gameserver.recharge.msg.GCDoubleTurn;
import com.gameserver.recharge.msg.GCObtainCoupon;
import com.gameserver.recharge.msg.GCValidateOrder;
import com.gameserver.recharge.template.RechargeTemplate;
import com.gameserver.redis.enums.RedisKey;
import com.gameserver.shop.enums.ShopCatergoryEnum;
import com.gameserver.task.enums.ClientType;
import com.gameserver.task.enums.RefreshType;
import com.gameserver.vipnew.VipNewServer;

public class RechargeLogic {
	
	private static Logger logger = Loggers.rechargeLogger;
	
	/**
	 * 订单付款
	 * @param player 角色
	 * @param orderId 订单号
	 * @param cost 充值钱数
	 */
	/*public static void onRecharge(Player player,long orderId,int cost){
		
		Human human = player.getHuman();
		
		if(human==null){
			logger.warn("玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		//充值管理器
		HumanRechargeManager humanRechargeManager = human.getHumanRechargeManager();
		//订单
		HumanRechargeOrder order =  humanRechargeManager.getOrderById(orderId);
		
		if(order == null){
			logger.warn("玩家["+player.getPassportId()+"]"+"回调 orderid["+ orderId+"]不存在");
			return;
		}
		//验证订单
		humanRechargeManager.verifyOrder(order);
		
		long diamondAdd =0;
		//判断角色渠道类型
		if(player.getChannelType() == ChannelTypeEnum.UNIPIN){
			
			RechargeTemplate rechargeIdrTemplate = Globals.getRechargeService().rechargeIdrTemplateByCost(player.getChannelType(),cost*100);
			if(rechargeIdrTemplate ==null){
				logger.error("玩家["+human.getPassportId()+"]订单号["+order.getId()+"],印尼钱数["+cost*100+"]不在列表中");
				return;
			}
			order.setProductId(rechargeIdrTemplate.getId());
			order.setCost(cost*100);
			order.setModified();
			diamondAdd = rechargeIdrTemplate.getItemNum()+rechargeIdrTemplate.getGiftNum();
		
		}else{
			RechargeTemplate rechargeTemplate = Globals.getTemplateService().get(order.getProductId(), RechargeTemplate.class);
			diamondAdd = rechargeTemplate.getItemNum()+rechargeTemplate.getGiftNum();
		}
		
		//首冲判断
		if(human.getHumanMiscManager().ifFirstRecharge()){
			diamondAdd *=2;
			logger.info("玩家["+human.getPassportId()+"]首冲["+order.getId()+"],产品id["+order.getProductId()+"]双倍");
			human.getHumanMiscManager().firstRecharge();
		}
		
		//发货
		String detailReason = MessageFormat.format( LogReasons.DiamondLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		human.giveMoney(diamondAdd, Currency.GOLD, true, LogReasons.DiamondLogReason.RECHARGE, detailReason, order.getProductId(), 1);
		String detailReason = MessageFormat.format( LogReasons.DiamondLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		human.giveMoney(diamondAdd, Currency.DIAMOND, true, LogReasons.DiamondLogReason.RECHARGE, detailReason, order.getProductId(), 1);
		
		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		if(player.getChannelType().isIOS()){
			gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
		}else{
			gcValidateOrder.setOrderId(order.getDbId());
		}
		gcValidateOrder.setResult(1);
		gcValidateOrder.setGems((int)diamondAdd);
		human.sendMessage(gcValidateOrder);
		
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		
		logger.info("玩家["+human.getPassportId()+"]验证 订单号["+order.getId()+"],产品id["+order.getProductId()+"]");
		Globals.getLogService().sendRechargeLog(human, LogReasons.RechargeLogReason.VALIDATE_ORDER,  LogReasons.RechargeLogReason.VALIDATE_ORDER.getReasonText(), order.getDbId(), order.getProductId(),order.getCost());
	}*/
	
	//==============================================================================================================
	 
	/**
	 * @param player
	 * @param extraparam1 订单
	 * @param amount 实际收入的美元现在的值扩大了100倍(为了计算方便)
	 * @param gold 实际发生的元宝
	 */
	public static void onRechargeGameView(Player player,long extraparam1,long amount,long gold){
		
        Human human = player.getHuman();
		
		if(human==null){
			logger.warn("玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		//充值管理器
		HumanRechargeManager humanRechargeManager = human.getHumanRechargeManager();
		//订单
		HumanRechargeOrder order =  humanRechargeManager.getOrderById(extraparam1);
		
		if(order == null){
			logger.warn("玩家["+player.getPassportId()+"]"+"回调 orderid["+ extraparam1+"]不存在");
			return;
		}
		
		//验证订单
		humanRechargeManager.verifyOrder(order);
		
		//获取模板数据
	    RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(order.getChannelId(), order.getProductId());
	    
	    if(rechargeTemplate == null){
	    	logger.warn("玩家["+player.getPassportId()+"]"+"回调 模板数据不存在["+ extraparam1+"]不存在");
	    	return;
	    }
	    
	    //物品ID （1~100）为金钱
	    int itemId = rechargeTemplate.getItemId();
	    
	    //1 筹码 每个档次*2
	    int category = rechargeTemplate.getCategory();
	    
	    boolean fly = false;
	    if(itemId < 100){
	    	fly = buyGoldGameView(player,human,order,(int)gold,rechargeTemplate,amount,category);
	    }else{
	    	fly = buyItemGameView(player,human,order,(int)gold,rechargeTemplate,amount);
	    }
	    
	    if(fly){
	    	//奖励vip点 
	  	  int vipPoint =  rechargeTemplate.getVipPoint();
	  	  
	  	  human.getHumanVipNewManager().addPoint(vipPoint);
	  	  
	  	 
	  	  
	  	  CategoryEnum categoryEnum = CategoryEnum.valueOf(rechargeTemplate.getCategory());
	  	  
	  	  if(categoryEnum == CategoryEnum.CATEGORYENUM3){
	  		human.getHumanGiftManager().updateGift(order.getProductId());
	  	  }
	  	  
	  	//long money = rechargeTemplate.getNum();
	  	 //首充记录
	  	long isPay = human.getIsPay();
	  	NewRechargeLogReason reason = NewRechargeLogReason.RECHARGE3;
	  	long now = Globals.getTimeService().now();
	  	if(isPay <= 0){
	  		human.setIsPay(amount);
	  		long createTime = human.getCreateTime();
			if(TimeUtils.isSameDay(now, createTime)){
				reason = NewRechargeLogReason.RECHARGE2;//新用户首付
			}else{
				reason = NewRechargeLogReason.RECHARGE1;;//老用户首付
			}
			//首次充值成就
			human.getHumanAchievementManager().updateFirstCharge();
	  	}
	  	int timeDian = TimeUtils.getHourTime(now);
	  	
	  	Globals.getLogService().sendNewRechargeLog(human, reason, reason.getReasonText(), amount, 
	  			timeDian, player.getChannelType().getIndex(), player.getDeviceModel(), 
	  			player.getClientVersion(),String.valueOf(extraparam1),player.getCountries(),
	  			human.getAge(),human.getIpCountries(),human.getGirl());	
	  	
	    }
		
	}
	/**
	 * 
	 * @param player
	 * @param extraparam1
	 * @param virtualCurrencyAmount
	 * @param amount
	 */
	public static void onRechargeMOL(Player player,long extraparam1,Double virtualCurrencyAmount,int amount){
		
		Human human = player.getHuman();
		
		if(human==null){
			logger.warn("玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		//充值管理器
		HumanRechargeManager humanRechargeManager = human.getHumanRechargeManager();
		//订单
		HumanRechargeOrder order =  humanRechargeManager.getOrderById(extraparam1);
		
		if(order == null){
			logger.warn("玩家["+player.getPassportId()+"]"+"回调 orderid["+ extraparam1+"]不存在");
			return;
		}
		
		//验证订单
		humanRechargeManager.verifyOrder(order);
		
		//获取模板数据
		RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(order.getChannelId(), order.getProductId());
		
		if(rechargeTemplate == null){
			logger.warn("玩家["+player.getPassportId()+"]"+"回调 模板数据不存在["+ extraparam1+"]不存在");
			return;
		}
		
		//物品ID （1~100）为金钱
		int itemId = rechargeTemplate.getItemId();
		
		//1 筹码 每个档次*2
		int category = rechargeTemplate.getCategory();
		
		boolean fly = false;
		if(itemId < 100){
			fly = buyGoldMOL(player,human,order,virtualCurrencyAmount.intValue(),rechargeTemplate,amount,category);
		}else{
			//fly = buyItemGameView(player,human,order,(int)gold,rechargeTemplate,amount);
		}
		
		if(fly){
			//奖励vip点 
			int vipPoint =  rechargeTemplate.getVipPoint();
			
			human.getHumanVipNewManager().addPoint(vipPoint);
			
			
			
			CategoryEnum categoryEnum = CategoryEnum.valueOf(rechargeTemplate.getCategory());
			
			if(categoryEnum == CategoryEnum.CATEGORYENUM3){
				human.getHumanGiftManager().updateGift(order.getProductId());
			}
			
			//long money = rechargeTemplate.getNum();
			//首充记录
			long isPay = human.getIsPay();
			NewRechargeLogReason reason = NewRechargeLogReason.RECHARGE3;
			long now = Globals.getTimeService().now();
			if(isPay <= 0){
				human.setIsPay(amount);
				long createTime = human.getCreateTime();
				if(TimeUtils.isSameDay(now, createTime)){
					reason = NewRechargeLogReason.RECHARGE2;//新用户首付
				}else{
					reason = NewRechargeLogReason.RECHARGE1;;//老用户首付
				}
				//首次充值成就
				human.getHumanAchievementManager().updateFirstCharge();
			}
			int timeDian = TimeUtils.getHourTime(now);
			
			Globals.getLogService().sendNewRechargeLog(human, reason, reason.getReasonText(), 
					amount, timeDian, player.getChannelType().getIndex(), player.getDeviceModel(), 
					player.getClientVersion(),String.valueOf(extraparam1),player.getCountries(),
					human.getAge(),human.getIpCountries(),human.getGirl());	
			
		}
		
	}
	
	
	
	//=============================================================================================================
	
	/**
	 * 发货
	 * @param player
	 * @param orderId
	 * @param cost
	 */
	public static void onRechargeMyCard(Player player,long orderId,int cost){
		
		Human human = player.getHuman();
		
		if(human==null){
			logger.warn("玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		//充值管理器
		HumanRechargeManager humanRechargeManager = human.getHumanRechargeManager();
		//订单
		HumanRechargeOrder order =  humanRechargeManager.getOrderById(orderId);
		
		if(order == null){
			logger.warn("玩家["+player.getPassportId()+"]"+"回调 orderid["+ orderId+"]不存在");
			return;
		}
		//验证订单
		humanRechargeManager.verifyOrder(order);
		
		RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(order.getChannelId(), order.getProductId());
		
		if(rechargeTemplate == null){
			logger.warn("玩家["+player.getPassportId()+"]"+"回调 模板数据不存在["+ orderId+"]不存在");
			return;
		}
		
		//物品ID （1~100）为金钱
		int itemId = rechargeTemplate.getItemId();
		
		boolean fly = false;
		if(itemId < 100){
			fly = buyGoldMycard(player,human,order,cost,rechargeTemplate);
		}else{
			fly = buyItem(player,human,order,cost,rechargeTemplate);
		}
		
		if(fly){
			
			//奖励vip点 
			int vipPoint =  rechargeTemplate.getVipPoint();
			
			human.getHumanVipNewManager().addPoint(vipPoint);
			
			CategoryEnum categoryEnum = CategoryEnum.valueOf(rechargeTemplate.getCategory());
			
			if(categoryEnum == CategoryEnum.CATEGORYENUM3){
				human.getHumanGiftManager().updateGift(order.getProductId());
			}
			
			
			long money = rechargeTemplate.getNum();
			//首充记录
			long isPay = human.getIsPay();
			
			NewRechargeLogReason reason = NewRechargeLogReason.RECHARGE3;
			long now = Globals.getTimeService().now();
			if(isPay <= 0){
				human.setIsPay(money);
				long createTime = human.getCreateTime();
				
				
				if(TimeUtils.isSameDay(now, createTime)){
					reason = NewRechargeLogReason.RECHARGE2;//新用户首付
				}else{
					reason = NewRechargeLogReason.RECHARGE1;;//老用户首付
				}
				//首次充值成就
				human.getHumanAchievementManager().updateFirstCharge();
			}
			int timeDian = TimeUtils.getHourTime(now);
			
			Globals.getLogService().sendNewRechargeLog(human, reason, reason.getReasonText(),
					money, timeDian, player.getChannelType().getIndex(), player.getDeviceModel(), 
					player.getClientVersion(),String.valueOf(orderId),player.getCountries(),
					human.getAge(),human.getIpCountries(),human.getGirl());	
			
		}
		
	}
	
	
	/**
	 * 发货
	 * @param player
	 * @param orderId
	 * @param cost
	 */
	public static void onRecharge(Player player,long orderId,int cost){
		
        Human human = player.getHuman();
		
		if(human==null){
			logger.warn("玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		//充值管理器
		HumanRechargeManager humanRechargeManager = human.getHumanRechargeManager();
		//订单
		HumanRechargeOrder order =  humanRechargeManager.getOrderById(orderId);
		
		if(order == null){
			logger.warn("玩家["+player.getPassportId()+"]"+"回调 orderid["+ orderId+"]不存在");
			return;
		}
		//验证订单
		humanRechargeManager.verifyOrder(order);
		logger.info("--3--更新订单 到数据库数据库... ...orderID:"+order.getChannelOrderId());
	    RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(order.getChannelId(), order.getProductId());
	    
	    logger.info("--3-1--更新订单 到数据库数据库... ...rechargeTemplate:"+order.getChannelId()+"---------"+order.getProductId());
	    if(rechargeTemplate == null){
	    	logger.warn("玩家["+player.getPassportId()+"]"+"回调 模板数据不存在["+ orderId+"]不存在");
	    	return;
	    }
	    
	    //物品ID （1~100）为金钱
	   /* int itemId = rechargeTemplate.getItemId();
	    boolean fly = false;
	    if(itemId < 100){
	    	fly = buyGold(player,human,order,cost,rechargeTemplate);
	    }else{
	    	fly = buyItem(player,human,order,cost,rechargeTemplate);
	    }*/
	    
	    int categrory = rechargeTemplate.getCategory();
	    int smallCategory = rechargeTemplate.getSmallCategory();
	    boolean fly = false;
	    /**
	     * 类别：1、筹码；2:物品3、礼包 4、功能性付费
	     */
	    if(categrory==1 || categrory == 3 || categrory ==4 ){
	    	if(smallCategory==SmallCategoryEnumBackup.SMALLCATEGORYENUM16.getIndex()){
	    		fly = buyWinnerWheelGold(player,human,order,cost,rechargeTemplate);
	    	}else{
	    		fly = buyGold(player,human,order,cost,rechargeTemplate);
	    	}
	    }else{
	    	fly = buyItem(player,human,order,cost,rechargeTemplate);
	    }
	    
	    
	    if(fly){
			    	
			  	long money = rechargeTemplate.getNum();
			  	 //首充记录
			  	long isPay = human.getIsPay();
			  	
			  	NewRechargeLogReason reason = NewRechargeLogReason.RECHARGE3;
			  	long now = Globals.getTimeService().now();
			  	if(isPay <= 0){
			  		human.setIsPay(money);
			  		long createTime = human.getCreateTime();
			  		
					
					if(TimeUtils.isSameDay(now, createTime)){
						reason = NewRechargeLogReason.RECHARGE2;//新用户首付
					}else{
						reason = NewRechargeLogReason.RECHARGE1;;//老用户首付
					}
					//首次充值成就
					human.getHumanAchievementManager().updateFirstCharge();
			  	}
			  	int timeDian = TimeUtils.getHourTime(now);
			  	
			  	Globals.getLogService().sendNewRechargeLog(human, reason, reason.getReasonText(),
			  			money, timeDian, player.getChannelType().getIndex(), player.getDeviceModel(), 
			  			player.getClientVersion(),String.valueOf(orderId),player.getCountries(),
			  			human.getAge(),human.getIpCountries(),human.getGirl());	
			  	
			  	  int pid = rechargeTemplate.getPid();
			  	  
			  	  /**
			  	   * 充值转盘
			  	   */
			  	  if(Globals.getLuckySpinService().isBigWheel(pid)){
			  		GCValidateOrder gcValidateOrder = new GCValidateOrder();
					if(player.getChannelType().isIOS()){
						if(Globals.getServerConfig().isTest()){
							//AAA:ios修改
							gcValidateOrder.setOrderId(order.getDbId());
						}else{
							gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
						}
						
					}else{
						gcValidateOrder.setOrderId(order.getDbId());
					}
					gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
					RandRewardData[] data = new RandRewardData[1];
					RandRewardData rr = new RandRewardData();
					rr.setRewardId(3);
					rr.setRewardCount(0);
					data[0] = rr;
					gcValidateOrder.setRandRewardList(data);
					gcValidateOrder.setPId(rechargeTemplate.getPid());
					logger.info("-------4-3-返回客户端接口----------发货中的金币：："+rechargeTemplate.getPid());
					human.sendMessage(gcValidateOrder);
					
					
			  		Globals.getLuckySpinService().spinZhuanpan(human);
			  	  }
			  	  
			  	 Globals.getTaskNewService().slotTopup(human, ClientType.TASK101.getIndex(), RefreshType.RefreshType4.getIndex(), pid);
			  	 Globals.getTaskNewService().spinSlot(human, ClientType.TASK101.getIndex(), RefreshType.RefreshType3.getIndex());
			  	logger.info("--5--订单结束... ...");
	    }
		
	}
	
	
	
	/**
	 * 大奖 转盘
	 * @param player
	 * @param human
	 * @param order
	 * @param cost
	 * @param rechargeTemplate
	 * @return
	 */
	private static boolean buyWinnerWheelGold(Player player, Human human,
			HumanRechargeOrder order, int cost,
			RechargeTemplate rechargeTemplate) {
		
		
		long totalGold = human.getHumanSlotManager().getHumanTemporaryData().getWinnerGold();
		
		/**
		 * 发货
		 */
		logger.info("-------"+human.getPassportId()+"-[大奖转盘]----------发货中的金币：："+totalGold);
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),totalGold);
		human.giveMoney(totalGold, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailReason, order.getProductId(), 1);
		List<RandRewardData> RandRewardDataList = new ArrayList<RandRewardData>();
		RandRewardData RandRewardData = new RandRewardData();
		RandRewardData.setRewardId(3);
		RandRewardData.setRewardCount(Long.valueOf(totalGold).intValue());
		RandRewardDataList.add(RandRewardData);
		
		int vipPoint =  rechargeTemplate.getVipPoint();
		logger.info("-------"+human.getPassportId()+"-[大奖转盘]-----------增加VIP点数... ...前-vipPoint:"+vipPoint);
		human.getHumanVipNewManager().addPoint(vipPoint);
		
		logger.info("-------"+human.getPassportId()+"-[大奖转盘]-----------增加VIP点数... ...后-vipPoint:"+vipPoint);
		//发送消息 给客户端
		backMessage(player,order,rechargeTemplate,RandRewardDataList);
		//处理公共的部分 例如 活动
		handlePubContent(human,rechargeTemplate.getNum(),totalGold,order);
	
		//买一赠一功能
		try{
			Globals.getFunctionService().buyOneSendOne(human, rechargeTemplate, Currency.GOLD.getIndex(), Long.valueOf(totalGold).intValue());
		 }catch(Exception e){
			  logger.info("买一赠一报错：",e);
		 }
		return true;
	}
	
	
	/**
	 * 一些 充值 要调用的公共的部分
	 * @param human
	 * @param dollar
	 * @param totalGold
	 * @param order
	 */
	private static void handlePubContent(Human human,int dollar,long totalGold,HumanRechargeOrder order) {
		
		
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		
		sendRechargeLog(human,order,order.getCost());
		//活动这块 活动金币 和 购买是不一样的
		Globals.getActivityService().onGrandRechargeActivity(human);
		Globals.getActivityService().onGrandRechargeDollarActivity(human, dollar);
		Globals.getActivityService().onSeriesRechargeActivity(human, Long.valueOf(totalGold).intValue());
		Globals.getActivityService().onCommonSeriesRechargeActivity(human, totalGold);
		
	}
	//返回给前端的接口 Gc_validater_order
	private static void backMessage(Player player,HumanRechargeOrder order,RechargeTemplate rechargeTemplate,List<RandRewardData> RandRewardDataList){
		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		if(player.getChannelType().isIOS()){
			if(Globals.getServerConfig().isTest()){
				//AAA:ios修改
				gcValidateOrder.setOrderId(order.getDbId());
			}else{
				gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
			}
			
		}else{
			gcValidateOrder.setOrderId(order.getDbId());
		}
		gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
		gcValidateOrder.setPId(rechargeTemplate.getPid());

		if(RandRewardDataList != null && RandRewardDataList.size()>0){
			RandRewardData[] RandRewardDataArr = new RandRewardData[RandRewardDataList.size()];
			for(int i=0;i<RandRewardDataList.size();i++){
				RandRewardDataArr[i]=RandRewardDataList.get(i);
			}
			gcValidateOrder.setRandRewardList(RandRewardDataArr);
		}
		logger.info("-------"+player.getPassportId()+"-[GCValidateOrder]----------返回客户端的接口：：--"
				+ "--getOrderId:"+gcValidateOrder.getOrderId()
				+ "--getResult:"+gcValidateOrder.getResult()
				+ "--getPid:"+rechargeTemplate.getPid());
		player.sendMessage(gcValidateOrder);
	}
	/**
	 * 购买物品
	 * @param player
	 * @param human
	 * @param order
	 * @param cost
	 * @param rechargeTemplate
	 * @return false:失败
	 */
	private static boolean buyItem(Player player,Human human,HumanRechargeOrder order,int cost,RechargeTemplate rechargeTemplate){
		
		int itemId = rechargeTemplate.getItemId();
		
		int itemNum = rechargeTemplate.getItemNum();
		
		//背包管理器
	    HumanBagManager humanBagManager = human.getHumanBagManager();
	    
	    //判断是否可以放商品
		int maxCanAdd = humanBagManager.getMaxCanAdd(itemId);
		if(maxCanAdd!=-1 && maxCanAdd<itemNum)
		{
			player.getHuman().sendSystemMessage(LangConstants.EXCEED_BAG_CAPACITY);
			return false;
		} 
		
		ItemTemplate itemTemplate = Globals.getItemService().getItemTemplWithId(itemId);
		
		switch(ShopCatergoryEnum.indexOf(rechargeTemplate.getSmallCategory())){
		
		case MONTH_CARD:
		{
			HumanMonthCardManager humanMonthCardManager = human.getHumanMonthCardManager();
			List<RandRewardData>  randRewardDataList = humanMonthCardManager.getMonthInitRewardData(itemTemplate.getTime());
			//增加vip点数
			human.getHumanVipNewManager().addOnlyThePassPoint(rechargeTemplate.getVipPoint());
			
			
			CommonLogic.getInstance().giveRandReward(human, randRewardDataList,LogReasons.GoldLogReason.MONTH_CARD_INIT,LogReasons.DiamondLogReason.MONTH_CARD_INIT,LogReasons.CharmLogReason.MONTH_CARD_INIT,LogReasons.ItemLogReason.MONTH_CARD_INIT, true);
			Globals.getLogService().sendMonthcardLog(human, LogReasons.MonthcardLogReason.MONTH_CARD_BUY, LogReasons.MonthcardLogReason.MONTH_CARD_BUY.getReasonText(), humanMonthCardManager.getHumanMonthCard().getDuration());
			
		}
			break;
		case WEEK_CARD:
		{
			HumanWeekCardManager humanWeekCardManager = human.getHumanWeekCardManager();
			List<RandRewardData>  randRewardDataList = humanWeekCardManager.getWeekInitRewardData(itemTemplate.getTime());
			//增加vip点数
			human.getHumanVipNewManager().addOnlyThePassPoint(rechargeTemplate.getVipPoint());
			
			CommonLogic.getInstance().giveRandReward(human, randRewardDataList,LogReasons.GoldLogReason.WEEK_CARD_INIT,LogReasons.DiamondLogReason.WEEK_CARD_INIT,LogReasons.CharmLogReason.WEEK_CARD_INIT,LogReasons.ItemLogReason.WEEK_CARD_INIT, true);
			Globals.getLogService().sendWeekcardLog(human, LogReasons.WeekcardLogReason.WEEK_CARD_BUY, LogReasons.WeekcardLogReason.WEEK_CARD_BUY.getReasonText(), humanWeekCardManager.getHumanWeekCard().getDuration());
		}
			break;
		case COLLECT:
		{
			human.getHumanCollectManager().exchangeAndBuy(itemId,itemNum);
		}
		break;
		case expence:
		{
			/**
			 * 添加经验
			 * 
			 */
			human.getHumanBagManager().addDoubleExp(human,itemId, itemNum);
			human.getHumanVipNewManager().addOnlyThePassPoint(rechargeTemplate.getVipPoint());
			
		}
		break;
		case RENAME:
		{
			/**
			 * 增加vip点数
			 */
			human.getHumanVipNewManager().addOnlyThePassPoint(rechargeTemplate.getVipPoint());
		}
		break;
		case club_change_name:
		{
			human.getHumanVipNewManager().addOnlyThePassPoint(rechargeTemplate.getVipPoint());
		}
		break;
		default:
			break;
		}
		
		//加入背包
		List<Item> itemList = humanBagManager.addItem(itemId, itemNum);
		if(itemList.size()==0){
			logger.warn("玩家[" +player.getPassportId() + "]购买物品失败 id["+itemId+"]");
			return false;
		}
				
		logger.debug("玩家[" +player.getPassportId() + "]购买物品成功 id["+itemId+"]");

		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		if(player.getChannelType().isIOS()){
			gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
		}else{
			gcValidateOrder.setOrderId(order.getDbId());
		}
		gcValidateOrder.setPId(rechargeTemplate.getPid());
		gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
		RandRewardData[] data = new RandRewardData[1];
		RandRewardData rr = new RandRewardData();
		rr.setRewardId(itemId);
		rr.setRewardCount(itemNum);
		data[0] = rr;
		gcValidateOrder.setRandRewardList(data);
		human.sendMessage(gcValidateOrder);
		
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		
		human.sendMessage(humanBagManager.buildHumanBagInfoData());
		
		/*logger.info("玩家["+human.getPassportId()+"]验证 订单号["+order.getId()+"],产品id["+order.getProductId()+"]");
		Globals.getLogService().sendRechargeLog(human, LogReasons.RechargeLogReason.VALIDATE_ORDER,  LogReasons.RechargeLogReason.VALIDATE_ORDER.getReasonText(), order.getDbId(), order.getProductId(),order.getCost());*/
		sendRechargeLog(human,order,order.getCost());
		
		//买一赠一功能
		try{
			Globals.getFunctionService().buyOneSendOne(human, rechargeTemplate, itemId,itemNum);
		 }catch(Exception e){
			  logger.info("买一赠一报错：",e);
		 }
		 return true;
	}
	/**
	 * 购买物品
	 * @param player
	 * @param human
	 * @param order
	 * @param cost
	 * @param rechargeTemplate
	 * @return false:失败
	 */
	private static boolean buyItemGameView(Player player,Human human,HumanRechargeOrder order,int cost,RechargeTemplate rechargeTemplate,long amount){
		
		int itemId = rechargeTemplate.getItemId();
		
		int itemNum = rechargeTemplate.getItemNum();
		
		//背包管理器
		HumanBagManager humanBagManager = human.getHumanBagManager();
		
		//判断是否可以放商品
		int maxCanAdd = humanBagManager.getMaxCanAdd(itemId);
		if(maxCanAdd!=-1 && maxCanAdd<itemNum)
		{
			player.getHuman().sendSystemMessage(LangConstants.EXCEED_BAG_CAPACITY);
			return false;
		} 
		
		ItemTemplate itemTemplate = Globals.getItemService().getItemTemplWithId(itemId);
		
		switch(ShopCatergoryEnum.indexOf(rechargeTemplate.getSmallCategory())){
		
		case MONTH_CARD:
		{
			HumanMonthCardManager humanMonthCardManager = human.getHumanMonthCardManager();
			List<RandRewardData>  randRewardDataList = humanMonthCardManager.getMonthInitRewardData(itemTemplate.getTime());
			
			CommonLogic.getInstance().giveRandReward(human, randRewardDataList,LogReasons.GoldLogReason.MONTH_CARD_INIT,LogReasons.DiamondLogReason.MONTH_CARD_INIT,LogReasons.CharmLogReason.MONTH_CARD_INIT,LogReasons.ItemLogReason.MONTH_CARD_INIT, true);
			Globals.getLogService().sendMonthcardLog(human, LogReasons.MonthcardLogReason.MONTH_CARD_BUY, LogReasons.MonthcardLogReason.MONTH_CARD_BUY.getReasonText(), humanMonthCardManager.getHumanMonthCard().getDuration());
			
		}
		break;
		case WEEK_CARD:
		{
			HumanWeekCardManager humanWeekCardManager = human.getHumanWeekCardManager();
			List<RandRewardData>  randRewardDataList = humanWeekCardManager.getWeekInitRewardData(itemTemplate.getTime());
			CommonLogic.getInstance().giveRandReward(human, randRewardDataList,LogReasons.GoldLogReason.WEEK_CARD_INIT,LogReasons.DiamondLogReason.WEEK_CARD_INIT,LogReasons.CharmLogReason.WEEK_CARD_INIT,LogReasons.ItemLogReason.WEEK_CARD_INIT, true);
			
			Globals.getLogService().sendWeekcardLog(human, LogReasons.WeekcardLogReason.WEEK_CARD_BUY, LogReasons.WeekcardLogReason.WEEK_CARD_BUY.getReasonText(), humanWeekCardManager.getHumanWeekCard().getDuration());
		}
		break;
		
		default:
			break;
		}
		
		//加入背包
		List<Item> itemList = humanBagManager.addItem(itemId, itemNum);
		if(itemList.size()==0){
			logger.warn("玩家[" +player.getPassportId() + "]购买物品失败 id["+itemId+"]");
			return false;
		}
		
		logger.debug("玩家[" +player.getPassportId() + "]购买物品成功 id["+itemId+"]");
		
		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		if(player.getChannelType().isIOS()){
			gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
		}else{
			gcValidateOrder.setOrderId(order.getDbId());
		}
		gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
		RandRewardData[] data = new RandRewardData[1];
		RandRewardData rr = new RandRewardData();
		rr.setRewardId(itemId);
		rr.setRewardCount(1);
		data[0] = rr;
		gcValidateOrder.setRandRewardList(data);
		human.sendMessage(gcValidateOrder);
		logger.debug("玩家[" +player.getPassportId() + "]返回的客户端的订单 IOS-ID["+gcValidateOrder.getOrderId()+"]");
		
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		
		human.sendMessage(humanBagManager.buildHumanBagInfoData());
		
		/*logger.info("玩家["+human.getPassportId()+"]验证 订单号["+order.getId()+"],产品id["+order.getProductId()+"]");
		Globals.getLogService().sendRechargeLog(human, LogReasons.RechargeLogReason.VALIDATE_ORDER,  LogReasons.RechargeLogReason.VALIDATE_ORDER.getReasonText(), order.getDbId(), order.getProductId(),(int)amount);*/
		sendRechargeLog(human,order,amount);
		return true;
	} 
	
	
	
	/**
	 * 购买筹码
	 * @param player
	 * @param human
	 * @param order
	 * @param cost 实际发的钱
	 */
	private static boolean buyGoldMycard(Player player,Human human,HumanRechargeOrder order,int cost, RechargeTemplate rechargeTemplate){
		long diamondAdd =0;
		//判断角色渠道类型
		if(player.getChannelType() == ChannelTypeEnum.UNIPIN){
			
			RechargeTemplate rechargeIdrTemplate = Globals.getRechargeService().rechargeIdrTemplateByCost(player.getChannelType(),cost*100);
			if(rechargeIdrTemplate ==null){
				logger.error("玩家["+human.getPassportId()+"]订单号["+order.getId()+"],印尼钱数["+cost*100+"]不在列表中");
				return false;
			}
			order.setProductId(rechargeIdrTemplate.getId());
			order.setCost(cost*100);
			order.setModified();
			diamondAdd = rechargeIdrTemplate.getItemNum()+rechargeIdrTemplate.getGiftNum();
		}else{
			diamondAdd = rechargeTemplate.getItemNum()+rechargeTemplate.getGiftNum();
		}
		
		//计算VIP加成
		VipNewServer server = Globals.getVipNewServer();
		
		diamondAdd = server.getBuyGoldBylv(human.getHumanVipNewManager().getVipLv(),diamondAdd);
		
		//发货
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		human.giveMoney(diamondAdd, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailReason, order.getProductId(), 1);
		
		
		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		if(player.getChannelType().isIOS()){
			gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
		}else{
			gcValidateOrder.setOrderId(order.getDbId());
		}
		gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
		RandRewardData[] data = new RandRewardData[1];
		RandRewardData rr = new RandRewardData();
		rr.setRewardId(3);
		rr.setRewardCount((int)diamondAdd);
		data[0] = rr;
		gcValidateOrder.setRandRewardList(data);
		human.sendMessage(gcValidateOrder);
		
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		
		/*logger.info("玩家["+human.getPassportId()+"]验证 订单号["+order.getId()+"],产品id["+order.getProductId()+"]");
		Globals.getLogService().sendRechargeLog(human, LogReasons.RechargeLogReason.VALIDATE_ORDER,  LogReasons.RechargeLogReason.VALIDATE_ORDER.getReasonText(), order.getDbId(), order.getProductId(),order.getCost());*/
		sendRechargeLog(human,order,order.getCost());
		return true;
	}
	
	
	/**
	 * 购买筹码
	 * @param player
	 * @param human
	 * @param order
	 * @param cost 实际发的钱
	 */
	@SuppressWarnings("incomplete-switch")
	private static boolean buyGold(Player player,Human human,HumanRechargeOrder order,int cost, RechargeTemplate rechargeTemplate){
		
		
		
		long diamondAdd = rechargeTemplate.getItemNum()+rechargeTemplate.getGiftNum();
		/**
		 * 这个值 是翻倍转盘用的
		 */
        long baseDiamondAdd = diamondAdd;
		String productId = rechargeTemplate.getProductId();
		logger.info("-------1-----------发货中的金币：：diamondAdd=="+diamondAdd+"---productId==="+productId+"----channelID=="+rechargeTemplate.getChannelId());
		
		//首冲判断
		boolean isFirstRecharge = false;
		if(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM1.getIndex() && !human.getGameview().contains(productId)){
			isFirstRecharge=true;
			diamondAdd *=2;
			logger.info("玩家["+human.getPassportId()+"]首冲["+order.getId()+"],产品id["+order.getProductId()+"]双倍");
			human.getGameview().add(productId);
		}
		logger.info("-------2-----------发货中的金币：：diamondAdd=="+diamondAdd);
		
		CategoryEnum categoryEnum = CategoryEnum.valueOf(rechargeTemplate.getCategory());
		/**
	  	 * 如果不是 周特惠礼包  ，，月特惠礼包 才有vip加成
	  	 */
	  	if(!(categoryEnum == CategoryEnum.CATEGORYENUM3 && rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM14.getIndex())
	  			&& !(categoryEnum == CategoryEnum.CATEGORYENUM3 && rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM15.getIndex())
	  			&& !(categoryEnum == CategoryEnum.CATEGORYENUM3 && rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM11.getIndex())){
	  		//计算VIP加成
	  		VipNewServer server = Globals.getVipNewServer();
	  		diamondAdd = server.getBuyGoldBylv(human.getHumanVipNewManager().getVipLv(),diamondAdd);
	  		baseDiamondAdd=diamondAdd;
	  	}
	  	
	  	
		logger.info("-------2-0----------发货中的金币：：diamondAdd=="+diamondAdd);
		//首先必须是筹码，只有金币商店才能用 优惠券
		if(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM1.getIndex()){
			//优惠券 (在所有前边的都算完了 才算优惠券) 
			if(human.getCouponExtraChip()>0 && human.getCouponDurationDate().getTime()> new Date().getTime()){
				long extraChip = human.getCouponExtraChip();
				logger.info("-------2-1--优惠券--------发货中的金币：："+diamondAdd+"---extraChip:"+extraChip);
				diamondAdd=diamondAdd+diamondAdd*extraChip/100;
				logger.info("-------2-2--优惠券--------发货中的金币：："+diamondAdd);
				human.setCouponExtraChip(0);
				human.setCouponDurationDate(null);
				Item item = human.getHumanBagManager().getCouponItem();
				human.getHumanBagManager().removeItem(item.getTemplateId(), 1);
				human.setModified();
				//如果没有优惠券 或者 优惠券过期 就从买一张
			}
			/**
			 * 只要买金币 就执行 随机优惠券
			 */
			CouponTemplate couponTemplate = Globals.getCouponService().getPost();
			human.setCouponExtraChip(couponTemplate.getCouponExtraChip());
			long durationDate = couponTemplate.getCouponDuration()*1000;
			Date endDate = TimeUtils.formatYMDHMSTimeToDate(new Date().getTime()+durationDate);
			human.setCouponDurationDate(endDate);
			int itemId = couponTemplate.getItemID();
			GCObtainCoupon gCObtainCoupon = new GCObtainCoupon();
			gCObtainCoupon.setCouponId(itemId);
			human.getPlayer().sendMessage(gCObtainCoupon);
			if(itemId > 0){
				human.getHumanBagManager().addItem(itemId, 1);
			}
			human.setModified();
			human.getPlayer().sendMessage(human.getHumanBagManager().buildHumanBagInfoData());
		}
		logger.info("-------3-----------发货中的金币：："+diamondAdd);
		//发货
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		/**
		 * 
		 * 首先 功能性付费都 是在用户的功能里 给钱，此支付流程里都不给钱了
		 * 存钱罐  大类：catagrey-4-功能性付费，小类：smallCatagrey-10-储钱罐
		 * 
		 * 
		 * **/
		if(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM4.getIndex() ){/*
			SmallCategoryEnum sce = SmallCategoryEnum.valueOf(rechargeTemplate.getSmallCategory());
			switch(sce)
			{
			case SMALLCATEGORYENUM9:
			{
		        ClubService.clubOperationLock.lock();
		        Jedis j = null;
		        try {
		        	j = Globals.getRedisService().getJedisPool().getResource();
		        	ClubData cd = ClubMessageHandler.checkClub(human);
		        	if(cd!=null)
		        	{
		        		BoardMsgData bm = new BoardMsgData();
		        		String msgId = UUID.randomUUID().toString();
		        		bm.setPassportId(human.getPassportId());
		        		bm.setNoteType(ClubConsts.board_note_type_gift);
		        		bm.setGiftId(rechargeTemplate.getItemId());
		        		ClubMemberData cmd = ClubCache.retrieveMemberIfExist(human.getClubId(), human.getPassportId());
		        		if(cmd==null)
		        		{
		        			logger.error("passportId: "+human.getPassportId()+" 不是俱乐部成员："+human.getClubId());
		        			return false;
		        		}
		        		bm.setZhiwu(cmd.getZhiwu());
		        		String clubId = cd.getId();
		        		long now = System.currentTimeMillis();
		        		j.zadd(RedisKey.CLUB_BOARD_SORT_SET__+clubId, now, msgId);
		        		String key = RedisKey.CLUB_BOARD_CONTENT_KEY__+msgId;
		        		j.set(key, Globals.gson.toJson(bm));
		        		j.expire(key, 7*24*3600);//保留七天
		        		
		        		ClubRechargeTemplate ct = Globals.getClubTemplateService().getClubRechargeTemplate(bm.getGiftId());
		        		human.giveMoney(ct.getItemNum1(), Currency.valueOf(ct.getItem1Id()), true, LogReasons.GoldLogReason.CLUB_GIFT, LogReasons.GoldLogReason.CLUB_GIFT.getReasonText(), -1, 1);
		        		
		        		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		    			if(player.getChannelType().isIOS()){
		    				if(Globals.getServerConfig().isTest()){
		    					//AAA:ios修改
		    					gcValidateOrder.setOrderId(order.getDbId());
		    				}else{
		    					gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
		    				}
		    				
		    			}else{
		    				gcValidateOrder.setOrderId(order.getDbId());
		    			}
		    			gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
		    			RandRewardData[] data = new RandRewardData[1];
		    			RandRewardData rr = new RandRewardData();
		    			rr.setRewardId(ct.getItem1Id());
		    			rr.setRewardCount(ct.getItemNum1());
		    			data[0] = rr;
		    			gcValidateOrder.setRandRewardList(data);
		    			gcValidateOrder.setPId(rechargeTemplate.getPid());
		    			human.sendMessage(gcValidateOrder);
		        		
		        		
		        		j.sadd(RedisKey.CLUB_GIFTED_SET__+msgId, String.valueOf(human.getPassportId()));
		        		j.expire(RedisKey.CLUB_GIFTED_SET__+msgId, 7*24*3600);
		        		
		        		ClubMessageUtils.pushBoardList(clubId);
		        		
		        		ClubGiftRecordEntity cre = new ClubGiftRecordEntity();
		        		cre.setClubId(clubId);
		        		cre.setSenderPassportId(human.getPassportId());
		        		cre.setTs(System.currentTimeMillis());
		        		Globals.getAsyncService().createOperationAndExecuteAtOnce(new AsyncIoOpeOnly() {
							@Override
							public int doIo() {
								ClubGiftRecordtDao clubGiftRecordtDao = Globals.getDaoService().getClubGiftRecordtDao();
								clubGiftRecordtDao.save(cre);
								return STAGE_IO_DONE;
							}
						});
		        	}
		        	else
		        	{
		        		logger.error("passprotId: "+human.getPassportId()+"has no club");
		        	}
				} finally {
					ClubService.clubOperationLock.unlock();
					if(j!=null) {j.close();}
				}
				logger.info("-------9俱乐部礼包, charId: "+human.getPassportId());
				return true;
			}
			case SMALLCATEGORYENUM10:
			{
				diamondAdd=human.getHumanTreasuryManager().spendMoneyBuyTreasury(human,rechargeTemplate.getPid(),order.getProductId());
				logger.info("-------3-储蓄罐----------发货中的金币：："+diamondAdd);
				break;
			}
			}*/
			
//			if(rechargeTemplate.getSmallCategory() == SmallCategoryEnum.SMALLCATEGORYENUM10.getIndex()){
//				diamondAdd=human.getHumanTreasuryManager().spendMoneyBuyTreasury(human,rechargeTemplate.getPid(),order.getProductId());
//				logger.info("-------3-储蓄罐----------发货中的金币：："+diamondAdd);
//			}
		}else{
			/**
			 * 不是能性付费  都走下变
			 */
			logger.info("-------4-给用户加钱----------发货中的金币：："+diamondAdd);
			human.giveMoney(diamondAdd, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailReason, order.getProductId(), 1);
			
			/**
		  	 * 功能 买一曾一
		  	 */
		  try{
			  Globals.getFunctionService().buyOneSendOne(human, rechargeTemplate, Currency.GOLD.getIndex(), Long.valueOf(diamondAdd).intValue());
		  }catch(Exception e){
			  logger.info("买一赠一报错：",e);
		  }
		}
		
		
		if( !Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid())){
			logger.info("-------4-1-返回客户端接口----------发货中的金币：：");
			doublefff(player,rechargeTemplate,order,diamondAdd,human);
		}
		
		
		

		/**
		 * 当pid不是32（翻倍商品）的时候，先把这把需要翻倍的值设置好
		 */
		
		int id = 0;
		int multiple = 0;
		//如果是需要充值翻倍的  同时 当前商品的pid != 32 (即不是翻倍转盘调用过来的)
		logger.info(" 翻倍转盘--发送--1--：isPidNew："+Globals.getMultipleWheelServer().isPidNew(rechargeTemplate.getPid()) );
		logger.info(" 翻倍转盘--发送--2--：！isPid："+!Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid()));
		if(Globals.getMultipleWheelServer().isPidNew(rechargeTemplate.getPid()) && !Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid())){
			
			human.getHumanRechargeManager().setDiamondAdd(baseDiamondAdd);
			//ID
			 id = Globals.getMultipleWheelServer().getId();
			//倍数
			multiple = Globals.getMultipleWheelServer().getMultiple(id);
			human.getHumanRechargeManager().setMultipleDouble(multiple);
			//发消息告诉客户端
			player.sendMessage(sendMessageDM(baseDiamondAdd,id));
		}
		/**
		 * 紧接着 就是pid=32的时候，把 上一把 翻倍
		 */
		long diamon = human.getHumanRechargeManager().getDiamondAdd();
		int multipleDouble = 0;
		logger.info(" 翻倍转盘--发送--3--：isPid："+Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid()));
		logger.info(" 翻倍转盘--发送--4--：diamon："+diamon);
		int dollar = rechargeTemplate.getNum();
		if(Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid()) && diamon != 0){
			multipleDouble = human.getHumanRechargeManager().getMultipleDouble();
			//发货
			String detailRea = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamon*multiple);
			baseDiamondAdd=diamon*multipleDouble;
			human.giveMoney(diamon*multipleDouble, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailRea, order.getProductId(), 1);
			human.getHumanRechargeManager().setDiamondAdd(0);
			
			
			logger.info("-------5-翻倍转盘----------发货中的金币：：diamondAdd=="+baseDiamondAdd+"---diamon::"+diamon+"multipleDouble"+multipleDouble);
			doublefff(player,rechargeTemplate,order,baseDiamondAdd,human);
			//在翻倍转盘 这里  活动金币 和 购买是不一样的
			Globals.getActivityService().onGrandRechargeActivity(human);
			Globals.getActivityService().onGrandRechargeDollarActivity(human, dollar);
			Globals.getActivityService().onSeriesRechargeActivity(human, Long.valueOf(diamon*(multipleDouble)).intValue());
			Globals.getActivityService().onCommonSeriesRechargeActivity(human, diamon*(multipleDouble));
			
		  
		  
		}else{
			//活动这块 活动金币 和 购买是不一样的
			Globals.getActivityService().onGrandRechargeActivity(human);
			Globals.getActivityService().onGrandRechargeDollarActivity(human, dollar);
			Globals.getActivityService().onSeriesRechargeActivity(human, Long.valueOf(baseDiamondAdd).intValue());
			Globals.getActivityService().onCommonSeriesRechargeActivity(human, baseDiamondAdd);
			
		  
		}
		
		
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		
		
		sendRechargeLog(human,order,order.getCost());
		
		logger.info("-------6-结束----------发货中的金币：：diamondAdd=="+diamondAdd+"---diamon::"+diamon+"multipleDouble"+multipleDouble);
		/**
		 * 首次充值 要推送 这个消息
		 */
		//首冲判断
		if(isFirstRecharge){
			logger.info("玩家["+human.getPassportId()+"]首冲------推送消息");
			GCHumanDetailInfo gCHumanDetailInfo = new GCHumanDetailInfo();
			HumanInfoData humanInfoData = Globals.getHumanService().getHumanInfo(human);
			gCHumanDetailInfo.setHuman(humanInfoData);
			player.sendMessage(gCHumanDetailInfo);
		}
		
		if( !(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM4.getIndex()) || !(rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM10.getIndex())){
			/**
			 * 是否是周月礼包
			 */
			boolean isWeekOrMonth =false;
			if(categoryEnum == CategoryEnum.CATEGORYENUM3 && 
					(rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM14.getIndex() 
						|| rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM15.getIndex())){
				isWeekOrMonth=true;
			}
			/**
			 * 如果不是周月礼包 就用下边的 增加vip点数
			 */
			if(!isWeekOrMonth){
				
				int vipPoint =  rechargeTemplate.getVipPoint();
				logger.info("--4--增加VIP点数... ...前-vipPoint:"+vipPoint);
				human.getHumanVipNewManager().addPoint(vipPoint);
				
				logger.info("--4--增加VIP点数... ...后-vipPoint:"+vipPoint);
			}
		}
    	//奖励vip点 
	  	SmallCategoryEnum smallCategoryEnum = SmallCategoryEnum.valueOf(rechargeTemplate.getSmallCategory());
	  	  
	  	/*if(categoryEnum == CategoryEnum.CATEGORYENUM3 ){
	  		  if(smallCategoryEnum == SmallCategoryEnumBackup.SMALLCATEGORYENUM11){
		  		  if(human.getNewGuyGift() == 0){
		  			  human.setNewGuyGift(1);//新手大礼包 一旦购买此状态置为1
		  			  human.setModified();
		  		  }
		  		logger.info("---["+human.getPassportId()+"]-新手礼包------推送消息");
				GCHumanDetailInfo gCHumanDetailInfo = new GCHumanDetailInfo();
				HumanInfoData humanInfoData = Globals.getHumanService().getHumanInfo(human);
				gCHumanDetailInfo.setHuman(humanInfoData);
				player.sendMessage(gCHumanDetailInfo);
	  		  }
	  		human.getHumanGiftManager().updateGift(order.getProductId());
	  	}*/
		
	  	
	  	
	  	/**
	  	 * 周特惠礼包  如果购买一次就关闭
	  	 */
	  	if(categoryEnum == CategoryEnum.CATEGORYENUM3 && rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM14.getIndex()){
	  		human.getHumanRegularTimeManager().changeIsBuy(0,rechargeTemplate);
	  	}
	  	/**
	  	 * 月特惠礼包  如果购买一次就关闭
	  	 */
	  	if(categoryEnum == CategoryEnum.CATEGORYENUM3 && rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM15.getIndex()){
	  		human.getHumanRegularTimeManager().changeIsBuy(1,rechargeTemplate);
	  	}
	  	
	  	
	  
		return true;
	}
	
	
	
	private static void doublefff(Player player,RechargeTemplate rechargeTemplate,HumanRechargeOrder order,long diamondAdd,Human human){
		logger.info("-------4-1-返回客户端接口----------发货中的金币：：PID"+Globals.getLuckySpinService().getPid());
		if(!Globals.getLuckySpinService().isBigWheel(rechargeTemplate.getPid())){
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			if(player.getChannelType().isIOS()){
				if(Globals.getServerConfig().isTest()){
					//AAA:ios修改
					gcValidateOrder.setOrderId(order.getDbId());
				}else{
					gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
				}
				
			}else{
				gcValidateOrder.setOrderId(order.getDbId());
			}
			gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
			RandRewardData[] data = new RandRewardData[1];
			RandRewardData rr = new RandRewardData();
			rr.setRewardId(3);
			rr.setRewardCount((int)diamondAdd);
			data[0] = rr;
			gcValidateOrder.setRandRewardList(data);
			gcValidateOrder.setPId(rechargeTemplate.getPid());
			logger.info("-------4-2-返回客户端接口----------发货中的金币：："+rechargeTemplate.getPid());
			human.sendMessage(gcValidateOrder);

			
		}
		
	}
	
	
	public static GCDoubleTurn sendMessageDM(long doubleMoney,int multipleId){
		
		GCDoubleTurn message = new GCDoubleTurn();
		message.setDoubleMoney(doubleMoney);
		message.setMultipleId(multipleId);
		return message;
	}
	
	
	/**
	 * 购买筹码
	 * @param player
	 * @param human
	 * @param order
	 * @param cost 实际发的钱
	 */
	private static boolean buyGoldGameView(Player player,Human human,HumanRechargeOrder order,int cost, RechargeTemplate template,long amount,int category){
		
		long diamondAdd = cost + template.getGiftNum();
	
		String productId = template.getProductId();
		//首冲判断
		if(category == CategoryEnum.CATEGORYENUM1.getIndex() && !human.getGameview().contains(productId)){
			diamondAdd *=2;
			logger.info("玩家["+human.getPassportId()+"]首冲["+order.getId()+"],产品id["+order.getProductId()+"]双倍");
			human.getGameview().add(productId);
		}
		
		//计算VIP加成
		VipNewServer server = Globals.getVipNewServer();
		
		diamondAdd = server.getBuyGoldBylv(human.getHumanVipNewManager().getVipLv(),diamondAdd);
		
		//发货
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		human.giveMoney(diamondAdd, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailReason, order.getProductId(), 1);
		
		
		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		if(player.getChannelType().isIOS()){
			gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
		}else{
			gcValidateOrder.setOrderId(order.getDbId());
		}
		gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
		RandRewardData[] data = new RandRewardData[1];
		RandRewardData rr = new RandRewardData();
		rr.setRewardId(3);
		rr.setRewardCount((int)diamondAdd);
		data[0] = rr;
		gcValidateOrder.setRandRewardList(data);
		human.sendMessage(gcValidateOrder);
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		sendRechargeLog(human,order,amount);
		return true;
	}
	
	/**
	 * 
	 * @param player
	 * @param human
	 * @param order
	 * @param cost
	 * @param template
	 * @param amount
	 * @param category
	 * @return
	 */
	private static boolean buyGoldMOL(Player player,Human human,HumanRechargeOrder order,int cost, RechargeTemplate template,int amount,int category){
		
		long diamondAdd = cost + template.getGiftNum();
		
		String productId = template.getProductId();
		//首冲判断
		if(category == CategoryEnum.CATEGORYENUM1.getIndex() && !human.getGameview().contains(productId)){
			diamondAdd *=2;
			logger.info("玩家["+human.getPassportId()+"]首冲["+order.getId()+"],产品id["+order.getProductId()+"]双倍");
			human.getGameview().add(productId);
		}
		
		//计算VIP加成
		VipNewServer server = Globals.getVipNewServer();
		
		diamondAdd = server.getBuyGoldBylv(human.getHumanVipNewManager().getVipLv(),diamondAdd);
		
		//发货
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		human.giveMoney(diamondAdd, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailReason, order.getProductId(), 1);
		
		
		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		if(player.getChannelType().isIOS()){
			gcValidateOrder.setOrderId(Long.parseLong(order.getChannelOrderId()));
		}else{
			gcValidateOrder.setOrderId(order.getDbId());
		}
		gcValidateOrder.setResult(OrderStatus.VALIDATE.getIndex());
		RandRewardData[] data = new RandRewardData[1];
		RandRewardData rr = new RandRewardData();
		rr.setRewardId(3);
		rr.setRewardCount((int)diamondAdd);
		data[0] = rr;
		gcValidateOrder.setRandRewardList(data);
		human.sendMessage(gcValidateOrder);
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		sendRechargeLog(human,order,amount);
		return true;
	}
	
	/**
	 * 发货日志统一记录
	 * @param human
	 * @param order
	 * @param amount
	 */
	private static void sendRechargeLog(Human human,HumanRechargeOrder order,long amount){
		logger.info("玩家["+human.getPassportId()+"]验证 订单号["+order.getId()+"],产品id["+order.getProductId()+"]");
		
		int topUpType = order.getTopUpType();
		String reason = LogReasons.RechargeLogReason.VALIDATE_ORDER.getReasonText();
		if(topUpType == TopUpType.MYCARD.getIndex()){
			//200代表mycard支付
			reason = reason+","+200+",mycard订单号 ["+order.getMyCardTradeNo()+"]";
		}else if(topUpType == TopUpType.MOL.getIndex()){
			//201代表MOL支付
			reason = reason+","+201+", MOL币种  ["+order.getCurrencyCode()+"]"+" 金额单位分 ["+order.getAmountmol()+"]";
		}else{
			//渠道类型
			int channelType = human.getPlayer().getChannelType().getIndex();
			
			switch (channelType) {
			case 1://谷歌
				reason = reason+","+channelType+",Google支付  ";
				break;
			case 2://IOS
				reason = reason+","+channelType+",IOS支付 ";
				break;
			case 3://AC
				reason = reason+","+channelType+",AC支付 ";
				break;
			case 13:// 金沙娱乐城
				reason = reason+","+channelType+",金沙娱乐城支付 ";
				break;
			case 14:///GAME_VIEW
				reason = reason+","+channelType+",GAME_VIEW支付";
				break;
			case 15://amazon
				reason = reason+","+channelType+",Amazon支付 UserId=["+order.getUserId()+"] receiptId=["+order.getReceiptId()+"]";
				break;
			default:
				break;
			}
			
		}
		
		Globals.getLogService().sendRechargeLog(human, LogReasons.RechargeLogReason.VALIDATE_ORDER,reason, order.getDbId(), order.getProductId(),(int)amount);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 购买新  分类  category  smallCategory
	 */
	
	
	
	public void giveTheThing(Player player,long orderId,int cost){
			Human human = player.getHuman();
			if(human==null){
				logger.warn("玩家["+player.getPassportId()+"]已经下线");
				return;
			}
			
			//充值管理器
			HumanRechargeManager humanRechargeManager = human.getHumanRechargeManager();
			//订单
			HumanRechargeOrder order =  humanRechargeManager.getOrderById(orderId);
			
			if(order == null){
				logger.warn("玩家["+player.getPassportId()+"]"+"回调 orderid["+ orderId+"]不存在");
				return;
			}
			//验证订单
			humanRechargeManager.verifyOrder(order);
			logger.info("--3--更新订单 到数据库数据库... ...orderID:"+order.getChannelOrderId());
		    RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(order.getChannelId(), order.getProductId());
		    
		    logger.info("--3-1--更新订单 到数据库数据库... ...rechargeTemplate:"+order.getChannelId()+"---------"+order.getProductId());
		    if(rechargeTemplate == null){
		    	logger.warn("玩家["+player.getPassportId()+"]"+"回调 模板数据不存在["+ orderId+"]不存在");
		    	return;
		    }
		    
		    
		    int categrory = rechargeTemplate.getCategory();
		    int smallCategrory = rechargeTemplate.getSmallCategory();
		    boolean fly = false;
		    /**
		     * 类别：1、筹码；2:物品3、礼包 4、功能性付费
		     */
		    //筹码
		    if(categrory == CategoryEnum.CATEGORYENUM1.getIndex()){
		    	//筹码
		    	if(smallCategrory == SmallCategoryEnum.SMALLCATEGORYENUM1.getIndex()){
		    		buyMallGold(player,human,order,cost,rechargeTemplate);
		    	}
		    	
		    //物品
		    }else if(categrory == CategoryEnum.CATEGORYENUM2.getIndex()){
		    	//周卡
		    	if(smallCategrory == SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex()){
		    		
		    	//月卡
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM3.getIndex()){
		    		
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM5.getIndex()){
		    		
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM8.getIndex()){
		    		
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM12.getIndex()){
		    		
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM13.getIndex()){
		    		
		    	}
		    //礼包
		    }else if(categrory == CategoryEnum.CATEGORYENUM3.getIndex()){
		    	if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM11.getIndex()){
		    		
		    	//周 特惠礼包
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM14.getIndex()){
		    		buyMailIWeekAndMonthGold(player,human,order,cost,rechargeTemplate);
		    		
	    		//月 特惠礼包
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM15.getIndex()){
		    		buyMailIWeekAndMonthGold(player,human,order,cost,rechargeTemplate);
		    		
		    	}
		    //功能性付费
		    }else if(categrory == CategoryEnum.CATEGORYENUM4.getIndex()){
		    	if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM6.getIndex()){
		    		
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM7.getIndex()){
		    		
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM9.getIndex()){
		    		
		    	}else if(smallCategrory == SmallCategoryEnumBackup.SMALLCATEGORYENUM10.getIndex()){
		    		
		    	}
		    }
		    
		    
		    
		    /**
		     * 
		     */
		    
		    if(fly){
			  	long money = rechargeTemplate.getNum();
			  	 //首充记录
			  	long isPay = human.getIsPay();
			  	
			  	NewRechargeLogReason reason = NewRechargeLogReason.RECHARGE3;
			  	long now = Globals.getTimeService().now();
			  	if(isPay <= 0){
				  		human.setIsPay(money);
				  		long createTime = human.getCreateTime();
				  		
						
						if(TimeUtils.isSameDay(now, createTime)){
							reason = NewRechargeLogReason.RECHARGE2;//新用户首付
						}else{
							reason = NewRechargeLogReason.RECHARGE1;;//老用户首付
						}
						//首次充值成就
						human.getHumanAchievementManager().updateFirstCharge();
			  	}
			  	int timeDian = TimeUtils.getHourTime(now);
			  	
			  	Globals.getLogService().sendNewRechargeLog(human, reason, reason.getReasonText(),
				  			money, timeDian, player.getChannelType().getIndex(), player.getDeviceModel(), 
				  			player.getClientVersion(),String.valueOf(orderId),player.getCountries(),
				  			human.getAge(),human.getIpCountries(),human.getGirl());	
			  	
			  	int pid = rechargeTemplate.getPid();
			  	  
			  	/**
			  	  * 充值转盘
			  	  */
			  	if(Globals.getLuckySpinService().isBigWheel(pid)){
			  		Globals.getLuckySpinService().spinZhuanpan(human);
			  	}
			  	  
			  	Globals.getTaskNewService().slotTopup(human, ClientType.TASK101.getIndex(), RefreshType.RefreshType4.getIndex(), pid);
			  	Globals.getTaskNewService().spinSlot(human, ClientType.TASK101.getIndex(), RefreshType.RefreshType3.getIndex());
			  	logger.info("--5--订单结束... ...");
		    }
	}
	
	/**
	 * 触发活动
	 */
	public void goActivity(Human human,int dollar,int diamondAdd){
		//活动这块 活动金币 和 购买是不一样的
		Globals.getActivityService().onGrandRechargeActivity(human);
		Globals.getActivityService().onGrandRechargeDollarActivity(human, dollar);
		Globals.getActivityService().onSeriesRechargeActivity(human, diamondAdd);
		Globals.getActivityService().onCommonSeriesRechargeActivity(human, diamondAdd);
	}
	
	
	private boolean buyMailIWeekAndMonthGold(Player player,Human human,HumanRechargeOrder order,int cost, RechargeTemplate rechargeTemplate) {
		long diamondAdd = rechargeTemplate.getItemNum()+rechargeTemplate.getGiftNum();
		
        
		String productId = rechargeTemplate.getProductId();
		logger.info("-------1-----------发货中的金币：：diamondAdd=="+diamondAdd+"---productId==="+productId+"----channelID=="+rechargeTemplate.getChannelId());
		
		//发货
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		/**
		 * 不是能性付费  都走下变
		 */
		logger.info("-------4-给用户加钱----------发货中的金币：："+diamondAdd);
		human.giveMoney(diamondAdd, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailReason, order.getProductId(), 1);
		
		
		if( !Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid())){
			doublefff(player,rechargeTemplate,order,diamondAdd,human);
		}
		
		/**
		 * 触发活动
		 */
		goActivity(human,rechargeTemplate.getNum(),Long.valueOf(diamondAdd).intValue());
		
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		
		sendRechargeLog(human,order,order.getCost());
		
	  	
	  	/**
	  	 * 周特惠礼包  如果购买一次就关闭
	  	 */
	  	if(rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM14.getIndex()){
	  		human.getHumanRegularTimeManager().changeIsBuy(0,rechargeTemplate);
	  	}
	  	/**
	  	 * 月特惠礼包  如果购买一次就关闭
	  	 */
	  	if(rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM15.getIndex()){
	  		human.getHumanRegularTimeManager().changeIsBuy(1,rechargeTemplate);
	  	}
	  	
		return true;
		
	}
	/**
	 * 购买金币
	 * @param player
	 * @param human
	 * @param order
	 * @param cost
	 * @param rechargeTemplate
	 * @return
	 */
	private boolean buyMallGold(Player player,Human human,HumanRechargeOrder order,int cost, RechargeTemplate rechargeTemplate) {
		long diamondAdd = rechargeTemplate.getItemNum()+rechargeTemplate.getGiftNum();
		String productId = rechargeTemplate.getProductId();
		logger.info("-------1-----------发货中的金币：：diamondAdd=="+diamondAdd+"---productId==="+productId+"----channelID=="+rechargeTemplate.getChannelId());
		
		//首冲判断
		boolean isFirstRecharge = false;
		if(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM1.getIndex() && !human.getGameview().contains(productId)){
			isFirstRecharge=true;
			diamondAdd *=2;
			logger.info("玩家["+human.getPassportId()+"]首冲["+order.getId()+"],产品id["+order.getProductId()+"]双倍");
			human.getGameview().add(productId);
		}
		logger.info("-------2-----------发货中的金币：：diamondAdd=="+diamondAdd);
		
		
		CategoryEnum categoryEnum = CategoryEnum.valueOf(rechargeTemplate.getCategory());
		VipNewServer server = Globals.getVipNewServer();
		diamondAdd = server.getBuyGoldBylv(human.getHumanVipNewManager().getVipLv(),diamondAdd);
	
	  	
	  	
		logger.info("-------2-0----------发货中的金币：：diamondAdd=="+diamondAdd);
		//首先必须是筹码，只有金币商店才能用 优惠券
		//优惠券 (在所有前边的都算完了 才算优惠券) 
		if(human.getCouponExtraChip()>0 && human.getCouponDurationDate().getTime()> new Date().getTime()){
			long extraChip = human.getCouponExtraChip();
			logger.info("-------2-1--优惠券--------发货中的金币：："+diamondAdd+"---extraChip:"+extraChip);
			diamondAdd=diamondAdd+diamondAdd*extraChip/100;
			logger.info("-------2-2--优惠券--------发货中的金币：："+diamondAdd);
			human.setCouponExtraChip(0);
			human.setCouponDurationDate(null);
			Item item = human.getHumanBagManager().getCouponItem();
			human.getHumanBagManager().removeItem(item.getTemplateId(), 1);
			human.setModified();
			//如果没有优惠券 或者 优惠券过期 就从买一张
		}
		/**
		 * 只要买金币 就执行 随机优惠券
		 */
		CouponTemplate couponTemplate = Globals.getCouponService().getPost();
		human.setCouponExtraChip(couponTemplate.getCouponExtraChip());
		long durationDate = couponTemplate.getCouponDuration()*1000;
		Date endDate = TimeUtils.formatYMDHMSTimeToDate(new Date().getTime()+durationDate);
		human.setCouponDurationDate(endDate);
		int itemId = couponTemplate.getItemID();
		GCObtainCoupon gCObtainCoupon = new GCObtainCoupon();
		gCObtainCoupon.setCouponId(itemId);
		human.getPlayer().sendMessage(gCObtainCoupon);
		if(itemId > 0){
			human.getHumanBagManager().addItem(itemId, 1);
		}
		human.setModified();
		logger.info("-------3-----------发货中的金币：："+diamondAdd);
		
		//发货
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		human.giveMoney(diamondAdd, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailReason, order.getProductId(), 1);
		
		if( !Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid())){
			doublefff(player,rechargeTemplate,order,diamondAdd,human);
		}
		

		/**
		 * 当pid不是32（翻倍商品）的时候，先把这把需要翻倍的值设置好
		 */
		
		int id = 0;
		int multiple = 0;
		//如果是需要充值翻倍的  同时 当前商品的pid != 32 (即不是翻倍转盘调用过来的)
		logger.info(" 翻倍转盘--发送--1--：isPidNew："+Globals.getMultipleWheelServer().isPidNew(rechargeTemplate.getPid()) );
		logger.info(" 翻倍转盘--发送--2--：！isPid："+!Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid()));
		if(Globals.getMultipleWheelServer().isPidNew(rechargeTemplate.getPid()) && !Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid())){
			
			human.getHumanRechargeManager().setDiamondAdd(diamondAdd);
			//ID
			 id = Globals.getMultipleWheelServer().getId();
			//倍数
			multiple = Globals.getMultipleWheelServer().getMultiple(id);
			human.getHumanRechargeManager().setMultipleDouble(multiple);
			//发消息告诉客户端
			player.sendMessage(sendMessageDM(diamondAdd,id));
		}
		/**
		 * 紧接着 就是pid=32的时候，把 上一把 翻倍
		 */
		long diamon = human.getHumanRechargeManager().getDiamondAdd();
		int multipleDouble = 0;
		logger.info(" 翻倍转盘--发送--3--：isPid："+Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid()));
		logger.info(" 翻倍转盘--发送--4--：diamon："+diamon);
		int dollar = rechargeTemplate.getNum();
		if(Globals.getMultipleWheelServer().isPid(rechargeTemplate.getPid()) && diamon != 0){
			multipleDouble = human.getHumanRechargeManager().getMultipleDouble();
			//发货
			String detailRea = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamon*multiple);
			diamondAdd=diamon*multipleDouble;
			human.giveMoney(diamon*multipleDouble, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailRea, order.getProductId(), 1);
			human.getHumanRechargeManager().setDiamondAdd(0);
			
			
			logger.info("-------5-翻倍转盘----------发货中的金币：：diamondAdd=="+diamondAdd+"---diamon::"+diamon+"multipleDouble"+multipleDouble);
			doublefff(player,rechargeTemplate,order,diamondAdd,human);
			//在翻倍转盘 这里  活动金币 和 购买是不一样的
			Globals.getActivityService().onGrandRechargeActivity(human);
			Globals.getActivityService().onGrandRechargeDollarActivity(human, dollar);
			Globals.getActivityService().onSeriesRechargeActivity(human, Long.valueOf(diamon*(multipleDouble)).intValue());
			Globals.getActivityService().onCommonSeriesRechargeActivity(human, diamon*(multipleDouble));
		}else{
			//活动这块 活动金币 和 购买是不一样的
			Globals.getActivityService().onGrandRechargeActivity(human);
			Globals.getActivityService().onGrandRechargeDollarActivity(human, dollar);
			Globals.getActivityService().onSeriesRechargeActivity(human, Long.valueOf(diamondAdd).intValue());
			Globals.getActivityService().onCommonSeriesRechargeActivity(human, diamondAdd);
		}
		
		
		Globals.getRechargeService().onRecharge(human, order.getProductId());
		
		
		sendRechargeLog(human,order,order.getCost());
		
		logger.info("-------6-结束----------发货中的金币：：diamondAdd=="+diamondAdd+"---diamon::"+diamon+"multipleDouble"+multipleDouble);
		/**
		 * 首次充值 要推送 这个消息
		 */
		//首冲判断
		if(isFirstRecharge){
			logger.info("玩家["+human.getPassportId()+"]首冲------推送消息");
			GCHumanDetailInfo gCHumanDetailInfo = new GCHumanDetailInfo();
			HumanInfoData humanInfoData = Globals.getHumanService().getHumanInfo(human);
			gCHumanDetailInfo.setHuman(humanInfoData);
			player.sendMessage(gCHumanDetailInfo);
		}
		
	  	
		return true;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
