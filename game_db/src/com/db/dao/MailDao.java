package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.MailEntity;

/**
 * 邮件dao
 * @author wayne
 *
 */
public class MailDao extends BaseDao<MailEntity>
{
	private static final String GET_MAIL_BY_CHARID = "queryMailByCharid";
	private static final String[] GET_MAIL_BY_CHARID_PARAMS = new String[] { "charId" };
	
	private static final String GET_MAIL_BY_SENDID = "queryMailBySendid";
	private static final String[] GET_MAIL_BY_SENDID_PARAMS = new String[] { "charId" };
	

	
	public MailDao(DBService dbServ)
	{
		super(dbServ);
	}
	
	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */

	public List<MailEntity> getMailEntityByCharId(long charId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_MAIL_BY_CHARID, GET_MAIL_BY_CHARID_PARAMS,
				new Object[] { charId});
	}
	
	/**
	 * 根据发件人ID从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */

	public List<MailEntity> getMailEntityBySendId(long charId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_MAIL_BY_SENDID, GET_MAIL_BY_SENDID_PARAMS,
				new Object[] { charId});
	}
	

	public List<MailEntity> selectPlayerWithRewardMail(int mailStatus)
	{
		return this._dbServ.findByNamedQueryAndNamedParam("selectPlayerWithRewardMail", new String[]{"mailStatus"},
				new Object[] {mailStatus});
	}
	
	@Override
	protected Class<MailEntity> getEntityClazz() 
	{
		return MailEntity.class;
	}
}
