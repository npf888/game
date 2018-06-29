package com.gameserver.baccart;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanBaccartDao;
import com.db.model.HumanBaccartEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;


/**
 * 百家乐更新
 * @author wayne
 *
 */
public class HumanBaccartUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanBaccartDao humanBaccartDao = Globals.getDaoService().getHumanBaccartDao();
		IIoOperation _oper = new SaveObjectOperation<HumanBaccartEntity,HumanBaccart>((HumanBaccart) obj, humanBaccartDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}

