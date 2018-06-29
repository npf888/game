package com.gameserver.common.thread;

import com.gameserver.common.db.operation.BindUUIDIoOperation;


/**
 * 统一定制的对其他离线玩家数据进行操作的IoOperation
 * @author Thinker
 * @deprecated
 */
public class CommonOfflineRoleIoOperation implements BindUUIDIoOperation
{

	private IOtherRoleOperation operationImpl;
	
	public CommonOfflineRoleIoOperation(IOtherRoleOperation operationImpl)
	{
		this.operationImpl = operationImpl;
	}

	@Override
	public int doStart() 
	{
		return STAGE_START_DONE;
	}
	
	@Override
	public int doIo()
	{
		//执行离线操作
		operationImpl.offlineAction();
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop()
	{
		return STAGE_STOP_DONE;
	}

	@Override
	public long getBindUUID() 
	{
		return operationImpl.getRoleUUID();
	}
	
}
