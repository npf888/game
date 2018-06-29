package com.gameserver.common.db.operation;

import com.core.async.IIoOperation;

/**
 * 与UUID绑定的数据异步操作接口
 * 
 * 
 * 
 */
public interface BindUUIDIoOperation extends IIoOperation {
	
	/**
	 * 取得该操作绑定的uuid
	 * 
	 * @return
	 */
	public long getBindUUID();
}
