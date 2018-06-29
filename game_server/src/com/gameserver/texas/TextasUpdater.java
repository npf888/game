package com.gameserver.texas;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.TextasDao;
import com.db.model.TextasEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 
 * @author 郭君伟
 *
 */
public class TextasUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		TextasDao textasDao = Globals.getDaoService().getTextasDao();
		IIoOperation _oper = new SaveObjectOperation<TextasEntity,Textas>((Textas) obj, textasDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
	    throw new UnsupportedOperationException();

	}

}
