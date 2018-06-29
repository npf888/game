package com.gameserver.recharge;


import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanRechargeOrderDao;
import com.db.model.HumanRechargeOrderEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 订单
 * @author wayne
 *
 */
public class HumanRechargeOrderUpdater implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanRechargeOrderDao humanRechargeOrderDao=Globals.getDaoService().getRechargeOrderDao();
		IIoOperation _oper = new SaveObjectOperation<HumanRechargeOrderEntity,HumanRechargeOrder>((HumanRechargeOrder) obj, humanRechargeOrderDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
