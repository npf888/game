package com.core.async;

import com.core.msg.IGlobalLogicQueueMessage;
import com.core.msg.MessageType;
import com.core.msg.sys.ScheduledMessage;

/**
 * 调度执行的异步消息通知,主要用于当异步消息处理完毕后，通知由玩家所在的场景线程进行收尾处理
 * 
 * 
 */
public class ScheduleGlobalLogicAsyncFinishMessage extends ScheduledMessage
		implements IGlobalLogicQueueMessage {

	private final AsyncOperation operation;

	public ScheduleGlobalLogicAsyncFinishMessage(long createTime,
			AsyncOperation operation) {
		super(createTime);
		this.operation = operation;
	}

	public AsyncOperation getOperation() {
		return operation;
	}

	@Override
	public short getType() {
		return MessageType.SCHD_PLAYER_ASYNC_FINISH;
	}

	@Override
	public String getTypeName() {
		String operationName = this.operation != null ? this.operation
				.toString() : "null";
		return "SCHD_PLAYER_ASYNC_FINISH [" + operationName + "]";
	}

	@Override
	public void execute() {
		operation.execute();
	}


}
