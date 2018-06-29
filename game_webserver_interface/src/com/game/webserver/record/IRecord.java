package com.game.webserver.record;

public interface IRecord {
	public abstract void recordInfo(String paramString);

	public abstract void recordError(String paramString, Throwable paramThrowable);
}
