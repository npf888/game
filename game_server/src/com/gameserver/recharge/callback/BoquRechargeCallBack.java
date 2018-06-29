package com.gameserver.recharge.callback;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.game.webserver.recharge.CheckBoquRechargeRes;
import com.game.webserver.recharge.CheckIOSRechargeRes;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.RechargeBazooLogic;
import com.gameserver.recharge.async.AsyncHttpOperation.IAsyncHttpCallBack;
import com.gameserver.recharge.template.RechargeTemplate;
/**
 * 博趣验证成功之后的返回
 * @author JavaServer
 *
 */
public class BoquRechargeCallBack implements IAsyncHttpCallBack<CheckBoquRechargeRes>{
	private Logger logger = Loggers.rechargeLogger;
	@Override
	public void onFinished(Player player, int errorCode,
			CheckBoquRechargeRes res) {
		Human human = player.getHuman();
		if(human==null){
			logger.warn("玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		if(errorCode!=0){
			logger.warn("玩家["+player.getPassportId()+"]请求本地服务器数据失败");
			return ;
		}
		
		
		RechargeTemplate tempRechargeTemplate = Globals.getRechargeService().rechargeTemplateBySkuId(player.getChannelType(),res.getProductId());
		
		HumanRechargeOrder order = Globals.getRechargeService().generateOrder(human,tempRechargeTemplate.getPid(),tempRechargeTemplate.getNum());
		
		order.setChannelOrderId(res.getChannelOrderId());
		order.setModified();
		human.getHumanRechargeManager().addOrder(order);
		
		logger.info("博趣-验证订单通过... 开始发货...userId:"+player.getPassportId());
//		RechargeLogic.onRecharge(player,order.getId(),order.getCost());
		RechargeBazooLogic.onRecharge(player,order.getId(),order.getCost());
	}
	
}
