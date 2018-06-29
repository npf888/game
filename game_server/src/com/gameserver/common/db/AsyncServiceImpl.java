package com.gameserver.common.db;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.constants.AsyncConstants;
import com.common.constants.CommonErrorLogInfo;
import com.core.async.AsyncOperation;
import com.core.async.AsyncService;
import com.core.async.IIoOperation;
import com.core.async.ILocalRelativeOperation;
import com.core.async.SyncOperation;
import com.core.server.IMessageProcessor;
import com.core.util.ErrorsUtil;
import com.core.util.ExecutorUtil;
import com.gameserver.common.db.operation.BindUUIDIoOperation;
import com.gameserver.common.db.operation.IoCheckAsyncOperation;
import com.gameserver.startup.GameServerRuntime;


/**
 * 异步操作管理器
 * @author Thinker
 *
 * 
 */
public class AsyncServiceImpl implements AsyncService 
{
	private static final Logger logger = LoggerFactory.getLogger("async");

	/** 位于主线程的消息处理器,用于当异步操作完成后,在主线程中执行收尾操作 */
	private final IMessageProcessor messageProcessor;
	/** 与玩家角色绑定的线程池,即根据玩家角色的id号{@link PlayerCharacter#getTemplateId()},固定的操作总在同一个线程池中运行 */
	private final ExecutorService[] charBindExecutors;
	/** 不与玩家角色绑定的线程池 */
	private final ExecutorService charUnBindExecutor;
	/** 登录线程池 */
	private final ExecutorService loginExecutor;
	
	/** 同步线程*/
	private final ExecutorService syncExecutor;


	public AsyncServiceImpl(
			final int charBindExecutorSize, 
			final int charUnBindExecutorSize, 
			final int loginExecutorSize, 
			IMessageProcessor messageProcessor) {
		charBindExecutors = new ExecutorService[charBindExecutorSize];
		for (int i = 0; i < charBindExecutorSize; i++) {
			charBindExecutors[i] = Executors.newSingleThreadExecutor();
		}
		this.messageProcessor = messageProcessor;
		this.charUnBindExecutor = Executors.newFixedThreadPool(charUnBindExecutorSize);
		this.loginExecutor = Executors.newFixedThreadPool(loginExecutorSize);
		this.syncExecutor = Executors.newSingleThreadExecutor();

		
	}
	
	public void stop() {
		try {
			for (ExecutorService _executor : this.charBindExecutors) {
				ExecutorUtil.shutdownAndAwaitTermination(_executor);
			}
			ExecutorUtil.shutdownAndAwaitTermination(this.charUnBindExecutor);
		} catch (Exception e) {
			String msg = ErrorsUtil.error(CommonErrorLogInfo.DB_OPERATE_FAIL, "#GS.AsyncManagerImpl.stop", null);
			
		

			if (logger.isErrorEnabled()) {
				logger.error(msg, e);
			}
		}
	}


	@Override
	public SyncOperation createSyncOperationAndExecuteAtOnce(IIoOperation operation) {
		SyncOperation _operation = new SyncOperation(operation);
		_operation.execute();
		return _operation;
	}

	@Override
	public AsyncOperation createOperationAndExecuteAtOnce(IIoOperation operation) {
		if (GameServerRuntime.isShutdowning()) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.INVALID_STATE,
						"#GS.AsyncManagerImpl.createOperationAndExecuteAtOnce", "Shutdowning the server"),
						new Exception());
			}
		}
		AsyncOperation _operation = this.createOperation(operation);
		_operation.execute();
		return _operation;
	}
	
	@Override
	public AsyncOperation createOperationAndExecuteAtOnce(IIoOperation operation, long uuid) {
		if (GameServerRuntime.isShutdowning()) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.INVALID_STATE,
						"#GS.AsyncManagerImpl.createOperationAndExecuteAtOnce",
						"Shutdowning the server"), new Exception());
			}
		}
		AsyncOperation _operation = this.createOperation(operation,uuid);
		_operation.execute();
		return _operation;
	}
	
	private AsyncOperation createOperation(IIoOperation operation) {
		if (operation instanceof BindUUIDIoOperation) {
			long _charId = ((BindUUIDIoOperation) operation).getBindUUID();
			int _executorIndex = (int) (_charId % this.charBindExecutors.length);
			_executorIndex = _executorIndex < 0 ? 0 : _executorIndex;
			ExecutorService _asyncExecutor = this.charBindExecutors[_executorIndex];
			if (logger.isDebugEnabled()) {
				logger.debug("[#GS.AsyncManagerImpl.createOperation] [char:" + _charId + " bind to executor :"
						+ _executorIndex + "]");
			}
			return new IoCheckAsyncOperation(operation, _asyncExecutor, messageProcessor);
		} else if (operation instanceof ILocalRelativeOperation) {
			// 如果是登陆操作, 
			// 则使用专用的登录线程执行
			return new IoCheckAsyncOperation(operation, loginExecutor, messageProcessor);
		} else {
			return new IoCheckAsyncOperation(operation, charUnBindExecutor, messageProcessor);
		}
	}
	
	private AsyncOperation createOperation(IIoOperation operation,long uuid) {
		if (operation instanceof BindUUIDIoOperation) {
			long _charId = ((BindUUIDIoOperation) operation).getBindUUID();
			int _executorIndex = (int) (_charId % this.charBindExecutors.length);
			_executorIndex = _executorIndex < 0 ? 0 : _executorIndex;
			ExecutorService _asyncExecutor = this.charBindExecutors[_executorIndex];
			if (logger.isDebugEnabled()) {
				logger.debug("[#GS.AsyncManagerImpl.createOperation] [char:" + _charId + " bind to executor :"
						+ _executorIndex + "]");
			}
			return new AsyncOperation(operation, _asyncExecutor, messageProcessor,uuid);
		} else {
			
				return new AsyncOperation(operation, charUnBindExecutor, messageProcessor,uuid);
		
		}
	}
	@Override
	public AsyncOperation createGlobalAsyncOperationAndExecuteAtOnce(
			IIoOperation operation) {
			if (GameServerRuntime.isShutdowning()) {
				if (logger.isErrorEnabled()) {
					logger.error(ErrorsUtil.error(CommonErrorLogInfo.INVALID_STATE,
							"#GS.AsyncManagerImpl.createOperationAndExecuteAtOnce",
							"Shutdowning the server"), new Exception());
				}
			}
			AsyncOperation _operation = this.createOperation(operation, AsyncConstants.GLOBAL_LOGIC_ASYNC_OPERATION_ID);
			_operation.execute();
			return _operation;
	}
	
	@Override
	public SyncOperation createGlobalSyncOperationAndExecuteAtOnce(
			IIoOperation operation) {
			
			final SyncOperation _operation = new SyncOperation(operation);
			syncExecutor.execute(new Runnable(){

				@Override
				public void run() {
			
					_operation.execute();
				}
				
			});
			
			return _operation;
	}
}
