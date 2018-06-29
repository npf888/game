using System.Collections;

public class HumanInfoData
{
	/** 人物角色UUID */
	public long roleId;
	/** 名字 */
	public string name;
	/** 性别 */
	public int sex;
	/** 级别 */
	public long level;
	/** VIP 等级 */
	public long vipLevel;
	/** 钻石 */
	public long diamond;
	/** 金币 */
	public long gold;
	/** 经验 */
	public long curExp;
	/** 经验上限 */
	public long maxExp;
	/** 魅力值 */
	public long charm;
	/** 当前所在城镇ID */
	public int sceneId;
	/** gameview首次充值记录 */
	public string gvfirst;
	/** 0 大礼包 1 百家乐 2 德州 3 老虎 */
	public string newguide;
	/** 视频观看次数 */
	public int watchNum;
	/** 国籍 */
	public string countries;
	/** 排行榜积分 */
	public long integral;
	/** 新手大礼包:1已购买，0 未购买  */
	public int newGuyGift;
	/** 1 当天已经显示，0 当天未显示 */
	public int todayView;
	/** 新手的进度({classicCompleted:0, niuniuCompleted:0, showhandCompleted:0, redblackCompleted:0}) */
	public string bazooNewGuyProcess;
	/** 无双吹牛 用户金币的数量 */
	public long bazooGold;
	/** 无双吹牛   用户所在的房间 */
	public string bazooRoom;
}