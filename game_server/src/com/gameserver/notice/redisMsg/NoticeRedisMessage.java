package com.gameserver.notice.redisMsg;


import com.gameserver.notice.data.NoticeData;
import com.gameserver.notice.handler.NoticeHandlerFactory;
import com.gameserver.redis.IRedisMessage;


public class NoticeRedisMessage  implements IRedisMessage{

	private NoticeData noticeEntity;
	public NoticeData getNoticeEntity(){
		return noticeEntity;
	}
	
	public void setNoticeEntity(NoticeData noticeEntity){
		this.noticeEntity = noticeEntity;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		NoticeHandlerFactory.getRedisHandler().handleNoticeRedisMessage(this);
	}
}

