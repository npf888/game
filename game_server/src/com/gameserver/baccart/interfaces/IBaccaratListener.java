package com.gameserver.baccart.interfaces;

import com.gameserver.human.Human;

public interface IBaccaratListener {
	public void onBaccaratPlay(Human human,int roomId);
	public void onBaccaratPlayFinished(Human human,int roomId,long win);
}
