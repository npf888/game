using System.Collections;

public class ItemInfoDataNew
{
	/** 道具来源 从哪里来的 */
	public long fromPlayerId;
	/** 道具ID */
	public int itemId;
	/** 道具类型 */
	public int itemType;
	/** 道具名称 */
	public string itemName;
	/** 道具价格 */
	public long price;
	/** 道具图片 */
	public string img;
	/** 正在使用中的 色钟的ID */
	public int usingClockItemId;
	/** 是否已经存在 0：否，1：是 */
	public int isExist;
}