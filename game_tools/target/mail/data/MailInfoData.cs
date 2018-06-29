using System.Collections;

public class MailInfoData
{
	/** 邮件id */
	public long mailId;
	/** 发件角色id */
	public long sendId;
	/** 发件角色名称 */
	public string sendName;
	/** 邮件还有多长时间过期 */
	public long mailCdTime;
	/** 是否有奖品未领取 */
	public int hasAttachment;
	/** 邮件状态 */
	public int mailStatus;
	/** 邮件标题 */
	public string mailTitle;
	/** 邮件发送时间 */
	public long mailCreatTime;
	/** 是否是好友发送的    0:是，1:否 */
	public int isFriendSend;
	/** vip等级 */
	public int vipLevel;
	/** 头像 */
	public string headName;
}