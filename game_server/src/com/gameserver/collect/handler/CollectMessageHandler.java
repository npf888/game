package com.gameserver.collect.handler;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.gameserver.collect.msg.CGCardExchange;
import com.gameserver.collect.msg.CGCharmExchange;
import com.gameserver.collect.msg.CGCollectInit;
import com.gameserver.collect.msg.CGRaffle;
import com.gameserver.collect.msg.GCCardExchange;
import com.gameserver.collect.msg.GCCharmExchange;
import com.gameserver.collect.msg.GCGetVouchers;
import com.gameserver.collect.msg.GCRaffle;
import com.gameserver.collect.template.CardRewardTemplate;
import com.gameserver.collect.template.ExchangeTemplate;
import com.gameserver.collect.template.ShavePrizeTemplate;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanCollectManager;
import com.gameserver.item.enums.ItemType;
import com.gameserver.item.template.ItemTemplate;
import com.gameserver.player.Player;

/**
 * 收集系统
 * @author 郭君伟
 *
 */
public class CollectMessageHandler {
	
	private Logger logger = Loggers.COLLECT;

	public void handleCharmExchange(Player player, CGCharmExchange cgCharmExchange) {
		
		Human human = player.getHuman();
		
		int exchangeId = cgCharmExchange.getExchangeId();
		
		GCCharmExchange messasge = new GCCharmExchange();
		messasge.setReturnType(1);//1 成功
		
		ExchangeTemplate temp = Globals.getCollectServer().getExchange(exchangeId);
		if(temp == null){
			messasge.setReturnType(3);//请求物品不存在
			logger.error("角色ID【"+human.getPassportId()+"】 请求的ID "+exchangeId+" 不存在");
		}else{
			//魅力值
			int consumeCount = temp.getConsumeCount();
			
			long charm = human.getCharm();
			
			if(consumeCount > charm){
				messasge.setReturnType(2);//魅力值不够
			}else{
				//扣除魅力值
				String inDetailReason = LogReasons.CharmLogReason.COLLECT.getReasonText();
				player.getHuman().costMoney(consumeCount, Currency.CHARM, true, LogReasons.CharmLogReason.COLLECT, inDetailReason,-1, 1);
				human.getHumanCollectManager().exchangeAndBuyBoth(temp.getExchangeID(),temp.getExchangeCount(),true);
				/*GCGetVouchers GCGetVouchers = new GCGetVouchers();
				GCGetVouchers.setItemId(temp.getExchangeID());
				GCGetVouchers.setNum(temp.getExchangeCount());
				player.sendMessage(GCGetVouchers);*/
			}
		}
		player.sendMessage(player.getHuman().getHumanBagManager().buildHumanBagInfoData());
		player.sendMessage(messasge);
	}

	/**
	 * 抽奖
	 * @param player
	 * @param cgRaffle
	 */
	public void handleRaffle(Player player, CGRaffle cgRaffle) {
		
		/** 1 铜 2 银 3 金 */
		int poolType = cgRaffle.getCardType();
		
		Human human = player.getHuman();
		
		HumanCollectManager manger = human.getHumanCollectManager();
		
		GCRaffle message = new GCRaffle();
		message.setCardType(poolType);
		message.setReturnRes(0);
		message.setId(-1);
		if(manger.isEnough(poolType)){
		   message.setReturnRes(1);
			//奖池里面随机物品出来
			ShavePrizeTemplate shavePrizeTemplate = Globals.getCollectServer().getShavePrizeTemplate(poolType);
			message.setId(shavePrizeTemplate.getId());
			int itemId = shavePrizeTemplate.getItemID();
			
			int num = shavePrizeTemplate.getRewardNum();
			
			int itemIdType = Globals.getItemService().getItemType(itemId);
			
			if(itemIdType != -1){
				
				switch (ItemType.indexOf(itemIdType)) {
				case ItemType10://金 银 铜 可以 再 刮出金银铜
					manger.exchangeAndBuyBoth(itemId,num,false);
					break;
				case ItemType11://金钱礼包
					int money = num;
					 human.giveMoney(money, Currency.GOLD, false, LogReasons.GoldLogReason.COLLECT, LogReasons.GoldLogReason.COLLECT.getReasonText(), -1, 1);
					break;
				case ItemType12://碎片
					manger.addDebris(itemId,num);
					break;
				case ItemType13:// 双倍经验加成卡
					/**
					 * 添加经验
					 * 
					 */
					human.getHumanBagManager().addDoubleExp(human,itemId, num);
					
					break;
				default:
					break;
				}
			}
			/*manger.remCard(poolType);
			manger.save();*/
			//移除收集系统中  使用过的 金 银 铜
			manger.removeCard(poolType);
			//移除背包中 使用过的 金银铜
			ItemTemplate itemTemplate = Globals.getItemService().getItemTemplateByPoolType(poolType);
			human.getHumanBagManager().removeItem(itemTemplate.getId(), 1);
			
//			player.sendMessage(manger.getGCCollectInit());
			//更新背包系统
			player.sendMessage(human.getHumanBagManager().buildHumanBagInfoData());
		}
		player.sendMessage(message);
	}
	
	

	public void handleCollectInit(Player player, CGCollectInit cgCollectInit) {
		
		Human human = player.getHuman();
		
		HumanCollectManager manger = human.getHumanCollectManager();

		player.sendMessage(manger.getGCCollectInit());
	}
	
	

	public void handleCardExchange(Player player, CGCardExchange cgCardExchange) {
		
		/** 1 农场卡 2 建筑卡3 赌场卡 */
		int cardType = cgCardExchange.getCardType();
		
        Human human = player.getHuman();
		
		HumanCollectManager manger = human.getHumanCollectManager();
		
		GCCardExchange message = new GCCardExchange();
		message.setCardType(cardType);
		message.setReturnRes(0);
		if(manger.iscardEnough(cardType)){
			message.setReturnRes(1);
		   CardRewardTemplate cr = Globals.getCardRewardServer().getCardReward(cardType);
			int rewardNum = cr.getRewardNum();
			 human.giveMoney(rewardNum, Currency.GOLD, false, LogReasons.GoldLogReason.COLLECT, LogReasons.GoldLogReason.COLLECT.getReasonText(), -1, 1);
			 manger.remCardTyp(cardType);
			 manger.save();
			player.sendMessage(manger.getGCCollectInit());
		}

		player.sendMessage(message);
		
	}

}
