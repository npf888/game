package com.game.webserver.callback;

import com.game.webserver.response.IResponse;

public interface ICallBack {
	public void onSuccess(IResponse paramIResponse);
	public void onFail(IResponse paramIResponse);
}
