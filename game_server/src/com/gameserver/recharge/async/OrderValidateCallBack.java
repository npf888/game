package com.gameserver.recharge.async;

import com.gameserver.player.Player;
import com.gameserver.recharge.async.AsyncHttpOperation.IAsyncHttpCallBack;

public class OrderValidateCallBack implements IAsyncHttpCallBack<OrderValidationRes> {

	@Override
	public void onFinished(Player player, int errorCode, OrderValidationRes res) {
		
		
	}

}
