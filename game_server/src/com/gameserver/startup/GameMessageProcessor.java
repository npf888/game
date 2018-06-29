package com.gameserver.startup;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IGlobalLogicQueueMessage;
import com.core.msg.IMessage;
import com.core.msg.PlayerQueueMessage;
import com.core.msg.SysInternalMessage;
import com.core.msg.sys.ScheduledMessage;
import com.core.server.ExecutableMessageHandler;
import com.core.server.IMessageProcessor;
import com.core.server.QueueMessageProcessor;
import com.core.util.LogUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.common.msg.CGPing;
import com.gameserver.player.Player;


/**
 * 游戏服务器消息处理器
 * @author Thinker
 */
public class GameMessageProcessor implements IMessageProcessor{
	
	protected static final Logger log = Loggers.msgLogger;

	/** 主消息处理器，处理服务器内部消息、玩家不属于任何场景时发送的消息 */
	private QueueMessageProcessor mainMessageProcessor;

	public GameMessageProcessor(){
		mainMessageProcessor = new QueueMessageProcessor(new ExecutableMessageHandler());
	}

	@Override
	public boolean isFull() {
		return mainMessageProcessor.isFull();
	}

	/**
	 * <pre>
	 * 1、服务器内部消息、玩家不属于任何场景时发送的消息，单独一个消息队列进行处理
	 * 2、玩家在场景中发送过来的消息，添加到玩家的消息队列中，在场景的线程中进行处理
	 * </pre>
	 */
	@Override
	public void put(IMessage msg){
		
		if (!GameServerRuntime.isOpen() && !(msg instanceof SysInternalMessage)
				&& !(msg instanceof ScheduledMessage)){
			log.info("【Receive but will not process because server not open】"	+ msg);
			return;
		}
		if (msg instanceof CGMessage) {
			
			GameClientSession session = ((CGMessage) msg).getSession();
			if (session == null){
				log.error("【Receive msg but not session】"	+ msg);
				return;
			}
			
			Player player = session.getPlayer();
			if (player == null){
				log.error("player not found. msg:" + msg);
				return;
			}
			if (log.isDebugEnabled()) {
				if (player.getHuman() != null) {
					if(!(msg instanceof CGPing)){//过滤掉CGPing
						log.debug("【Receive】" + msg + " from player : "
								+ player.getHuman().getName() + ",roleUUID : "
								+ player.getHuman().getUUID() + ", sceneRoleId : "
								+ player.getHuman().getId());
					}
				}else{
					log.debug("【Receive】" + msg);
				}
			}
			if (!player.getStateManager().canProcess(msg)) {
				
				Loggers.gameLogger.warn(LogUtils.buildLogInfoStr(player
						.getRoleUUID(), "msg type " + msg.getType()+"("+msg.getTypeName()+")"
						+ " can't be processed in curState:"
						+ player.getStateManager().getState()));
			
				return;
			}
			
		
			if (player.isInScene()) {
				player.putMessage(msg);
			}else{
				Globals.getGlobalLogicRunner().put(msg);
			}
		}else if(msg instanceof IGlobalLogicQueueMessage){
			if (log.isDebugEnabled()){
				log.debug("【Receive】" + msg + " uuid : " + -2);
			}
		
			Globals.getGlobalLogicRunner().put(msg);
		}else if (msg instanceof PlayerQueueMessage){
			PlayerQueueMessage playerQueueMsg = (PlayerQueueMessage) msg;
			long roleUUID = playerQueueMsg.getRoleUUID();
			Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
			if (player == null) {
				log.error("player  with roleUUID:" + roleUUID + " not found");
				return;
			}
			if (log.isDebugEnabled()) {
				log.debug("【Receive】" + msg + " roleUUID : " + roleUUID);
			}
			player.putMessage(msg);
		}else{
			if (log.isDebugEnabled() && !(msg instanceof ScheduledMessage)) {
				log.debug("【Receive】" + msg);
			}
			mainMessageProcessor.put(msg);
			return;
		}
	}

	@Override
	public void start(){
		mainMessageProcessor.start();
	}

	@Override
	public void stop(){
		mainMessageProcessor.stop();
	}

	/**
	 * 获得主消息处理线程Id
	 * 
	 * @return
	 */
	public long getThreadId() {
		return mainMessageProcessor.getThreadId();
	}

	/**
	 * @return
	 */
	public boolean isStop() {
		return mainMessageProcessor.isStop();
	}
}
