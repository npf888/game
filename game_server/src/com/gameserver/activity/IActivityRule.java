package com.gameserver.activity;

import com.gameserver.human.Human;

public interface IActivityRule {
	
	public boolean ifRewardStatusChanged(Human human,HumanActivity humanActivity);

}
