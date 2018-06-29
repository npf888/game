package com.core.async;

public abstract class AsyncIoOpeOnly implements IIoOperation {
	@Override
	public int doStop() {
		return STAGE_STOP_DONE;
	}
	
	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}
}
