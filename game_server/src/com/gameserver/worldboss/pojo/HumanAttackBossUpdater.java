package com.gameserver.worldboss.pojo;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanAttackBossDao;
import com.db.model.HumanAttackBossEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 任务更新
 * @author wayne
 *
 */
public class HumanAttackBossUpdater implements POUpdater{
	
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanAttackBossDao humanAttackBossDao=Globals.getDaoService().getHumanAttackBossDao();
		IIoOperation _oper = new SaveObjectOperation<HumanAttackBossEntity,HumanAttackBoss>((HumanAttackBoss) obj, humanAttackBossDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
