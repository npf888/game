package com.gameserver.shop.handler;


import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.LogReasons.DiamondLogReason;
import com.common.LogReasons.GoldLogReason;
import com.common.LogReasons.ILogReason;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanBagManager;
import com.gameserver.human.manager.HumanMonthCardManager;
import com.gameserver.human.manager.HumanWeekCardManager;
import com.gameserver.item.Item;
import com.gameserver.item.template.ItemTemplate;
import com.gameserver.player.Player;
import com.gameserver.shop.data.ShopInfoData;
import com.gameserver.shop.enums.ShopCatergoryEnum;
import com.gameserver.shop.msg.CGBuyItem;
import com.gameserver.shop.msg.CGShopList;
import com.gameserver.shop.msg.GCBuyItem;
import com.gameserver.shop.msg.GCShopList;
import com.gameserver.shop.template.ShopTemplate;

/**
 * 商城处理器
 * @author wayne
 *
 */
public class ShopMessageHandler {

	/** 日志 */
	private static final Logger logger = Loggers.shopLogger;
	
	public ShopMessageHandler() {
	
	}
	
	/**
	 * 购买商品
	 * @param player
	 * @param cgBuyItem
	 */
	public void handleBuyItem(Player player, CGBuyItem cgBuyItem) {
		Human human = player.getHuman();
		
		//背包管理器
		HumanBagManager humanBagManager = human.getHumanBagManager();
		
		//判断是否足够的金钱
		int shopId = cgBuyItem.getShopId();
		
		//
		ShopTemplate shopTemplate = Globals.getTemplateService().get(shopId, ShopTemplate.class);
		
		if(shopTemplate == null)
		{
			logger.warn("玩家[" +player.getPassportId() + "]请求错误的商品id["+shopId+"]");
			return;
		}
		
		Currency currency = shopTemplate.getCurrency();
	
		int cost = shopTemplate.getNum();
		
		//金币不足
		if(!human.hasEnoughMoney(shopTemplate.getNum(), currency))
		{
			player.getHuman().sendSystemMessage(LangConstants.NO_ENOUGH_MONEY);
			return;
		}
		
		int itemId = shopTemplate.getItemId();
		int itemNum = shopTemplate.getItemNum();
		ShopCatergoryEnum shopCategoryEnum  = shopTemplate.getShopCategoryEnum();
		
		//物品的商品
		if(shopCategoryEnum != ShopCatergoryEnum.COINS  )
		{
			//判断是否可以放商品
			int maxCanAdd = humanBagManager.getMaxCanAdd(itemId);
			if(maxCanAdd!=-1 && maxCanAdd<itemNum)
			{
				player.getHuman().sendSystemMessage(LangConstants.EXCEED_BAG_CAPACITY);
				return;
			}
			
		}
		
		ItemTemplate itemTemplate = Globals.getItemService().getItemTemplWithId(itemId);
		
		
		String detailReason = "";
		ILogReason logReason = DiamondLogReason.BUY_ITEM;
		switch(currency){
		case DIAMOND:
			logReason = DiamondLogReason.BUY_ITEM;
			detailReason = MessageFormat.format(DiamondLogReason.BUY_ITEM.getReasonText(), shopId,1);
			break;
		case GOLD:
			logReason = GoldLogReason.BUY_ITEM;
			detailReason = MessageFormat.format(GoldLogReason.BUY_ITEM.getReasonText(), shopId,1);
			break;
		default:
			break;
		}
		
		//购买成功 扣除金钱
		if(!human.costMoney(cost, currency, true, logReason, detailReason, shopId, 1))
		{
			player.getHuman().sendSystemMessage(LangConstants.NO_ENOUGH_MONEY);
			return;
		}
		
	
		
		switch(shopCategoryEnum){
		case COINS:
			//给金币
			itemNum += Math.ceil(itemNum*shopTemplate.getUpRate()/10000);
			
			human.giveMoney(itemNum, Currency.GOLD, true, LogReasons.GoldLogReason.BUY_COINS, LogReasons.GoldLogReason.BUY_COINS.getReasonText(), shopId, 1);
			logger.debug("玩家[" +player.getPassportId() + "]购买商品成功 id["+shopId+"]");
			GCBuyItem gcBuyItem = new GCBuyItem();
			gcBuyItem.setShopId(cgBuyItem.getShopId());
			player.sendMessage(gcBuyItem);
			return;
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
		if(itemList.size()==0)
		{
			logger.warn("玩家[" +player.getPassportId() + "]购买商品失败 id["+shopId+"]");
			return;
		}
		
		logger.debug("玩家[" +player.getPassportId() + "]购买商品成功 id["+shopId+"]");
		GCBuyItem gcBuyItem = new GCBuyItem();
		gcBuyItem.setShopId(cgBuyItem.getShopId());
		player.sendMessage(gcBuyItem);
	
		player.sendMessage(humanBagManager.buildHumanBagInfoData());
		
		//道具原因
        String itemDetailReason = MessageFormat.format(LogReasons.ItemLogReason.ITEM_BUY.getReasonText(), shopId);
		Globals.getLogService().sendItemLog(human, LogReasons.ItemLogReason.ITEM_BUY, itemDetailReason, itemId, itemNum, humanBagManager.getCount(itemId));
	}

	/**
	 * 请求商品列表
	 * @param player
	 * @param cgShopList
	 */
	public void handleShopList(Player player, CGShopList cgShopList) {
		logger.debug("玩家[" +player.getPassportId() + "]请求商品列表");
		List<ShopInfoData> shopList = Globals.getShopService().getShopInfoDataList();
		GCShopList gcShopList = new GCShopList();
		gcShopList.setShopList(shopList.toArray(new ShopInfoData[shopList.size()]));
		player.sendMessage(gcShopList);
	}

}
