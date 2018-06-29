 package com.gameserver.recharge.handler;



import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.db.model.UserInfo;
import com.game.webserver.recharge.CheckGooglePlayRechargeParam;
import com.game.webserver.recharge.CheckGooglePlayRechargeRes;
import com.game.webserver.recharge.CheckIOSRechargeParam;
import com.game.webserver.recharge.CheckIOSRechargeRes;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanRechargeManager;
import com.gameserver.player.Player;
import com.gameserver.player.enums.ChannelTypeEnum;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.RechargeBazooLogic;
import com.gameserver.recharge.RechargeLogic;
import com.gameserver.recharge.async.AsyncHttpOperation;
import com.gameserver.recharge.async.AsyncHttpOperation.IAsyncHttpCallBack;
import com.gameserver.recharge.data.AmazonDelivery;
import com.gameserver.recharge.data.HumanRechargeOrderInfoData;
import com.gameserver.recharge.data.PurchaseData;
import com.gameserver.recharge.enums.OrderStatus;
import com.gameserver.recharge.enums.TopUpType;
import com.gameserver.recharge.https.AmazonVerifyReceipt;
import com.gameserver.recharge.https.AuthCodeInfo;
import com.gameserver.recharge.https.Parameter;
import com.gameserver.recharge.https.TradeQuery;
import com.gameserver.recharge.msg.CGCouponExist;
import com.gameserver.recharge.msg.CGOrderAmazon;
import com.gameserver.recharge.msg.CGOrderAmazonDelivery;
import com.gameserver.recharge.msg.CGRequestOrder;
import com.gameserver.recharge.msg.CGRequestOrderMol;
import com.gameserver.recharge.msg.CGRequestOrderMycard;
import com.gameserver.recharge.msg.CGValidateOrder;
import com.gameserver.recharge.msg.CGValidateOrderMol;
import com.gameserver.recharge.msg.CGValidateOrderMycard;
import com.gameserver.recharge.msg.GCCouponExist;
import com.gameserver.recharge.msg.GCRequestOrder;
import com.gameserver.recharge.msg.GCRequestOrderThirdParty;
import com.gameserver.recharge.msg.GCValidateOrder;
import com.gameserver.recharge.template.RechargeTemplate;

/**
 * 充值
 * @author wayne
 *
 */
public class RechargeMessageHandler {

	private Logger logger = Loggers.rechargeLogger;
	
	/**
	 * 下 单
	 * 
	 * 处理申请订单
	 * @param player
	 * @param cgRequestOrder
	 */
	public void handleRequestOrder(Player player, CGRequestOrder cgRequestOrder) {
		
		Human human = player.getHuman();
		
		// 数据表里面的PID
		int productId = cgRequestOrder.getProductId();
		
		
		RechargeTemplate  rechargeTemplate =  Globals.getRechargeService().getTemplate(player.getChannelType().getIndex(), productId);
		
		if(rechargeTemplate == null){
			player.sendSystemMessage(LangConstants.PRODUCT_NO_EXIST);
			logger.warn("玩家["+human.getPassportId()+"]申请订单,产品id["+productId+"]不存在");
			return;
		}
		
		//创建订单
		HumanRechargeOrder order = Globals.getRechargeService().generateOrder(human,productId,rechargeTemplate.getNum());
		order.setTopUpType(TopUpType.COMMON.getIndex());
		
		order.setModified();
		
		//加入角色未处理订单列表
		human.getHumanRechargeManager().addOrder(order);
		
		HumanRechargeOrderInfoData humanRechargeOrderInfoData= HumanRechargeOrderInfoData.convertFromHumanRechargeOrder(order);
		
		//原先的17(google支付)  18(ios支付) 还走原先的
		if(player.getChannelType().getIndex() == ChannelTypeEnum.IOS18.getIndex() 
				|| player.getChannelType().getIndex() == ChannelTypeEnum.GOOGLEPLAY17.getIndex()){
			GCRequestOrder gcRequestOrder = new GCRequestOrder();
			gcRequestOrder.setOrder(humanRechargeOrderInfoData);
			player.sendMessage(gcRequestOrder);
			
		}else if(player.getChannelType().getIndex() == ChannelTypeEnum.BOQU19.getIndex()){//博趣平台的 支付
			GCRequestOrderThirdParty thirdParty = new GCRequestOrderThirdParty();
			//组装json
			JSONObject jb = new JSONObject();
//			jb.put("gameid", 111);//TODO 这个ID咋整
			UserInfo userInfo = Globals.getDaoService().getUserInfoDao().get(human.getPassportId());
			jb.put("access_token", userInfo.getAccessToken());
			jb.put("token_type", userInfo.getTokenType());
			
			jb.put("game_nickname", human.getName());
			jb.put("game_user_id", human.getPassportId());
			
			jb.put("price", rechargeTemplate.getNum());
			jb.put("info", order.getId()+"&"+human.getPassportId());
			jb.put("title", rechargeTemplate.getLangDesc());
			jb.put("redirect_url", Globals.getServerConfig().getBoquRedirectUrl()+"?channel=hangzhou&username="+userInfo.getUsername());
			jb.put("open_id", userInfo.getUsername());
			logger.info("当前商品信息："+jb.toJSONString());
			String base64JB = new String(Base64.getEncoder().encode(jb.toJSONString().getBytes()));
			thirdParty.setHtmlPage(Globals.getServerConfig().getBoquURL()+"?data="+base64JB);
			player.sendMessage(thirdParty);
		}
		logger.info("玩家["+human.getPassportId()+"]申请订单 订单号["+order.getId()+"],产品id["+productId+"]");
		Globals.getLogService().sendRechargeLog(human, LogReasons.RechargeLogReason.REQUEST_ORDER,  LogReasons.RechargeLogReason.REQUEST_ORDER.getReasonText(), order.getId(), productId,order.getCost());
		
	}
	
	
	

	/**
	 * 
	 * 支付成功  回调
	 * 
	 * 处理验证订单
	 * @param player
	 * @param cgValidateOrder
	 * @throws UnsupportedEncodingException 
	 */
	public void handleValidateOrder(Player player,CGValidateOrder cgValidateOrder)  {

		Human human = player.getHuman();
		HumanRechargeManager humanRechargeManager= human.getHumanRechargeManager();
		logger.info("[订单"+player.getPassportId()+"]开始验证订单... ...userId:"+player.getPassportId()+"--当前渠道："+player.getChannelType());
		
		if(player.getChannelType() == ChannelTypeEnum.IOS18){//IOS支付
			Globals.getChannelDiffService().iosRecharge(player,cgValidateOrder.getSignature(),cgValidateOrder.getPurchaseData(),humanRechargeManager);
		
		}else if(player.getChannelType() == ChannelTypeEnum.GOOGLEPLAY17){//谷歌支付
			Globals.getChannelDiffService().googlePlayRecharge(player,cgValidateOrder.getSignature(),cgValidateOrder.getPurchaseData(),humanRechargeManager);
		
		}else if(player.getChannelType() == ChannelTypeEnum.BOQU19){//博趣 平台的支付
			Globals.getChannelDiffService().boquRecharge(player,cgValidateOrder.getSignature(),cgValidateOrder.getPurchaseData(),humanRechargeManager);
		}
	}
	
	
	
	
	
	
	

	/**
	 * mycard 支付返回
	 * @param player
	 * @param cgValidateOrderMycard
	 */
	public void handleValidateOrderMycard(Player player, CGValidateOrderMycard cgValidateOrderMycard) {
		
	
		long orderId = cgValidateOrderMycard.getOrderId();
		
		Human human = player.getHuman();
		
		HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(orderId);
		
		if(hro != null && !hro.getAuthCode().equals("")){
			
			Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(new TradeQuery(human,hro.getAuthCode()));
		}
	}




	public void handleRequestOrderMycard(Player player, CGRequestOrderMycard cgRequestOrderMycard) {
		Human human = player.getHuman();
		
		// 数据表里面的PID
		int productId = cgRequestOrderMycard.getProductId();
		
		
		RechargeTemplate  rechargeTemplate =  Globals.getRechargeService().getTemplate(player.getChannelType().getIndex(), productId);
		
		if(rechargeTemplate == null){
			player.sendSystemMessage(LangConstants.PRODUCT_NO_EXIST);
			logger.warn("玩家["+human.getPassportId()+"]申请订单,产品id["+productId+"]不存在");
			return;
		}
		
		//创建订单
		HumanRechargeOrder order = Globals.getRechargeService().generateOrder(human,productId,rechargeTemplate.getNum());
		order.setTopUpType(TopUpType.MYCARD.getIndex());
		order.setModified();
		
		//加入角色未处理订单列表
		human.getHumanRechargeManager().addOrder(order);
		
		HumanRechargeOrderInfoData humanRechargeOrderInfoData= HumanRechargeOrderInfoData.convertFromHumanRechargeOrder(order);
		GCRequestOrder gcRequestOrder = new GCRequestOrder();
		gcRequestOrder.setOrder(humanRechargeOrderInfoData);
		player.sendMessage(gcRequestOrder);
		logger.info("玩家["+human.getPassportId()+"]申请订单 订单号["+order.getId()+"],产品id["+productId+"]");
		Globals.getLogService().sendRechargeLog(human, LogReasons.RechargeLogReason.REQUEST_ORDER,  LogReasons.RechargeLogReason.REQUEST_ORDER.getReasonText(), order.getId(), productId,order.getCost());
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("FacServiceId", Parameter.facServiceId);
    	paramMap.put("FacTradeSeq", String.valueOf(order.getId()));
    	paramMap.put("TradeType", "1");
    	paramMap.put("CustomerId", String.valueOf(human.getPassportId()));
    	paramMap.put("ProductName", rechargeTemplate.getProductId());
    	paramMap.put("Amount", String.valueOf(rechargeTemplate.getNum()));
    	paramMap.put("Currency", "TWD");
    	paramMap.put("SandBoxMode", "true");
    
		Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(new AuthCodeInfo(human,paramMap,order.getId()));
	}



    /**
     * 亚马逊验证订单
     * @param player
     * @param cgOrderAmazon
     */
	public void handleOrderAmazon(Player player, CGOrderAmazon cgOrderAmazon) {
		
        long orderId = cgOrderAmazon.getOrderId();
		
		Human human = player.getHuman();
		
		HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(orderId);
		
		if(hro != null){
			//客户亚马逊ID
			String userId = cgOrderAmazon.getUserId();
			//收据ID
			String receiptId = cgOrderAmazon.getReceiptId();
			
		   Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(new AmazonVerifyReceipt(human,userId,receiptId,orderId));
		}
	}



    /**
     * 处理亚马逊没有验证过的订单
     * @param player
     * @param cgOrderAmazonDelivery
     */
	public void handleOrderAmazonDelivery(Player player, CGOrderAmazonDelivery cgOrderAmazonDelivery) {
		
		AmazonDelivery[] list = cgOrderAmazonDelivery.getAmazonDeliveryList();
		
		logger.info("AmazonDelivery() ===  "+JSON.toJSONString(list));
		
		Human human = player.getHuman();
		
		for(AmazonDelivery ad : list){
			//客户亚马逊ID
			String userId = ad.getUserId();
			//收据ID
			String receiptId = ad.getReceiptId();
			//产品ID
			int productId = ad.getProductId();
			
			HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderByProductId(productId,human.getPlayer().getChannelType().getIndex());
			
			if(hro != null){
				 Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(new AmazonVerifyReceipt(human,userId,receiptId,hro.getId()));
			}else{
				logger.error("Amazon == error == "+JSON.toJSONString(ad));
			}
			
		}
	}



    /**
     * 获取MOL订单
     * @param player
     * @param cgRequestOrderMol
     */
	public void handleRequestOrderMol(Player player, CGRequestOrderMol cgRequestOrderMol) {
		
        Human human = player.getHuman();
		
		// 数据表里面的PID
		int productId = cgRequestOrderMol.getProductId();
		
		
		RechargeTemplate  rechargeTemplate =  Globals.getRechargeService().getTemplate(player.getChannelType().getIndex(), productId);
		
		if(rechargeTemplate == null){
			player.sendSystemMessage(LangConstants.PRODUCT_NO_EXIST);
			logger.warn("玩家["+human.getPassportId()+"]申请订单,产品id["+productId+"]不存在");
			return;
		}
		
		//创建订单
		HumanRechargeOrder order = Globals.getRechargeService().generateOrder(human,productId,rechargeTemplate.getNum());
		order.setTopUpType(TopUpType.MOL.getIndex());
		order.setModified();
		
		//加入角色未处理订单列表
		human.getHumanRechargeManager().addOrder(order);
		
		HumanRechargeOrderInfoData humanRechargeOrderInfoData= HumanRechargeOrderInfoData.convertFromHumanRechargeOrder(order);
		GCRequestOrder gcRequestOrder = new GCRequestOrder();
		gcRequestOrder.setOrder(humanRechargeOrderInfoData);
		player.sendMessage(gcRequestOrder);
		logger.info("玩家["+human.getPassportId()+"]申请订单 订单号["+order.getId()+"],产品id["+productId+"]");
		Globals.getLogService().sendRechargeLog(human, LogReasons.RechargeLogReason.REQUEST_ORDER,  LogReasons.RechargeLogReason.REQUEST_ORDER.getReasonText(), order.getId(), productId,order.getCost());
		
	}



    /**
     * 验证MOL订单
     * @param player
     * @param cgValidateOrderMol
     */
	public void handleValidateOrderMol(Player player, CGValidateOrderMol vom) {
		
		/** 订单号 */
		long orderId = vom.getOrderId();
		/** 币种 */
		String currencyCode = vom.getCurrencyCode();
		/** 单位是分 */
		int amount = vom.getAmount();
		/** 发放的游戏币PID为-1的时候有效 */
		double virtualCurrencyAmount = Double.valueOf(vom.getVirtualCurrencyAmount());
		
		String paymentId = vom.getPaymentId();
		
		Human human = player.getHuman();
		
		HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(orderId);
		
		if(hro == null){
			return;
		}
		hro.setCurrencyCode(currencyCode);
		hro.setAmountmol(amount);
		hro.setPaymentIdmol(paymentId);
		if(hro.getProductId() == -1){//点卡支付
			RechargeLogic.onRechargeMOL(player,orderId,virtualCurrencyAmount,amount);
		}else{//普通购买商品支付
			RechargeLogic.onRecharge(player,orderId,0);
		}
		
		hro.setModified();
		
	}




	public void handleCouponExist(Player player, CGCouponExist cgCouponExist) {
		GCCouponExist gCCouponExist = new GCCouponExist();
		Date date = player.getHuman().getCouponDurationDate();
		long now = Globals.getTimeService().now();
		if(date != null && now >= date.getTime()){//没有优惠券
			gCCouponExist.setCouponExist(0);
		}else{//有优惠券
			gCCouponExist.setCouponExist(1);
			//付费新手引导 通知客户端
			player.getHuman().getHumanPayguideManager().sendCouponPayGuide();
		}
		player.sendMessage(gCCouponExist);
	}
	
}
