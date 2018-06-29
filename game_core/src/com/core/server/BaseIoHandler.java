package com.core.server;

import org.apache.mina.common.IoHandlerAdapter;
/**
 * 
 * @author 郭君伟
 *
 */
public class BaseIoHandler extends IoHandlerAdapter {
	
	/**GameMessageProcessor **/
	protected IMessageProcessor msgProcessor;

	/**
	 * 设置MessageProcessor
	 * 
	 * @param msgProcessor
	 */
	public void setMessageProcessor(IMessageProcessor msgProcessor) {
		this.msgProcessor = msgProcessor;
	}
}
