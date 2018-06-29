using System.Collections;

public class AchievementStateData
{
	/** 成就ID */
	public int id;
	/** 1 没有完成 2 已经完成但没有领取 3 已经领取 */
	public int state;
	/** 完成时间（状态2的情况下有用） */
	public long completeTime;
}