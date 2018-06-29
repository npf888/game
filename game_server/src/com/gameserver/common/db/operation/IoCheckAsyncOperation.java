package com.gameserver.common.db.operation;

import static com.core.async.IIoOperation.STAGE_START_DONE;

import java.util.concurrent.ExecutorService;

import com.common.constants.Loggers;
import com.core.async.AsyncOperation;
import com.core.async.IIoOperation;
import com.core.server.IMessageProcessor;


/**
 * 检测IO异步线上的执行时间
 * @author Thinker
 */
public class IoCheckAsyncOperation extends AsyncOperation
{
	/** 最大IO执行时间，毫秒级 */
	public static long IO_ALLOW_MAX_TIME = 500; // 500毫秒

	public IoCheckAsyncOperation(IIoOperation operation, ExecutorService asyncExecutor, IMessageProcessor messageProcessor)
	{
		super(operation, asyncExecutor, messageProcessor);
	}

	public IoCheckAsyncOperation(IIoOperation operation, ExecutorService asyncExecutor, IMessageProcessor messageProcessor, long uuid) 
	{
		super(operation, asyncExecutor, messageProcessor, uuid);
	}

	/**
	 * 如果是DO-IO，则进入执行时间判断记录
	 */
	@Override
	public void execute()
	{
		// 只有这一种情况下才执行doIO
		if (getStage() == STAGE_START_DONE)
		{
			long startIOTime = System.currentTimeMillis();
			// 执行do-io.Operation逻辑
			super.execute();
			// IO执行时间
			long ioLastTime = System.currentTimeMillis() - startIOTime;
			// 如果io执行时间超出设定的最大值，则记录日志
			if (ioLastTime > IO_ALLOW_MAX_TIME)
			{
				String message = "AsyncOperation.IIoOperation=" + getOperation() + "[" + getOperation().getClass() + "],ioLastTime=" + ioLastTime + ",IO_ALLOW_MAX_TIME="
						+ IO_ALLOW_MAX_TIME + ",uuid=" + getUUID();
				// 存日志
				Loggers.errorLogger.error(message);
			}
		} else
		{
			// 执行Operation逻辑
			super.execute();
		}
	}
}
