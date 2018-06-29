package com.gameserver.common.listener;

/**
 * 监听器类
 * @author Thinker
 */
public interface Listenable<T extends Listener>
{
	/**
	 * 注册监听器,该方法的实现有义务调用 {@link Listener#onRegisted(Listenable)}
	 * 
	 * @param listener 监听者
	 */
	public void registerListener(T listener);
	
	/**
	 * 删除监听者,该方法的实现有义务调用 {@link Listener#onDeleted(Listenable)}
	 * 
	 * @param listener 监听者
	 */
	public void deleteListener(T listener);
}