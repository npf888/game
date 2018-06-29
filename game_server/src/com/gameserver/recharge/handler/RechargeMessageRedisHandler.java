package com.gameserver.recharge.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.recharge.RechargeLogic;
import com.gameserver.recharge.redisMsg.DeliveryRedisMessage;
import com.gameserver.recharge.redisMsg.PaymentRedisMessage;


public class RechargeMessageRedisHandler {
	private Logger logger = Loggers.rechargeLogger;

	public void handlePaymentRedisMessage(
			PaymentRedisMessage paymentRedisMessage) {
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(paymentRedisMessage.getUserId());
		if(player == null){
			logger.warn("玩家已经下线");
			return;
		}
		logger.info("------redis---------------------调用充值");
		RechargeLogic.onRecharge(player, paymentRedisMessage.getOrderId(),paymentRedisMessage.getCost());
	}

	public void handleDeliyerRedisMessage(DeliveryRedisMessage delivery) {
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(delivery.getRoleID());
		if(player == null){
			logger.warn("玩家已经下线");
			return;
		}
		RechargeLogic.onRechargeGameView(player, delivery.getExtraparam1(), delivery.getAmount(), delivery.getGold());
	}


}
