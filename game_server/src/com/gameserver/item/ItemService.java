package com.gameserver.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.item.enums.ItemType;
import com.gameserver.item.interfaces.IItemListener;
import com.gameserver.item.template.InteractiveItemTemplate;
import com.gameserver.item.template.ItemCostTemplate;
import com.gameserver.item.template.ItemTemplate;

/**
 * 物品服务
 * @author wayne
 *
 */
public class ItemService implements InitializeRequired,AfterInitializeRequired{
	
	/** 物品模板管理器,负责从模板中取得物品 */
	private TemplateService templateService;
	/** 物品模板map */
	private Map<Integer, ItemTemplate> itemTempMap;
	/**监听器*/
	private List<IItemListener> itemListnerList = new ArrayList<IItemListener>();
	
	/**游戏类型 房间ID  物品ID  对应的实体类 **/
	private Map<Integer,Map<Integer,Map<Integer,ItemCostTemplate>>> itemCostMap = new HashMap<Integer,Map<Integer,Map<Integer,ItemCostTemplate>>>();
	
	/**ID 数据 **/
	private Map<Integer,InteractiveItemTemplate> interactiveItemMap = new HashMap<Integer,InteractiveItemTemplate>();
	
	/**品质ID **/
	private List<Integer> InteractiveListist = new ArrayList<Integer>();
	/**品质ID 所属的物品**/
	private Map<Integer,List<InteractiveItemTemplate>> qualityData = new HashMap<Integer,List<InteractiveItemTemplate>>();
	
	//卡片收集============================================
	
	private Map<Integer,Set<Integer>> cardMap = new HashMap<Integer,Set<Integer>>();
	
	
	public ItemService(TemplateService templateService){
		this.templateService = templateService;
	
	}
	
	@Override
	public void init() {
		this.itemTempMap = templateService.getAll(ItemTemplate.class);
		
		Map<Integer, ItemCostTemplate> costMap = templateService.getAll(ItemCostTemplate.class);
		
		for(ItemCostTemplate cost : costMap.values()){
			
			int gameType = cost.getGameId();
			int roomId = cost.getRoomId();
			int itemId = cost.getItemId();
			
			if(itemCostMap.containsKey(gameType)){
				
				Map<Integer,Map<Integer,ItemCostTemplate>> map = itemCostMap.get(gameType);
				
				Map<Integer,ItemCostTemplate> map1 = map.get(roomId);
				
				if(map1 == null){
					map1 = new HashMap<Integer,ItemCostTemplate>();
					map.put(roomId, map1);	
				}
				map1.put(itemId, cost);
							
			}else{
				Map<Integer,Map<Integer,ItemCostTemplate>> map = new HashMap<Integer,Map<Integer,ItemCostTemplate>>();
				Map<Integer,ItemCostTemplate> map1 = new HashMap<Integer,ItemCostTemplate>();
				map1.put(itemId, cost);
				map.put(roomId, map1);
				itemCostMap.put(gameType, map);
			}
			
		}
		
		interactiveItemMap = templateService.getAll(InteractiveItemTemplate.class);
		
		for(ItemTemplate temp : itemTempMap.values()){
			
			int itemId = temp.getId();
			
			int cardType = temp.getCardType();
			
			Set<Integer> set = cardMap.get(cardType);
			
			if(set == null){
				set = new HashSet<Integer>();
				cardMap.put(cardType, set);
			}
			
			set.add(itemId);
		}
		
	}
	
	/**
	 * 获取发送物品表情
	 * @param gameType
	 * @param roomId
	 * @param itemId
	 * @return null 没有对应的物品
	 */
	public ItemCostTemplate getItemCostTemplate(int gameType,int roomId,int itemId){
		
		if(itemCostMap.containsKey(gameType)){
			Map<Integer,Map<Integer,ItemCostTemplate>> map = itemCostMap.get(gameType);
			if(map.containsKey(roomId)){
				Map<Integer,ItemCostTemplate> map1 = map.get(roomId);
				return map1.get(itemId);
			}
		}
		return null;
	}
	
	/**
	 * 根据ID获取道具模板
	 * @param itemId
	 * @return
	 */
	public ItemTemplate getItemTemplWithId(int itemId)
	{
		return itemTempMap.get(itemId);
	}

	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		
	}


	public void addItemListner(IItemListener itemListener){
		itemListnerList.add(itemListener);
	}
	
	public void removeItemListner(IItemListener itemListener){
		itemListnerList.remove(itemListener);
	}

	public void onSendInteractiveItem(Human human, int itemId) {
		// TODO Auto-generated method stub
		for(IItemListener itemListener :itemListnerList){
			itemListener.onSendInteractive(human, itemId);
		}
	}
	
	public InteractiveItemTemplate getInteractiveItem(int id){
		return interactiveItemMap.get(id);
	}
	
	/**
	 * 
	 * @param itemId
	 * @return
	 */
	public Map<Integer,Integer> getCarNum(int itemId){
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		if(itemTempMap.containsKey(itemId)){
			ItemTemplate temp = itemTempMap.get(itemId);
			
			int rewardId = temp.getRewardId();
			
			if(rewardId != 0 && rewardId != 3){//TODO 这里判断需要改进
				if(itemTempMap.containsKey(rewardId)){
					ItemTemplate tempRew = itemTempMap.get(rewardId);
					
					map.put(tempRew.getPoolType(), temp.getRewardNum());
				}
			}else{
				map.put(temp.getPoolType(), 1);
			}
		}
		return map;
	}
	
	/**
	 * 获取物品类型
	 * @param itemId
	 * @return
	 */
	public int getItemType(int itemId){
		//现在背包里找
		if(itemTempMap.containsKey(itemId)){
			return itemTempMap.get(itemId).getItemType();
		}
		//如果在背包里没有找到 就去 currency
		Currency[] currencys = Currency.values();
		for(Currency currency:currencys){
			if(itemId == currency.GOLD.getIndex()){
				return ItemType.ItemType11.getIndex();
			}
		}
		//什么也没有找到 就是 -1
		return -1;
	}
	
	/**
	 * 获取钱
	 * @param itemId
	 * @return
	 */
	public int getMoney(int itemId){
		if(itemTempMap.containsKey(itemId)){
			return itemTempMap.get(itemId).getRewardNum();
		}
		return 0;
	}
	
	public int getCards(int cardType){
		//这个判断 是后来加的 不知道 是不是 对其他地方有影响 TODO
		if(cardMap.get(cardType) == null){
			return 0;
		}
		return cardMap.get(cardType).size();
	}
	
	public Set<Integer> cardId(int cardType){
		return cardMap.get(cardType);
	}
	
	public boolean getCardId(int itemId,int cardType){
		return itemTempMap.get(itemId).getCardType() == cardType;
	}
	/**
	 * 根据 poolType 来获取 金银铜
	 * @return
	 */
	public ItemTemplate getItemTemplateByPoolType(int poolType){
		for(ItemTemplate itemTemplate:itemTempMap.values()){
			if(itemTemplate.getPoolType() == poolType){
				return itemTemplate;
			}
		}
		return null;
	}
	
	public List<ItemTemplate> getItemIdByItemType(int itemType){
		List<ItemTemplate> itemTemplateList = new ArrayList<ItemTemplate>();
		for(ItemTemplate itemTemplate:itemTempMap.values()){
			if(itemType == itemTemplate.getItemType()){
				itemTemplateList.add(itemTemplate);
			}
		}
		return itemTemplateList;
	}
}
