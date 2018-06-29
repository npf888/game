package com.gameserver.gift.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.gift.msg.CGNewComerGift;
import com.gameserver.gift.msg.CGRequestGift;
import com.gameserver.gift.msg.CGRequestGiftTimeEnd;
import com.gameserver.gift.msg.GCRequestGift;
import com.gameserver.gift.template.GiftTemplate;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanGiftManager;
import com.gameserver.player.Player;

/**
 * 
 * @author 郭君伟
 *
 */
public class GiftMessageHandler {
	
	private Logger logger = Loggers.gfitLogger;

	/**
	 * 获取礼包
	 * @param player
	 * @param cgRequestGift
	 */
	public void handleRequestGift(Player player, CGRequestGift cgRequestGift) {
		
		Human human = player.getHuman();
		
		//当前角色VIP等级
	    int vipLevel = human.getHumanVipNewManager().getVipLv();
		
		HumanGiftManager manager = human.getHumanGiftManager();
		
		//上次刷新时间
		Long startTime = manager.getHumanGift().getRefreshTime();
		
		int giftId = manager.getHumanGift().getGiftid();
		
		int humanLevel = (int)human.getLevel();
		
		long currTime = Globals.getTimeService().now();
		
		
		if(giftId == 0){
			
			giftId = Globals.getGiftService().getGiftNoId(humanLevel);
			if(giftId == -1){
				return;
			}
			//重新设置倒计时
			manager.updateHumanGift(giftId,currTime);
			
			startTime = currTime;
			
		}else{
			
			GiftTemplate template = Globals.getGiftService().getTemplate(giftId);
			
			if(template == null){
				logger.warn("玩家["+player.getPassportId()+"]请求优惠礼包出错！！！");
				return;
			}
			
			//需要的时间块
			long time = 0l;
			if(template.getVipLevel() > vipLevel){
				time = template.getNoVipGiftTime()*1000;
			}else{
				time = template.getVipGiftTime()*1000;
			}
			
			//时间到期了刷新礼包
			if(currTime - startTime > time){
				giftId = Globals.getGiftService().getGiftNoId(humanLevel);
				if(giftId == -1){
					return;
				}
				//重新设置倒计时
				manager.updateHumanGift(giftId,currTime);
				startTime = currTime;
			}
		
		}

		//通知客户端
		GCRequestGift gift = new GCRequestGift();
		gift.setGiftId(giftId);
		gift.setStartTime(startTime);
		human.sendMessage(gift);
	}

	public void handleRequestGiftTimeEnd(Player player,
			CGRequestGiftTimeEnd cgRequestGiftTimeEnd) {
		player.getHuman().getHumanPayguideManager().sendPreference();
		
	}

	public void handleNewComerGift(Player player, CGNewComerGift cgNewComerGift) {
		player.getHuman().getHumanNewComerManager().sendTime();
		
	}

}
