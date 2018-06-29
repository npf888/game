using System.Collections;

public class ClubNoteUnit
{
	/** 留言id */
	public string noteId;
	/** 玩家id */
	public long playerId;
	/** 玩家名字 */
	public string playerName;
	/** 头像 */
	public string img;
	/** 国籍 */
	public string guoji;
	/** 等级 */
	public long level;
	/** 类型 0 常规 1 礼物 */
	public int noteType;
	/** 内容 */
	public string content;
	/** 礼物id */
	public int giftId;
	/** 1已经领取 0 未领取 */
	public int alreadyGet;
	/** 职务 1 主席  2副主席 3主管4成员 */
	public int zhiwu;
	/** 创建时间 */
	public long time;
}