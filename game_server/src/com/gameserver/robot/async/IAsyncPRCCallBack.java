package com.gameserver.robot.async;

public interface IAsyncPRCCallBack <T>{
	void onFinished(T res);
}
