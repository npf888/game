/**
 * LiarsDiceRoomTaskTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class LiarsDiceRoomTaskTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 名字id */
	public int nameId;
		/** 多语言描述id */
	public int descrip;
		/** 多语言描述 */
	public string langDesc;
		/** 图标 */
	public string icons;
		/** 刷新类型  1:按天 ，2:按周 ，3:成就任务(成就不用刷新) */
	public int refreshtype;
		/** 吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈 */
	public int modeType;
		/** 大的分类：0:任务，成就：1：胜利者，2：实践家，3：资本家 */
	public int bigType;
		/** 玩的方式：（1：只要玩了就算，2：胜场数，3：连胜数，4：玩的局数，5：钱数，6：段位数） */
	public int wayOfPlay;
		/** 应该满足的 条件 */
	public int condition;
		/** 应当给予的奖励 */
	public int rewardNum;
	
}