/**
 * BonusSpartaRewardTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class BonusSpartaRewardTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 名字id */
	public int nameId;
		/** 多语言描述id */
	public int descrip;
		/** 多语言描述 */
	public string langDesc;
		/** icon */
	public string icon;
		/** 老虎机类型 */
	public int slotsNum;
		/** 1.第一关 2.第二关 3.第三关 */
	public int type;
		/** 获胜次数 */
	public int winTimes;
		/** 获胜奖励：单线下注额的倍数，数值除以100使用 */
	public int rewardNum;
		/** 获胜概率（除以100） */
	public int value;
	
}