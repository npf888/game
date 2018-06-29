package com.gameserver.slot;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.RobotStatisDataDao;
import com.db.model.RobotStatisDataEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class RobotStatisDataUpdater  implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		RobotStatisDataDao robotStatisDataDao = Globals.getDaoService().getRobotStatisDataDao();
		IIoOperation _oper = new SaveObjectOperation<RobotStatisDataEntity,RobotStatisData>((RobotStatisData) obj, robotStatisDataDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


}
