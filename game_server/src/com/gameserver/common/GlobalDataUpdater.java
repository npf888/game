package com.gameserver.common;


import java.util.LinkedHashMap;
import java.util.Map;

import com.core.annotation.NotThreadSafe;
import com.core.object.LifeCycle;
import com.core.object.PersistanceObject;
import com.core.persistance.AbstractDataUpdater;
import com.gameserver.activity.Activity;
import com.gameserver.activity.ActivityUpdater;
import com.gameserver.activity.CommonActivity;
import com.gameserver.activity.CommonActivityUpdater;
import com.gameserver.baccart.BaccartRoomModel;
import com.gameserver.baccart.BaccartRoomModelUpdater;
import com.gameserver.bazoo.service.room.BazooRoomCreate;
import com.gameserver.bazoo.service.room.BazooRoomCreateUpdater;
import com.gameserver.common.db.POUpdater;
import com.gameserver.compensation.Compensation;
import com.gameserver.compensation.CompensationUpdater;
import com.gameserver.conversioncode.ConversioncodeData;
import com.gameserver.conversioncode.ConversioncodeUpdater;
import com.gameserver.function.Function;
import com.gameserver.function.FunctionUpdater;
import com.gameserver.notice.Notice;
import com.gameserver.notice.NoticeUpdater;
import com.gameserver.ranknew.Rank;
import com.gameserver.ranknew.RankUpdater;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotUpdater;
import com.gameserver.texas.Textas;
import com.gameserver.texas.TextasUpdater;
import com.gameserver.worldboss.pojo.Boss;
import com.gameserver.worldboss.pojo.BossUpdater;



/**
 * 场景更新接口
 * @author Thinker
 *
 */
@NotThreadSafe
public class GlobalDataUpdater extends AbstractDataUpdater
{
	private static Map<Class<? extends PersistanceObject<?, ?>>, POUpdater> operationDbMap = new LinkedHashMap<Class<? extends PersistanceObject<?, ?>>, POUpdater>();

	static
	{
		operationDbMap.put(Notice.class, new NoticeUpdater());
		operationDbMap.put(Activity.class, new ActivityUpdater());
		operationDbMap.put(Function.class, new FunctionUpdater());
		operationDbMap.put(CommonActivity.class, new CommonActivityUpdater());
		operationDbMap.put(Compensation.class, new CompensationUpdater());
		operationDbMap.put(BaccartRoomModel.class, new BaccartRoomModelUpdater());
		operationDbMap.put(Slot.class, new SlotUpdater());
		operationDbMap.put(Textas.class, new TextasUpdater());
		operationDbMap.put(Rank.class, new RankUpdater());
		operationDbMap.put(ConversioncodeData.class, new ConversioncodeUpdater());
		operationDbMap.put(Boss.class, new BossUpdater());
//		operationDbMap.put(HumanAttackBoss.class, new HumanAttackBossUpdater());
		operationDbMap.put(BazooRoomCreate.class, new BazooRoomCreateUpdater());
		
	}
	public GlobalDataUpdater()
	{
		setUpdaterName("GlobalDataUpdater");
	}

	@Override
	protected void doUpdate(LifeCycle lc)
	{
		if (!lc.isActive())
		{
			throw new IllegalStateException("Only the live object can be updated.");
		}

		PersistanceObject<?, ?> po = lc.getPO();
		POUpdater poUpdater = operationDbMap.get(po.getClass());
		poUpdater.save(po);
	}

	@Override
	protected void doDel(LifeCycle lc)
	{
		if (!lc.isDestroyed())
		{
			throw new IllegalStateException("Only the destroyed object can be deleted.");
		}
		PersistanceObject<?, ?> po = lc.getPO();
		operationDbMap.get(po.getClass()).delete(po);
	}
}