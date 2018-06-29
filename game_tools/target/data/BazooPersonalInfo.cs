using System.Collections;

public class BazooPersonalInfo
{
	/** 性别 */
	public int modeType;
	/** 所有模式:局数 */
	public int numberOfGame;
	/** 吹牛模式:单局最高 */
	public long singleTopGold;
	/** 吹牛模式:胜率 */
	public int rateOfWinning;
	/** 吹牛模式:连胜 */
	public int aWinningStreak;
	/** 牛牛模式:通杀 */
	public int passToKill;
	/** 牛牛模式:最大牌型 */
	public string bigPatterns;
	/** 梭哈模式:豹子数 */
	public int pantherNumber;
	/** 三杀 */
	public int threeKill;
	/** 四杀 */
	public int fourKill;
	/** 五杀 */
	public int fiveKill;
	/** 每天的盈利 */
	public long dayProfit;
	/** 每周的盈利 */
	public long weekProfit;
	/** 每月的盈利 */
	public long monthProfit;
}