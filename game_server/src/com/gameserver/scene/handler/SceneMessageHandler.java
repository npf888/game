package com.gameserver.scene.handler;

import com.common.constants.Loggers;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.scene.PlayerLeaveSceneCallback;
import com.gameserver.scene.Scene;
import com.gameserver.scene.SceneService;

/**
 * 场景消息处理，主要处理玩家进入、退出场景，玩家移动等消息
 * @author Thinker
 * 
 */
public class SceneMessageHandler
{
	private SceneService sceneService;
	private OnlinePlayerService onlinePlayerService;

	protected SceneMessageHandler(SceneService sceneManager,OnlinePlayerService onlinePlayerManager)
	{
		this.sceneService = sceneManager;
		this.onlinePlayerService = onlinePlayerManager;
	}

	/**
	 * <pre>
	 * 玩家进入场景，应只在场景所在的线程中调用，先将玩家从当前场景中移
	 * 除
	 * </pre>
	 * 
	 * @param playerId
	 * @param sceneId
	 */
	public boolean handleEnterScene(int playerId, int sceneId) 
	{
		Scene scene = sceneService.getScene(sceneId);
		Player player = onlinePlayerService.getPlayerByTempId(playerId);
		if(player == null) return false;
		return scene.onPlayerEnter(player,sceneId);
	}

	/**
	 * 玩家离开场景，应只在场景所在的线程中调用，先将玩家从当前场景中移
	 * 除，然后再发送消息给主线程
	 * 
	 * @param playerId
	 * @param sceneId
	 */
	public void handleLeaveScene(int playerId,int sceneId,PlayerLeaveSceneCallback callback)
	{
		Scene scene = sceneService.getScene(sceneId);
		if(!scene.getPlayerManager().containPlayer(playerId)) return;
		Player player=onlinePlayerService.getPlayerByTempId(playerId);
		if(player!= null)
		{
			scene.onPlayerLeave(player);
			if(callback!=null) callback.afterLeaveScene(player);
		}else
		{
			Loggers.gameLogger.error(String.format("leave scene fail. cannot find player in onlinePlayerService. id=%d, sceneId=%d",playerId, sceneId));
		}
	}

	public void handleRemoveScenePlayer(Player player)
	{
		Scene scene = sceneService.getScene(player.getSceneId());
		if(!scene.getPlayerManager().containPlayer(player.getId())) return;
		//player.getHuman().RemoveSyncSceneHuman();
		scene.getPlayerManager().removeScenePlayer(player.getId());
	}
}
