using System.Collections;

public class PlayerInfoData
{
	/** 玩家id */
	public long playerId;
	/** 名字 */
	public string name;
	/** 图片 */
	public string img;
	/** 玩家筹码 */
	public long gold;
	/** 钻石 */
	public long diamond;
	/** 玩家筹码 */
	public long charm;
	/** 等级 */
	public long level;
	/** 性别 */
	public int sex;
	/** vip等级 */
	public int viplevel;
	/** 国籍 */
	public string countries;
	/** 年龄 */
	public int age;
	/** 总转次数 */
	public long slotRotate;
	/** 总赢得分 */
	public long slotWin;
	/** 单次赢取最大 */
	public long slotSingleWin;
	/** 玩家总胜利次数 */
	public long slotWinNum;
	/** 排行榜积分 */
	public long integral;
	/** 是否已经申请添加好友 0:未申请,1:已申请  */
	public int isRequest;
	/** 新手大礼包:1已购买，0 未购买 */
	public int newGuyGift;
	/** 俱乐部id，空字符串表示未加入俱乐部 */
	public string clubId;
	/** 俱乐部图标 */
	public int clubIco;
	/** 被俱乐部邀请次数，0为未被邀请 */
	public int clubInvitedTimes;
	/** 无双吹牛 成就  完成个数/总个数 */
	public string achieveRate;
}