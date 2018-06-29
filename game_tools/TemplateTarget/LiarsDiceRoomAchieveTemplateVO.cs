/**
 * LiarsDiceRoomAchieveTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class LiarsDiceRoomAchieveTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 名字id */
	public int nameId;
		/** 多语言描述id */
	public int descrip;
		/** 多语言描述 */
	public string langDesc;
		/** 字符串类型图标 */
	public string icons;
		/** 图标 */
	public int icon;
		/** 吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈 */
	public int modeType;
		/** 大的分类：1：胜利者，2：实践家，3：资本家 */
	public int bigType;
		/** 小的分类：1：胜场数，2：连胜数，3：玩的局数，4：钱数，5：段位数 */
	public int wayOfPlay;
		/** 应该满足的 条件 */
	public int condition;
		/** 应当给予的奖励 */
	public int rewardNum;
	
}