/**
 * BounsRedgirlRewardTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class BounsRedgirlRewardTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 多语言描述 */
	public string langDesc;
		/** 老虎机类型 */
	public int slotsNum;
		/** 1：单线下注额倍数；2：后退2步 3.前进4步 4.宝箱奖励 */
	public int type;
		/** 单线下注额倍数,选取的次数(百分比，实际值除以100） */
	public int times;
	
}