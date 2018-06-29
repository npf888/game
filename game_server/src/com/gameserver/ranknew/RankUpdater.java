package com.gameserver.ranknew;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.RankDao;
import com.db.model.RankEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;
/**
 * 
 * @author 郭君伟
 *
 */
public class RankUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		
		RankDao rankDao = Globals.getDaoService().getRankDao();
		
		IIoOperation _oper = new SaveObjectOperation<RankEntity,Rank>((Rank) obj, rankDao);
		
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		throw new UnsupportedOperationException();
	}

}
