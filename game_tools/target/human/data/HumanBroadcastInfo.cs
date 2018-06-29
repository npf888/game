using System.Collections;

public class HumanBroadcastInfo
{
	/** 玩家id */
	public long playerId;
	/** 图片 */
	public string img;
	/** 等级 */
	public int level;
	/** 国籍 */
	public string countries;
	/** 玩家筹码 */
	public long gold;
	/** 名字 */
	public string name;
	/** 礼品的ID没有就是0 */
	public int giftId;
	/** 是否已经申请添加好友 0:未申请,1:已申请  */
	public int isRequest;
	/** 用户vip的等级 */
	public int vipLevel;
	/** 用户性别 1：男，2：女 */
	public int girlFlag;
}