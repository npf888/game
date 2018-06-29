package com.gameserver.conversioncode;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.ConversioncodeDao;
import com.db.model.Conversioncode;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class ConversioncodeUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		ConversioncodeDao conversioncodeDao = Globals.getDaoService().getConversioncodeDao();
		IIoOperation _oper = new SaveObjectOperation<Conversioncode,ConversioncodeData>((ConversioncodeData) obj, conversioncodeDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);

	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		ConversioncodeDao conversioncodeDao = Globals.getDaoService().getConversioncodeDao();
		Conversioncode conversioncode=(Conversioncode)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<Conversioncode>(conversioncode, 0L, conversioncodeDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);

	}

}
