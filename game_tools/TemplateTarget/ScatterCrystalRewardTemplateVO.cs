/**
 * ScatterCrystalRewardTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class ScatterCrystalRewardTemplateVO
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
		/** 下限 */
	public int slotsNum;
		/** 1.单线下注额的倍数 2.免费转动次数 */
	public int type;
		/** 拼图完成后奖励，为单线下注额的倍数，数值除以100使用 */
	public int rewardNum;
		/** 权值 */
	public int value;
		/** 等级下限 */
	public int levelDown;
		/** 等级上限 */
	public int levelUp;
	
}