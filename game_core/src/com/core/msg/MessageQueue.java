package com.core.msg;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.common.constants.Loggers;

/**
 * 消息队列，使用{@link BlockingQueue}实现
 * 
 * @see BlockingQueue
 */
public class MessageQueue<T>{
	/** 玩家的消息队列 */
	private BlockingQueue<T> msgQueue;

	public MessageQueue() {
		msgQueue = new LinkedBlockingQueue<T>();
	}

	/**
	 * @param msg
	 */
	public void put(T msg) {
		if (msg == null) {
			return;
		}
		try {
			msgQueue.put(msg);
		} catch (InterruptedException e) {
			Loggers.msgLogger.error("",e);
		}
	}

	/**
	 * 返回消息队列中的一个消息
	 * 
	 * @return
	 */
	public T get() {
		if (isEmpty()) {
			return null;
		}
		try {
			return msgQueue.take();
		} catch (InterruptedException e) {
			Loggers.msgLogger.error("",e);
			return null;
		}
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		return msgQueue.isEmpty();
	}

	@Override
	public String toString() {
		return "MessageQueue [msgQueue=" + msgQueue + "]";
	}
}
