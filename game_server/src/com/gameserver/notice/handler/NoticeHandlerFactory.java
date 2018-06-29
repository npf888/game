package com.gameserver.notice.handler;



public class NoticeHandlerFactory {
	private static NoticeMessageRedisHandler reidsHandler = new NoticeMessageRedisHandler();
	
	public static NoticeMessageRedisHandler getRedisHandler() {
		// TODO Auto-generated method stub
		return reidsHandler;
	}
}
