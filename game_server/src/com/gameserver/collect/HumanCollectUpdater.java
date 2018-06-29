package com.gameserver.collect;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanCollectDao;
import com.db.model.HumanCollectEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanCollectUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		HumanCollectDao collectDao=Globals.getDaoService().gethumanCollectDao();
		IIoOperation _oper = new SaveObjectOperation<HumanCollectEntity,HumanCollect>((HumanCollect) obj, collectDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		HumanCollectDao collectDao=Globals.getDaoService().gethumanCollectDao();
		HumanCollectEntity collectEntity=(HumanCollectEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<HumanCollectEntity>(collectEntity, collectEntity.getHumanId(), collectDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
