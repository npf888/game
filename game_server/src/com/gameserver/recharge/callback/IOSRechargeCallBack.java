package com.gameserver.recharge.callback;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.game.webserver.recharge.CheckIOSRechargeRes;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.RechargeBazooLogic;
import com.gameserver.recharge.async.AsyncHttpOperation.IAsyncHttpCallBack;
import com.gameserver.recharge.template.RechargeTemplate;

//IOS 回调的内部类
public	class IOSRechargeCallBack implements IAsyncHttpCallBack<CheckIOSRechargeRes>{
		private Logger logger = Loggers.rechargeLogger;
		@Override
		public void onFinished(Player player, int errorCode,
				CheckIOSRechargeRes res) {
			Human human = player.getHuman();
			if(human==null){
				logger.warn("玩家["+player.getPassportId()+"]已经下线");
				return;
			}
			
			if(errorCode!=0){
				logger.warn("玩家["+player.getPassportId()+"]请求本地服务器数据失败");
				return ;
			}
			
			long orderId=0L;
			try{
				orderId  = Long.parseLong(res.getDeveloperPayload());
			}catch(Exception e){
				logger.error("玩家["+human.getPassportId()+"]订单回调,订单数据错误["+res.getDeveloperPayload()+"]");
				return;
			}
			
			RechargeTemplate tempRechargeTemplate = Globals.getRechargeService().rechargeTemplateBySkuId(player.getChannelType(),res.getProductId());
			
			HumanRechargeOrder order = Globals.getRechargeService().generateOrder(human,tempRechargeTemplate.getPid(),tempRechargeTemplate.getNum());
			
			order.setChannelOrderId(res.getDeveloperPayload());
			order.setModified();
			human.getHumanRechargeManager().addOrder(order);
			
			logger.info("-2-IOS-验证订单通过... 开始发货...userId:"+player.getPassportId());
//			RechargeLogic.onRecharge(player,order.getId(),order.getCost());
			RechargeBazooLogic.onRecharge(player,order.getId(),order.getCost());
		}
		
	}
