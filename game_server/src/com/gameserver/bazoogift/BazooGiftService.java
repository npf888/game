package com.gameserver.bazoogift;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoogift.msg.GCBazooRedPackage;
import com.gameserver.bazoogift.msg.GCBazooSendGift;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.item.Item;
import com.gameserver.item.ItemNewService;
import com.gameserver.item.template.ItemNewTemplate;
import com.gameserver.mail.MailLogic;
import com.gameserver.mail.handler.MailHandlerFactory;
import com.gameserver.mail.msg.CGSendMail;
import com.gameserver.player.Player;
import com.gameserver.recharge.enums.SmallCategoryEnum;

public class BazooGiftService implements InitializeRequired {
	private Logger logger = Loggers.BAZOO;
	
	/**
	 * 所有道具 item
	 */
	List<ItemNewTemplate> itemNewTemplateList = new ArrayList<ItemNewTemplate>();
	
	ItemNewService itemNewService = null;
	public BazooGiftService(ItemNewService itemNewService){
		this.itemNewService=itemNewService;
	}
	@Override
	public void init() {
		itemNewTemplateList.addAll(itemNewService.getItemNewTemplateList());
	}

	/**
	 * 给某个人 发送礼物
	 * @param curPlayer
	 * @param toPlayerId
	 * @param itemId
	 * @param number
	 */
	public void sendGiftToSomeone(Player curPlayer,long toPlayerId, int itemId, int itemType,int number) {
		
		//红包
		if(itemType == SmallCategoryEnum.SMALLCATEGORYENUM3.getIndex()){
			List<ItemNewTemplate> itemNewTemplateList = itemNewService.getItemNewTemplateList();
			for(ItemNewTemplate template:itemNewTemplateList){
				if(template.getItemType() == SmallCategoryEnum.SMALLCATEGORYENUM3.getIndex()){
					itemId = template.getId();
				}
			}
		}
	
		boolean isSuccess = itemNewService.BuyItemByGold(curPlayer, toPlayerId, itemId,itemType, number);
		if(!isSuccess){//如果购买不成功
			return;
		}
		
		//红包 发送邮件 通知
		if(itemType == SmallCategoryEnum.SMALLCATEGORYENUM3.getIndex()){
			String title = String.valueOf(LangConstants.DICE_RED_PACKAGE_GET)+"|"+String.valueOf(curPlayer.getHuman().getName());
			String strContent = String.valueOf(LangConstants.DICE_RED_PACKAGE_GET);
			List<Long> listId = new ArrayList<Long>();
			listId.add(toPlayerId);
			//发邮件
			MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, new ArrayList<RandRewardData>()); 
			
		}
		
		ItemNewTemplate ItemNewTemplate = itemNewService.getItemTemplateByItemId(itemId);
		//成功之后  给房间内所有人发送消息
		Room room = Globals.getBazooPubService().getRoomService().getRoom(curPlayer);
		if(room == null){
			return;
		}
		List<Player> players = room.getPlayers();
		for(Player player:players){
			GCBazooSendGift GCBazooSendGift = getMessage(curPlayer,toPlayerId,itemId,number,ItemNewTemplate.getItemType());
			player.sendMessage(GCBazooSendGift);
		}
		
	}

	
	public GCBazooSendGift getMessage(Player curPlayer,long toPlayerId, int itemId, int number,int itemType){
		GCBazooSendGift GCBazooSendGift = new GCBazooSendGift();
		GCBazooSendGift.setFromPlayerGold(curPlayer.getHuman().getGold());
		GCBazooSendGift.setFromPlayerId(curPlayer.getPassportId());
		GCBazooSendGift.setToPlayerId(toPlayerId);
		GCBazooSendGift.setItemId(itemId);
		GCBazooSendGift.setItemType(itemType);//1：是金币，2：道具
		GCBazooSendGift.setNumber(number);
		return GCBazooSendGift;
	}
	/**
	 * 领取所有红包
	 * @param itemId
	 */
	public void takeAllRedPackage(Player player,int itemId) {
		ItemNewTemplate Item = itemNewService.getItemTemplateByItemId(itemId);
		Item item = player.getHuman().getHumanBagManager().getItem(itemId);
		if(item == null){//没有红包
			logger.info("[无双吹牛]---[领取红包失败]---[当前没有红包]---[当前用户ID:"+player.getPassportId()+"]");
			sendFeedBackMessage(player,1,LangConstants.DICE_NO_RED_PACKAGE,new String[]{});
			return;
		}
		int usering = item.getUseing();
		if(usering == 1){//已经领取
			logger.info("[无双吹牛]---[领取红包失败]---[当前红包已领取]---[当前用户ID:"+player.getPassportId()+"]");
			sendFeedBackMessage(player,1,LangConstants.DICE_HAS_TAKE_PACKAGE,new String[]{});
			return;
		}
		//红包总数量
		int totalNum = item.getOverlap();
		//单个红包 需要多少金币
		long gold = Item.getNum();
		long totalGold = totalNum*gold;
		//获得前
		player.getHuman().giveMoney(totalGold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_RED_PACKAGE_GET_GOLD, LogReasons.GoldLogReason.BAZOO_RED_PACKAGE_GET_GOLD.getReasonText(), -1, 1);
		item.setUseing(1);
		item.setModified();
		
		logger.info("[无双吹牛]---[领取红包失败]---[成功领取]---[当前用户ID:"+player.getPassportId()+"]");
		sendFeedBackMessage(player,0,LangConstants.DICE_HAS_TAKE_PACKAGE,new String[]{});
		
	}
	
	
	
	public void sendFeedBackMessage(Player player,int isSucess,int langId,String[] params){
		GCBazooRedPackage GCBazooRedPackage = new GCBazooRedPackage();
		GCBazooRedPackage.setIsSucess(isSucess);
		GCBazooRedPackage.setLangId(langId);
		GCBazooRedPackage.setParamsList(params);
		player.sendMessage(GCBazooRedPackage);
	}
}
