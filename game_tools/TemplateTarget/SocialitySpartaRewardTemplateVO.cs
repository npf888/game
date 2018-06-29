/**
 * SocialitySpartaRewardTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class SocialitySpartaRewardTemplateVO
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
		/** 1.第一奖池 2.第二奖池 3.第三奖池 4.第四奖池 */
	public int type;
		/** 奖池wild个数 */
	public int wildNum;
		/** 奖池奖励，单线下注额的倍数，数值除以100使用 */
	public int rewardNum;
		/** 攻城次数（N1<玩家≤N2）,起始位0 */
	public int wallTimes;
		/** 奖励百分比，奖励除以100 */
	public int rewardPercent;
	
}