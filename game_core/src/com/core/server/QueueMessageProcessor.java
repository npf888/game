package com.core.server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;

import com.common.constants.CommonErrorLogInfo;
import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.core.msg.SessionMessage;
import com.core.util.ErrorsUtil;

/**
 * 基于队列的消息处理器(源自天书game_server同名类),使用单线程依次用于处理队列中消息
 * 
 * @author Thinker
 * 
 * @see {@link NoQueueMessageProcessor}
 */
public class QueueMessageProcessor implements Runnable, IMessageProcessor {
	private static final Logger logger = Loggers.msgLogger;

	/** 消息队列 * */
	private final BlockingQueue<IMessage> queue;
	/** 消息处理线程停止时剩余的还未处理的消息 **/
	private volatile List<IMessage> leftQueue;
	/** 消息处理器 * */
	private final IMessageHandler<IMessage> handler;
	/** 消息处理线程 */
	private volatile Thread messageProcessorThread;
	/** 运行的线程id * */
	private long threadId;
	/** 是否停止 */
	private volatile boolean stop = false;
	/** 处理的消息总数 */
	private long statisticsMessageCount = 0;
	
	/** 等待消息处理线程退出 */
	private volatile CountDownLatch stopLatch;
	private final boolean processLeft;

	public QueueMessageProcessor(IMessageHandler<IMessage> messageHandler) {
		this(messageHandler, false);
	}

	public QueueMessageProcessor(IMessageHandler<IMessage> messageHandler,
			boolean processLeft) {
		queue = new LinkedBlockingQueue<IMessage>();
		handler = messageHandler;
		this.processLeft = processLeft;
	}

	

	@Override
	public void put(IMessage msg) {
		if (this.stop) {
			return;
		}
		try {
			queue.put(msg);
		} catch (InterruptedException e) {
			if (logger.isErrorEnabled()) {
				logger.error(CommonErrorLogInfo.THRAD_ERR_INTERRUPTED, e);
			}
		}
	}

	/**
	 * 主处理函数，从消息队列中阻塞取出消息，然后处理
	 */
	public void run() {
		threadId = Thread.currentThread().getId();
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		this.stopLatch = countDownLatch;
		try {
			while (!stop) {
				try {
					process(queue.take());
				} catch (InterruptedException e) {
					if (logger.isWarnEnabled()) {
						logger.warn("[#CORE.QueueMessageProcessor.run] [Stop process]");
					}
					break;
				} catch (Exception e) {
					logger.error(CommonErrorLogInfo.MSG_PRO_ERR_EXP, e);
				}
			}
		} finally {
			countDownLatch.countDown();
		}
	}

	/**
	 * 处理具体的消息，每个消息有自己的参数和来源,如果在处理消息的过程中发生异常,则马上将此消息的发送者断掉
	 * 
	 * @param msg
	 */
	public void process(IMessage msg) {
		if (msg == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("[#CORE.QueueMessageProcessor.process] ["
						+ CommonErrorLogInfo.MSG_PRO_ERR_NULL_MSG + "]");
			}
			return;
		}
		long begin = 0;
		if (logger.isInfoEnabled()) {
			begin = System.nanoTime();
		}
		this.statisticsMessageCount++;
		try {
			this.handler.execute(msg);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("Error",
						"#.QueueMessageProcessor.process", "param"), e);
			}
			try {
				// TODO 此处的逻辑应该由一个接口代为实现
				if (msg instanceof SessionMessage<?>) {
					// 如果在处理消息的过程中出现了错误,将其断掉
					final SessionMessage<?> imsg = (SessionMessage<?>) msg;
					if (imsg.getSession() != null) {
						if (logger.isErrorEnabled()) {
							logger.error(ErrorsUtil.error(
									CommonErrorLogInfo.MSG_PRO_ERR_EXP,
									"Disconnect sender", msg.getTypeName()
											+ "@" + imsg.getSession()), e);
						}
						imsg.getSession().close();
					}
				}
			} catch (Exception ex) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.MSG_PRO_ERR_DIS_SENDER_FAIL,
						"#CORE.QueueMessageProcessor.process", null), ex);
			}
		} finally {
			if (logger.isInfoEnabled()) {
				// 特例，统计时间跨度
				long time = (System.nanoTime() - begin) / (1000);

				// if (time > 1)
				// {
				int type = msg.getType();
				logger.info("Message:" + msg.getTypeName() + " Type:" + type
						+ " Time:" + time + "us" + " Total:"
						+ this.statisticsMessageCount);
				// }
			}
		}
	}

	

	public long getThreadId() {
		return threadId;
	}

	/**
	 * 取得未处理消息队列的长度
	 * 
	 * @return
	 */
	public int getQueueLength() {
		return queue.size();
	}

	public synchronized void stop() {
		logger.info("Message processor thread " + this.messageProcessorThread
				+ " stopping ...");
		this.stop = true;
		if (this.messageProcessorThread != null) {
			this.messageProcessorThread.interrupt();
		}
		logger.info("Message processor thread " + this.messageProcessorThread
				+ " finish");
		this.messageProcessorThread = null;
		// 需要处理完未处理的消息
		// 等待消息处理线程退出
		try {
			final CountDownLatch _stopLatch = this.stopLatch;
			if (_stopLatch != null) {
				_stopLatch.await();
			}
			// 将未处理的消息放入到leftQueue中,以备后续处理
			if (this.processLeft) {
				this.leftQueue = new LinkedList<IMessage>();
				while (true) {
					IMessage _msg = this.queue.poll();
					if (_msg != null) {
						this.leftQueue.add(_msg);
					} else {
						break;
					}
				}
			}
		} catch (InterruptedException e) {
		}
		this.queue.clear();
	}

	/**
	 * 开始消息处理
	 */
	public synchronized void start() {
		this.queue.clear();
		stop = false;
		if (this.messageProcessorThread != null) {
			throw new IllegalStateException("The thread has already run"
					+ this.messageProcessorThread);
		}
		this.messageProcessorThread = new Thread(this,
				"MessageProcessor Thread");
		this.messageProcessorThread.start();
		logger.info("Message processor thread started ["
				+ this.messageProcessorThread + "]");
	}

	

	/**
	 * 达到5000上限时认为满了
	 */
	@Override
	public boolean isFull() {
		return this.queue.size() > 5000;
	}


	public boolean isStop() {
		return stop;
	}
}
