using System.Collections;

public class ActivityData
{
	/** vip领取时间 */
	public long activityId;
	/** 标题 */
	public string title;
	/** 中文标题 */
	public string title_cn;
	/** 繁体标题 */
	public string title_tw;
	/** 描述 */
	public string desc;
	/** 中文描述 */
	public string desc_cn;
	/** 繁体描述 */
	public string desc_tw;
	/** 大厅图片,中文繁体 */
	public string hall_pic_tw;
	/** 大厅图片,中文简体 */
	public string hall_pic_cn;
	/** 大厅图片 */
	public string hall_pic;
	/** 图片 */
	public string pic;
	/** 图片描述 */
	public string pic_cn;
	/** 图片描述 */
	public string pic_tw;
	/** 跳转链接 */
	public int link;
	/** 规则数据 */
	public string ruleSre;
	/** 开始时间 */
	public long startTime;
	/** 结束时间 */
	public long endTime;
	/** 全服累计金币 或者 次数 或者其他 */
	public string fullValue;
}