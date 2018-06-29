using System.Collections;

public class BlackWhiteDiceInfo
{
	/** 用户ID */
	public long passportId;
	/** 0:未出局，1：已出局 */
	public int isOut;
	/** 每一把被移除 之前所有 的色子值 */
	public ArrayList diceValues;
	/** 被移除的色子 index的值 */
	public ArrayList removeDiceValues;
}