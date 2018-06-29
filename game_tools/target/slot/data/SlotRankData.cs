using System.Collections;

public class SlotRankData
{
	/** 老虎机ID */
	public int tournamentType;
	/** 名次 -1 表示没有上榜 */
	public int rank;
	/** 头像 */
	public string img;
	/** 名称 */
	public string name;
	/** 累计中奖金额 */
	public long bonus;
	/** 角色ID */
	public long passportId;
	/** 用户等级 */
	public int vipLevel;
}