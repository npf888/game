package com.gameserver.scene.persistance;

import java.util.LinkedHashMap;
import java.util.Map;

import com.core.annotation.NotThreadSafe;
import com.core.object.LifeCycle;
import com.core.object.PersistanceObject;
import com.core.persistance.AbstractDataUpdater;
import com.gameserver.common.db.POUpdater;

/**
 * 场景更新接口
 * @author Thinker
 *
 */
@NotThreadSafe
public class SceneDataUpdater extends AbstractDataUpdater
{
	private static Map<Class<? extends PersistanceObject<?, ?>>, POUpdater> operationDbMap = new LinkedHashMap<Class<? extends PersistanceObject<?, ?>>, POUpdater>();

	static
	{
		
	}

	public SceneDataUpdater()
	{
		setUpdaterName("SceneDataUpdater");
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