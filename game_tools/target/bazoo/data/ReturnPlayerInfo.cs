using System.Collections;

public class ReturnPlayerInfo
{
	/** 用户ID */
	public long passportId;
	/** 用户的名称 */
	public string name;
	/** 性别 0:女       1:男 */
	public int girlFlag;
	/** 用户当前的状态 0：观战   未参与(因为刚进入房间 别的用户 正在玩，所以此时只能观战)    1：参与      2:纯粹的观战模式 （进去就是看看 什么其他的也不干） */
	public int userStatus;
	/** 当前的金币 */
	public long curGold;
	/** 头像信息 */
	public string headImg;
	/** 色钟 不同状态 的色子 有不同的色钟 */
	public string diceContainer;
	/** 位置 */
	public int seat;
	/** 几连胜 */
	public int winTimes;
	/** vip等级 */
	public int vipLevel;
	/** 用户最后叫号 色子的个数 */
	public int num;
	/** 用户最后叫号 色子的值 */
	public int value;
	/** 色子的int名 */
	public int diceType;
	/** 色子的值 */
	public ArrayList diceValues;
	/** 红色 色子的值 */
	public ArrayList redDiceValues;
	/** 色钟图片 */
	public string clockImg;
	/** 色钟itemId */
	public int clockItemId;
}