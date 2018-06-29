package com.gameserver.recharge;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.InitializeRequired;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.game.webserver.recharge.CheckBoquRechargeParam;
import com.game.webserver.recharge.CheckBoquRechargeRes;
import com.game.webserver.recharge.CheckGooglePlayRechargeParam;
import com.game.webserver.recharge.CheckGooglePlayRechargeRes;
import com.game.webserver.recharge.CheckIOSRechargeParam;
import com.game.webserver.recharge.CheckIOSRechargeRes;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanRechargeManager;
import com.gameserver.player.Player;
import com.gameserver.recharge.async.AsyncHttpOperation;
import com.gameserver.recharge.callback.BoquRechargeCallBack;
import com.gameserver.recharge.callback.GooglePlayRechargeCallBack;
import com.gameserver.recharge.callback.IOSRechargeCallBack;
import com.gameserver.recharge.data.BoquData;
import com.gameserver.recharge.data.PurchaseData;
import com.gameserver.recharge.enums.OrderStatus;
import com.gameserver.recharge.msg.GCValidateOrder;
import com.gameserver.recharge.template.RechargeTemplate;

/**
 * 不同渠道的支付  分开走
 * @author JavaServer
 *
 */
public class ChannelDiffService implements InitializeRequired{
	private Logger logger = Loggers.rechargeLogger;
	@Override
	public void init() {
		
	}

	/**
	 * IOS 支付
	 * @param player
	 * @param signature
	 * @param purchaseData
	 * @param humanRechargeManager
	 */
	public void iosRecharge(Player player,String signature,String purchaseData,HumanRechargeManager humanRechargeManager) {
		logger.info("[订单"+player.getPassportId()+"]开始验证订单... IOS ...userId");
		String orderIdStr = signature;
		logger.info("[订单"+player.getPassportId()+"]-----------orderId::"+orderIdStr);
		long orderId = Long.parseLong(orderIdStr);
		HumanRechargeOrder order  = humanRechargeManager.getOrderByChannelOrderId(orderIdStr);
		//检查订单是否存在
		if(order != null)
		{
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,渠道订单id["+order.getChannelOrderId()+"]已经验证");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(1);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}
		if(Globals.getServerConfig().isTest()){
			//AAA:ios修改
			/*purchaseData = JSON.parseObject(cgValidateOrder.getPurchaseData(),PurchaseData.class);
			if(purchaseData.getProductId().equalsIgnoreCase("android.test.purchased"))
			{*/
				HumanRechargeOrder orderH =humanRechargeManager.getOrderById(Long.valueOf(orderIdStr));
				if(orderH != null){
					RechargeBazooLogic.onRecharge(player,orderH.getId(),orderH.getCost());
				}
				return;
//			}
		}
	  logger.info("[订单"+player.getPassportId()+"]IOS-1--订单... ...userId:"+player.getPassportId());
	  ios(player,orderId,purchaseData);
		
	}
	private void ios(Player player,long orderId,String receiptData){
		Human human = player.getHuman();
		if(human==null){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		
		logger.warn("[订单"+player.getPassportId()+"]IOS 去登录服务验证前：["+player.getPassportId()+"]");
		
		//发送异步调用
		CheckIOSRechargeParam checkIOSRechargeParam = new CheckIOSRechargeParam(Globals.getLocalConfig().getRequestDomain(),"api/recharge/ios.json",true);
		checkIOSRechargeParam.setOrderId(orderId);
		checkIOSRechargeParam.setReceiptData(receiptData);
		checkIOSRechargeParam.setSandbox(Globals.getServerConfig().isTest());
		logger.warn("[订单"+player.getPassportId()+"]IOS 去登录服务验证后：["+player.getPassportId()+"]"+orderId);
		logger.warn("[订单"+player.getPassportId()+"]IOS 去登录服务验证后：["+player.getPassportId()+"]"+receiptData);
		
		
		AsyncHttpOperation<CheckIOSRechargeRes> asyncHttpOper = new AsyncHttpOperation<CheckIOSRechargeRes>(player,checkIOSRechargeParam, new IOSRechargeCallBack());
		Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(asyncHttpOper);
		
	}

	
	
	
	
	
	
	/**
	 * googlePlay 支付
	 * @param player
	 * @param signature
	 * @param purchaseData
	 * @param humanRechargeManager
	 */
	public void googlePlayRecharge(Player player, String signature,
			String purchaseDataStr, HumanRechargeManager humanRechargeManager) {
		PurchaseData purchaseData = null;
		try{
			logger.info("[订单"+player.getPassportId()+"]安卓-2--订单... ...userId");
			purchaseData  = JSON.parseObject(purchaseDataStr,PurchaseData.class);
		}catch(Exception e){
			logger.error("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]请求验证订单,数据["+purchaseDataStr+"],签名["+signature+"]有问题,异常["+e.getMessage()+"]");
			return;
		}
		
		String orderIdStr = purchaseData.getDeveloperPayload();
		
		long orderId = Long.parseLong(orderIdStr);
	
	
		HumanRechargeOrder order =humanRechargeManager.getOrderById(orderId);
		
		logger.info("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]请求验证订单,数据["+purchaseDataStr+"],签名["+signature+"]");
		
		//检查订单是否存在
		if(order == null){
			player.sendSystemMessage(LangConstants.ORDER_NO_EXIST);
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,订单id["+orderId+"]不存在");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}

		//检查订单是否已经处理
		if(order.getOrderStatus() != OrderStatus.NON_VALIDATE )
		{
			player.sendSystemMessage(LangConstants.ORDER_ALEADY_HANDLE);
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,订单id["+orderId+"]已经验证过");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}
		
		RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(player.getChannelType().getIndex(), order.getProductId());
		if(rechargeTemplate == null)
		{
			player.sendSystemMessage(LangConstants.PRODUCT_NO_EXIST);
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,产品id["+order.getProductId()+"]不存在");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}
		
		//检查充值项目是否使用
		if(rechargeTemplate.getUsed()==0){
			player.sendSystemMessage(LangConstants.PRODUCT_NO_EXIST);
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,充值项["+order.getProductId()+"]不存在");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}
	
		logger.info("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]请求验证订单,包名["+purchaseData.getPackageName()+"],订单id["+orderId+"],签名["+signature+"]");

		googleplay(player,order,purchaseData.getPackageName(),purchaseData.getProductId(),purchaseData.getPurchaseToken());
	}
	private void googleplay(Player player,HumanRechargeOrder order,String packageName,String productIdStr,String token){
		Human human = player.getHuman();
		if(human==null){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		
		if(Globals.getServerConfig().isTest())
		{
			if(productIdStr.equalsIgnoreCase("android.test.purchased"))
			{
				logger.info("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]测试充值,产品id["+order.getProductId()+"]"+productIdStr);
				RechargeBazooLogic.onRecharge(player,order.getId(),order.getCost());
				return;
			}
		}

		//发送异步调用
		CheckGooglePlayRechargeParam checkGooglePlayRechargeParam = new CheckGooglePlayRechargeParam(Globals.getLocalConfig().getRequestDomain(),"api/recharge/googleplay.json",true);
		checkGooglePlayRechargeParam.setGoogleId(order.getChannelId());
		checkGooglePlayRechargeParam.setPackageName(packageName);
		checkGooglePlayRechargeParam.setProductId(productIdStr);
		checkGooglePlayRechargeParam.setToken(token);
		
		
		AsyncHttpOperation<CheckGooglePlayRechargeRes> asyncHttpOper = new AsyncHttpOperation<CheckGooglePlayRechargeRes>(player,checkGooglePlayRechargeParam,new GooglePlayRechargeCallBack());
		Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(asyncHttpOper);
		
	}

	
	
	
	
	
	
	/**
	 * 
	 * 博趣
	 * 第三方平台  支付
	 * @param player
	 * @param signature
	 * @param purchaseData
	 * @param humanRechargeManager
	 */
	
	public void boquRecharge(Player player, String signature,
			String purchaseDataStr, HumanRechargeManager humanRechargeManager) {
		BoquData boquData = null;
		try{
			logger.info("[订单"+player.getPassportId()+"]博趣-1--订单... ...userId");
			boquData  = JSON.parseObject(purchaseDataStr,BoquData.class);
		}catch(Exception e){
			logger.error("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]请求验证订单,数据["+purchaseDataStr+"],签名["+signature+"]有问题,异常["+e.getMessage()+"]");
			return;
		}
		
		String orderIdStr = boquData.getInfo().split("&")[0];//咱们的 订单ID
		
		long orderId = Long.parseLong(orderIdStr);
	
	
		HumanRechargeOrder order =humanRechargeManager.getOrderById(orderId);
		
		logger.info("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]请求验证订单,数据["+purchaseDataStr+"],签名["+signature+"]");
		
		//检查订单是否存在
		if(order == null){
			player.sendSystemMessage(LangConstants.ORDER_NO_EXIST);
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,订单id["+orderId+"]不存在");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}
		//把第三方 平台的order_id 设置上
		order.setChannelOrderId(boquData.getOrder_id());
		
		//检查订单是否已经处理
		if(order.getOrderStatus() != OrderStatus.NON_VALIDATE )
		{
			player.sendSystemMessage(LangConstants.ORDER_ALEADY_HANDLE);
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,订单id["+orderId+"]已经验证过");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}
		
		RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(player.getChannelType().getIndex(), order.getProductId());
		if(rechargeTemplate == null)
		{
			player.sendSystemMessage(LangConstants.PRODUCT_NO_EXIST);
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,产品id["+order.getProductId()+"]不存在");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}
		
		//检查充值项目是否使用
		if(rechargeTemplate.getUsed()==0){
			player.sendSystemMessage(LangConstants.PRODUCT_NO_EXIST);
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]验证订单,充值项["+order.getProductId()+"]不存在");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			player.sendMessage(gcValidateOrder);
			return;
		}
		
		//检查订单 是否成功了
		if(!boquData.getStatus().equals("complete")){
			logger.info("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]请求验证订单-失败,订单状态["+boquData.getStatus()+"]");
			return;
		}
	
		logger.info("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]请求验证订单,订单id["+orderId+"],签名["+signature+"]");

		boquPlay(player,order,rechargeTemplate.getProductId());
	}
	
	/**
	 * 博趣 远程验证并发货
	 * @param player
	 * @param order
	 * @param productIdStr
	 */
	private void boquPlay(Player player,HumanRechargeOrder order,String productIdStr){
		Human human = player.getHuman();
		if(human==null){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		if(Globals.getServerConfig().isTest())
		{
			logger.info("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]测试充值,产品id["+order.getProductId()+"]"+productIdStr);
			RechargeBazooLogic.onRecharge(player,order.getId(),order.getCost());
			return;
		}

		//发送异步调用
		CheckBoquRechargeParam checkBoquRechargeParam = new CheckBoquRechargeParam(Globals.getLocalConfig().getRequestDomain(),"api/recharge/boqu.json",true);
		checkBoquRechargeParam.setChannelOrderId(order.getChannelOrderId());
		checkBoquRechargeParam.setOrderId(String.valueOf(order.getId()));
		checkBoquRechargeParam.setProductId(productIdStr);
		checkBoquRechargeParam.setUserId(String.valueOf(player.getPassportId()));
		AsyncHttpOperation<CheckBoquRechargeRes> asyncHttpOper = new AsyncHttpOperation<CheckBoquRechargeRes>(player,checkBoquRechargeParam,new BoquRechargeCallBack());
		Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(asyncHttpOper);
		
	}
	
}
