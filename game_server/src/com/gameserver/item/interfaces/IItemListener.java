package com.gameserver.item.interfaces;

import com.gameserver.human.Human;

public interface IItemListener {
	public void onSendInteractive(Human human,int itemId);
}
