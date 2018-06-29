package com.core.server;

import com.common.constants.CommonErrorLogInfo;
import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.core.util.ErrorsUtil;


/**
 * 可自执行的消息处理器
 * @author Thinker
 * 
 */
public class ExecutableMessageHandler implements IMessageHandler<IMessage> {
	@Override
	public void execute(IMessage message){
		try {
			message.execute();
		} catch (Exception e){
			String exceptionMessage = ErrorsUtil.errorAllThrowableStackMessage(CommonErrorLogInfo.MSG_PRO_ERR_EXP, e);
			Loggers.msgLogger.error(
					CommonErrorLogInfo.MSG_PRO_ERR_EXP
					+ "|TypeName:" + message.getTypeName()
					+ "|Type:" + message.getType()
					+ "|Message:" + message.toString()
					+ "|Exception:" + exceptionMessage);
		}
	}

	@Override
	public short[] getTypes() {
		return null;
	}
}
