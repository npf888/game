package com.db.dao;

import java.util.List;
import com.core.orm.DBService;
import com.db.model.NoticeEntity;

public class NoticeDao  extends BaseDao<NoticeEntity>{

	private static final String GET_NOTICES = "queryNotices";

	
	public NoticeDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */
	
	public List<NoticeEntity> getAllNotice()
	{
		return this._dbServ.findByNamedQuery(GET_NOTICES);
	}
	
	
	@Override
	protected Class<NoticeEntity> getEntityClazz() 
	{
		return NoticeEntity.class;
	}
}
