using System.Collections;

public class ClubMemberListUnit
{
	/** 成员id */
	public long playerId;
	/** 成员名字 */
	public string name;
	/** ico */
	public string ico;
	/** 成员等级 */
	public int level;
	/** 国籍 */
	public string country;
	/** 职务 */
	public int zhiwu;
	/** 个人贡献 */
	public int gongxian;
	/** 个人活跃度 */
	public int huoyue;
	/** 是否在线 */
	public int online;
	/** 是否游戏中 */
	public int inGame;
	/** 上次下线时间 */
	public long logoutTime;
	/** 不可弹劾:0 可弹劾:1 弹劾进行中:2 弹劾成功:3  */
	public int tanheState;
	/** 同意人数 */
	public int agree;
	/** 拒绝人数 */
	public int refuse;
	/** 个人状态 1 同意 2 拒绝 0 未表态 */
	public int selfState;
	/** 用户vip的等级 */
	public int vipLevel;
	/** 用户性别 1：男，2：女 */
	public int girlFlag;
}