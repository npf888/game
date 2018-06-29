package com.gameserver.texas.sng;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanTexasSNGDao;
import com.db.model.HumanTexasSNGEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;


/**
 * sng updater
 * @author wayne
 *
 */
public class HumanTexasSNGUpdater  implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub

		HumanTexasSNGDao humanTexasSNGDao = Globals.getDaoService().getHumanTexasSNGDao();
		IIoOperation _oper = new SaveObjectOperation<HumanTexasSNGEntity,HumanTexasSNG>((HumanTexasSNG) obj, humanTexasSNGDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
