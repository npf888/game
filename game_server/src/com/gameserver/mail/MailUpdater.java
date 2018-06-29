package com.gameserver.mail;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.MailDao;
import com.db.model.MailEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 邮件更新
 * @author wayne
 *
 */
public class MailUpdater implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		MailDao mailDao=Globals.getDaoService().getMailDao();
		IIoOperation _oper = new SaveObjectOperation<MailEntity,Mail>((Mail) obj, mailDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		MailDao mailDao=Globals.getDaoService().getMailDao();
		MailEntity mailEntity=(MailEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<MailEntity>(mailEntity, mailEntity.getCharId(), mailDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}
