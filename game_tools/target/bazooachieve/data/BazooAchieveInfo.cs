using System.Collections;

public class BazooAchieveInfo
{
	/** 大的分类：1：胜利者，2：实践家，3：资本家 */
	public int bigtype;
	/** 吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈 */
	public int modeType;
	/** 小的分类：1：胜场数，2：连胜数，3：玩的局数，4：钱数，5：段位数 */
	public int wayOfPlay;
	/** 应该满足的 条件 */
	public int condition;
	/** 应当给予的奖励 */
	public int rewardNum;
	/** 用户完成次数 或者 金币 */
	public int finishVlaues;
	/** 是否完成 0:未完成，1:已完成 */
	public int isFinish;
	/** 名称ID */
	public int nameId;
	/** 描述ID */
	public int descrip;
	/** 图标 */
	public string icon;
}