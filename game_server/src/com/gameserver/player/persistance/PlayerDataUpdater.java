package com.gameserver.player.persistance;

import java.util.LinkedHashMap;
import java.util.Map;

import com.core.annotation.NotThreadSafe;
import com.core.object.LifeCycle;
import com.core.object.PersistanceObject;
import com.core.persistance.AbstractDataUpdater;
import com.gameserver.achievement.HumanAchievement;
import com.gameserver.achievement.HumanAchievementUpdater;
import com.gameserver.activity.HumanActivity;
import com.gameserver.activity.HumanActivityUpdater;
import com.gameserver.activity.HumanMonthWeek;
import com.gameserver.activity.HumanMonthWeekUpdater;
import com.gameserver.baccart.HumanBaccart;
import com.gameserver.baccart.HumanBaccartUpdater;
import com.gameserver.bazooachieve.HumanBazooAchieve;
import com.gameserver.bazooachieve.HumanBazooAchieveUpdater;
import com.gameserver.bazoonewguy.HumanBazooNewGuy;
import com.gameserver.bazoonewguy.HumanBazooNewGuyUpdater;
import com.gameserver.bazoopersonal.HumanBazooPersonal;
import com.gameserver.bazoopersonal.HumanBazooPersonalUpdater;
import com.gameserver.bazoorank.HumanBazooRank;
import com.gameserver.bazoorank.HumanBazooRankUpdater;
import com.gameserver.bazoosignin.HumanBazooSignIn;
import com.gameserver.bazoosignin.HumanBazooSignInUpdater;
import com.gameserver.bazootask.HumanBazooTask;
import com.gameserver.bazootask.HumanBazooTaskUpdater;
import com.gameserver.bazoowins.HumanBazooWins;
import com.gameserver.bazoowins.HumanBazooWinsUpdater;
import com.gameserver.collect.HumanCollect;
import com.gameserver.collect.HumanCollectUpdater;
import com.gameserver.common.db.POUpdater;
import com.gameserver.compensation.HumanCompensation;
import com.gameserver.compensation.HumanCompensationUpdater;
import com.gameserver.gift.HumanGift;
import com.gameserver.gift.HumanGiftUpdater;
import com.gameserver.gift.HumanNewComer;
import com.gameserver.gift.HumanNewComerUpdater;
import com.gameserver.givealike.pojo.HumanGivealike;
import com.gameserver.givealike.pojo.HumanGivealikeUpdater;
import com.gameserver.guide.pojo.HumanPayguide;
import com.gameserver.guide.pojo.HumanPayguideUpdater;
import com.gameserver.human.Human;
import com.gameserver.human.HumanUpdater;
import com.gameserver.item.Item;
import com.gameserver.item.ItemUpdater;
import com.gameserver.luckyspin.HumanLuckyMatch;
import com.gameserver.luckyspin.HumanLuckyMatchUpdater;
import com.gameserver.luckyspin.HumanLuckySpin;
import com.gameserver.luckyspin.HumanLuckySpinUpdater;
import com.gameserver.mail.Mail;
import com.gameserver.mail.MailUpdater;
import com.gameserver.misc.HumanMisc;
import com.gameserver.misc.HumanMiscUpdater;
import com.gameserver.monthcard.HumanMonthCard;
import com.gameserver.monthcard.HumanMonthCardUpdater;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.HumanRechargeOrderUpdater;
import com.gameserver.refund.HumanRefund;
import com.gameserver.refund.HumanRefundUpdater;
import com.gameserver.relation.Friend;
import com.gameserver.relation.FriendGift;
import com.gameserver.relation.FriendGiftUpdater;
import com.gameserver.relation.FriendRequest;
import com.gameserver.relation.FriendRequestUpdater;
import com.gameserver.relation.FriendUpdater;
import com.gameserver.signin.HumanWeekSignIn;
import com.gameserver.signin.HumanWeekSignInUpdater;
import com.gameserver.slot.HumanSlot;
import com.gameserver.slot.HumanSlotUpdater;
import com.gameserver.slot.RobotStatisData;
import com.gameserver.slot.RobotStatisDataUpdater;
import com.gameserver.task.HumanTask;
import com.gameserver.task.HumanTaskUpdater;
import com.gameserver.texas.HumanTexas;
import com.gameserver.texas.HumanTexasUpdater;
import com.gameserver.texas.sng.HumanTexasSNG;
import com.gameserver.texas.sng.HumanTexasSNGUpdater;
import com.gameserver.treasury.HumanTreasury;
import com.gameserver.treasury.HumanTreasuryUpdater;
import com.gameserver.vip.HumanVip;
import com.gameserver.vip.HumanVipUpdater;
import com.gameserver.vipnew.HumanVipNew;
import com.gameserver.vipnew.HumanVipNewUpdater;
import com.gameserver.weekcard.HumanWeekCard;
import com.gameserver.weekcard.HumanWeekCardUpdater;


/**
 * 
 * Player数据更新接口:角色要保持的数据都在这里注册
 * @author Thinker:异步存储必须这里注册
 * 
 */
@NotThreadSafe
public class PlayerDataUpdater extends AbstractDataUpdater
{
	private static Map<Class<? extends PersistanceObject<?, ?>>, POUpdater> operationDbMap = new LinkedHashMap<Class<? extends PersistanceObject<?, ?>>, POUpdater>();

	static
	{
		operationDbMap.put(Human.class, new HumanUpdater());
		operationDbMap.put(HumanTexas.class, new HumanTexasUpdater());
		
		operationDbMap.put(HumanMonthCard.class, new HumanMonthCardUpdater());
		operationDbMap.put(HumanWeekCard.class, new HumanWeekCardUpdater());
		operationDbMap.put(Friend.class, new FriendUpdater());
;
		
		operationDbMap.put(FriendRequest.class, new FriendRequestUpdater());
		operationDbMap.put(FriendGift.class, new FriendGiftUpdater());
		operationDbMap.put(HumanWeekSignIn.class, new HumanWeekSignInUpdater());
		operationDbMap.put(HumanMisc.class, new HumanMiscUpdater());
		operationDbMap.put(HumanRechargeOrder.class, new HumanRechargeOrderUpdater());
		operationDbMap.put(HumanTask.class, new HumanTaskUpdater());
		operationDbMap.put(HumanActivity.class, new HumanActivityUpdater());
		operationDbMap.put(HumanVip.class, new HumanVipUpdater());
		operationDbMap.put(HumanVipNew.class, new HumanVipNewUpdater());
		operationDbMap.put(Mail.class, new MailUpdater());
		operationDbMap.put(Item.class, new ItemUpdater());
		operationDbMap.put(HumanTexasSNG.class, new HumanTexasSNGUpdater());
		operationDbMap.put(HumanCompensation.class, new HumanCompensationUpdater());
		operationDbMap.put(HumanRefund.class, new HumanRefundUpdater());
		operationDbMap.put(HumanBaccart.class, new HumanBaccartUpdater());
		operationDbMap.put(HumanLuckySpin.class, new HumanLuckySpinUpdater());
		operationDbMap.put(HumanLuckyMatch.class, new HumanLuckyMatchUpdater());
		operationDbMap.put(HumanSlot.class, new HumanSlotUpdater());
		operationDbMap.put(HumanGift.class, new HumanGiftUpdater());
		operationDbMap.put(HumanAchievement.class, new HumanAchievementUpdater());
		operationDbMap.put(RobotStatisData.class, new RobotStatisDataUpdater());
		operationDbMap.put(HumanCollect.class, new HumanCollectUpdater());
		operationDbMap.put(HumanTreasury.class, new HumanTreasuryUpdater());
		operationDbMap.put(HumanGivealike.class, new HumanGivealikeUpdater());
		operationDbMap.put(HumanMonthWeek.class, new HumanMonthWeekUpdater());
		operationDbMap.put(HumanPayguide.class, new HumanPayguideUpdater());
		operationDbMap.put(HumanNewComer.class, new HumanNewComerUpdater());
		operationDbMap.put(HumanBazooSignIn.class, new HumanBazooSignInUpdater());
		operationDbMap.put(HumanBazooRank.class, new HumanBazooRankUpdater());
		operationDbMap.put(HumanBazooPersonal.class, new HumanBazooPersonalUpdater());
		operationDbMap.put(HumanBazooTask.class, new HumanBazooTaskUpdater());
		operationDbMap.put(HumanBazooAchieve.class, new HumanBazooAchieveUpdater());
		operationDbMap.put(HumanBazooWins.class, new HumanBazooWinsUpdater());
		operationDbMap.put(HumanBazooNewGuy.class, new HumanBazooNewGuyUpdater());
		
	}

	public PlayerDataUpdater()
	{
		super();
		setUpdaterName("PlayerDataUpdater");
	}

	@Override
	protected void doUpdate(LifeCycle lc)
	{
		if(!lc.isActive())
		{
			throw new IllegalStateException("Only the live object can be updated.");
		}
		PersistanceObject<?, ?> po = lc.getPO();
		POUpdater poUpdater = operationDbMap.get(po.getClass());

		if (poUpdater == null) 
		{
			// 抛出运行时异常
			throw new RuntimeException("cannot find Updater for "+ po.getClass().getName());
		}
		poUpdater.save(po);
	}

	@Override
	protected void doDel(LifeCycle lc)
	{
		if(!lc.isDestroyed())
		{
			throw new IllegalStateException("Only the destroyed object can be deleted.");
		}
		PersistanceObject<?, ?> po = lc.getPO();
		operationDbMap.get(po.getClass()).delete(po);
	}
}
