package com.gameserver.recharge.interfaces;

import com.gameserver.human.Human;

public interface IRechargeListener {
	public void onRecharge(Human human,int productId);
}
