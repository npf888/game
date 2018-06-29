package com.gameserver.item;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.db.model.HumanItemEntity;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.item.data.ItemInfoDataNew;
import com.gameserver.item.msg.GCBazooItemBuyByGold;
import com.gameserver.item.msg.GCBazooItemClockChange;
import com.gameserver.item.msg.GCBazooItemClockChangeToAll;
import com.gameserver.item.msg.GCBazooItemRequest;
import com.gameserver.item.msg.GCBazooMallRequest;
import com.gameserver.item.template.ItemNewTemplate;
import com.gameserver.player.Player;
import com.gameserver.recharge.enums.CategoryEnum;
import com.gameserver.recharge.enums.SmallCategoryEnum;

/**
 * 物品服务
 * @author wayne
 *
 */
public class ItemNewService implements InitializeRequired{
	
	private Logger logger = Loggers.BAZOO;
	/**
	 * 所有道具 item
	 */
	List<ItemNewTemplate> itemNewTemplateList = new ArrayList<ItemNewTemplate>();
	@Override
	public void init() {
		Map<Integer,ItemNewTemplate> itemNewTemplateMap = Globals.getTemplateService().getAll(ItemNewTemplate.class);
		for(ItemNewTemplate item:itemNewTemplateMap.values()){
			if(item.getCardType() ==  CategoryEnum.CATEGORYENUM2.getIndex()){
				itemNewTemplateList.add(item);
			}
		}
		
	}
	
	public ItemInfoDataNew[] getItemMallList(Player player) {
		
		
		List<ItemInfoDataNew> ItemInfoDataNewList = new ArrayList<ItemInfoDataNew>();
		List<Item> itemList = player.getHuman().getHumanBagManager().getItemClock(SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex());
		for(ItemNewTemplate itemT:itemNewTemplateList){
			if(itemT.getItemType() == SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex()){	//默认 只取 色钟
				if(itemT.getNum() <= 1){
					continue;
				}
				int isExist = 0;
				for(Item item:itemList){
					if(item.getTemplateId() == itemT.getId()){
						isExist=1;
					}
				}
				ItemInfoDataNew itemInfoDataNew = ItemInfoDataNew.convertListFromItem(itemT);
				itemInfoDataNew.setIsExist(isExist);
				ItemInfoDataNewList.add(itemInfoDataNew);
			}
		}
		
		
		ItemInfoDataNew[] ItemInfoDataNewArr = new ItemInfoDataNew[ItemInfoDataNewList.size()];
		for(int i=0;i<ItemInfoDataNewList.size();i++){
			ItemInfoDataNew info = ItemInfoDataNewList.get(i);
			ItemInfoDataNewArr[i]=info;
		}
		return ItemInfoDataNewArr;
	}

	//色钟
	public GCBazooItemRequest getClockItem(Player player) {
		return player.getHuman().getHumanBagManager().getItemInfoDataNew(itemNewTemplateList,SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex());
	}
	//红包
	public GCBazooItemRequest getRedPackageItem(Player player) {
		return player.getHuman().getHumanBagManager().getItemInfoDataNew(itemNewTemplateList,SmallCategoryEnum.SMALLCATEGORYENUM3.getIndex());
	}
	//礼物
	public GCBazooItemRequest getGiftItem(Player player) {
		return player.getHuman().getHumanBagManager().getItemInfoDataNew(itemNewTemplateList,SmallCategoryEnum.SMALLCATEGORYENUM4.getIndex());
	}

	/**
	 * 更换色钟
	 * @param itemId
	 */
	public void changeItemClock(Player player,int itemId) {
		//获取所有色钟
		List<ItemNewTemplate> ItemNewTemplateList = getAllClock();
		
		Item item = player.getHuman().getHumanBagManager().getItem(itemId);
		if(item == null){
			logger.info("[无双吹牛]---[更换色钟]---[当前色钟不存在]---[当前用户ID:"+player.getPassportId()+"]");
			return;
		}
		//把背包中所有的色钟 全部置为 未使用
		player.getHuman().getHumanBagManager().setClockNotUse(ItemNewTemplateList);
		//把背包中 itemId 色钟 置为使用中
		player.getHuman().getHumanBagManager().setClockUse(itemId);
		
		//更换色钟返回
		GCBazooItemClockChange GCBazooItemClockChange = new GCBazooItemClockChange();
		GCBazooItemClockChange.setItemId(itemId);
		player.sendMessage(GCBazooItemClockChange);
		
		
		//通知房间内所有人 我更换了色钟
		Room room = Globals.getBazooPubService().getRoomService().getRoom(player);
		if(room == null){
			return;
		}
		GCBazooItemClockChangeToAll GCBazooItemClockChangeToAll = new GCBazooItemClockChangeToAll();
		GCBazooItemClockChangeToAll.setItemId(itemId);
		GCBazooItemClockChangeToAll.setPlayerId(player.getPassportId());
		for(ItemNewTemplate one:ItemNewTemplateList){
			if(one.getId() == itemId){
				GCBazooItemClockChangeToAll.setImg("http://"+Globals.getServerConfig().getHttpDataAddress()+"/clock/"+one.getIcon()+".png");
				break;
			}
		}
		for(Player p:room.getPlayers()){
				p.sendMessage(GCBazooItemClockChangeToAll);
		}
		
	}
	
	/**
	 * 获取所有的色钟
	 */
	public List<ItemNewTemplate> getAllClock(){
		List<ItemNewTemplate> ItemNewTemplateList = new ArrayList<ItemNewTemplate>();
		for(ItemNewTemplate template:itemNewTemplateList){
			if(template.getCardType() == CategoryEnum.CATEGORYENUM2.getIndex()
					&& template.getItemType() == SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex()){
				ItemNewTemplateList.add(template);
			}
		}
		return ItemNewTemplateList;
	}
	/**
	 * 购买色钟
	 */
	public boolean BuyItemByGold(Player curPlayer,long toPlayerId, int itemId,int itemType, int number) {
		ItemNewTemplate ItemNewTemplate = getItemTemplateByItemId(itemId);
		
		long needGold = Long.valueOf(ItemNewTemplate.getNum()*number).longValue();
		//首先看看钱够不够
		if(needGold>curPlayer.getHuman().getGold()){
			logger.info("[无双吹牛]---[发送送礼物]---[失败]---[金币不足]---[用户::"+curPlayer.getPassportId()+"-itemId"+itemId+"]");
			//失败 消息
			sendErrorMessage(curPlayer,1, LangConstants.DICE_USER_GOLD_NOT_ENOUGH,new String[]{needGold+""});
			return false;
		}
		//扣掉当前用户的钱
		curPlayer.getHuman().costMoney(needGold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_SEND_GIFT_COST_GOLD, LogReasons.GoldLogReason.BAZOO_SEND_GIFT_COST_GOLD.getReasonText(), -1, 1);
		//把礼物加到 目标对象身上
		Player toPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(toPlayerId);
		if(toPlayer != null){
			toPlayer.getHuman().getHumanBagManager().addItemNew(curPlayer.getPassportId(),toPlayer,itemId,itemType, number);
			
			//成功 消息
			sendErrorMessage(curPlayer,0, LangConstants.DICE_USER_GOLD_NOT_ENOUGH,new String[]{});
			//给接收人 推送商城列表
			GCBazooMallRequest GCBazooMallRequest = new GCBazooMallRequest();
			GCBazooMallRequest.setItemInfoDataNew(Globals.getItemNewService().getItemMallList(toPlayer));
			toPlayer.sendMessage(GCBazooMallRequest);
		//用户没有在线
		}else{
			List<HumanItemEntity> HumanItemEntityList = Globals.getDaoService().getHumanItemDao().getItemsByCharId(toPlayerId);
			for(HumanItemEntity entity :HumanItemEntityList){
				if(entity.getTemplateId() == itemId){
					entity.setOverlap(entity.getOverlap()+number);
					Globals.getDaoService().getHumanItemDao().saveOrUpdate(entity);
					break;
				}
			}
			//成功 消息
			sendErrorMessage(curPlayer,0, LangConstants.DICE_USER_GOLD_NOT_ENOUGH,new String[]{});
		}
		//给接收人 推送背包消息列表
		/*GCBazooItemRequest GCBazooItemRequest = toPlayer.getHuman().getHumanBagManager().getItemInfoDataNew(itemNewTemplateList,ItemNewTemplate.getItemType());
		toPlayer.sendMessage(GCBazooItemRequest);*/
		
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ItemNewTemplate getItemTemplateByItemId(int itemId){
		for(ItemNewTemplate Item:itemNewTemplateList){
			if(itemId == Item.getId()){
				return Item;
			}
		}
		return null;
	}
	public ItemNewTemplate getItemNewTemplateByItemId(int itemId){
		for(ItemNewTemplate item:itemNewTemplateList){
			if(itemId == item.getId()){
				return item;
			}
		}
		return null;
	}
	
	
	//不满足 条件返回的 对象
	public void sendErrorMessage(Player curPlayer,int isSucess, int langId,String[] params){
		GCBazooItemBuyByGold GCBazooItemBuyByGold = new GCBazooItemBuyByGold();
		GCBazooItemBuyByGold.setIsSucess(isSucess);
		GCBazooItemBuyByGold.setLangId(langId);
		GCBazooItemBuyByGold.setParamsList(params);
		curPlayer.sendMessage(GCBazooItemBuyByGold);
	}

	//默认色钟 就是 购买需要的金币为零
	public ItemNewTemplate getDefaultClock(){
		for(ItemNewTemplate template :itemNewTemplateList){
			if(template.getCardType() == CategoryEnum.CATEGORYENUM2.getIndex() 
					&& template.getItemType() == SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex()){
				if(template.getNum() <= 1){
					return template;
				}
			}
		}
		return null;
	}

	public List<ItemNewTemplate> getItemNewTemplateList() {
		return itemNewTemplateList;
	}

	public void setItemNewTemplateList(List<ItemNewTemplate> itemNewTemplateList) {
		this.itemNewTemplateList = itemNewTemplateList;
	}

	public ItemNewTemplate getUseingClock(Player p) {
		List<Item> itemList = p.getHuman().getHumanBagManager().getItemClock(SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex());
		for(Item item:itemList){
			if(item.getUseing() == 1){
				for(ItemNewTemplate template:itemNewTemplateList){
					if(template.getId() == item.getTemplateId()){
						return template;
					}
				}
			}
		}
		return null;
	}
	
	
	
	
	
}
