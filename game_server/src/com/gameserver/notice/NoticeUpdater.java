package com.gameserver.notice;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.NoticeDao;
import com.db.model.NoticeEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 公告更新器
 * @author wayne
 *
 */
public class NoticeUpdater  implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		NoticeDao noticeDao = Globals.getDaoService().getNoticeDao();
		IIoOperation _oper = new SaveObjectOperation<NoticeEntity,Notice>((Notice) obj, noticeDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
//		NoticeDao noticeDao=Globals.getDaoService().getNoticeDao();
//		NoticeEntity noticeEntity=(NoticeEntity)obj.toEntity();
//		IIoOperation _oper = new DeleteEntityOperation<NoticeEntity>(noticeEntity, 0L, noticeDao);
//		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
		throw new UnsupportedOperationException();
	}
}
