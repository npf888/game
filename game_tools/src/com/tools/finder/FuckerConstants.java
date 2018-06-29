package com.tools.finder;

/**
 * 作弊者常量
 * 
 * @author Thinker
 * 
 */
public class FuckerConstants 
{
	/** 检查间隔是五分钟 */
	public static final long RECORED_INTERVAL = 1000 * 60 * 5;
	/** 一天的毫秒数 */
	public static final long DAY_OF_MS = 60 * 60 * 24 * 1000;

	/** 判断作弊者的标准, 达到3次以上为作弊者 */
	public static final int JUDGE_COUNT = 3;
	public static final int LIMIT_COUNT = 1000;
	/** 用来判断的字符窜 */
	public static final String FUCK_POSTFIX = "当前玩家军令数量=0";

	// 日志库的配置
	public static final String LOG_URL = "jdbc:mysql://127.0.0.1:3306/tr_log";
	public static final String LOG_USER = "root";
	public static final String LOG_PASSWORD = "";
	// 游戏库的配置
	public static final String GAME_URL = "jdbc:mysql://127.0.0.1:3306/tr";
	public static final String GAME_USER = "root";
	public static final String GAME_PASSWORD = "";

}
