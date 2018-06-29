package com.gameserver.common;

import java.util.List;

import com.common.LogReasons;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanBagManager;
import com.gameserver.item.template.ItemTemplate;

/**
 * 通用逻辑
 * @author wayne
 *
 */
public class CommonLogic {
	
	private static CommonLogic instance = new CommonLogic();
	
	public static CommonLogic getInstance(){
		return instance;
	}
	
	/**
	 * 给奖励
	 * @param human
	 * @param randRewardDataList
	 * @param neendNotify
	 */
	public void giveRandReward(Human human,List<RandRewardData> randRewardDataList,LogReasons.GoldLogReason goldLogReason,LogReasons.DiamondLogReason diamondLogReason,LogReasons.CharmLogReason charmLogReason,LogReasons.ItemLogReason itemLogReason,boolean neendNotify)
	{
		
		for(RandRewardData randRewardData:randRewardDataList)
		{
			if(randRewardData.getRewardId()<=100)
			{
				Currency currency = Currency.valueOf(randRewardData.getRewardId());
				if(currency == null)
				{
					continue;
				}
				
				giveRandCurrencyReward(human,currency,randRewardData.getRewardCount(),goldLogReason,diamondLogReason,charmLogReason,neendNotify);
				
			}
			else
			{
				giveRandItemReward(human,randRewardData.getRewardId(),randRewardData.getRewardCount(),itemLogReason,neendNotify);
			}
			
		}
	}
	/**
	 * 全服的活动 用这个
	 * 根据 vip等级 赠送 奖励
	 * @param human
	 * @param randRewardDataList
	 * @param neendNotify
	 */
	public void giveAllRandReward(Human human,List<RandRewardData> randRewardDataList,LogReasons.GoldLogReason goldLogReason,LogReasons.DiamondLogReason diamondLogReason,LogReasons.CharmLogReason charmLogReason,LogReasons.ItemLogReason itemLogReason,boolean neendNotify)
	{

	   	 for(RandRewardData randRewardData:randRewardDataList)
	   	 {
	   		if(randRewardData.getRewardId()<=100)
	   		{
	   			Currency currency = Currency.valueOf(randRewardData.getRewardId());
	   			if(currency == null)
	   			{
	   				continue;
	   			}
	   			if(human.getHumanVipNewManager().getVipLv() == randRewardData.getVippoint()){
	   				giveRandCurrencyReward(human,currency,randRewardData.getRewardCount(),goldLogReason,diamondLogReason,charmLogReason,neendNotify);
	   			}
	   			
	   		}
	   		else
	   		{
	   			if(human.getHumanVipNewManager().getVipLv() == randRewardData.getVippoint()){
	   				giveRandItemReward(human,randRewardData.getRewardId(),randRewardData.getRewardCount(),itemLogReason,neendNotify);
	   			}
	   		}
	   		 
	   	 }
	}
	
	 /**
	  * 给予道具掉落奖励
	  * @param human //用户信息
	  * @param itemid //奖励的道具id
	  * @param count //奖励的道具数量
	  */
	public void giveRandItemReward(Human human,int itemid,int count,LogReasons.ItemLogReason itemLogReason,boolean needNotify)
	{
	   	ItemTemplate itemTempl=Globals.getItemService().getItemTemplWithId(itemid);
		if(itemTempl!=null)
		{
			// 玩家背包
			HumanBagManager humanBagManager = human.getHumanBagManager();
			// 添加道具到玩家背包
			humanBagManager.addItem(itemTempl.getId(),count);
			Globals.getLogService().sendItemLog(human, itemLogReason, itemLogReason.getReasonText(), itemid, count, humanBagManager.getCount(itemid));
		}
	}
	


	 /**
	  * 给予元宝掉落奖励
	  * @param human //用户信息
	  * @param itemid //奖励的道具id
	  * @param count //奖励的道具数量
	  */
	 public void giveRandCurrencyReward(Human human,Currency currencyType,int diamond,LogReasons.GoldLogReason goldLogReason,LogReasons.DiamondLogReason diamondLogReason,LogReasons.CharmLogReason charmLogReason,boolean needNotify){
		 
		switch(currencyType)
		{
		case EXP:
			human.addExp(diamond, null);
			break;
		case DIAMOND:
			human.giveMoney(diamond,currencyType,needNotify,diamondLogReason, diamondLogReason.getReasonText(),-1,1);
			break;
		case GOLD:
			human.giveMoney(diamond,currencyType,needNotify,goldLogReason, goldLogReason.getReasonText(),-1,1);
			break;
		case CHARM:
			human.giveMoney(diamond,currencyType,needNotify,charmLogReason, charmLogReason.getReasonText(),-1,1);
			break;
		default:
			break;
		}
	
	 }
}
