using System.Collections;

public class AttackRankData
{
	/** 用户的名称 */
	public string name;
	/** boss的ID */
	public long bossId;
	/** 用户的ID */
	public long userId;
	/** 攻击总血量 */
	public long attackTotalBlood;
	/** vip加成（百分号 前边的数） */
	public int vipAdditionRate;
	/** 当前用户 打掉的血 占据 总血 的 比例   ，（百分号 前边的数） */
	public int attackRate;
	/** 排序字段 */
	public int sortIndex;
}