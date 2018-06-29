using System.Collections;

public class FriendDetailInfoData
{
	/** 玩家id */
	public long playerId;
	/** 名字 */
	public string name;
	/** 图片 */
	public string img;
	/** 礼物时间 */
	public long giftTime;
	/** 筹码 */
	public long gold;
	/** 等级 */
	public long level;
	/** facebook */
	public int facebook;
	/** 性别 */
	public int sex;
	/** 国籍 */
	public string countries;
	/** 是否在游戏中 1 在游戏中 0 没有在 */
	public int isGame;
	/** 玩家状态 1 在线 0 不在线 */
	public int playerState;
	/** 下线时间点 */
	public long offlineTime;
	/** VIP等级  */
	public int vipLevel;
	/** 是否已经邀请加入俱乐部 */
	public int alreadyInvateClub;
	/** 是否已经加入俱乐部未加入: 0  已加入: 1    */
	public int alreadyJoinClub;
}