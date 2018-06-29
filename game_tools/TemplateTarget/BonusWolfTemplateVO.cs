/**
 * BonusWolfTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class BonusWolfTemplateVO
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
		/** 老虎机机器编号 */
	public int slotsNum;
		/** 1：单次bouns中奖,2；两次bouns中奖,3；三次bouns中奖 */
	public int type;
		/** 1：单次bouns中奖，2；两次bouns中奖，3；三次bouns中奖 */
	public int bounsNum;
		/** 拼图完成后奖励，为单线下注额的倍数，数值除以100使用 */
	public double rewardNum;
	
}