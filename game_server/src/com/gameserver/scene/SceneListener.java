package com.gameserver.scene;

import com.gameserver.common.listener.Listener;
import com.gameserver.human.Human;

/**
 * 场景监听器
 * @author Thinker
 */
public interface SceneListener extends Listener
{
	/**
	 * 在玩家进入场景后调用
	 * 
	 * @param scene 
	 * @param human 
	 * 
	 */
	public void afterHumanEnter(Scene scene, Human human);

	/**
	 * 在玩家离开场景时调用
	 * 
	 * @param scene
	 * @param human 
	 * 
	 */
	public void beforeHumanLeave(Scene scene, Human human);
	
	/**
	 * 以登出的方式离开场景
	 * 
	 * @param scene
	 * @param human 
	 * 
	 */
	public void leaveOnLogoutSaving(Scene scene,Human human);
}
