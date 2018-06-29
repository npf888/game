package com.gameserver.achievement;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanAchievementDao;
import com.db.model.HumanAchievementEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanAchievementUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		HumanAchievementDao dao = Globals.getDaoService().getHumanAchievementDao();
        IIoOperation _oper = new SaveObjectOperation<HumanAchievementEntity,HumanAchievement>((HumanAchievement) obj, dao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		throw new UnsupportedOperationException();
	}

}
