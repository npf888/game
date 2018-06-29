package com.gameserver.notice.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.db.model.NoticeEntity;
import com.gameserver.common.Globals;
import com.gameserver.notice.Notice;
import com.gameserver.notice.data.NoticeData;
import com.gameserver.notice.redisMsg.NoticeRedisMessage;

/**
 *
 * @author wayne
 *
 */
public class NoticeMessageRedisHandler {
	
	private Logger logger = Loggers.noticeLogger;

	public void handleNoticeRedisMessage(NoticeRedisMessage noticeRedisMessage) {
		// TODO Auto-generated method stub
		
		NoticeEntity noticeEntity =NoticeData.convertFromNoticeData(noticeRedisMessage.getNoticeEntity());
		Notice no = new Notice();
		no.fromEntity(noticeEntity);
		
		logger.info("接收活动 活动id["+no.getDbId()+"]");
		
		Globals.getNoticeService().updateNotice(no);
		
	}

}
