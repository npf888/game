package com.gameserver.texas.interfaces;

import com.gameserver.human.Human;


/**
 * 德州接口
 * @author wayne
 *
 */
public interface ITexasListener {
	public void onTexasPlay(Human human,int roomId);
	public void onTexasPlayFinished(Human human,int roomId,int handCardType,int win);
}
