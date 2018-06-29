using System.Collections;

public class BossInfoData
{
	/** 多语言ID */
	public int nameId;
	/** 描述 */
	public int descrip;
	/** 是否被杀 */
	public int beKilled;
	/** boss类型 */
	public int bossType;
	/** 当前血量 */
	public long changingBlood;
	/** 基础血量 */
	public int blood;
	/** 增长的总血量 */
	public long increaseBlood;
	/** 奖励类型1 */
	public int rewardNum1;
	/** 奖励类型2 */
	public int rewardNum2;
	/** 开始时间 */
	public long startTime;
	/** 持续时间 */
	public long continueTime;
	/** 结束时间 */
	public long endTime;
}