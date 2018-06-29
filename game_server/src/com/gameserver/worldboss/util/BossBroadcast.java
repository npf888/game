package com.gameserver.worldboss.util;

import java.util.ArrayList;
import java.util.List;

import com.db.model.HumanAttackBossEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.item.template.ItemTemplate;
import com.gameserver.mail.MailLogic;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.worldboss.pojo.Boss;
import com.gameserver.worldboss.template.BossList;
import com.gameserver.worldboss.template.BossTemplate;
import com.gameserver.worldboss.vo.AttackRankVO;

public class BossBroadcast {

	
	
	/**
	 * 如果boss 是被干 死的 就发送，rewardNum1
	 * @param human
	 */
	public  void broadcast(long rewardNum,Boss boss) {
		
		//给所有人 发送邮件 加钱
		List<AttackRankVO> AttackRankList = Globals.getWorldBossNewService().getAttackRankVOList();
		for(AttackRankVO entity:AttackRankList){
			
			
			long attackTotalBlood = entity.getAttackTotalBlood();
			long totalBlood = boss.getIncreaseBlood()+boss.getBlood();
			long humanShuldBlood = rewardNum*attackTotalBlood/totalBlood;
			
			
			PlayerCacheInfo  playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(entity.getUserId());
//			String title = String.valueOf(LangConstants.WORLD_BOSS_REWARD);
			String title = "World Boss Gold Reward";
			String strContent = "Congratulations ! Thanks for  take part in!";
			List<Long> listId = new ArrayList<Long>();
			listId.add(playerCacheInfo.getPlayerId());
			
			
			List<RandRewardData> list = new ArrayList<RandRewardData>();
			RandRewardData data = new RandRewardData();
			data.setRewardCount(Long.valueOf(humanShuldBlood).intValue());
			data.setRewardId(Currency.GOLD.index);
			list.add(data);
			//发邮件
			MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, list);
		}
	}
	@SuppressWarnings("unchecked")
	public  void broadcast1(List<AttackRankVO> attackRankVOList,List<BossList> bossList) {
		//获取前 100名 有机会获得 工具奖励
		int size = 100;
		if(attackRankVOList.size() < size){
			size = attackRankVOList.size();
		}
		for(int j=0;j<size;j++){
			int number = j+1;
			AttackRankVO entity = attackRankVOList.get(j);
			for(int i=0;i<bossList.size();i++){
				BossList singleBoss = bossList.get(i);
				int list1 = singleBoss.getList1();
				int list2 = singleBoss.getList2();
				if(number>=list1 && number<=list2){
					if(singleBoss.getItemid2() > 100){
						ItemTemplate  itemTemplate  = Globals.getItemService().getItemTemplWithId(singleBoss.getItemid2());
						/**
						 * 发送背包奖励
						 * 
						 */
						PlayerCacheInfo  playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(entity.getUserId());
						String title = "World Boss Item Reward";
						String strContent = "Congratulations ! Thanks for  take part in!";
						List<Long> listId = new ArrayList<Long>();
						listId.add(playerCacheInfo.getPlayerId());
						
						List<RandRewardData> list = new ArrayList<RandRewardData>();
						RandRewardData data = new RandRewardData();
						data.setRewardCount(singleBoss.getRewardNum2());
						data.setRewardId(Integer.valueOf(itemTemplate.getId()));
						list.add(data);
						//发邮件
						MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, list);
					}else{
						PlayerCacheInfo  playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(entity.getUserId());
//						String title = String.valueOf(LangConstants.WORLD_BOSS_REWARD);
						String title = "World Boss Gold Reward";
						String strContent = "Congratulations ! Thanks for  take part in!";
						List<Long> listId = new ArrayList<Long>();
						listId.add(playerCacheInfo.getPlayerId());
						
						
						List<RandRewardData> list = new ArrayList<RandRewardData>();
						RandRewardData data = new RandRewardData();
						data.setRewardCount(singleBoss.getRewardNum2());
						data.setRewardId(Currency.GOLD.index);
						list.add(data);
						//发邮件
						MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, list);
					}
					break;
				}
			}
		}
		
	}
	
	//给个人的奖励
	public  void giveTHeHumanThing(BossTemplate bossTemplate,Human human) {
		
		/**
		 * 物品 bossTemplate.getRewardlastID() > 100 都是物品
		 * 金币 bossTemplate.getRewardlastID() < 100 都是金币
		 */
		if(bossTemplate.getRewardlastID() > 100){
			ItemTemplate  itemTemplate  = Globals.getItemService().getItemTemplWithId(bossTemplate.getRewardlastID());
			/**
			 * 发送背包奖励
			 * 
			 */
			String title = "World Boss Item Reward special";
			String strContent = "Congratulations ! Thanks for  take part in!";
			List<Long> listId = new ArrayList<Long>();
			listId.add(human.getPassportId());
			
			List<RandRewardData> list = new ArrayList<RandRewardData>();
			RandRewardData data = new RandRewardData();
			data.setRewardCount(bossTemplate.getRewardNum());
			data.setRewardId(Integer.valueOf(itemTemplate.getId()));
			list.add(data);
			//发邮件
			MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, list);
		}
		
		if(bossTemplate.getRewardlastID() <= 100){
			//给所有人 发送邮件 加钱
//				String title = String.valueOf(LangConstants.WORLD_BOSS_REWARD);
				String title = "World Boss be kill by you ";
				String strContent = "Congratulations ! Thanks for  killed the boss ,this is the reward";
				List<Long> listId = new ArrayList<Long>();
				listId.add(human.getPassportId());
				
				
				List<RandRewardData> list = new ArrayList<RandRewardData>();
				RandRewardData data = new RandRewardData();
				data.setRewardCount(bossTemplate.getRewardNum());
				data.setRewardId(Currency.GOLD.index);
				list.add(data);
				//发邮件
				MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, list);
		}
	}
}
