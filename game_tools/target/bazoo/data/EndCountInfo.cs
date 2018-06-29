using System.Collections;

public class EndCountInfo
{
	/** 用户ID */
	public long passportId;
	/** 所翻的倍数 */
	public int multiple;
	/** 输赢 多少钱  整数 是 赢钱，负数 是输钱 */
	public long gold;
	/** 牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等 */
	public string name;
	/** 摇出色子的值得集合 */
	public ArrayList diceValues;
	/** 赢得人的ID */
	public ArrayList winPassportId;
	/** 倍数 */
	public ArrayList winMultiple;
	/** 用户 自己的总金币 */
	public long personTotalGold;
}