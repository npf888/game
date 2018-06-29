package com.gameserver.gift;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanGiftDao;
import com.db.model.HumanGiftEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;
/**
 * 
 * @author 郭君伟
 *
 */
public class HumanGiftUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		
		HumanGiftDao humanGiftDao = Globals.getDaoService().getHumanGiftDao();
		IIoOperation _oper = new SaveObjectOperation<HumanGiftEntity,HumanGift>((HumanGift) obj, humanGiftDao);
		
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);

	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		throw new UnsupportedOperationException();
	}

}
