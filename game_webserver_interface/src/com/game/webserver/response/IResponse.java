package com.game.webserver.response;

public interface IResponse {
	public abstract boolean isSuccess();

	public abstract int getErrorCode();

	public abstract long getUseTime();

	public abstract void setUseTime(long paramLong);

	public abstract void onSuccess(String[] paramArrayOfString);
}
