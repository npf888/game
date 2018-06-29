using System.Collections;

public class BazooTaskInfo
{
	/** 任务ID */
	public int taskId;
	/** 刷新类型  按天:1、按周:2、按月 等等 */
	public int refreshtype;
	/** 吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈 */
	public int modeType;
	/** 大的分类：0:任务，成就：1：胜利者，2：实践家，3：资本家 */
	public int bigType;
	/** 玩的方式：1：只要玩了就算，2：必须赢了才算，3:连胜  */
	public int wayOfPlay;
	/** 应该满足的 条件 */
	public int condition;
	/** 应当给予的奖励 */
	public int rewardNum;
	/** 用户完成次数 */
	public int finishTimes;
	/** 是否完成 0:未完成，1:已完成 */
	public int isFinish;
	/** 名称ID */
	public int nameId;
	/** 描述ID */
	public int descrip;
	/** 图标 */
	public string icon;
}