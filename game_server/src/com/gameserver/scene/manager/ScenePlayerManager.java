package com.gameserver.scene.manager;

import java.util.ArrayList;
import java.util.List;

import com.core.heartbeat.HeartBeatAble;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.scene.Scene;

/**
 * 管理场景内玩家列表
 * 
 */
public class ScenePlayerManager implements HeartBeatAble {

	/** 当前场景对象 **/
	private Scene scene;
	/** 所有在线玩家的管理器,根据此对象取得玩家的实例 **/
	private OnlinePlayerService onlinePlayerManager;
	/** 玩家ID的列表.此处不引用玩家的实例,获取玩家实例需调用onlinePlayerManager **/
	private List<Integer> playerIds;

	/** 场景玩家ID.不包括进入竞技场和过关斩将的玩家 **/
	private List<Integer> scenePlayerIds;
	/** 镜像人数,仅用于非实时性的一些需求,且可能在多线程中调用 */
	private volatile int mirrorPlayerNum;

	public ScenePlayerManager(Scene scene,
			OnlinePlayerService onlinePlayerManager, int maxPlayerCount) {
		this.scene = scene;
		this.onlinePlayerManager = onlinePlayerManager;
		playerIds = new ArrayList<Integer>(maxPlayerCount);
		scenePlayerIds = new ArrayList<Integer>(maxPlayerCount);
	}

	public void addPlayer(Integer playerId) {
		if (containPlayer(playerId))
			playerIds.remove(playerId);
		playerIds.add(playerId);
	}

	/**
	 * 当前场景中是否包含某个玩家
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean containPlayer(Integer playerId) {
		return this.playerIds.contains(playerId);
	}

	public void removePlayer(Integer playerId) {
		this.playerIds.remove(playerId);
	}

	public void addScenePlayer(Integer playerId) {
		if (containScenePlayer(playerId))
			scenePlayerIds.remove(playerId);
		scenePlayerIds.add(playerId);
	}

	/**
	 * 当前场景中是否包含某个玩家
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean containScenePlayer(Integer playerId) {
		return this.scenePlayerIds.contains(playerId);
	}

	public void removeScenePlayer(Integer playerId) {
		this.scenePlayerIds.remove(playerId);
	}

	/**
	 * 处理场景内玩家的输入输出消息
	 */
	public void tick() {
		List<Integer> removePlayerId = new ArrayList<Integer>();
		Integer playerId = null;
		for (int i = 0; i < playerIds.size(); i++) {
			playerId = playerIds.get(i);
			Player player = onlinePlayerManager.getPlayerByTempId(playerId);
			// 如果玩家已不在在线列表中,或已经不属于当前场景，则从ID列表中删除
			if (player == null || player.getSceneId() != scene.getId()) {
				removePlayerId.add(playerId);
				continue;
			}
			player.processMessage();
		}
		playerIds.removeAll(removePlayerId);
		scenePlayerIds.removeAll(removePlayerId);
	}

	@Override
	public void heartBeat() {
		List<Integer> removePlayerId = new ArrayList<Integer>();
		Integer playerId = null;

		for (int i = 0; i < playerIds.size(); i++) {
			playerId = playerIds.get(i);
			Player player = onlinePlayerManager.getPlayerByTempId(playerId);
			// 如果玩家已不在在线列表中,或已经不属于当前场景，则从ID列表中删除
			if (player == null || player.getSceneId() != scene.getId()) {
				removePlayerId.add(playerId);
				continue;
			}
			player.heartBeat();
		}

		playerIds.removeAll(removePlayerId);
		scenePlayerIds.removeAll(removePlayerId);

		mirrorPlayerNum = playerIds.size();
	}

	public List<Integer> getPlayerIds() {
		return this.playerIds;
	}

	public List<Integer> getScenePlayerIds() {
		return this.scenePlayerIds;
	}

	/**
	 * 当前场景一个非实时的人数
	 * 
	 * @see #mirrorPlayerNum
	 * 
	 * @return
	 */
	public int getMirrorPlayerNum() {
		return mirrorPlayerNum;
	}
}
