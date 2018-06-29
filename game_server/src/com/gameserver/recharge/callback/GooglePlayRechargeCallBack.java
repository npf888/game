package com.gameserver.recharge.callback;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.game.webserver.recharge.CheckGooglePlayRechargeRes;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanRechargeManager;
import com.gameserver.player.Player;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.RechargeBazooLogic;
import com.gameserver.recharge.async.AsyncHttpOperation.IAsyncHttpCallBack;
import com.gameserver.recharge.msg.GCValidateOrder;

public class GooglePlayRechargeCallBack implements IAsyncHttpCallBack<CheckGooglePlayRechargeRes>{
	private Logger logger = Loggers.rechargeLogger;
	/**
	 * 订单取消
	 * @param player
	 * @param orderId
	 */
	private void onCancel(Player player,long orderId){
		Human human = player.getHuman();
		if(human==null){
			logger.warn("玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		HumanRechargeManager humanRechargeManager = human.getHumanRechargeManager();
		HumanRechargeOrder order =  humanRechargeManager.getOrderById(orderId);
		if(order == null){
			logger.warn("玩家["+player.getPassportId()+"]"+"回调 orderid["+ orderId+"]不存在");
			return;
		}
		humanRechargeManager.cancelOrder(order);
		
		GCValidateOrder gcValidateOrder = new GCValidateOrder();
		gcValidateOrder.setOrderId(orderId);
		gcValidateOrder.setResult(0);
		gcValidateOrder.setRandRewardList(new RandRewardData[0]);
		human.sendMessage(gcValidateOrder);
		logger.info("玩家["+human.getPassportId()+"]取消 订单号["+order.getId()+"],产品id["+order.getProductId()+"]");
		
	}
	



	@Override
	public void onFinished(Player player,int errorCode, CheckGooglePlayRechargeRes res) {
		Human human = player.getHuman();
		if(human==null){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		if(errorCode!=0){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]请求本地服务器数据失败");
			return ;
		}
		
		long orderId=0L;
		try{
			orderId  = Long.parseLong(res.getDeveloperPayload());
		}catch(Exception e){
			logger.error("[订单"+player.getPassportId()+"]玩家["+human.getPassportId()+"]订单回调,订单数据错误["+res.getDeveloperPayload()+"]");
			return;
		}
		
		//付款无效
		if(res.getPurchaseState()!=0){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]付款无效");
			onCancel(player,orderId);
			return;
		}
		
		//已经使用过了
		if(res.getConsumptionState()==1){
			logger.warn("[订单"+player.getPassportId()+"]玩家["+player.getPassportId()+"]使用过了");
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
			gcValidateOrder.setOrderId(orderId);
			gcValidateOrder.setResult(0);
			gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			human.sendMessage(gcValidateOrder);
			return;
		}
		logger.info("[订单"+player.getPassportId()+"]-2-安卓-验证订单通过... 开始发货...userId:"+player.getPassportId());
		
//		RechargeLogic.onRecharge(player,orderId,0);
		RechargeBazooLogic.onRecharge(player,orderId,0);
		
	}
	

}
