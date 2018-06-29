package com.gameserver.item.handler;

import java.text.MessageFormat;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanBaccartManager;
import com.gameserver.human.manager.HumanTexasManager;
import com.gameserver.item.data.ItemInfoDataNew;
import com.gameserver.item.msg.CGBazooItemBuyByGold;
import com.gameserver.item.msg.CGBazooItemClockChange;
import com.gameserver.item.msg.CGBazooItemRequest;
import com.gameserver.item.msg.CGBazooMallRequest;
import com.gameserver.item.msg.CGGroupSendInteractiveItem;
import com.gameserver.item.msg.CGSendInteractiveItem;
import com.gameserver.item.msg.GCBazooItemBuyByGold;
import com.gameserver.item.msg.GCBazooItemRequest;
import com.gameserver.item.msg.GCBazooMallRequest;
import com.gameserver.item.msg.GCGroupSendInteractiveItem;
import com.gameserver.item.msg.GCSendInteractiveItem;
import com.gameserver.item.template.ItemCostTemplate;
import com.gameserver.lobby.enums.GameType;
import com.gameserver.player.Player;
import com.gameserver.recharge.enums.SmallCategoryEnum;

/**
 * 道具
 * @author wayne
 *
 */
public class ItemMessageHandler {

	private Logger logger = Loggers.itemLogger;
	
	public void handleSendInteractiveItem(Player player,
			CGSendInteractiveItem cgSendInteractiveItem) {
		
		Human human = player.getHuman();
		
		long otherPlayerId = cgSendInteractiveItem.getPlayerId();
		
		int itemId = cgSendInteractiveItem.getItemId();
		
        Player other = Globals.getOnlinePlayerService().getPlayerByPassportId(otherPlayerId);
		
		if(other == null){
			logger.warn("玩家["+player.getPassportId()+"]发送给不存在的玩家["+otherPlayerId+"]");
			return;
		}
		
		//判断是否同一个玩家
		if(player.getPassportId() == otherPlayerId){
			logger.warn("玩家["+player.getPassportId()+"]发送给自己");
			return;
		 }
		
		
		boolean isBaccar = false;
		boolean isTexas = false;
		
		int gameType = 0;
		int roomId = 0;
		
		//判断是否在同一桌
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		if(humanTexasManager.getTexasRoom() != null && humanTexasManager.isInTable()){
	
			HumanTexasManager otherTexasManager = other.getHuman().getHumanTexasManager();
			if(otherTexasManager.getTexasRoom() != null && otherTexasManager.isInTable()){
				if(humanTexasManager.getTexasRoom() ==otherTexasManager.getTexasRoom() )
					isTexas = true;
				gameType = GameType.TEXAS.getIndex();
				roomId = humanTexasManager.getTexasRoom().getId();
			}
		}

		HumanBaccartManager otherHumanBaccartManager = other.getHuman().getHumanBaccartManager();
		if(otherHumanBaccartManager.getBaccartRoom()!=null&& otherHumanBaccartManager.getBaccartRoom()== human.getHumanBaccartManager().getBaccartRoom()){
			isBaccar  =true;
			gameType = GameType.BACCART.getIndex();
			roomId = otherHumanBaccartManager.getBaccartRoom().getId();
		}
		
		if(!isBaccar && !isTexas){
			logger.warn("玩家["+player.getPassportId()+"]和对方["+other.getPassportId()+"]不在同一个房间");
			return;
		}
		
		ItemCostTemplate itemCost = Globals.getItemService().getItemCostTemplate(gameType, roomId, itemId);
		
		if(itemCost == null){
			logger.warn("玩家["+player.getPassportId()+"]发送不存在的互动道具["+itemId+"]");
			return;
		}

		//判断是否足够筹码
		if(!human.hasEnoughMoney(itemCost.getCostChips(), Currency.GOLD)){
			logger.warn("玩家["+player.getPassportId()+"]没有足够的筹码");
			return;
		}
		
		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM.getReasonText(),cgSendInteractiveItem.getItemId() ,1);
//		human.costMoney(itemCost.getCostChips(), Currency.GOLD, true, LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM, detailReason, cgSendInteractiveItem.getItemId(), 1);
		//免费发送道具
		human.costMoney(0, Currency.GOLD, true, LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM, detailReason, cgSendInteractiveItem.getItemId(), 1);
		
		String inDetailReason = MessageFormat.format(LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM.getReasonText(),cgSendInteractiveItem.getItemId());
		
		if(itemCost.getChangeCharm()>0){
			 other.getHuman().giveMoney(itemCost.getChangeCharm(), Currency.CHARM, true, LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM, inDetailReason, cgSendInteractiveItem.getItemId(), 1);
		}else{
			 other.getHuman().costMoney(itemCost.getChangeCharm(), Currency.CHARM, true, LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM, inDetailReason, cgSendInteractiveItem.getItemId(), 1);
		}
		
		GCSendInteractiveItem gcSendInteractiveItem = new GCSendInteractiveItem();
		gcSendInteractiveItem.setFromId(player.getPassportId());
		gcSendInteractiveItem.setToId(other.getPassportId());
		gcSendInteractiveItem.setItemId(cgSendInteractiveItem.getItemId());
		
		if(isTexas){
			Globals.getTexasService().broadcastMsg(humanTexasManager.getTexasRoom(), gcSendInteractiveItem);
		}else{
			Globals.getBaccartService().broadcastMsg(otherHumanBaccartManager.getBaccartRoom(),gcSendInteractiveItem);
		}
		Globals.getItemService().onSendInteractiveItem(human,itemId);
		human.getHumanAchievementManager().updateSingleProps();
	}
	
	/**
	 * 群发
	 * @param player
	 * @param cgGroupSendInteractiveItem
	 */
	public void handleGroupSendInteractiveItem(Player player,
			CGGroupSendInteractiveItem cgGroupSendInteractiveItem) {
		
		Human human = player.getHuman();
		
		int itemId = cgGroupSendInteractiveItem.getItemId();
		
		
		boolean isBaccar = false;
		boolean isTexas = false;
		int gameType = 0;
		int roomId = 0;
		
		//判断是否在同一桌
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		HumanBaccartManager humanBaccartManager = human.getHumanBaccartManager();
		
		int num = 0;
		
		if(humanTexasManager.getTexasRoom() != null && humanTexasManager.isInTable() ){
			isTexas = true;
			num = humanTexasManager.getTexasRoom().getPlayerManager().getRoomPlayerList().size();
			gameType = GameType.TEXAS.getIndex();
			roomId = humanTexasManager.getTexasRoom().getId();
		}
		
		if(humanBaccartManager.getBaccartRoom() != null ){
			isBaccar = true;
			num = humanBaccartManager.getBaccartRoom().getBaccartRoomPlayerManager().getPlayerNum();
			gameType = GameType.BACCART.getIndex();
			roomId = humanBaccartManager.getBaccartRoom().getId();
		}
		
		if(!isBaccar && !isTexas){
			logger.warn("玩家["+player.getPassportId()+"不在房间");
			return;
		}
		
		num -=1;
		//判断人数
		if(num<=0){
			logger.warn("没有可以发送的玩家");
			return;
		}
		ItemCostTemplate itemCost = Globals.getItemService().getItemCostTemplate(gameType, roomId, itemId);
		
		if(itemCost == null){
			logger.warn("玩家["+player.getPassportId()+"]发送不存在的互动道具["+itemId+"]");
			return;
		}
	
		int totalCost = itemCost.getCostChips()*num;
		//判断是否足够筹码
		if(!human.hasEnoughMoney(totalCost, Currency.GOLD)){
			logger.warn("玩家["+player.getPassportId()+"]没有足够的筹码");
			return;
		}
		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM.getReasonText(),cgGroupSendInteractiveItem.getItemId() ,num);
		human.costMoney(totalCost, Currency.GOLD, true, LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM, detailReason, cgGroupSendInteractiveItem.getItemId(), 1);
		
		String inDetailReason = MessageFormat.format(LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM.getReasonText(),cgGroupSendInteractiveItem.getItemId());

		
		if(isBaccar){
			for(Player other :humanBaccartManager.getBaccartRoom().getBaccartRoomPlayerManager().getPlayerList()){
				if(other == player)
					continue;
				if(itemCost.getChangeCharm()>0){
					other.getHuman().giveMoney(itemCost.getChangeCharm(), Currency.CHARM, true, LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM, inDetailReason, cgGroupSendInteractiveItem.getItemId(), 1);
			
				}else{
					other.getHuman().costMoney(itemCost.getChangeCharm(), Currency.CHARM, true, LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM, inDetailReason, cgGroupSendInteractiveItem.getItemId(), 1);
				}
			}
		}
		
		if(isTexas){
			for(Player other :humanTexasManager.getTexasRoom().getPlayerManager().getRoomPlayerList()){
				if(other == player)
					continue;
				if(itemCost.getChangeCharm()>0){
					other.getHuman().giveMoney(itemCost.getChangeCharm(), Currency.CHARM, true, LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM, inDetailReason, cgGroupSendInteractiveItem.getItemId(), 1);
			
				}else{
					other.getHuman().costMoney(itemCost.getChangeCharm(), Currency.CHARM, true, LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM, inDetailReason, cgGroupSendInteractiveItem.getItemId(), 1);
				}
			}
		}
			
		GCGroupSendInteractiveItem gcGroupSendInteractiveItem = new GCGroupSendInteractiveItem();
		gcGroupSendInteractiveItem.setFromId(player.getPassportId());
		gcGroupSendInteractiveItem.setItemId(cgGroupSendInteractiveItem.getItemId());
		
		if(isTexas)
			Globals.getTexasService().broadcastMsg(humanTexasManager.getTexasRoom(), gcGroupSendInteractiveItem);
		else{
			Globals.getBaccartService().broadcastMsg( humanBaccartManager.getBaccartRoom(),gcGroupSendInteractiveItem);
		}
		
		for(int i=0;i<num;i++){
			Globals.getItemService().onSendInteractiveItem(human,itemId);
		}
		human.getHumanAchievementManager().updateAllProps();
	}

	public void handleBazooItemBuyByGold(Player player,
			CGBazooItemBuyByGold cgBazooItemBuyByGold) {
		int itemId = cgBazooItemBuyByGold.getItemId();
		int itemType = 2;
		Globals.getItemNewService().BuyItemByGold(player,player.getPassportId(),itemId,itemType,1);
	}
	
	/**
	 * 更换色钟
	 */
	public void handleBazooItemClockChange(Player player,
			CGBazooItemClockChange cgBazooItemClockChange) {
		int itemId = cgBazooItemClockChange.getItemId();
		Globals.getItemNewService().changeItemClock(player,itemId);
	}

	/**
	 * 请求背包列表
	 * @param player
	 * @param cgBazooItemRequest
	 */
	public void handleBazooItemRequest(Player player,
			CGBazooItemRequest cgBazooItemRequest) {
		int itemType = cgBazooItemRequest.getItemType();
		if(itemType == 1){
			
		}else if(itemType == SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex()){
			GCBazooItemRequest GCBazooItemRequest = Globals.getItemNewService().getClockItem(player);
			player.sendMessage(GCBazooItemRequest);
			
		}else if(itemType == SmallCategoryEnum.SMALLCATEGORYENUM3.getIndex()){
			GCBazooItemRequest GCBazooItemRequest = Globals.getItemNewService().getRedPackageItem(player);
			player.sendMessage(GCBazooItemRequest);
			
		}else if(itemType == SmallCategoryEnum.SMALLCATEGORYENUM4.getIndex()){
			GCBazooItemRequest GCBazooItemRequest = Globals.getItemNewService().getGiftItem(player);
			player.sendMessage(GCBazooItemRequest);
		}
	}

	/**
	 * 请求商城的道具列表
	 * @param player
	 * @param cgBazooMallRequest
	 */
	public void handleBazooMallRequest(Player player,
			CGBazooMallRequest cgBazooMallRequest) {
		//获取道具商城的列表
		ItemInfoDataNew[] ItemInfoDataNewArr = Globals.getItemNewService().getItemMallList(player);
		GCBazooMallRequest GCBazooMallRequest = new GCBazooMallRequest();
		GCBazooMallRequest.setItemInfoDataNew(ItemInfoDataNewArr);
		player.sendMessage(GCBazooMallRequest);
	}
	
	

}
