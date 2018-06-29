package com.gameserver.refund;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanRefundDao;
import com.db.model.HumanRefundEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * human refund
 * @author wayne
 *
 */
public class HumanRefundUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanRefundDao humanRefundDao = Globals.getDaoService().getHumanRefundDao();
		IIoOperation _oper = new SaveObjectOperation<HumanRefundEntity,HumanRefund>((HumanRefund) obj, humanRefundDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		HumanRefundDao humanRefundDao=Globals.getDaoService().getHumanRefundDao();
		HumanRefundEntity humanRefundEntity=(HumanRefundEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<HumanRefundEntity>(humanRefundEntity, humanRefundEntity.getCharId(), humanRefundDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}
