package com.gameserver.human.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.common.InitializeRequired;
import com.core.util.RandomUtil;
import com.core.uuid.UUIDType;
import com.db.model.HumanCollectEntity;
import com.gameserver.collect.HumanCollect;
import com.gameserver.collect.data.ItemData1;
import com.gameserver.collect.data.ItemData2;
import com.gameserver.collect.msg.GCCollectInit;
import com.gameserver.collect.msg.GCGetVouchers;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.item.template.ItemTemplate;

/**
 * 收集系统管理器
 * 
 * @author 郭君伟
 *
 */
public class HumanCollectManager implements InitializeRequired {

	private Human human;

	private HumanCollect humanCollect;

	public HumanCollectManager(Human human) {
		this.human = human;
	}

	/**
	 * 加载数据库数据到内存
	 */
	public void load() {
		HumanCollectEntity entity = Globals.getDaoService().gethumanCollectDao()
				.getCollectHumanById(human.getPassportId());

		humanCollect = new HumanCollect();
		humanCollect.setOwner(human);

		if (entity == null) {
			long currTime = Globals.getTimeService().now();
			humanCollect.setDbId(Globals.getUUIDService().getNextUUID(currTime, UUIDType.COLLECT));
			humanCollect.setHumanId(human.getPassportId());
			
			Map<Integer,Integer> carIds = humanCollect.getCarIds();
			if(carIds == null || carIds.size() == 0){
				carIds = new HashMap<Integer,Integer>();
				carIds.put(1, 1);
				carIds.put(2, 1);
				carIds.put(3, 1);
				humanCollect.setCarIds(carIds);
				ItemTemplate  ItemTemplate1 = Globals.getItemService().getItemTemplateByPoolType(1);
				ItemTemplate  ItemTemplate2 = Globals.getItemService().getItemTemplateByPoolType(2);
				ItemTemplate  ItemTemplate3 = Globals.getItemService().getItemTemplateByPoolType(3);
				human.getHumanBagManager().addItem(ItemTemplate1.getId(), 1);
				human.getHumanBagManager().addItem(ItemTemplate2.getId(), 1);
				human.getHumanBagManager().addItem(ItemTemplate3.getId(), 1);
			}
			humanCollect.setCreateTime(currTime);
			humanCollect.getCarIds();
			humanCollect.setInDb(false);
			humanCollect.active();
			humanCollect.setModified();
		} else {
			humanCollect.fromEntity(entity);
		}

	}

	@Override
	public void init() {
	}

	/**
	 * 每转动老虎机调用，用于抽奖券的获得
	 * 
	 * @param slotId
	 *            老虎机ID
	 * @param itemId
	 *            掉落的物品ID
	 * @param count1
	 *            掉落的个数
	 * @param count2
	 *            多少次内
	 */
	public void spinSlot(int slotId, int itemId, int count1, int count2) {

		Map<Integer, Integer> map1 = humanCollect.getSlotpoint();

		Map<Integer, Integer> map2 = humanCollect.getSlotspin();

		if (!map1.containsKey(slotId) || !map2.containsKey(slotId)) {
			map1.put(slotId, RandomUtil.nextInt(0, count2));
			map2.put(slotId, 1);
		} else {
			map2.put(slotId, map2.get(slotId) + 1);
		}

		int num = map2.get(slotId);
		if (num > count2) {// 清空数据从头算起
			map1.put(slotId, RandomUtil.nextInt(0, count2));
			map2.put(slotId, 1);
		}

		int point = map1.get(slotId);

		if (num == point) {// 中奖了
			Map<Integer, Integer> carIds = humanCollect.getCarIds();
			Map<Integer, Integer> data = Globals.getItemService().getCarNum(itemId);
			for (Entry<Integer, Integer> en : data.entrySet()) {
				int type = en.getKey();
				int value = en.getValue();
				if (carIds.containsKey(type)) {
					carIds.put(type, carIds.get(type) + value);
				} else {
					carIds.put(type, value);
				}
			}
			sendVouchers(itemId, 1);// 获取1个物品
		}
		humanCollect.setModified();
	}

	/**
	 * 魅力值兑换或者 购买卷
	 * 
	 * @param itemId
	 * @param num
	 */
	public void exchangeAndBuy(int itemId, int num) {
		Map<Integer, Integer> data = Globals.getItemService().getCarNum(itemId);
		if (!data.isEmpty()) {
			Map<Integer, Integer> carIds = humanCollect.getCarIds();
			for (Entry<Integer, Integer> en : data.entrySet()) {
				int type = en.getKey();
				int value = en.getValue() * num;
				if (carIds.containsKey(type)) {
					carIds.put(type, carIds.get(type) + value);
				} else {
					carIds.put(type, value);
				}
			}
			humanCollect.setModified();
			sendVouchers(itemId, num);
		}
	}
	/**
	 * 金银铜 添加到 收集系统， 例如世界boss 获得了 金银铜，然后同步到收集系统
	 * @param itemId
	 * @param num
	 */
	public void syncToCollect(int itemId, int num) {
		Map<Integer, Integer> data = Globals.getItemService().getCarNum(itemId);
		if (!data.isEmpty()) {
			Map<Integer, Integer> carIds = humanCollect.getCarIds();
			for (Entry<Integer, Integer> en : data.entrySet()) {
				int type = en.getKey();
				int value = en.getValue() * num;
				if (carIds.containsKey(type)) {
					carIds.put(type, carIds.get(type) + value);
				} else {
					carIds.put(type, value);
				}
			}
			humanCollect.setModified();
		}
	}
	
	/**
	 * 同时修改 背包 和收集 系统 中的 魅力值兑换或者 购买卷
	 * 
	 * @param itemId
	 * @param num
	 */
	public void exchangeAndBuyBoth(int itemId, int num,boolean send) {
		Map<Integer, Integer> data = Globals.getItemService().getCarNum(itemId);
		if (!data.isEmpty()) {
			Map<Integer, Integer> carIds = humanCollect.getCarIds();
			for (Entry<Integer, Integer> en : data.entrySet()) {
				int type = en.getKey();
				int value = en.getValue() * num;
				if (carIds.containsKey(type)) {
					carIds.put(type, carIds.get(type) + value);
					
					human.getHumanBagManager().addItem(itemId, value);
				} else {
					human.getHumanBagManager().addItem(itemId, value);
					carIds.put(type, value);
					
				}
			}
			humanCollect.setModified();
			
			//刮奖的时候不用发，魅力值兑换的时候发
			if(send){
				sendVouchers(itemId, num);
			}
		}
	}

	
	
	private void sendVouchers(int itemid, int num) {
		GCGetVouchers message = new GCGetVouchers();
		message.setItemId(itemid);
		message.setNum(num);
		this.human.sendMessage(message);
	}

	public ItemData1[] getItemData1() {
		Map<Integer, Integer> map = humanCollect.getCarIds();
		ItemData1[] data = new ItemData1[map.size()];
		int index = 0;
		for (Entry<Integer, Integer> en : map.entrySet()) {
			ItemData1 itemData = new ItemData1();
			itemData.setCardType(en.getKey());
			itemData.setNum(en.getValue());
			data[index++] = itemData;
		}
		return data;
	}

	/**
	 * 过滤掉其他物品
	 * 
	 * @return
	 */
	public ItemData2[] getItemData2() {
		Map<Integer, Integer> map = humanCollect.getDebris();
		ItemData2[] data = new ItemData2[map.size()];
		int index = 0;
		for (Entry<Integer, Integer> en : map.entrySet()) {
			ItemData2 itemData = new ItemData2();
			itemData.setItemid(en.getKey());
			itemData.setNum(en.getValue());
			data[index++] = itemData;
		}
		return data;
	}

	public GCCollectInit getGCCollectInit() {
		GCCollectInit message = new GCCollectInit();
		message.setItemData1(getItemData1());
		message.setItemData2(getItemData2());
		return message;
	}

	/**
	 * 是否有奖券
	 * 
	 * @param poolType
	 * @return
	 */
	public boolean isEnough(int poolType) {
		Map<Integer, Integer> carIds = humanCollect.getCarIds();
		if (carIds.containsKey(poolType)) {
			return carIds.get(poolType) > 0;
		}
		return false;
	}

	public void remCard(int poolType) {
		Map<Integer, Integer> debris = humanCollect.getCarIds();
		int num = debris.get(poolType);
		debris.put(poolType, num - 1 < 0 ? 0 : num - 1);
	}

	public void save() {
		humanCollect.setModified();
	}

	public void addDebris(int itemId, int num) {
		Map<Integer, Integer> debris = humanCollect.getDebris();
		if (debris.containsKey(itemId)) {
			debris.put(itemId, debris.get(itemId) + num);
		} else {
			debris.put(itemId, num);
		}
	}

	/**
	 * 兌換卡片是否足夠
	 * 
	 * @param cardType
	 * @return true : 條件允許
	 */
	public boolean iscardEnough(int cardType) {
		Map<Integer, Integer> debris = humanCollect.getDebris();
		int size = Globals.getItemService().getCards(cardType);
		int n = 0;
		for (int key : debris.keySet()) {
			if (Globals.getItemService().getCardId(key, cardType)) {
				n++;
			}
		}
		return size == n;
	}

	public void remCardTyp(int cardType) {
		Map<Integer, Integer> debris = humanCollect.getDebris();

		Set<Integer> set = Globals.getItemService().cardId(cardType);
		if(set == null || set.size() == 0){
			return;
		}
		for (int itemId : set) {
			int n = debris.get(itemId) - 1;

			if (n <= 0) {
				debris.put(itemId, 0);
			} else {
				debris.put(itemId, n);
			}
		}
	}

	/**
	 * 移除 使用过的 金 银 铜
	 * @param poolType
	 */
	public void removeCard(int poolType) {
		Map<Integer, Integer> cardIds = humanCollect.getCarIds();
		for(Map.Entry<Integer,Integer>  entry: cardIds.entrySet()){
			int type = entry.getKey();
			int value = entry.getValue();
			if(poolType == type){
				if(value > 1){
					cardIds.put(type, value-1);
				}else{
					cardIds.put(type, 0);
				}
			}
		}
		humanCollect.setModified();
		
	}

}
