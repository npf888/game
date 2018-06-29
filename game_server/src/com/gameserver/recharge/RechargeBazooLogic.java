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

public class RechargeBazooLogic {
	
	private static Logger logger = Loggers.rechargeLogger;
	

	
	
	/**
	 * 发货
	 * @param player
	 * @param orderId
	 * @param cost
	 */
	public static void onRecharge(Player player,long orderId,int cost){
		
        Human human = player.getHuman();
		
		if(human==null){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		//充值管理器
		HumanRechargeManager humanRechargeManager = human.getHumanRechargeManager();
		//订单
		HumanRechargeOrder order =  humanRechargeManager.getOrderById(orderId);
		
		if(order == null){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]"+"回调 orderid["+ orderId+"]不存在");
			return;
		}
		//验证订单
		humanRechargeManager.verifyOrder(order);
		logger.info("[订单"+player.getPassportId()+"]--3--更新订单 到数据库数据库... ...orderID:"+order.getChannelOrderId());
	    RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(order.getChannelId(), order.getProductId());
	    
	    logger.info("[订单"+player.getPassportId()+"]--3-1--更新订单 到数据库数据库... ...rechargeTemplate:"+order.getChannelId()+"---------"+order.getProductId());
	    if(rechargeTemplate == null){
	    	logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]"+"回调 模板数据不存在["+ orderId+"]不存在");
	    	return;
	    }
	    
	    
	    int categrory = rechargeTemplate.getCategory();
//	    int smallCategory = rechargeTemplate.getSmallCategory();
	    boolean fly = false;
	    /**
	     * 类别：1、筹码；2:物品3、礼包 4、功能性付费
	     */
	    if(categrory==1 || categrory == 3 || categrory ==4 ){
//	    	if(smallCategory==SmallCategoryEnum.SMALLCATEGORYENUM16.getIndex()){
	    	fly = buyGold(player,human,order,cost,rechargeTemplate);
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
			  	}
			  	int timeDian = TimeUtils.getHourTime(now);
			  	
			  	Globals.getLogService().sendNewRechargeLog(human, reason, reason.getReasonText(),
			  			money, timeDian, player.getChannelType().getIndex(), player.getDeviceModel(), 
			  			player.getClientVersion(),String.valueOf(orderId),player.getCountries(),
			  			human.getAge(),human.getIpCountries(),human.getGirl());	
			  	  
			  	logger.info("[订单"+player.getPassportId()+"]--5--订单结束... ...");
	    }
		
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
		String productId = rechargeTemplate.getProductId();
		logger.info("[订单"+player.getPassportId()+"]-------1-----------发货中的金币：：diamondAdd=="+diamondAdd+"---productId==="+productId+"----channelID=="+rechargeTemplate.getChannelId());
		
		//首冲判断
		boolean isFirstRecharge = false;
		if(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM1.getIndex() && !human.getGameview().contains(productId)){
			isFirstRecharge=true;
			diamondAdd *=2;
			logger.info("[订单"+player.getPassportId()+"]玩家["+human.getPassportId()+"]首冲["+order.getId()+"],产品id["+order.getProductId()+"]双倍");
			human.getGameview().add(productId);
		}
		
	  	
		logger.info("[订单"+player.getPassportId()+"]-------2-0----------发货中的金币：：diamondAdd=="+diamondAdd);
		
		//发货
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.RECHARGE.getReasonText(), order.getProductId(),diamondAdd);
		
		logger.info("[订单"+player.getPassportId()+"]-------4-给用户加钱----------发货中的金币：："+diamondAdd);
		human.giveMoney(diamondAdd, Currency.GOLD, false, LogReasons.GoldLogReason.RECHARGE, detailReason, order.getProductId(), 1);
			
		
		logger.info("[订单"+player.getPassportId()+"]-------4-1-返回客户端接口----------发货中的金币：：");
		doublefff(player,rechargeTemplate,order,diamondAdd,human);
		
		
		sendRechargeLog(human,order,order.getCost());
		
		/**
		 * 首次充值 要推送 这个消息
		 */
		//首冲判断
		if(isFirstRecharge){
			logger.info("[订单"+player.getPassportId()+"]玩家["+human.getPassportId()+"]首冲------推送消息");
			GCHumanDetailInfo gCHumanDetailInfo = new GCHumanDetailInfo();
			HumanInfoData humanInfoData = Globals.getHumanService().getHumanInfo(human);
			gCHumanDetailInfo.setHuman(humanInfoData);
			player.sendMessage(gCHumanDetailInfo);
		}
		return true;
	}
	
	
	
	private static void doublefff(Player player,RechargeTemplate rechargeTemplate,HumanRechargeOrder order,long diamondAdd,Human human){
		logger.info("[订单"+player.getPassportId()+"]-------4-1-返回客户端接口----------发货中的金币：：PID"+Globals.getLuckySpinService().getPid());
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
		logger.info("[订单"+player.getPassportId()+"]-------4-2-返回客户端接口----------发货中的金币：："+rechargeTemplate.getPid());
		human.sendMessage(gcValidateOrder);
		
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
	
	
	
	
	
	
}
