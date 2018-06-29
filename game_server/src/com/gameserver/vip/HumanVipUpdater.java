package com.gameserver.vip;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanVipDao;
import com.db.model.HumanVipEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;


/**
 * vip updater
 * @author wayne
 *
 */
public class HumanVipUpdater  implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		HumanVipDao humanVipDao = Globals.getDaoService().getVipDao();
		IIoOperation _oper = new SaveObjectOperation<HumanVipEntity,HumanVip>((HumanVip) obj, humanVipDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}



